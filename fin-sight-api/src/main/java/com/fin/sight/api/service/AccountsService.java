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
     * @param request:   the create account request.
     * @param userToken: the user token for which the Account is to be created.
     * @return the {@link ResponseEntity} containing the account creation response.
     */
    public ResponseEntity<?> createAccount(@NotNull final CreateAccountRequest request, @NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            if (userData == null) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid User token");
            }
            Accounts accounts = new Accounts();
            accounts.setAccountName(request.accountName());
            accounts.setAccountType(request.accountType());
            accounts.setUserGuid(userData.guid());
            accounts = accountsRepository.save(accounts);
            return ResponseGenerator.generateSuccessResponse(
                    new CreateAccountResponse(accounts.getAccountId(), accounts.getAccountName()),
                    HttpStatus.CREATED);
        } catch (InvalidTokenException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid User token");
        } catch (Exception e) {
            log.error("Error occurred while creating account: {}", e.getMessage());
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    /**
     * Method to get account details.
     *
     * @param userToken: the user token for which the Account is to be fetched.
     * @return the {@link ResponseEntity} containing the account details.
     */
    public ResponseEntity<?> getAccountDetails(@NotNull final String userToken) {
        try {
            JwtData userData = jwtUtils.decodeJwt(userToken);
            if (userData == null) {
                return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid User token");
            }
            List<Accounts> accounts = accountsRepository.findAccountsByUserGuid(userData.guid());
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
