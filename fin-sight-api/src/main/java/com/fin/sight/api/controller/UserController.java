package com.fin.sight.api.controller;

import com.fin.sight.api.service.UserService;
import com.fin.sight.common.Constants;
import com.fin.sight.common.dto.CreateUserRequest;
import com.fin.sight.common.dto.CreateUserResponse;
import com.fin.sight.common.exceptions.InvalidRequestException;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            Validator.validateUserDetails(request);
            CreateUserResponse response = userService.registerUser(request);
            if (Objects.nonNull(response)) {
                return ResponseGenerator.generateUserRegisterResponse(response.guid(), response.jwt());
            } else {
                return ResponseGenerator.generateFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            }
        } catch (InvalidRequestException e) {
            return ResponseGenerator.generateFailureResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
