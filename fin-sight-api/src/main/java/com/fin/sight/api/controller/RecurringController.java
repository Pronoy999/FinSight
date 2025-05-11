package com.fin.sight.api.controller;

import com.fin.sight.api.service.RecurringService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateRecurringRequest;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.utils.ResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = Constants.RECURRING_PATH)
@RequiredArgsConstructor
public class RecurringController {
    private final RecurringService recurringService;

    @PostMapping
    public ResponseEntity<?> createRecurringTxn(@RequestBody CreateRecurringRequest request, @RequestHeader Map<String, String> httpHeaders) {
        try {
            String userToken = httpHeaders.get("user-token");
            if (userToken == null || userToken.isEmpty()) {
                throw new InvalidRequestException("User token is missing");
            }
            return recurringService.createRecurringTxn(request, userToken);
        } catch (InvalidRequestException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
        }
    }

    @GetMapping
    public ResponseEntity<?> getRecurringTxn(@RequestHeader Map<String, String> httpHeaders) {
        try {
            String userToken = httpHeaders.get("user-token");
            if (userToken == null || userToken.isEmpty()) {
                throw new InvalidRequestException("User token is missing");
            }
            return recurringService.getRecurringForUser(userToken);
        } catch (InvalidRequestException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
        }
    }
}
