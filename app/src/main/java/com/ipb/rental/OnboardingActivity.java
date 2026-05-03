package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ConstraintLayout btnNext;
    private TextView tvSkip, tvBtnText;
    private LinearLayout llDots;
    private ImageView[] dots;
    private final int totalPages = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        btnNext   = findViewById(R.id.btn_next);
        tvSkip    = findViewById(R.id.tv_skip);
        llDots    = findViewById(R.id.ll_dots);
        tvBtnText = findViewById(R.id.tv_btn_text);

        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(
            "Sewa Apa Saja\ndi Kampus IPB 🎓",
            "Kamera, laptop, alat olahraga, proyektor, dan ratusan item keren dari sesama mahasiswa IPB tersedia untukmu!",
            R.drawable.on_boarding_1
        ));
        items.add(new OnboardingItem(
            "Aman &\nTerpercaya 🔒",
            "Semua pengguna diverifikasi mahasiswa IPB. Deposit tersimpan aman sampai transaksi benar-benar selesai.",
            R.drawable.on_boarding_2
        ));
        items.add(new OnboardingItem(
            "Chat, Negosiasi,\ndan Sepakat 💬",
            "Hubungi pemilik langsung, negosiasi harga, dan selesaikan transaksi dalam satu platform yang mudah.",
            R.drawable.on_boarding_3
        ));

        OnboardingAdapter adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);
        setupDots(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setupDots(position);
                updateButton(position);
            }
        });

        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current < totalPages - 1) {
                viewPager.setCurrentItem(current + 1);
            } else {
                goToLogin();
            }
        });

        tvSkip.setOnClickListener(v -> goToLogin());
    }

    private void setupDots(int currentPage) {
        llDots.removeAllViews();
        dots = new ImageView[totalPages];
        for (int i = 0; i < totalPages; i++) {
            dots[i] = new ImageView(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    i == currentPage ? dpToPx(24) : dpToPx(8), 
                    dpToPx(8)
            );
            p.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            dots[i].setLayoutParams(p);
            dots[i].setBackgroundResource(i == currentPage ? R.drawable.bg_dot_active : R.drawable.bg_dot_inactive);
            llDots.addView(dots[i]);
        }
    }

    private void updateButton(int position) {
        if (position == totalPages - 1) {
            tvBtnText.setText("MULAI");
            tvSkip.setVisibility(View.INVISIBLE);
        } else {
            tvBtnText.setText("LANJUT");
            tvSkip.setVisibility(View.VISIBLE);
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
