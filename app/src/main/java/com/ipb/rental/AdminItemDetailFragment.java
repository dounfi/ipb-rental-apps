package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ipb.rental.databinding.FragmentAdminItemDetailBinding;

public class AdminItemDetailFragment extends Fragment implements AdminEditItemBottomSheet.OnItemEditedListener {

    private FragmentAdminItemDetailBinding binding;
    private AdminItem item;

    public static AdminItemDetailFragment newInstance(AdminItem item) {
        AdminItemDetailFragment fragment = new AdminItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = (AdminItem) getArguments().getSerializable("item");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminItemDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        if (item == null) return;

        binding.tvDetailName.setText(item.getName());
        binding.tvDetailCategory.setText(item.getCategory());
        binding.tvDetailPrice.setText("Rp " + item.getPricePerDay() + "/hari");
        binding.tvDetailCondition.setText(item.getCondition());
        binding.tvDetailDescription.setText(item.getDescription());
        binding.tvDetailRentCount.setText(item.getRentCount() + " kali");
        
        // Owner Info
        binding.tvOwnerName.setText(item.getOwnerName());
        binding.tvOwnerEmail.setText(item.getOwnerEmail());
        binding.tvOwnerInitial.setText(String.valueOf(item.getOwnerName().charAt(0)));

        if (item.getImageRes() != 0) {
            binding.ivDetailPhoto.setImageResource(item.getImageRes());
        }

        updateStatusUI();
    }

    private void updateStatusUI() {
        binding.tvDetailStatusBadge.setText(item.getStatus());
        
        int statusColor;
        int bgColor;
        
        switch (item.getStatus()) {
            case "Tersedia":
                statusColor = Color.parseColor("#16A34A");
                bgColor = Color.parseColor("#DCFCE7");
                binding.btnBlokirItem.setVisibility(View.VISIBLE);
                binding.btnEditItem.setVisibility(View.VISIBLE);
                binding.btnAktifkanItem.setVisibility(View.GONE);
                break;
            case "Disewa":
                statusColor = Color.parseColor("#92400E");
                bgColor = Color.parseColor("#FEF3C7");
                binding.btnBlokirItem.setVisibility(View.VISIBLE);
                binding.btnEditItem.setVisibility(View.VISIBLE);
                binding.btnAktifkanItem.setVisibility(View.GONE);
                break;
            case "Diblokir":
                statusColor = Color.parseColor("#DC2626");
                bgColor = Color.parseColor("#FEE2E2");
                binding.btnBlokirItem.setVisibility(View.GONE);
                binding.btnEditItem.setVisibility(View.GONE);
                binding.btnAktifkanItem.setVisibility(View.VISIBLE);
                break;
            default: // Nonaktif
                statusColor = Color.parseColor("#6B7280");
                bgColor = Color.parseColor("#F3F4F6");
                binding.btnBlokirItem.setVisibility(View.VISIBLE);
                binding.btnEditItem.setVisibility(View.VISIBLE);
                binding.btnAktifkanItem.setVisibility(View.GONE);
                break;
        }
        
        binding.tvDetailStatusBadge.setTextColor(statusColor);
        binding.tvDetailStatusBadge.setBackgroundTintList(ColorStateList.valueOf(bgColor));
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        binding.btnBlokirItem.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Blokir Item?")
                    .setMessage("Item ini tidak akan bisa disewa. Lanjutkan?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Blokir", (dialog, which) -> {
                        item.setStatus("Diblokir");
                        updateStatusUI();
                    })
                    .show();
        });

        binding.btnAktifkanItem.setOnClickListener(v -> {
            item.setStatus("Tersedia");
            updateStatusUI();
        });

        binding.btnEditItem.setOnClickListener(v -> {
            AdminEditItemBottomSheet.newInstance(item, this)
                    .show(getChildFragmentManager(), "EditItem");
        });
    }

    @Override
    public void onItemEdited(AdminItem updatedItem) {
        this.item = updatedItem;
        setupUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
