package com.fin.sight.api.service;

import com.fin.sight.api.entities.TranLog;
import com.fin.sight.api.repository.TranLogRepository;
import com.fin.sight.common.dto.CreateTxnLogRequest;
import com.fin.sight.common.dto.CreateTxnLogResponse;
import com.fin.sight.common.dto.GetTxnRequest;
import com.fin.sight.common.dto.JwtData;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.JwtUtils;
import com.fin.sight.common.utils.QueryUtils;
import com.fin.sight.common.utils.ResponseGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"LoggingSimilarMessage", "SqlSourceToSinkFlow"})
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TranLogRepository tranLogRepository;
    private final JwtUtils jwtUtils;
    private final TxnCategoryService txnCategoryService;
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Create a transaction log
     *
     * @param request:   the request object containing the transaction details
     * @param userToken: the JWT token of the user
     * @return ResponseEntity: the response entity containing the status and message
     */
    public ResponseEntity<?> createTransLog(@NotNull final CreateTxnLogRequest request, @NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            if (userData == null) {
                log.error("Invalid token: {}", userToken);
                return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            TranLog tranLog = new TranLog();

            tranLog.setYear(request.year());
            tranLog.setMonth(request.month());
            tranLog.setDate(request.date());
            tranLog.setUserGuid(userData.guid());
            tranLog.setTxnCategory(txnCategoryService.getTxnCategoryById(request.txnCategoryId()));
            tranLog.setTxnSubCategory(txnCategoryService.getTxnSubCategoryById(request.txnSubCategoryId()));
            tranLog.setTxnAmount(request.txnAmount());
            if (request.recurringId() > 0) {
                tranLog.setRecurringId(request.recurringId());
            }
            tranLog.setTransferType(request.transferType());
            tranLog.setTxnFrequency(request.txnFrequency());
            tranLog.setAccountId(request.accountId());

            tranLog = tranLogRepository.save(tranLog);

            CreateTxnLogResponse response = new CreateTxnLogResponse(tranLog.getId());
            return ResponseGenerator.generateSuccessResponse(response, HttpStatus.CREATED);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", userToken);
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            log.error("Error creating transaction log: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating transaction log");
        }
    }

    /**
     * Method to fetch the transactions.
     *
     * @param request:   the request.
     * @param userToken: The user token for which the transactions are to be fetched.
     * @return the list of transactions.
     */
    public ResponseEntity<?> getTxnsByFilter(@NotNull final GetTxnRequest request, @NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            if (userData == null) {
                log.error("Invalid token: {}", userToken);
                return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            String rawQuery = QueryUtils.getTxnFilterQuery(request.filters(), userData.guid());
            Query query = entityManager.createNativeQuery(rawQuery, TranLog.class);
            List<?> results = query.getResultList();
            return ResponseGenerator.generateSuccessResponse(results, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", userToken);
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            log.error("Error fetching transaction logs: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching transaction logs");
        }
    }

    /**
     * Method to get the Transcaction details for a given transaction ID.
     *
     * @param txnId:     the transaction ID for which the details are to be fetched.
     * @param userToken: the user token for which the transaction details are to be fetched.
     * @return the transaction details.
     */
    public ResponseEntity<?> getTxnDetails(@NotNull final int txnId, @NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            if (userData == null) {
                log.error("Invalid token: {}", userToken);
                return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
            }
            TranLog tranLog = tranLogRepository.findByIdAndUserGuid(txnId, userData.guid());
            if (tranLog == null) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.NOT_FOUND, "Transaction not found");
            }
            return ResponseGenerator.generateSuccessResponse(tranLog, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", userToken);
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            log.error("Error fetching transaction details: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching transaction details");
        }
    }
}
