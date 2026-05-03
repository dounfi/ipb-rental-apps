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

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView tvError, tvBack, tvGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName            = findViewById(R.id.et_name);
        etEmail           = findViewById(R.id.et_email);
        etPassword        = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnSignUp         = findViewById(R.id.btn_signup);
        tvError           = findViewById(R.id.tv_error);
        tvBack            = findViewById(R.id.tv_back);
        tvGotoLogin       = findViewById(R.id.tv_goto_login);

        btnSignUp.setOnClickListener(v -> validateAndRegister());
        tvBack.setOnClickListener(v -> finish());
        tvGotoLogin.setOnClickListener(v -> finish());
    }

    private void validateAndRegister() {
        String name     = etName.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm  = etConfirmPassword.getText().toString().trim();
        tvError.setVisibility(View.GONE);

        if (TextUtils.isEmpty(name)) {
            showError("Nama tidak boleh kosong"); return;
        }
        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            showError("Masukkan email yang valid"); return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            showError("Password minimal 8 karakter"); return;
        }
        if (!password.equals(confirm)) {
            showError("Konfirmasi password tidak cocok"); return;
        }

        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
        
        // Alur: Sign Up -> Login
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }
}