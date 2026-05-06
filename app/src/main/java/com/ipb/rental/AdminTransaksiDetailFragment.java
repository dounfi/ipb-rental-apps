package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ipb.rental.databinding.FragmentAdminTransaksiDetailBinding;

public class AdminTransaksiDetailFragment extends Fragment {

    private FragmentAdminTransaksiDetailBinding binding;
    private AdminTransaksi transaksi;

    public static AdminTransaksiDetailFragment newInstance(AdminTransaksi transaksi) {
        AdminTransaksiDetailFragment fragment = new AdminTransaksiDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("transaksi", transaksi);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (AdminTransaksi) getArguments().getSerializable("transaksi");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminTransaksiDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        if (transaksi == null) return;

        binding.tvBookingId.setText(transaksi.getBookingId());
        binding.tvMetodeBayar.setText(transaksi.getMetodePembayaran());
        binding.tvDetailItemName.setText(transaksi.getItemName());
        binding.tvDetailCategory.setText(transaksi.getItemCategory());
        binding.tvDetailPricePerDay.setText("Rp " + String.format("%,d", transaksi.getTotalHarga() / Integer.parseInt(transaksi.getDurasi().split(" ")[0])).replace(',', '.') + "/hari");
        
        binding.tvPenyewaName.setText(transaksi.getPenyewaName());
        binding.tvPenyewaEmail.setText(transaksi.getPenyewaEmail());
        binding.tvPenyewaInitial.setText(String.valueOf(transaksi.getPenyewaName().charAt(0)));
        
        binding.tvOwnerName.setText(transaksi.getOwnerName());
        binding.tvOwnerInitial.setText(String.valueOf(transaksi.getOwnerName().charAt(0)));

        binding.tvDetailStartDate.setText(transaksi.getTanggalMulai());
        binding.tvDetailEndDate.setText(transaksi.getTanggalSelesai());
        binding.tvDetailDuration.setText(transaksi.getDurasi());
        binding.tvDetailTotalPrice.setText("Rp " + String.format("%,d", transaksi.getTotalHarga()).replace(',', '.'));
        binding.tvDetailDpPaid.setText("Rp " + String.format("%,d", transaksi.getDpAmount()).replace(',', '.'));
        binding.tvDetailRemaining.setText("Rp " + String.format("%,d", transaksi.getTotalHarga() - transaksi.getDpAmount()).replace(',', '.'));

        if (transaksi.getImageRes() != 0) {
            binding.ivDetailItemPhoto.setImageResource(transaksi.getImageRes());
        }

        updateStatusUI();
    }

    private void updateStatusUI() {
        binding.tvDetailStatusBadge.setText(transaksi.getStatus());
        
        int statusColor;
        int bgColor;
        
        binding.btnActionPrimary.setVisibility(View.GONE);
        binding.btnActionSecondary.setVisibility(View.GONE);
        binding.btnActionFull.setVisibility(View.GONE);

        switch (transaksi.getStatus()) {
            case "Aktif":
                statusColor = Color.parseColor("#16A34A");
                bgColor = Color.parseColor("#DCFCE7");
                binding.btnActionFull.setVisibility(View.VISIBLE);
                binding.btnActionFull.setText("Tandai Selesai");
                binding.btnActionFull.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0F6E56")));
                
                binding.dotStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                binding.lineStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                binding.dotStep3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1C2B4A")));
                break;
            case "Selesai":
                statusColor = Color.parseColor("#0F6E56");
                bgColor = Color.parseColor("#D1FAE5");
                binding.btnActionFull.setVisibility(View.VISIBLE);
                binding.btnActionFull.setText("Tutup");
                binding.btnActionFull.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F5F0EB")));
                binding.btnActionFull.setTextColor(Color.parseColor("#6B7280"));
                
                binding.dotStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                binding.lineStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                binding.dotStep3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1C2B4A")));
                binding.lineStep3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1C2B4A")));
                binding.dotStep4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                binding.tvStep4Title.setText("Rental Selesai");
                binding.tvStep4Title.setTextColor(Color.parseColor("#1C2B4A"));
                binding.tvStep4Title.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            case "Pending":
                statusColor = Color.parseColor("#92400E");
                bgColor = Color.parseColor("#FEF3C7");
                binding.btnActionPrimary.setVisibility(View.VISIBLE);
                binding.btnActionSecondary.setVisibility(View.VISIBLE);
                binding.btnActionPrimary.setText("Konfirmasi DP");
                binding.btnActionSecondary.setText("Batalkan");
                
                binding.dotStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E5E7EB")));
                binding.lineStep2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E5E7EB")));
                break;
            case "Dibatalkan":
                statusColor = Color.parseColor("#DC2626");
                bgColor = Color.parseColor("#FEE2E2");
                binding.btnActionFull.setVisibility(View.VISIBLE);
                binding.btnActionFull.setText("Tutup");
                binding.btnActionFull.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F5F0EB")));
                binding.btnActionFull.setTextColor(Color.parseColor("#6B7280"));

                binding.dotStep4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC2626")));
                binding.tvStep4Title.setText("Dibatalkan");
                binding.tvStep4Title.setTextColor(Color.parseColor("#DC2626"));
                binding.tvStep4Title.setTypeface(null, android.graphics.Typeface.BOLD);
                break;
            default:
                statusColor = Color.BLACK;
                bgColor = Color.WHITE;
                break;
        }
        
        binding.tvDetailStatusBadge.setTextColor(statusColor);
        binding.tvDetailStatusBadge.setBackgroundTintList(ColorStateList.valueOf(bgColor));
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        binding.btnActionPrimary.setOnClickListener(v -> {
            if (transaksi.getStatus().equals("Pending")) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Konfirmasi DP")
                        .setMessage("Apakah Anda yakin ingin mengonfirmasi pembayaran DP untuk transaksi ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Konfirmasi", (dialog, which) -> {
                            transaksi.setStatus("Aktif");
                            updateStatusUI();
                        })
                        .show();
            }
        });

        binding.btnActionSecondary.setOnClickListener(v -> {
            if (transaksi.getStatus().equals("Pending")) {
                final EditText input = new EditText(requireContext());
                input.setHint("Alasan pembatalan...");
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Batalkan Transaksi")
                        .setMessage("Masukkan alasan pembatalan:")
                        .setView(input)
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Batalkan", (dialog, which) -> {
                            transaksi.setStatus("Dibatalkan");
                            updateStatusUI();
                        })
                        .show();
            }
        });

        binding.btnActionFull.setOnClickListener(v -> {
            if (binding.btnActionFull.getText().toString().equals("Tandai Selesai")) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Tandai Selesai")
                        .setMessage("Pastikan item sudah dikembalikan dengan baik oleh penyewa.")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Selesai", (dialog, which) -> {
                            transaksi.setStatus("Selesai");
                            updateStatusUI();
                        })
                        .show();
            } else if (binding.btnActionFull.getText().toString().equals("Tutup")) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
