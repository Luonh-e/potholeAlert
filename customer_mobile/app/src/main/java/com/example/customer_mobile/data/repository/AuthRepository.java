package com.example.customer_mobile.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.customer_mobile.data.network.RetrofitClient;
import com.example.customer_mobile.data.model.LoginRequest;
import com.example.customer_mobile.data.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final Context context;

    public AuthRepository(Context context) {
        this.context = context;
    }

    public LiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();
        LoginRequest request = new LoginRequest(email, password);
        
        RetrofitClient.getApiService(context).login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Gửi dữ liệu trả về nếu thành công
                    Log.d("data", "dữ liệu thành công");
                    liveData.postValue(response.body());
                } else {
                    // Xử lý lỗi API trả về
                    Log.d("data", "dữ liệu không thành công");
                    LoginResponse errorResponse = new LoginResponse();
                    liveData.postValue(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LoginResponse errorResponse = new LoginResponse();
                liveData.postValue(errorResponse);
            }
        });

        return liveData;
    }
}
