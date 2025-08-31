package com.fin.sight.api.service;

import com.fin.sight.api.entities.Accounts;
import com.fin.sight.api.repository.AccountsRepository;
import com.fin.sight.common.dto.CreateAccountRequest;
import com.fin.sight.common.dto.CreateAccountResponse;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountsService {
    private final JwtUtils jwtUtils;
    private final AccountsRepository accountsRepository;

    /**
     * Method to create an account.
     *
     * @param request:  the create account request.
     * @param apiToken: Service API Token for authentication.
     * @return the {@link ResponseEntity} containing the account creation response.
     */
    public ResponseEntity<?> createAccount(@NotNull final CreateAccountRequest request, @NotNull final String apiToken) {
        try {
            jwtUtils.verifyApiToken(apiToken);
            Accounts accounts = new Accounts();
            accounts.setAccountName(request.accountName());
            accounts.setAccountType(request.accountType());
            accounts.setUserGuid(request.userGuid());
            accountsRepository.save(accounts);
            List<Accounts> accountsList = accountsRepository.findAccountsByUserGuid(request.userGuid());
            return ResponseGenerator.generateSuccessResponse(accountsList, HttpStatus.CREATED);
        } catch (InvalidTokenException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid X-API-KEY");
        } catch (Exception e) {
            log.error("Error occurred while creating account: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    /**
     * Method to get account details.
     *
     * @param apiToken: the X-API-KEY for authentication.
     * @param userGuid: the user guid for which the Account is to be fetched.
     * @return the {@link ResponseEntity} containing the account details.
     */
    public ResponseEntity<?> getAccountDetails(@NotNull final String apiToken, String userGuid) {
        try {
            jwtUtils.verifyApiToken(apiToken);
            List<Accounts> accounts = accountsRepository.findAccountsByUserGuid(userGuid);
            if (accounts == null) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.NOT_FOUND, "Account not found");
            }
            return ResponseGenerator.generateSuccessResponse(accounts, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid User token");
        } catch (Exception e) {
            log.error("Error occurred while fetching account details: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}
