package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ipb.rental.databinding.FragmentCheckoutBinding;

public class CheckoutFragment extends BottomSheetDialogFragment {

    private FragmentCheckoutBinding binding;
    private Item item;
    private String tanggalMulai, tanggalSelesai;
    private int durasi;
    private long totalHarga, totalDP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("item");
            tanggalMulai = getArguments().getString("tanggal_mulai");
            tanggalSelesai = getArguments().getString("tanggal_selesai");
            durasi = getArguments().getInt("durasi");
            totalHarga = getArguments().getLong("total_harga");
            totalDP = getArguments().getLong("total_dp");
        }

        if (item != null) {
            displayCheckoutData();
        }

        setupListeners();
    }

    private void displayCheckoutData() {
        binding.tvCheckoutItemName.setText(item.getName());
        binding.ivCheckoutItem.setImageResource(item.getImageRes());
        binding.tvCheckoutItemTitle.setText(item.getName());
        binding.tvCheckoutItemSub.setText("Rp " + item.getPricePerDay() + " / hari • " + item.getCategory());
        
        binding.etMulaiCheckout.setText(tanggalMulai);
        binding.etSelesaiCheckout.setText(tanggalSelesai);
        
        binding.tvHargaSewaSummary.setText("Rp " + item.getPricePerDay() + " × " + durasi + " hari");
        binding.tvDurasiSummary.setText(durasi + " hari");
        binding.tvTotalBayarSummary.setText("Rp " + String.format("%,d", totalHarga).replace(",", "."));
    }

    private void setupListeners() {
        binding.btnBatalCheckout.setOnClickListener(v -> dismiss());
        
        binding.btnPilihPembayaran.setOnClickListener(v -> {
            PaymentFragment payment = new PaymentFragment();
            Bundle args = new Bundle();
            args.putSerializable("item", item);
            args.putString("item_name", item.getName());
            args.putString("tanggal_mulai", tanggalMulai);
            args.putString("tanggal_selesai", tanggalSelesai);
            args.putInt("durasi", durasi);
            args.putLong("total_bayar", totalHarga); // Or totalDP depending on business rule, using totalHarga for summary
            payment.setArguments(args);
            
            dismiss();
            payment.show(getParentFragmentManager(), "payment");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
