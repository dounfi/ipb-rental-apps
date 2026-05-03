package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ipb.rental.databinding.FragmentPaymentBinding;
import java.util.Locale;

public class PaymentFragment extends BottomSheetDialogFragment {

    private FragmentPaymentBinding binding;
    private String itemName, tanggalMulai, tanggalSelesai;
    private int durasi;
    private long totalBayar;
    private String selectedMethod = "qris";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            itemName = getArguments().getString("item_name");
            tanggalMulai = getArguments().getString("tanggal_mulai");
            tanggalSelesai = getArguments().getString("tanggal_selesai");
            durasi = getArguments().getInt("durasi");
            totalBayar = getArguments().getLong("total_bayar");
        }

        displayPaymentData();
        setupMethodListeners();
        setupActionListeners();
        updatePaymentUI();
    }

    private void displayPaymentData() {
        binding.tvTotalPembayaran.setText("Rp " + formatCurrency(totalBayar));
        binding.tvPaymentSummary.setText(String.format("%s → %s • %d hari", tanggalMulai, tanggalSelesai, durasi));
        binding.tvQrisAmount.setText("Rp " + formatCurrency(totalBayar));
    }

    private void setupMethodListeners() {
        binding.cardQris.setOnClickListener(v -> {
            selectedMethod = "qris";
            updateMethodSelectionUI();
            updatePaymentUI();
        });

        binding.cardTransfer.setOnClickListener(v -> {
            selectedMethod = "transfer";
            updateMethodSelectionUI();
            updatePaymentUI();
        });

        binding.cardEwallet.setOnClickListener(v -> {
            selectedMethod = "ewallet";
            updateMethodSelectionUI();
            updatePaymentUI();
        });

        binding.cardTunai.setOnClickListener(v -> {
            selectedMethod = "tunai";
            updateMethodSelectionUI();
            updatePaymentUI();
        });
    }

    private void updateMethodSelectionUI() {
        // Reset all
        resetCardStyle(binding.cardQris);
        resetCardStyle(binding.cardTransfer);
        resetCardStyle(binding.cardEwallet);
        resetCardStyle(binding.cardTunai);

        // Set selected
        if (selectedMethod.equals("qris")) setSelectedStyle(binding.cardQris);
        else if (selectedMethod.equals("transfer")) setSelectedStyle(binding.cardTransfer);
        else if (selectedMethod.equals("ewallet")) setSelectedStyle(binding.cardEwallet);
        else if (selectedMethod.equals("tunai")) setSelectedStyle(binding.cardTunai);
    }

    private void resetCardStyle(MaterialCardView card) {
        card.setBackgroundResource(R.drawable.bg_payment_card_normal);
        card.setStrokeColor(Color.parseColor("#E5E7EB"));
        card.setStrokeWidth(2);
    }

    private void setSelectedStyle(MaterialCardView card) {
        card.setBackgroundResource(R.drawable.bg_payment_card_selected);
        card.setStrokeColor(Color.parseColor("#1C2B4A"));
        card.setStrokeWidth(4);
    }

    private void updatePaymentUI() {
        binding.layoutQris.setVisibility(selectedMethod.equals("qris") ? View.VISIBLE : View.GONE);
        binding.layoutTransfer.setVisibility(selectedMethod.equals("transfer") ? View.VISIBLE : View.GONE);
        binding.layoutEwallet.setVisibility(selectedMethod.equals("ewallet") ? View.VISIBLE : View.GONE);
        binding.layoutTunai.setVisibility(selectedMethod.equals("tunai") ? View.VISIBLE : View.GONE);
    }

    private void setupActionListeners() {
        binding.btnBatalPayment.setOnClickListener(v -> dismiss());

        binding.btnKonfirmasiPembayaran.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Booking Berhasil!")
                .setMessage("Permintaan sewa kamu sudah dikirim ke pemilik. Tunggu konfirmasi dari pemilik ya!")
                .setPositiveButton("Lihat Rental Saya", (dialog, which) -> {
                    dismiss();
                    // Navigate logic usually via interface to activity
                    if (getActivity() instanceof HomeActivity) {
                        // Use binding.bottomNav to match activity_home.xml ID
                        ((HomeActivity) getActivity()).binding.bottomNav.setSelectedItemId(R.id.nav_rentals);
                    }
                })
                .setCancelable(false)
                .show();
        });
    }

    private String formatCurrency(long amount) {
        return String.format(Locale.getDefault(), "%,d", amount).replace(",", ".");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}