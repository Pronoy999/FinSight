package com.fin.sight.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private int age;
    private String password;
    private String googleOAuthToken;
}
