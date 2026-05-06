package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.FragmentAdminPengaduanDetailBinding;

public class AdminPengaduanDetailFragment extends Fragment {

    private FragmentAdminPengaduanDetailBinding binding;
    private AdminPengaduan pengaduan;

    public static AdminPengaduanDetailFragment newInstance(AdminPengaduan pengaduan) {
        AdminPengaduanDetailFragment fragment = new AdminPengaduanDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("pengaduan", pengaduan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pengaduan = (AdminPengaduan) getArguments().getSerializable("pengaduan");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminPengaduanDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        if (pengaduan == null) return;

        binding.tvDetailKategori.setText(pengaduan.getKategori());
        binding.tvDetailWaktu.setText(pengaduan.getWaktu());
        binding.tvDetailIsiPengaduan.setText(pengaduan.getIsiPengaduan());
        binding.tvDetailItemName.setText(pengaduan.getItemName());
        
        binding.tvPelaporName.setText(pengaduan.getPelaporName());
        binding.tvPelaporEmail.setText(pengaduan.getPelaporEmail());
        binding.tvPelaporInitial.setText(String.valueOf(pengaduan.getPelaporName().charAt(0)));

        updateStatusUI();
        updateCategoryUI();
    }

    private void updateStatusUI() {
        binding.tvDetailStatusBadge.setText(pengaduan.getStatus());
        int color;
        int bgColor;

        binding.llBaruActions.setVisibility(View.GONE);
        binding.llDiprosesActions.setVisibility(View.GONE);
        binding.btnTutupDetail.setVisibility(View.GONE);

        switch (pengaduan.getStatus()) {
            case "Baru":
                color = Color.parseColor("#EF4444");
                bgColor = Color.parseColor("#FEE2E2");
                binding.llBaruActions.setVisibility(View.VISIBLE);
                break;
            case "Diproses":
                color = Color.parseColor("#F59E0B");
                bgColor = Color.parseColor("#FEF3C7");
                binding.llDiprosesActions.setVisibility(View.VISIBLE);
                break;
            default: // Selesai
                color = Color.parseColor("#16A34A");
                bgColor = Color.parseColor("#DCFCE7");
                binding.btnTutupDetail.setVisibility(View.VISIBLE);
                break;
        }

        binding.tvDetailStatusBadge.setTextColor(color);
        binding.tvDetailStatusBadge.setBackgroundTintList(ColorStateList.valueOf(bgColor));
    }

    private void updateCategoryUI() {
        int iconBg = R.drawable.bg_pengaduan_icon_blue;
        int tintColor = Color.parseColor("#1D4ED8");

        switch (pengaduan.getKategori()) {
            case "Kondisi Item":
                iconBg = R.drawable.bg_pengaduan_icon_red;
                tintColor = Color.parseColor("#EF4444");
                break;
            case "Keterlambatan":
                iconBg = R.drawable.bg_pengaduan_icon_amber;
                tintColor = Color.parseColor("#F59E0B");
                break;
            case "Refund DP":
                iconBg = R.drawable.bg_pengaduan_icon_blue;
                tintColor = Color.parseColor("#1D4ED8");
                break;
            case "Sengketa Barang":
                iconBg = R.drawable.bg_pengaduan_icon_purple;
                tintColor = Color.parseColor("#7C3AED");
                break;
            case "Rating Tidak Adil":
                iconBg = R.drawable.bg_pengaduan_icon_green;
                tintColor = Color.parseColor("#0F6E56");
                break;
            case "Barang Rusak":
                iconBg = R.drawable.bg_pengaduan_icon_rose;
                tintColor = Color.parseColor("#BE123C");
                break;
        }

        binding.flDetailIconBg.setBackgroundResource(iconBg);
        binding.ivDetailCategoryIcon.setImageTintList(ColorStateList.valueOf(tintColor));
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        binding.btnProsesSekarang.setOnClickListener(v -> {
            pengaduan.setStatus("Diproses");
            updateStatusUI();
            Toast.makeText(getContext(), "Status diperbarui: Diproses", Toast.LENGTH_SHORT).show();
        });

        binding.btnTandaiSelesai.setOnClickListener(v -> {
            pengaduan.setStatus("Selesai");
            updateStatusUI();
            Toast.makeText(getContext(), "Pengaduan diselesaikan", Toast.LENGTH_SHORT).show();
        });

        binding.btnTolak.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Pengaduan ditolak", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });

        binding.btnTutupDetail.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
