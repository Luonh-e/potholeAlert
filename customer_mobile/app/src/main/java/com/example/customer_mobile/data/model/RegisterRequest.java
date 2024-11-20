package com.example.customer_mobile.data.model;

public class RegisterRequest {
    private String name;
    // private String phone;
    private String email;
    private String password;

    public RegisterRequest(String name, String email, String password) {
        this.name = name;
        // this.phone = phone;
        this.email = email;
        this.password = password;
    }
}

