package com.fin.sight.api.controller;

import com.fin.sight.api.service.UserService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateUserRequest;
import com.fin.sight.common.dto.CreateUserResponse;
import com.fin.sight.common.dto.LoginUserRequest;
import com.fin.sight.common.dto.LoginUserResponse;
import com.fin.sight.common.exceptions.InvalidCredentialsException;
import com.fin.sight.common.exceptions.InvalidRequestException;
import com.fin.sight.common.exceptions.InvalidTokenException;
import com.fin.sight.common.utils.ResponseGenerator;
import com.fin.sight.common.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController("userController")
@RequestMapping(Constants.USER_PATH)
@Slf4j
public class UserController {

    private final UserService userService;
    private final String TAG = UserController.class.getName();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            Validator.validateUserRegisterDetails(request);
            CreateUserResponse response = userService.registerUser(request);
            if (Objects.nonNull(response)) {
                return ResponseGenerator.generateUserRegisterResponse(response.guid(), response.jwt());
            }
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        } catch (InvalidRequestException e) {
            log.error("Error while registering user: ", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (InvalidTokenException e) {
            log.error("Invalid Google Token", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.UNAUTHORIZED, "Invalid Google OAuth token");
        } catch (Exception e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, Constants.GENERIC_ERROR_MESSAGE);
        }
    }

    @PostMapping(path = Constants.LOGIN_PATH)
    public ResponseEntity<?> loginUser(@RequestBody LoginUserRequest request) {
        try {
            Validator.validateLoginUserRequest(request);
            LoginUserResponse response = userService.loginUser(request);
            if (Objects.nonNull(response)) {
                return ResponseGenerator.generateUserRegisterResponse(response.userId(), response.jwtToken());
            }
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        } catch (InvalidCredentialsException | InvalidRequestException e) {
            log.error("Error while login user: ", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("Error while login user: ", e);
            return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, Constants.GENERIC_ERROR_MESSAGE);
        }
    }
}
