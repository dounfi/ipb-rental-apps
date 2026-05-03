package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ipb.rental.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    public ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddItem.setOnClickListener(v -> {
            AddItemBottomSheetFragment bottomSheet = new AddItemBottomSheetFragment();
            bottomSheet.show(getSupportFragmentManager(), "AddItemBottomSheet");
        });

        // Bottom Navigation Logic
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_explore) {
                startActivity(new Intent(this, ExploreActivity.class));
                return true;
            }
            return false;
        });
    }
}