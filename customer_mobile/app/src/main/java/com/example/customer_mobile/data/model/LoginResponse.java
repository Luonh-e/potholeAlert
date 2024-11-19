package com.example.customer_mobile.data.model;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private User user;
    private String msg;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }
}

