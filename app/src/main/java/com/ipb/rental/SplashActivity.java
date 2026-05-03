package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash);

        ImageView ivLogo = findViewById(R.id.iv_logo);
        ImageView ivTextLogo = findViewById(R.id.iv_text_logo);

        // Sembunyikan semua elemen awal
        ivLogo.setVisibility(View.INVISIBLE);
        ivTextLogo.setVisibility(View.INVISIBLE);

        // Load animasi
        Animation popUp = AnimationUtils.loadAnimation(this, R.anim.splash_pop_up);
        Animation fadeUp = AnimationUtils.loadAnimation(this, R.anim.splash_fade_up);

        // 1. Logo pop up setelah 300ms
        ivLogo.postDelayed(() -> {
            ivLogo.setVisibility(View.VISIBLE);
            ivLogo.startAnimation(popUp);
        }, 300);

        // 2. Text Logo fade up setelah logo muncul
        ivTextLogo.postDelayed(() -> {
            ivTextLogo.setVisibility(View.VISIBLE);
            ivTextLogo.startAnimation(fadeUp);
        }, 600);

        // 3. Kembali ke alur awal: Splash -> Onboarding
        ivTextLogo.postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}
