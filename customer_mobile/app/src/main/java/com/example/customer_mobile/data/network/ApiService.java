package com.example.customer_mobile.data.network;

import com.example.customer_mobile.data.model.LoginRequest;
import com.example.customer_mobile.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

