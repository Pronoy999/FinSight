package com.fin.sight.common.utils;

import com.fin.sight.common.dto.CreateUserRequest;
import com.fin.sight.common.dto.LoginUserRequest;
import com.fin.sight.common.exceptions.InvalidRequestException;

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
        if (Stream.of(request.firstName(), request.lastName(), request.emailId(), request.phoneNumber(), request.age(), request.password())
                .anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
        if (request.age() < 18 || request.age() > 100) {
            throw new InvalidRequestException("Please enter a valid age");
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
        if (Stream.of(request.emailId(), request.password()).anyMatch(Objects::isNull)) {
            throw new InvalidRequestException("Mandatory fields can't be empty");
        }
        if (request.emailId().length() < 5 || request.password().length() < 5) {
            throw new InvalidRequestException("Please enter a valid email id & password");
        }
    }
}
