package com.fin.sight.api.controller;

import com.fin.sight.api.service.AccountsService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateAccountRequest;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.utils.ResponseGenerator;
import com.fin.sight.common.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("accountsController")
@RequestMapping(Constants.ACCOUNT_PATH)
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request, @RequestHeader Map<String, String> requestHeaders) {
        if (requestHeaders.isEmpty()) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Missing User token");
        }
        try {
            Validator.validateAccountRequest(request);
            String userToken = requestHeaders.get(Constants.USER_TOKEN_HEADER);
            return accountsService.createAccount(request, userToken);
        } catch (InvalidRequestException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAccountDetails(@RequestHeader Map<String, String> requestHeaders) {
        if (requestHeaders.isEmpty()) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Missing User token");
        }
        String userToken = requestHeaders.get(Constants.USER_TOKEN_HEADER);
        return accountsService.getAccountDetails(userToken);
    }
}
