package com.fin.sight.api.service;

import com.fin.sight.api.entities.Recurring;
import com.fin.sight.api.repository.RecurringRepository;
import com.fin.sight.common.dto.CreateRecurringRequest;
import com.fin.sight.common.dto.CreateRecurringResponse;
import com.fin.sight.common.dto.JwtData;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.JwtUtils;
import com.fin.sight.common.utils.ResponseGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("LoggingSimilarMessage")
@Service
@RequiredArgsConstructor
@Slf4j
public class RecurringService {
    private final RecurringRepository recurringRepository;
    private final JwtUtils jwtUtils;

    /**
     * Method to create the recurring transactions for a user.
     *
     * @param request:   the recurring transaction request.
     * @param userToken: the user for which the recurring transaction is created.
     * @return the {@link ResponseEntity} containing the response for the recurring transaction creation.
     */
    public ResponseEntity<?> createRecurringTxn(@NotNull final CreateRecurringRequest request, @NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            String userGuid = userData.guid();

            Recurring recurring = new Recurring();
            recurring.setAccountId(request.accountId());
            recurring.setFrequency(request.frequency());
            recurring.setEstimatedAmount(request.estimatedAmount());
            recurring.setNature(request.nature());
            recurring.setType(request.type());
            recurring.setTransferType(request.transferType());
            recurring.setUserGuid(userGuid);

            recurring = recurringRepository.save(recurring);

            log.info("Recurring transaction created successfully for user: {}", userGuid);
            CreateRecurringResponse response = new CreateRecurringResponse(recurring.getId());
            return ResponseGenerator.generateSuccessResponse(response, HttpStatus.CREATED);

        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    /**
     * Method to get the recurring deposits for a user.
     *
     * @param userToken: the user token for which the recurring transactions are fetched.
     * @return the {@link ResponseEntity} containing the response for the recurring transactions.
     */
    public ResponseEntity<?> getRecurringForUser(@NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            String userGuid = userData.guid();

            log.info("Fetching recurring transactions for user: {}", userGuid);
            List<Recurring> recurrings = recurringRepository.findAllWithAccountByUserGuid(userGuid);
            if (recurrings.isEmpty()) {
                log.info("No recurring transactions found for user: {}", userGuid);
                return ResponseGenerator.generateSuccessResponse(List.of(), HttpStatus.OK);
            }
            return ResponseGenerator.generateSuccessResponse(recurrings, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            log.error("Error : {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
