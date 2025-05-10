package com.fin.sight.common.utils;

import com.fin.sight.common.dto.CreateUserResponse;
import com.fin.sight.common.dto.ErrorResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {
    /**
     * Method to generate the Successful User registration response.
     *
     * @param guid: the guid generated for the user.
     * @param jwt:  the JWT for the user.
     * @return the {@link ResponseEntity} containing the user registration response for success API response.
     */
    public static ResponseEntity<CreateUserResponse> generateUserRegisterResponse(@NotNull final String guid, @NotNull final String jwt) {
        CreateUserResponse response = new CreateUserResponse(guid, jwt);
        return ResponseEntity.ok(response);
    }


    /**
     * Method to generate Failure Response.
     *
     * @param status:  the error message.
     * @param message: the {@link HttpStatus} code.
     * @return the ResponseEntity object with appropriate Error code.
     */
    public static ResponseEntity<?> generateFailureResponse(@NotNull final HttpStatus status, final String message) {
        ErrorResponse response = new ErrorResponse(status.toString(), message);
        return switch (status) {
            case BAD_REQUEST -> ResponseEntity.badRequest().body(response);
            case INTERNAL_SERVER_ERROR -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            case UNAUTHORIZED -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            default -> ResponseEntity.status(status).body(response);
        };
    }

    /**
     * Method to generate a successful response.
     *
     * @param response: the response body.
     * @param status:   the HTTP status code.
     * @return the ResponseEntity.
     */
    public static ResponseEntity<?> generateSuccessResponse(@NotNull final Object response, HttpStatus status) {
        return ResponseEntity.status(status).body(response);
    }
}
