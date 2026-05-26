package com.sirest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    private String tokenType;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String token, String tokenType, String username) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
    }

}