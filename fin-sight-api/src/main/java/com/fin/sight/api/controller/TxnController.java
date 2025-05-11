package com.fin.sight.api.controller;

import ch.qos.logback.core.util.StringUtil;
import com.fin.sight.api.service.TransactionService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateTxnLogRequest;
import com.fin.sight.common.dto.GetTxnRequest;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.ResponseGenerator;
import com.fin.sight.common.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = Constants.TXN_LOG_PATH)
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class TxnController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTxnLog(@RequestBody CreateTxnLogRequest request, @RequestHeader Map<String, String> httpHeaders) {
        try {
            String userToken = httpHeaders.get(Constants.USER_TOKEN_HEADER);
            Validator.validateTranLogRequest(request);
            if (StringUtil.isNullOrEmpty(userToken)) {
                throw new InvalidTokenException("User token is missing");
            }
            log.info("Creating transaction log.");
            return transactionService.createTransLog(request, userToken);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (InvalidRequestException e) {
            log.error("Invalid request: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("Error creating transaction log: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @PostMapping(path = Constants.TXN_SEARCH_PATH)
    public ResponseEntity<?> searchTxns(@RequestBody final GetTxnRequest request, @RequestHeader Map<String, String> httpHeaders) {
        try {
            String userToken = httpHeaders.get(Constants.USER_TOKEN_HEADER);
            if (StringUtil.isNullOrEmpty(userToken)) {
                throw new InvalidTokenException("User token is missing");
            }
            log.info("Searching transactions");
            return transactionService.getTxnsByFilter(request, userToken);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (InvalidRequestException e) {
            log.error("Invalid request: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("Error searching transactions: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @GetMapping(path = Constants.TXN_SEARCH_BY_ID_PATH)
    public ResponseEntity<?> getTxnDetails(@PathVariable("id") int id, @RequestHeader Map<String, String> httpHeaders) {
        try {
            String userToken = httpHeaders.get(Constants.USER_TOKEN_HEADER);
            if (StringUtil.isNullOrEmpty(userToken)) {
                throw new InvalidTokenException("User token is missing");
            }
            log.info("Fetching transaction details for ID: {} and user token: {}", id, userToken);
            return transactionService.getTxnDetails(id, userToken);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (InvalidRequestException e) {
            log.error("Invalid request: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("Error fetching transaction details: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
}
