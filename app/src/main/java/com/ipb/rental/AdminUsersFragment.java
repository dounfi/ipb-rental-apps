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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentAdminUsersBinding;
import java.util.ArrayList;
import java.util.List;

public class AdminUsersFragment extends Fragment implements AdminUserAdapter.OnUserClickListener, AdminUserDetailBottomSheet.OnStatusChangedListener {

    private FragmentAdminUsersBinding binding;
    private AdminUserAdapter adapter;
    private List<AdminUser> userList;
    private TextView currentActiveChip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupRecyclerView();
        setupFilters();
        setupSearch();
    }

    private void setupData() {
        userList = new ArrayList<>();
        userList.add(new AdminUser("1", "Ahmad Rizky P", "ahmadricky@apps.ipb.ac.id", "G6401211001", "Pemilik", "Aktif", Color.parseColor("#1C2B4A")));
        userList.add(new AdminUser("2", "Dimas Pratama", "dimas@apps.ipb.ac.id", "G6401211002", "Penyewa", "Aktif", Color.parseColor("#0F6E56")));
        userList.add(new AdminUser("3", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "G6401211003", "Penyewa", "Aktif", Color.parseColor("#DC2626")));
        userList.add(new AdminUser("4", "Fajar Nugroho", "fajar@apps.ipb.ac.id", "G6401211004", "Pemilik", "Aktif", Color.parseColor("#D97706")));
        userList.add(new AdminUser("5", "Maya Sari", "maya@apps.ipb.ac.id", "G6401211005", "Penyewa", "Diblokir", Color.parseColor("#7C3AED")));
        userList.add(new AdminUser("6", "Hendra Wijaya", "hendra@apps.ipb.ac.id", "G6401211006", "Pemilik", "Aktif", Color.parseColor("#16A34A")));
        userList.add(new AdminUser("7", "Budi Santoso", "budi@apps.ipb.ac.id", "G6401211007", "Admin", "Aktif", Color.parseColor("#1C2B4A")));
    }

    private void setupRecyclerView() {
        adapter = new AdminUserAdapter(userList, this);
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewUsers.setAdapter(adapter);
    }

    private void setupFilters() {
        currentActiveChip = binding.chipSemua;

        binding.chipSemua.setOnClickListener(v -> setActiveChip(binding.chipSemua, "Semua"));
        binding.chipPenyewa.setOnClickListener(v -> setActiveChip(binding.chipPenyewa, "Penyewa"));
        binding.chipPemilik.setOnClickListener(v -> setActiveChip(binding.chipPemilik, "Pemilik"));
        binding.chipAdmin.setOnClickListener(v -> setActiveChip(binding.chipAdmin, "Admin"));
        binding.chipDiblokir.setOnClickListener(v -> setActiveChip(binding.chipDiblokir, "Diblokir"));
    }

    private void setActiveChip(TextView chip, String role) {
        // Reset previous chip
        currentActiveChip.setBackgroundResource(R.drawable.bg_chip_inactive_admin);
        currentActiveChip.setTextColor(Color.parseColor("#6B7280"));
        currentActiveChip.setTypeface(null, android.graphics.Typeface.NORMAL);

        // Set new active chip
        chip.setBackgroundResource(R.drawable.bg_chip_active_admin);
        chip.setTextColor(Color.WHITE);
        chip.setTypeface(null, android.graphics.Typeface.BOLD);

        currentActiveChip = chip;
        adapter.filterByRole(role);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
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
    public void onUserClick(AdminUser user) {
        AdminUserDetailBottomSheet.newInstance(user, this)
                .show(getChildFragmentManager(), "UserDetail");
    }

    @Override
    public void onStatusChanged(AdminUser user, String newStatus) {
        adapter.updateStatus(user.getId(), newStatus);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
