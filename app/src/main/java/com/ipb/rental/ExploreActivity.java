package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private List<Item> allProducts;
    private ExploreAdapter adapter;
    private TextView activeFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Setup RecyclerView
        RecyclerView rvExplore = findViewById(R.id.rv_explore);
        rvExplore.setLayoutManager(new GridLayoutManager(this, 2));

        // Data dummy menggunakan objek Item (bukan Product)
        allProducts = new ArrayList<>();
        allProducts.add(new Item("1", "Sony Alpha A7 III", "Fotografi", R.drawable.ic_box, "Tersedia", "4.9", "12", "85.000", "85k", "Bagus", "Deskripsi kamera Sony."));
        allProducts.add(new Item("2", "MacBook Pro 14\"", "Elektronik", R.drawable.ic_box, "Tersedia", "4.8", "8", "120.000", "120k", "Sangat Baik", "Deskripsi MacBook."));
        allProducts.add(new Item("3", "Sony WH-1000XM5", "Audio", R.drawable.ic_box, "Tersedia", "5.0", "20", "30.000", "30k", "Bagus", "Deskripsi headphone."));
        allProducts.add(new Item("4", "Portable Projector", "Elektronik", R.drawable.ic_box, "Tersedia", "4.7", "15", "50.000", "50k", "Baik", "Deskripsi proyektor."));
        allProducts.add(new Item("5", "GoPro Hero 12", "Fotografi", R.drawable.ic_box, "Tersedia", "4.7", "9", "65.000", "65k", "Bagus", "Deskripsi GoPro."));
        allProducts.add(new Item("6", "DJI Mini 4 Pro", "Drone", R.drawable.ic_box, "Tersedia", "4.9", "5", "150.000", "150k", "Sangat Baik", "Deskripsi drone DJI."));
        allProducts.add(new Item("7", "iPad Pro 11\"", "Elektronik", R.drawable.ic_box, "Tersedia", "4.8", "11", "75.000", "75k", "Bagus", "Deskripsi iPad."));
        allProducts.add(new Item("8", "Raket Badminton", "Lainnya", R.drawable.ic_box, "Tersedia", "4.7", "32", "10.000", "10k", "Baik", "Deskripsi raket."));

        // Perbaikan: Menambahkan listener sebagai argumen kedua
        adapter = new ExploreAdapter(allProducts, item -> {
            // Navigasi ke detail jika diperlukan
        });
        rvExplore.setAdapter(adapter);

        // Setup Filter Click Listeners
        setupFilter(findViewById(R.id.btn_filter_all), "Semua");
        setupFilter(findViewById(R.id.btn_filter_camera), "Fotografi");
        setupFilter(findViewById(R.id.btn_filter_elektronik), "Elektronik");
        setupFilter(findViewById(R.id.btn_filter_audio), "Audio");
        setupFilter(findViewById(R.id.btn_filter_drone), "Drone");

        activeFilter = findViewById(R.id.btn_filter_all);

        // Setup Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_explore);
        bottomNav.setSelectedItemId(R.id.nav_explore);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void setupFilter(TextView view, String category) {
        view.setOnClickListener(v -> {
            // Update UI chip states
            if (activeFilter != null) {
                activeFilter.setBackgroundResource(R.drawable.bg_chip_unselected);
                activeFilter.setTextColor(getResources().getColor(R.color.navy_blue));
            }
            view.setBackgroundResource(R.drawable.bg_chip_selected);
            view.setTextColor(getResources().getColor(R.color.white));
            activeFilter = view;

            // Filter logic
            filterByCategory(category);
        });
    }

    private void filterByCategory(String category) {
        if (category.equals("Semua")) {
            adapter.updateList(allProducts);
        } else {
            List<Item> filteredList = new ArrayList<>();
            for (Item p : allProducts) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    filteredList.add(p);
                }
            }
            adapter.updateList(filteredList);
        }
    }
}
