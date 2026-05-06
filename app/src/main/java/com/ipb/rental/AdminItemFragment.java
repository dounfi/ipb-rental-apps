package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.ipb.rental.databinding.FragmentAdminItemBinding;
import java.util.ArrayList;
import java.util.List;

public class AdminItemFragment extends Fragment implements AdminItemAdapter.OnItemClickListener {

    private FragmentAdminItemBinding binding;
    private AdminItemAdapter adapter;
    private List<AdminItem> itemList;
    private TextView currentActiveChip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupRecyclerView();
        setupFilters();
        setupSearch();

        binding.btnTambahItem.setOnClickListener(v -> {
            // This would normally open AdminTambahItemBottomSheet
            // For now, we use a simple toast or placeholder logic
        });
    }

    private void setupData() {
        itemList = new ArrayList<>();
        itemList.add(new AdminItem("1", "Sony Alpha A7 III", "Fotografi", "Ahmad Rizky P", "ahmadricky@apps.ipb.ac.id", "Tersedia", "85.000", "Sangat Baik", "Kamera full-frame mirrorless populer.", R.drawable.bg_banner_explore, 24, "4.9"));
        itemList.add(new AdminItem("2", "MacBook Pro 14", "Elektronik", "Dimas Pratama", "dimas@apps.ipb.ac.id", "Disewa", "120.000", "Baik", "Laptop powerful untuk editing.", R.drawable.bg_banner_explore, 12, "4.8"));
        itemList.add(new AdminItem("3", "Sony WH-1000XM5", "Audio", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "Tersedia", "30.000", "Sangat Baik", "Noise cancelling headphone terbaik.", R.drawable.bg_banner_explore, 45, "5.0"));
        itemList.add(new AdminItem("4", "DJI Mini 4 Pro", "Drone", "Fajar Nugroho", "fajar@apps.ipb.ac.id", "Tersedia", "150.000", "Sangat Baik", "Drone ringan tanpa perlu lisensi.", R.drawable.bg_banner_explore, 8, "4.9"));
        itemList.add(new AdminItem("5", "GoPro Hero 12", "Fotografi", "Ahmad Rizky P", "ahmadricky@apps.ipb.ac.id", "Tersedia", "65.000", "Sangat Baik", "Action cam terbaru.", R.drawable.bg_banner_explore, 30, "4.7"));
        itemList.add(new AdminItem("6", "iPad Pro 11", "Elektronik", "Hendra Wijaya", "hendra@apps.ipb.ac.id", "Disewa", "75.000", "Baik", "Tablet untuk produktivitas.", R.drawable.bg_banner_explore, 15, "4.8"));
        itemList.add(new AdminItem("7", "Canon EOS R6", "Fotografi", "Dimas Pratama", "dimas@apps.ipb.ac.id", "Tersedia", "95.000", "Sangat Baik", "Kamera full-frame handal.", R.drawable.bg_banner_explore, 10, "4.6"));
        itemList.add(new AdminItem("8", "Rode NT-USB Mini", "Audio", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "Tersedia", "25.000", "Sangat Baik", "Mic USB praktis.", R.drawable.bg_banner_explore, 20, "4.5"));
        itemList.add(new AdminItem("9", "DJI Osmo Pocket 3", "Drone", "Fajar Nugroho", "fajar@apps.ipb.ac.id", "Tersedia", "70.000", "Sangat Baik", "Gimbal camera compact.", R.drawable.bg_banner_explore, 18, "4.8"));
        itemList.add(new AdminItem("10", "Sepatu Futsal Nike", "Olahraga", "Maya Sari", "maya@apps.ipb.ac.id", "Diblokir", "15.000", "Cukup", "Sepatu olahraga nyaman.", R.drawable.bg_banner_explore, 5, "4.3"));
    }

    private void setupRecyclerView() {
        adapter = new AdminItemAdapter(itemList, this);
        binding.recyclerViewItems.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewItems.setAdapter(adapter);
    }

    private void setupFilters() {
        currentActiveChip = binding.chipSemua;

        binding.chipSemua.setOnClickListener(v -> setActiveChip(binding.chipSemua, "Semua"));
        binding.chipFotografi.setOnClickListener(v -> setActiveChip(binding.chipFotografi, "Fotografi"));
        binding.chipElektronik.setOnClickListener(v -> setActiveChip(binding.chipElektronik, "Elektronik"));
        binding.chipAudio.setOnClickListener(v -> setActiveChip(binding.chipAudio, "Audio"));
        binding.chipDrone.setOnClickListener(v -> setActiveChip(binding.chipDrone, "Drone"));
        binding.chipOlahraga.setOnClickListener(v -> setActiveChip(binding.chipOlahraga, "Olahraga"));
        binding.chipAkademik.setOnClickListener(v -> setActiveChip(binding.chipAkademik, "Akademik"));
    }

    private void setActiveChip(TextView chip, String category) {
        currentActiveChip.setBackgroundResource(R.drawable.bg_chip_inactive_admin);
        currentActiveChip.setTextColor(Color.parseColor("#6B7280"));
        currentActiveChip.setTypeface(null, android.graphics.Typeface.NORMAL);

        chip.setBackgroundResource(R.drawable.bg_chip_active_admin);
        chip.setTextColor(Color.WHITE);
        chip.setTypeface(null, android.graphics.Typeface.BOLD);

        currentActiveChip = chip;
        adapter.filterByCategory(category);
    }

    private void setupSearch() {
        binding.etSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterBySearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onItemClick(AdminItem item) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_admin, AdminItemDetailFragment.newInstance(item))
                .addToBackStack("item_detail")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
