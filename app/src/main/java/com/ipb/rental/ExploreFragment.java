package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private List<Item> allProducts;
    private ExploreAdapter adapter;
    private TextView activeFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvExplore = view.findViewById(R.id.recyclerViewExplore);
        
        // Setup LayoutManager
        rvExplore.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        
        // Initialize Data (Set imageRes to 0 to keep it empty as requested)
        allProducts = new ArrayList<>();
        allProducts.add(new Item("1", "Sony Alpha A7 III", "Fotografi", 0, "Tersedia", "4.9", "12", "85.000", "85k", "Bagus", "Deskripsi kamera Sony."));
        allProducts.add(new Item("2", "MacBook Pro 14\"", "Elektronik", 0, "Tersedia", "4.8", "8", "120.000", "120k", "Sangat Baik", "Deskripsi MacBook."));
        allProducts.add(new Item("3", "Sony WH-1000XM5", "Audio", 0, "Tersedia", "5.0", "20", "30.000", "30k", "Bagus", "Deskripsi headphone."));
        allProducts.add(new Item("4", "Portable Projector", "Elektronik", 0, "Tersedia", "4.7", "15", "50.000", "50k", "Baik", "Deskripsi proyektor."));
        allProducts.add(new Item("5", "GoPro Hero 12", "Fotografi", 0, "Tersedia", "4.7", "9", "65.000", "65k", "Bagus", "Deskripsi GoPro."));
        allProducts.add(new Item("6", "DJI Mini 4 Pro", "Drone", 0, "Tersedia", "4.9", "5", "150.000", "150k", "Sangat Baik", "Deskripsi drone DJI."));
        allProducts.add(new Item("7", "iPad Pro 11\"", "Elektronik", 0, "Tersedia", "4.8", "11", "75.000", "75k", "Bagus", "Deskripsi iPad."));
        allProducts.add(new Item("8", "Raket Badminton", "Lainnya", 0, "Tersedia", "4.7", "32", "10.000", "10k", "Baik", "Deskripsi raket."));

        // Initialize adapter with click listener to navigate to Detail
        adapter = new ExploreAdapter(allProducts, item -> {
            ExploreDetailFragment detailFragment = new ExploreDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item", item);
            detailFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        rvExplore.setAdapter(adapter);
        
        rvExplore.setNestedScrollingEnabled(false);
        rvExplore.setHasFixedSize(false);

        // Setup Filters
        setupFilter(view.findViewById(R.id.chipSemua), "Semua");
        setupFilter(view.findViewById(R.id.chipFotografi), "Fotografi");
        setupFilter(view.findViewById(R.id.chipElektronik), "Elektronik");
        setupFilter(view.findViewById(R.id.chipAudio), "Audio");
        setupFilter(view.findViewById(R.id.chipDrone), "Drone");

        activeFilter = view.findViewById(R.id.chipSemua);
    }

    private void setupFilter(TextView view, String category) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            if (activeFilter != null) {
                activeFilter.setBackgroundResource(R.drawable.bg_chip_inactive);
                activeFilter.setTextColor(ContextCompat.getColor(requireContext(), R.color.navy_blue));
            }
            view.setBackgroundResource(R.drawable.bg_chip_active);
            view.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            activeFilter = view;
            filterByCategory(category);
        });
    }

    private void filterByCategory(String category) {
        if (category.equals("Semua")) {
            adapter.updateList(allProducts);
        } else {
            List<Item> filteredList = new ArrayList<>();
            for (Item item : allProducts) {
                if (item.getCategory().equalsIgnoreCase(category)) {
                    filteredList.add(item);
                }
            }
            adapter.updateList(filteredList);
        }
    }
}