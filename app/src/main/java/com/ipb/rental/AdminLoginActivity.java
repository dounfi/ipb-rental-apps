package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ipb.rental.databinding.ActivityAdminLoginBinding;

public class AdminLoginActivity extends AppCompatActivity {

    private ActivityAdminLoginBinding binding;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                binding.etEmail.setError("Email tidak boleh kosong");
                return;
            }

            if (password.isEmpty()) {
                binding.etPassword.setError("Password tidak boleh kosong");
                return;
            }

            // Validasi hardcoded
            if (email.equals("admin@apps.ipb.ac.id") && password.equals("admin123")) {
                Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AdminLoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBackToUser.setOnClickListener(v -> {
            Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.btnTogglePassword.setImageResource(R.drawable.ic_explore); // Using ic_explore as placeholder for hidden, actually should be eye icon
        } else {
            binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            binding.btnTogglePassword.setImageResource(R.drawable.ic_profile); // Using ic_profile as placeholder for visible, actually should be eye-off icon
        }
        isPasswordVisible = !isPasswordVisible;
        binding.etPassword.setSelection(binding.etPassword.getText().length());
    }
}
