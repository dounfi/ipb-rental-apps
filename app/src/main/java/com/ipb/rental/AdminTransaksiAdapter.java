package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemAdminTransaksiBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminTransaksiAdapter extends RecyclerView.Adapter<AdminTransaksiAdapter.TransaksiViewHolder> {

    private List<AdminTransaksi> allTransaksi;
    private List<AdminTransaksi> filteredTransaksi;
    private String currentStatusFilter = "Semua";
    private String currentSearchQuery = "";
    private OnTransaksiClickListener listener;

    public interface OnTransaksiClickListener {
        void onTransaksiClick(AdminTransaksi transaksi);
    }

    public AdminTransaksiAdapter(List<AdminTransaksi> transaksi, OnTransaksiClickListener listener) {
        this.allTransaksi = new ArrayList<>(transaksi);
        this.filteredTransaksi = new ArrayList<>(transaksi);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminTransaksiBinding binding = ItemAdminTransaksiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransaksiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
        AdminTransaksi transaksi = filteredTransaksi.get(position);
        holder.bind(transaksi);
    }

    @Override
    public int getItemCount() {
        return filteredTransaksi.size();
    }

    public void filterByStatus(String status) {
        this.currentStatusFilter = status;
        applyFilters();
    }

    public void filterBySearch(String query) {
        this.currentSearchQuery = query.toLowerCase();
        applyFilters();
    }

    private void applyFilters() {
        filteredTransaksi = allTransaksi.stream()
                .filter(t -> {
                    boolean statusMatch = currentStatusFilter.equals("Semua") || t.getStatus().equals(currentStatusFilter);
                    boolean searchMatch = currentSearchQuery.isEmpty() ||
                            t.getItemName().toLowerCase().contains(currentSearchQuery) ||
                            t.getPenyewaName().toLowerCase().contains(currentSearchQuery) ||
                            t.getBookingId().toLowerCase().contains(currentSearchQuery);
                    return statusMatch && searchMatch;
                })
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public void updateStatus(String id, String newStatus) {
        for (AdminTransaksi t : allTransaksi) {
            if (t.getId().equals(id)) {
                t.setStatus(newStatus);
                break;
            }
        }
        applyFilters();
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {
        private ItemAdminTransaksiBinding binding;

        TransaksiViewHolder(ItemAdminTransaksiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AdminTransaksi t) {
            binding.tvItemName.setText(t.getItemName());
            binding.tvTransaksiInfo.setText(t.getPenyewaName() + " · " + t.getTanggalMulai() + " - " + t.getTanggalSelesai() + " (" + t.getDurasi() + ")");
            binding.tvTotalPrice.setText("Rp " + String.format("%,d", t.getTotalHarga()).replace(',', '.'));
            binding.tvDpAmount.setText("DP: Rp " + String.format("%,d", t.getDpAmount()).replace(',', '.'));

            if (t.getImageRes() != 0) {
                binding.ivItemImage.setImageResource(t.getImageRes());
            }

            binding.tvStatusBadge.setText(t.getStatus());
            switch (t.getStatus()) {
                case "Aktif":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DCFCE7")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#16A34A"));
                    break;
                case "Selesai":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D1FAE5")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#0F6E56"));
                    break;
                case "Pending":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEF3C7")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#92400E"));
                    break;
                case "Dibatalkan":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEE2E2")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#DC2626"));
                    break;
            }

            binding.getRoot().setOnClickListener(v -> listener.onTransaksiClick(t));
        }
    }
}
