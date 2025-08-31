package com.fin.sight.common.utils;

import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.*;
import com.fin.sight.common.exceptions.InvalidRequestException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Validator {
    /**
     * Method to validate the User Request.
     *
     * @param request: the request to be validated.
     */
    public static void validateUserRegisterDetails(CreateUserRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Request body can't be empty");
        }
        if (Stream.of(request.getFirstName(),
                        request.getLastName(),
                        request.getGoogleOAuthToken(),
                        request.getEmailId())
                .anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
    }

    /**
     * Method to validate the login user details.
     *
     * @param request: the user request to validate.
     */
    public static void validateLoginUserRequest(LoginUserRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Request body can't be empty");
        }
        if (Stream.of(request.emailId(), request.password()).anyMatch(Objects::isNull) && Stream.of(request.googleAuthToken()).anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
        if (request.emailId().length() < 5 || request.password().length() < 5) {
            throw new InvalidRequestException("Please enter a valid email id & password");
        }
    }

    /**
     * Method to validate the account request.
     *
     * @param request: the account request to validate.
     */
    public static void validateAccountRequest(CreateAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Request body can't be empty");
        }
        if (Stream.of(request.accountName(), request.accountType(), request.userGuid()).anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
    }

    /**
     * Method to validate the Tran Log create request.
     *
     * @param request: the request to be validated.
     */
    public static void validateTranLogRequest(final CreateTxnLogRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Request body can't be empty");
        }
        if (Stream.of(request.txnCategoryId(), request.txnTime(), request.txnAmount(), request.userShare(), request.userGuid(),
                        request.date(), request.accountId())
                .anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
    }

    /**
     * Method to validate the Transaction search request.
     *
     * @param request: the request to be validated.
     */
    public static void validateTxnSearchRequest(final GetTxnRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidRequestException("Request body can't be empty");
        }
        if (Objects.nonNull(request.filters()) && !request.filters().isEmpty()) {
            List<Filters> filters = request.filters();
            filters.forEach(filter -> {
                if (!Constants.TXN_FILTER_FIELDS.contains(filter.fieldName())) {
                    throw new InvalidRequestException("Invalid filter field: " + filter.fieldName());
                }
            });
        }
    }
}
