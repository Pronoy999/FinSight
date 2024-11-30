package com.fin.sight.common.dto;

public record CreateUserRequest(String firstName, String lastName, String emailId, String phoneNumber, int age,
                                String password) {
}
