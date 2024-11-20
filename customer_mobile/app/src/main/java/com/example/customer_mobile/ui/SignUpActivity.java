package com.example.customer_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.customer_mobile.R;
import com.example.customer_mobile.data.model.LoginRequest;
import com.example.customer_mobile.data.model.LoginResponse;
import com.example.customer_mobile.data.model.RegisterRequest;
import com.example.customer_mobile.data.model.RegisterResponse;
import com.example.customer_mobile.data.network.ApiClient;
import com.example.customer_mobile.data.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText uname;
    private EditText username;
    private EditText password;
    private Button signUpButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uname = findViewById(R.id.name);
//        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signupButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String name = uname.getText().toString().trim();


                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter email and password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                register(name, email, pass);
            }
        });
    }

    private void register(String name, String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RegisterRequest registerRequest = new RegisterRequest(name, email, password);

        apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(SignUpActivity.this, "Registration successful! Welcome, "
                            + response.body().getUser().getName(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign up failed: User already exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Login", "Error: ", t);
            }
        });
    }
}