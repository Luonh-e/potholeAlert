package com.example.customer_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_mobile.R;
import com.example.customer_mobile.data.model.EmailRequest;
import com.example.customer_mobile.data.network.ApiClient;
import com.example.customer_mobile.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button goToOtpVerify;
    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backButton = findViewById(R.id.backButton);
        goToOtpVerify = findViewById(R.id.goToOtpVerify);
        emailInput = findViewById(R.id.emailInput);

        backButton.setOnClickListener(view -> finish());

        goToOtpVerify.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            } else {
                sendOtpToEmail(email);
            }
        });
    }

    private void sendOtpToEmail(String email) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        EmailRequest emailRequest = new EmailRequest(email);

        apiService.sendOtp(emailRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "OTP sent to your email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, OtpVerifyActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send OTP. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ForgotPassword", "API Error: " + t.getMessage());
            }
        });
    }

}
