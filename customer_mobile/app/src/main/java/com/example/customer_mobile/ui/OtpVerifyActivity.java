package com.example.customer_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customer_mobile.R;

public class OtpVerifyActivity extends AppCompatActivity {

    ImageView backButton;
    Button goToResetPassword;
    EditText otp1, otp2, otp3, otp4, otp5, otp6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_verify);

        // Ánh xạ các view
        backButton = findViewById(R.id.backButton);
        goToResetPassword = findViewById(R.id.goToResetPassword);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        goToResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpVerifyActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setupOtpInputs();
    }

    private void setupOtpInputs() {
        otp1.addTextChangedListener(new GenericTextWatcher(otp2));
        otp2.addTextChangedListener(new GenericTextWatcher(otp3));
        otp3.addTextChangedListener(new GenericTextWatcher(otp4));
        otp4.addTextChangedListener(new GenericTextWatcher(otp5));
        otp5.addTextChangedListener(new GenericTextWatcher(otp6));
        otp6.addTextChangedListener(new GenericTextWatcher(null)); // Ô cuối không có ô tiếp theo
    }

    private static class GenericTextWatcher implements TextWatcher {
        final EditText nextEditText;

        public GenericTextWatcher(EditText nextEditText) {
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}
