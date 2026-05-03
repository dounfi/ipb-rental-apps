package com.ipb.rental;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentRentalsBinding;
import java.util.ArrayList;
import java.util.List;

public class RentalsFragment extends Fragment implements RentalAdapter.OnRentalActionListener {

    private FragmentRentalsBinding binding;
    private RentalAdapter adapter;
    private String currentMode = "penyewa";
    private String currentStatus = "Aktif";

    private List<RentalItem> penyewaData = new ArrayList<>();
    private List<RentalItem> pemilikData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRentalsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSampleData();
        setupRecyclerView();
        setupListeners();
        
        updateToggleUI();
        updateTabUI();
        filterAndShowData();
    }

    private void setupRecyclerView() {
        binding.recyclerViewRentals.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RentalAdapter(new ArrayList<>(), currentMode, this);
        binding.recyclerViewRentals.setAdapter(adapter);
    }

    private void setupListeners() {
        // Chat Header Icon
        binding.btnChat.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ChatListFragment())
                    .addToBackStack("rentals")
                    .commit();
        });

        // Mode Toggle
        binding.tvSebagaiPenyewa.setOnClickListener(v -> {
            currentMode = "penyewa";
            updateToggleUI();
            filterAndShowData();
        });
        binding.tvSebagaiPemilik.setOnClickListener(v -> {
            currentMode = "pemilik";
            updateToggleUI();
            filterAndShowData();
        });

        // Status Tabs
        binding.tabAktif.setOnClickListener(v -> {
            currentStatus = "Aktif";
            updateTabUI();
            filterAndShowData();
        });
        binding.tabPending.setOnClickListener(v -> {
            currentStatus = "Pending";
            updateTabUI();
            filterAndShowData();
        });
        binding.tabSelesai.setOnClickListener(v -> {
            currentStatus = "Selesai";
            updateTabUI();
            filterAndShowData();
        });
        binding.tabBatal.setOnClickListener(v -> {
            currentStatus = "Dibatalkan";
            updateTabUI();
            filterAndShowData();
        });
    }

    private void filterAndShowData() {
        List<RentalItem> filtered = new ArrayList<>();
        List<RentalItem> sourceList = currentMode.equals("penyewa") ? penyewaData : pemilikData;
        
        for (RentalItem item : sourceList) {
            if (item.getStatus().equals(currentStatus)) {
                filtered.add(item);
            }
        }
        
        adapter.setMode(currentMode);
        adapter.updateList(filtered);
    }

    private void updateToggleUI() {
        if (currentMode.equals("penyewa")) {
            binding.tvSebagaiPenyewa.setBackgroundResource(R.drawable.bg_toggle_active);
            binding.tvSebagaiPenyewa.setTextColor(Color.WHITE);
            binding.tvSebagaiPemilik.setBackgroundResource(R.drawable.bg_toggle_inactive);
            binding.tvSebagaiPemilik.setTextColor(Color.parseColor("#6B7280"));
        } else {
            binding.tvSebagaiPemilik.setBackgroundResource(R.drawable.bg_toggle_active);
            binding.tvSebagaiPemilik.setTextColor(Color.WHITE);
            binding.tvSebagaiPenyewa.setBackgroundResource(R.drawable.bg_toggle_inactive);
            binding.tvSebagaiPenyewa.setTextColor(Color.parseColor("#6B7280"));
        }
    }

    private void updateTabUI() {
        // Reset all tabs
        TextView[] tabs = {binding.tabAktif, binding.tabPending, binding.tabSelesai, binding.tabBatal};
        for (TextView tab : tabs) {
            tab.setTextColor(Color.parseColor("#9CA3AF"));
            tab.setTypeface(null, Typeface.NORMAL);
            tab.setBackgroundResource(R.drawable.bg_tab_inactive);
        }

        // Set active tab
        TextView activeTab;
        switch (currentStatus) {
            case "Aktif": activeTab = binding.tabAktif; break;
            case "Pending": activeTab = binding.tabPending; break;
            case "Selesai": activeTab = binding.tabSelesai; break;
            default: activeTab = binding.tabBatal; break;
        }

        activeTab.setTextColor(Color.parseColor("#1C2B4A"));
        activeTab.setTypeface(null, Typeface.BOLD);
        activeTab.setBackgroundResource(R.drawable.bg_tab_active);
    }

    private void initSampleData() {
        penyewaData.clear();
        pemilikData.clear();
        
        // Mode Penyewa
        penyewaData.add(new RentalItem("1","MacBook Pro 14\"","Dimas P.","Aktif","1 Mar — 4 Mar","Rp 360.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("2","Sony Alpha A7 III","Ahmadi R.","Aktif","3 Mar — 4 Mar","Rp 85.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("3","Sony WH-1000XM5","Ahmad Sony","Pending","5 Mar — 6 Mar","Rp 360.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("4","iPad Pro 11\"","Siti N.","Pending","3 Mar — 4 Mar","Rp 85.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("5","GoPro Hero 12","Hendri W.","Selesai","5 Mar — 8 Mar","Rp 195.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("6","Proyektor Mini","Budi S.","Selesai","3 Mar — 4 Mar","Rp 50.000","/total", R.drawable.ic_logo_placeholder));
        penyewaData.add(new RentalItem("7","DJI Mini 4 Pro","Maya S.","Dibatalkan","5 Mar — 8 Mar","Rp 360.000","/total", R.drawable.ic_logo_placeholder));

        // Mode Pemilik
        pemilikData.add(new RentalItem("8","Sepatu Futsal Nike","nana","Aktif","3 Mar — 4 Mar","Rp 10.000","/total", R.drawable.ic_logo_placeholder));
        pemilikData.add(new RentalItem("9","Raket Badminton Yonex","Fajar N.","Pending","10 Mar — 11 Mar","Rp 30.000","/hari", R.drawable.ic_logo_placeholder));
        pemilikData.add(new RentalItem("10","MacBook Pro 14\"","Nada H.","Selesai","20 Feb — 22 Feb","Rp 240.000","/total", R.drawable.ic_logo_placeholder));
        pemilikData.add(new RentalItem("11","Jas Lab Praktikum","Hendra W.","Dibatalkan","10 Feb","Rp 10.000","/total", R.drawable.ic_logo_placeholder));
    }

    @Override
    public void onChatClick(RentalItem item) {
        // Create dummy contact based on rental item
        ChatContact contact = new ChatContact(
            item.getId(),
            item.getPersonName(),
            "Halo, saya ingin menanyakan tentang " + item.getItemName(),
            "09:00",
            0,
            Color.parseColor("#1C2B4A"),
            "Online",
            item.getItemName(),
            item.getTotalPrice(),
            item.getDateRange()
        );
        
        ChatDetailFragment detailFragment = new ChatDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack("rentals")
                .commit();
    }

    @Override
    public void onDetailClick(RentalItem item, String mode) {
        Bundle args = new Bundle();
        args.putSerializable("rental_item", item);
        args.putString("mode", mode);

        Fragment detailFragment;
        if (mode.equals("penyewa")) {
            detailFragment = new DetailRentalPenyewaFragment();
        } else {
            detailFragment = new DetailRentalPemilikFragment();
        }
        detailFragment.setArguments(args);

        getParentFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack("detail")
            .commit();
    }

    @Override
    public void onBatalkanClick(RentalItem item) {
        Toast.makeText(getContext(), "Batalkan: " + item.getItemName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKonfirmasiClick(RentalItem item) {
        Toast.makeText(getContext(), "Konfirmasi: " + item.getItemName(), Toast.LENGTH_SHORT).show();
    }

    public void onUlasanClick(RentalItem item) {
        Toast.makeText(getContext(), "Ulasan: " + item.getItemName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
