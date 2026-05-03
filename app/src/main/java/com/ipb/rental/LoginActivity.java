package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvError, tvGotoSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail     = findViewById(R.id.et_email);
        etPassword  = findViewById(R.id.et_password);
        btnLogin    = findViewById(R.id.btn_login);
        tvError     = findViewById(R.id.tv_error);
        tvGotoSignUp = findViewById(R.id.tv_goto_signup);

        btnLogin.setOnClickListener(v -> validateAndLogin());
        tvGotoSignUp.setOnClickListener(v ->
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class))
        );
    }

    private void validateAndLogin() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        tvError.setVisibility(View.GONE);

        if (TextUtils.isEmpty(email)) {
            showError("Email tidak boleh kosong"); return;
        }
        if (!email.contains("@")) {
            showError("Format email tidak valid"); return;
        }
        if (TextUtils.isEmpty(password)) {
            showError("Password tidak boleh kosong"); return;
        }
        if (password.length() < 6) {
            showError("Password minimal 6 karakter"); return;
        }

        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show();
        
        // Alur: Login -> MainActivity (Fragment Container)
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }
}