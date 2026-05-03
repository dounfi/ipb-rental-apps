package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.FragmentDetailRentalPemilikBinding;

public class DetailRentalPemilikFragment extends Fragment {

    private FragmentDetailRentalPemilikBinding binding;
    private RentalItem item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailRentalPemilikBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            item = (RentalItem) getArguments().getSerializable("rental_item");
            if (item != null) {
                populateData();
                setupTimeline();
                setupActions();
            }
        }

        binding.btnTutup.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        
        View.OnClickListener chatClickListener = v -> {
            if (item != null) {
                navigateToChat();
            }
        };
        
        binding.btnChatBig.setOnClickListener(chatClickListener);
        binding.btnChatSmall.setOnClickListener(chatClickListener);
    }

    private void navigateToChat() {
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
                .addToBackStack("rental_detail")
                .commit();
    }

    private void populateData() {
        binding.tvItemName.setText(item.getItemName());
        binding.tvTenantLabel.setText("Disewa oleh " + item.getPersonName());
        binding.ivItem.setImageResource(item.getImageRes());
        binding.tvStatusBadge.setText(item.getStatus());
        binding.tvStatusText.setText(item.getStatus());
        binding.tvTotalPrice.setText(item.getTotalPrice());

        // Tenant Info
        binding.tvTenantName.setText(item.getPersonName());
        if (!item.getPersonName().isEmpty()) {
            binding.tvTenantInitial.setText(String.valueOf(item.getPersonName().charAt(0)).toUpperCase());
        }
        
        String[] dates = item.getDateRange().split(" — ");
        if (dates.length == 2) {
            binding.tvStartDate.setText(dates[0]);
            binding.tvEndDate.setText(dates[1]);
        } else {
            binding.tvStartDate.setText(item.getDateRange());
            binding.tvEndDate.setText("-");
        }

        // Status Badge Style
        switch (item.getStatus()) {
            case "Aktif":
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_aktif);
                binding.tvStatusBadge.setTextColor(Color.parseColor("#16A34A"));
                binding.tvStatusText.setTextColor(Color.parseColor("#16A34A"));
                break;
            case "Pending":
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_pending);
                binding.tvStatusBadge.setTextColor(Color.parseColor("#92400E"));
                binding.tvStatusText.setTextColor(Color.parseColor("#92400E"));
                break;
            case "Selesai":
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_selesai);
                binding.tvStatusBadge.setTextColor(Color.parseColor("#0F6E56"));
                binding.tvStatusText.setTextColor(Color.parseColor("#0F6E56"));
                break;
            case "Dibatalkan":
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_batal);
                binding.tvStatusBadge.setTextColor(Color.parseColor("#DC2626"));
                binding.tvStatusText.setTextColor(Color.parseColor("#DC2626"));
                break;
        }
    }

    private void setupTimeline() {
        binding.layoutTimeline.removeAllViews();
        String status = item.getStatus();

        if (status.equals("Aktif")) {
            addTimelineItem("Booking diterima pemilik", "1 Mar", "#22C55E", true);
            addTimelineItem("Item diserahkan ke penyewa", "1 Mar", "#22C55E", true);
            addTimelineItem("Menunggu pengembalian", "", "#E5E7EB", false);
        } else if (status.equals("Pending")) {
            addTimelineItem("Permintaan masuk", "1 Mar", "#F59E0B", true);
            addTimelineItem("Menunggu konfirmasi pemilik", "", "#E5E7EB", true);
            addTimelineItem("Item diserahkan", "", "#E5E7EB", false);
        } else if (status.equals("Selesai")) {
            addTimelineItem("Booking diterima pemilik", "1 Mar", "#0F6E56", true);
            addTimelineItem("Item diserahkan ke penyewa", "1 Mar", "#0F6E56", true);
            addTimelineItem("Item dikembalikan & selesai", "4 Mar", "#0F6E56", false);
        } else if (status.equals("Dibatalkan")) {
            addTimelineItem("Booking dibatalkan", "1 Mar", "#EF4444", false);
        }
    }

    private void setupActions() {
        String status = item.getStatus();
        
        binding.btnConfirm.setVisibility(View.GONE);
        binding.btnReject.setVisibility(View.GONE);
        binding.btnChatBig.setVisibility(View.GONE);
        binding.btnMarkReturned.setVisibility(View.GONE);

        if (status.equals("Pending")) {
            binding.btnConfirm.setVisibility(View.VISIBLE);
            binding.btnReject.setVisibility(View.VISIBLE);
        } else if (status.equals("Aktif")) {
            binding.btnChatBig.setVisibility(View.VISIBLE);
            binding.btnMarkReturned.setVisibility(View.VISIBLE);
        } else if (status.equals("Selesai")) {
            binding.btnChatBig.setVisibility(View.VISIBLE);
            binding.btnChatBig.setBackgroundResource(R.drawable.bg_input_field);
            binding.btnChatBig.setTextColor(Color.parseColor("#6B7280"));
        } else if (status.equals("Dibatalkan")) {
            // Only Tutup button
        }
    }

    private void addTimelineItem(String title, String time, String colorHex, boolean showLine) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_timeline, binding.layoutTimeline, false);
        
        View dot = view.findViewById(R.id.dot);
        View line = view.findViewById(R.id.line);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvTime = view.findViewById(R.id.tvTime);

        dot.setBackgroundColor(Color.parseColor(colorHex));
        line.setBackgroundColor(Color.parseColor(colorHex));
        line.setVisibility(showLine ? View.VISIBLE : View.GONE);
        
        tvTitle.setText(title);
        if (time.isEmpty()) {
            tvTime.setVisibility(View.GONE);
            tvTitle.setTextColor(Color.parseColor("#9CA3AF"));
        } else {
            tvTime.setText(time);
            tvTitle.setTextColor(Color.parseColor("#1C2B4A"));
        }

        binding.layoutTimeline.addView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
