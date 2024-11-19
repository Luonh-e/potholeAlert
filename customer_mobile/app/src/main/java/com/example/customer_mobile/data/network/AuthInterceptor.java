package com.example.customer_mobile.data.network;

import android.content.Context;

import com.example.customer_mobile.util.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final SharedPrefManager sharedPrefManager;

    public AuthInterceptor(Context context) {
        sharedPrefManager = new SharedPrefManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sharedPrefManager.getAccessToken();
        Request request = chain.request();
        if (token != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }
        return chain.proceed(request);
    }
}
