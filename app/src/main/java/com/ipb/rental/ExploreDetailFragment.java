package com.ipb.rental;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.FragmentExploreDetailBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExploreDetailFragment extends Fragment {

    private FragmentExploreDetailBinding binding;
    private Item item;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private long totalHarga = 0;
    private long totalDP = 0;
    private int durasiDays = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("item");
        }

        if (item != null) {
            displayItemDetails();
        }

        setupListeners();
    }

    private void displayItemDetails() {
        binding.ivItemImage.setImageResource(item.getImageRes());
        binding.tvCategory.setText(item.getCategory());
        binding.tvItemName.setText(item.getName());
        binding.tvPricePerDay.setText("Rp " + item.getPricePerDay());
        binding.tvDescription.setText(item.getDescription());
        
        // Status badge
        binding.tvStatusBadge.setText(item.getStatus());
        if ("Tersedia".equalsIgnoreCase(item.getStatus())) {
            binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_aktif);
            binding.tvStatusBadge.setTextColor(getResources().getColor(R.color.green_primary, null));
        } else {
            binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_pending);
            binding.tvStatusBadge.setTextColor(getResources().getColor(R.color.orange_primary, null));
        }

        // Special case for GoPro Hero 12 (as requested in prompt)
        if ("GoPro Hero 12".equalsIgnoreCase(item.getName())) {
            binding.cbSyarat.setVisibility(View.VISIBLE);
        }
    }

    private void setupListeners() {
        binding.btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        binding.tvReadMore.setOnClickListener(v -> {
            if (binding.tvDescription.getMaxLines() == 3) {
                binding.tvDescription.setMaxLines(Integer.MAX_VALUE);
                binding.tvReadMore.setText("Sembunyikan");
            } else {
                binding.tvDescription.setMaxLines(3);
                binding.tvReadMore.setText("Baca selengkapnya");
            }
        });

        binding.etTanggalMulai.setOnClickListener(v -> showDatePicker(true));
        binding.etTanggalSelesai.setOnClickListener(v -> showDatePicker(false));

        binding.btnLanjutBayar.setOnClickListener(v -> {
            if (durasiDays <= 0) {
                Toast.makeText(getContext(), "Pilih durasi sewa yang valid", Toast.LENGTH_SHORT).show();
                return;
            }
            if (binding.cbSyarat.getVisibility() == View.VISIBLE && !binding.cbSyarat.isChecked()) {
                Toast.makeText(getContext(), "Harap setujui syarat & ketentuan", Toast.LENGTH_SHORT).show();
                return;
            }

            CheckoutFragment checkout = new CheckoutFragment();
            Bundle args = new Bundle();
            args.putSerializable("item", item);
            args.putString("tanggal_mulai", binding.etTanggalMulai.getText().toString());
            args.putString("tanggal_selesai", binding.etTanggalSelesai.getText().toString());
            args.putInt("durasi", durasiDays);
            args.putLong("total_harga", totalHarga);
            args.putLong("total_dp", totalDP);
            checkout.setArguments(args);
            checkout.show(getChildFragmentManager(), "checkout");
        });
    }

    private void showDatePicker(boolean isMulai) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            String dateStr = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            if (isMulai) {
                binding.etTanggalMulai.setText(dateStr);
            } else {
                binding.etTanggalSelesai.setText(dateStr);
            }
            hitungDurasi();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        
        if (!isMulai && !binding.etTanggalMulai.getText().toString().isEmpty()) {
            try {
                Date startDate = sdf.parse(binding.etTanggalMulai.getText().toString());
                if (startDate != null) dpd.getDatePicker().setMinDate(startDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        dpd.show();
    }

    private void hitungDurasi() {
        String mulaiStr = binding.etTanggalMulai.getText().toString();
        String selesaiStr = binding.etTanggalSelesai.getText().toString();

        if (mulaiStr.isEmpty() || selesaiStr.isEmpty()) return;

        try {
            Date dateMulai = sdf.parse(mulaiStr);
            Date dateSelesai = sdf.parse(selesaiStr);

            if (dateMulai != null && dateSelesai != null) {
                long diffInMs = Math.abs(dateSelesai.getTime() - dateMulai.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS);
                
                // Minimal 1 hari
                if (diffInDays == 0 && !mulaiStr.equals(selesaiStr)) diffInDays = 1;
                else if (mulaiStr.equals(selesaiStr)) diffInDays = 1;

                durasiDays = (int) diffInDays;
                binding.tvDurasiValue.setText(durasiDays + " hari");

                // Parse harga/hari
                String cleanPrice = item.getPricePerDay().replaceAll("[^0-9]", "");
                long pricePerDay = Long.parseLong(cleanPrice);
                
                totalHarga = pricePerDay * durasiDays;
                totalDP = totalHarga / 2;

                binding.tvTotalHargaValue.setText("Rp " + formatCurrency(totalHarga));
                binding.tvWajibDPValue.setText("Rp " + formatCurrency(totalDP));
                binding.tvTotalDPBottom.setText("Rp " + formatCurrency(totalDP));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
