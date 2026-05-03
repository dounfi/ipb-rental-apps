package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.ActivityMyItemsBinding;
import java.util.ArrayList;
import java.util.List;

public class MyItemsActivity extends AppCompatActivity {

    private ActivityMyItemsBinding binding;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Status bar setup
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.parseColor("#F5F0EB"));

        setupRecyclerView();
        loadSampleData();

        binding.btnAddItem.setOnClickListener(v -> {
            AddItemBottomSheet bottomSheet = new AddItemBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), "AddItemBottomSheet");
        });

        // Bottom Nav setup
        binding.bottomNav.setSelectedItemId(R.id.nav_profile);
    }

    private void setupRecyclerView() {
        adapter = new ItemAdapter();
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvItems.setAdapter(adapter);
        
        // Bottom padding to avoid navigation bar overlap
        int paddingBottom = (int) getResources().getDimension(R.dimen.recycler_bottom_padding);
        binding.rvItems.setPadding(0, 0, 0, paddingBottom);
        binding.rvItems.setClipToPadding(false);
    }

    private void loadSampleData() {
        List<Item> list = new ArrayList<>();
        // Added rating string (6th argument) to match the Item constructor requirement (11 arguments total)
        list.add(new Item("1", "Sony Alpha A7 III", "Fotografi", R.drawable.ic_logo_placeholder, "Tersedia", "4.8", "12x disewa", "Rp 85.000/hari", "42.500", "Sangat Baik", "Sensor full-frame 24.2MP."));
        list.add(new Item("2", "MacBook Pro 14\"", "Elektronik", R.drawable.ic_logo_placeholder, "Disewa", "4.9", "9x disewa", "Rp 120.000/hari", "60.000", "Baik", "Apple M1 Pro chip."));
        list.add(new Item("3", "Sony WH-1000XM5", "Audio", R.drawable.ic_logo_placeholder, "Tersedia", "4.7", "20x disewa", "Rp 30.000/hari", "15.000", "Sangat Baik", "Industry-leading noise canceling."));
        adapter.updateList(list);
    }
}
