package com.fin.sight.common.dto;

public record LoginUserRequest(String emailId, String password,String googleAuthToken) {
}
