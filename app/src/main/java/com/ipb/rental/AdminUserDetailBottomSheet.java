package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ipb.rental.databinding.FragmentAdminUserDetailBinding;

public class AdminUserDetailBottomSheet extends BottomSheetDialogFragment {

    private FragmentAdminUserDetailBinding binding;
    private AdminUser user;
    private OnStatusChangedListener listener;

    public interface OnStatusChangedListener {
        void onStatusChanged(AdminUser user, String newStatus);
    }

    public static AdminUserDetailBottomSheet newInstance(AdminUser user, OnStatusChangedListener listener) {
        AdminUserDetailBottomSheet fragment = new AdminUserDetailBottomSheet();
        fragment.user = user;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminUserDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvDetailName.setText(user.getName());
        binding.tvDetailEmail.setText(user.getEmail());
        binding.tvDetailNim.setText(user.getNim());
        binding.tvInitialLarge.setText(String.valueOf(user.getName().charAt(0)));
        binding.avatarLarge.setBackgroundTintList(ColorStateList.valueOf(user.getAvatarColor()));

        binding.tvDetailRoleBadge.setText(user.getRole());
        if (user.getRole().equals("Penyewa")) {
            binding.tvDetailRoleBadge.setBackgroundResource(R.drawable.bg_badge_blue);
            binding.tvDetailRoleBadge.setTextColor(Color.parseColor("#1D4ED8"));
        } else if (user.getRole().equals("Pemilik")) {
            binding.tvDetailRoleBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D1FAE5")));
            binding.tvDetailRoleBadge.setTextColor(Color.parseColor("#065F46"));
        } else if (user.getRole().equals("Admin")) {
            binding.tvDetailRoleBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E8EDF4")));
            binding.tvDetailRoleBadge.setTextColor(Color.parseColor("#1C2B4A"));
        }

        updateStatusUI();

        binding.btnToggleStatus.setOnClickListener(v -> {
            String newStatus = user.getStatus().equals("Aktif") ? "Diblokir" : "Aktif";
            listener.onStatusChanged(user, newStatus);
            dismiss();
        });

        binding.btnDismiss.setOnClickListener(v -> dismiss());
    }

    private void updateStatusUI() {
        binding.tvDetailStatusBadge.setText(user.getStatus());
        if (user.getStatus().equals("Aktif")) {
            binding.tvDetailStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DCFCE7")));
            binding.tvDetailStatusBadge.setTextColor(Color.parseColor("#16A34A"));
            binding.btnToggleStatus.setText("Blokir User");
            binding.btnToggleStatus.setTextColor(Color.parseColor("#EF4444"));
            // Set stroke color for button manually since material components might be used
        } else {
            binding.tvDetailStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEE2E2")));
            binding.tvDetailStatusBadge.setTextColor(Color.parseColor("#DC2626"));
            binding.btnToggleStatus.setText("Aktifkan User");
            binding.btnToggleStatus.setTextColor(Color.parseColor("#16A34A"));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
