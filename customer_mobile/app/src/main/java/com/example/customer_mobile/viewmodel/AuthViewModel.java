package com.example.customer_mobile.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.customer_mobile.data.model.LoginResponse;
import com.example.customer_mobile.data.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository(application.getApplicationContext());
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return authRepository.login(email, password);
    }
}
