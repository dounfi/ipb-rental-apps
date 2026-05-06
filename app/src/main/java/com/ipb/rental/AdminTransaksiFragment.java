package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentAdminTransaksiBinding;
import java.util.ArrayList;
import java.util.List;

public class AdminTransaksiFragment extends Fragment implements AdminTransaksiAdapter.OnTransaksiClickListener {

    private FragmentAdminTransaksiBinding binding;
    private AdminTransaksiAdapter adapter;
    private List<AdminTransaksi> transaksiList;
    private TextView currentActiveChip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminTransaksiBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupRecyclerView();
        setupFilters();
        setupSearch();
        setupSpinner();
    }

    private void setupData() {
        transaksiList = new ArrayList<>();
        // Mock data matching the requirements
        transaksiList.add(new AdminTransaksi("1", "#IPB2026001", "MacBook Pro 14", "Elektronik", "Dimas Pratama", "dimas@apps.ipb.ac.id", "Owner 1", "03/01", "03/04", "3 hari", "Aktif", "QRIS", 180000, 90000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("2", "#IPB2026002", "Sony Alpha A7 III", "Fotografi", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "Owner 2", "03/02", "03/04", "2 hari", "Aktif", "M-Banking", 85000, 42500, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("3", "#IPB2026003", "Sony WH-1000XM5", "Audio", "Fajar Nugroho", "fajar@apps.ipb.ac.id", "Owner 3", "03/02", "03/05", "3 hari", "Pending", "Transfer Bank", 90000, 45000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("4", "#IPB2026004", "GoPro Hero 12", "Fotografi", "Budi Santoso", "budi@apps.ipb.ac.id", "Owner 4", "02/10", "02/13", "3 hari", "Selesai", "QRIS", 195000, 97500, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("5", "#IPB2026005", "DJI Mini 4 Pro", "Drone", "Maya Sari", "maya@apps.ipb.ac.id", "Owner 5", "02/10", "02/12", "2 hari", "Dibatalkan", "Transfer Bank", 300000, 150000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("6", "#IPB2026006", "iPad Pro 11", "Elektronik", "Hendra Wijaya", "hendra@apps.ipb.ac.id", "Owner 6", "02/25", "02/27", "2 hari", "Selesai", "QRIS", 150000, 75000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("7", "#IPB2026007", "Canon EOS R6", "Fotografi", "Ahmad Rizky", "ahmadricky@apps.ipb.ac.id", "Owner 7", "03/07", "03/09", "2 hari", "Aktif", "M-Banking", 190000, 95000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("8", "#IPB2026008", "Rode NT-USB Mini", "Audio", "Dimas Pratama", "dimas@apps.ipb.ac.id", "Owner 8", "03/02", "03/05", "3 hari", "Pending", "QRIS", 75000, 37500, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("9", "#IPB2026009", "DJI Osmo Pocket 3", "Drone", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "Owner 9", "03/02", "03/05", "3 hari", "Selesai", "Transfer Bank", 210000, 105000, R.drawable.bg_banner_explore));
        transaksiList.add(new AdminTransaksi("10", "#IPB2026010", "Fujifilm X-T5", "Fotografi", "Hendra Wijaya", "hendra@apps.ipb.ac.id", "Owner 10", "03/01", "03/11", "7 hari", "Aktif", "QRIS", 220000, 110000, R.drawable.bg_banner_explore));
    }

    private void setupRecyclerView() {
        adapter = new AdminTransaksiAdapter(transaksiList, this);
        binding.recyclerViewTransaksi.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTransaksi.setAdapter(adapter);
    }

    private void setupFilters() {
        currentActiveChip = binding.chipSemua;

        binding.chipSemua.setOnClickListener(v -> setActiveChip(binding.chipSemua, "Semua"));
        binding.chipAktif.setOnClickListener(v -> setActiveChip(binding.chipAktif, "Aktif"));
        binding.chipSelesai.setOnClickListener(v -> setActiveChip(binding.chipSelesai, "Selesai"));
        binding.chipPending.setOnClickListener(v -> setActiveChip(binding.chipPending, "Pending"));
        binding.chipDibatalkan.setOnClickListener(v -> setActiveChip(binding.chipDibatalkan, "Dibatalkan"));
    }

    private void setActiveChip(TextView chip, String status) {
        currentActiveChip.setBackgroundResource(R.drawable.bg_chip_inactive_admin);
        currentActiveChip.setTextColor(Color.parseColor("#6B7280"));
        currentActiveChip.setTypeface(null, android.graphics.Typeface.NORMAL);

        chip.setBackgroundResource(R.drawable.bg_chip_active_admin);
        chip.setTextColor(Color.WHITE);
        chip.setTypeface(null, android.graphics.Typeface.BOLD);

        currentActiveChip = chip;
        adapter.filterByStatus(status);
    }

    private void setupSearch() {
        binding.etSearchTransaksi.addTextChangedListener(new TextWatcher() {
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

    private void setupSpinner() {
        String[] months = {"Mar 2026", "Feb 2026", "Jan 2026"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, months);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBulan.setAdapter(spinnerAdapter);
    }

    @Override
    public void onTransaksiClick(AdminTransaksi transaksi) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_admin, AdminTransaksiDetailFragment.newInstance(transaksi))
                .addToBackStack("transaksi_detail")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
