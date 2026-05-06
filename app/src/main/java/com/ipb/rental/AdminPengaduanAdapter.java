package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemAdminPengaduanBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminPengaduanAdapter extends RecyclerView.Adapter<AdminPengaduanAdapter.PengaduanViewHolder> {

    private List<AdminPengaduan> allPengaduan;
    private List<AdminPengaduan> filteredPengaduan;
    private String currentStatusFilter = "Semua";
    private String currentKategoriFilter = "Semua";
    private String currentSearchQuery = "";
    private OnPengaduanActionListener listener;

    public interface OnPengaduanActionListener {
        void onProsesClick(AdminPengaduan item, int position);
        void onSelesaiClick(AdminPengaduan item, int position);
        void onHubungiClick(AdminPengaduan item);
        void onCardClick(AdminPengaduan item);
    }

    public AdminPengaduanAdapter(List<AdminPengaduan> pengaduan, OnPengaduanActionListener listener) {
        this.allPengaduan = new ArrayList<>(pengaduan);
        this.filteredPengaduan = new ArrayList<>(pengaduan);
        this.listener = listener;
    }

    @NonNull
    @Override
    public PengaduanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminPengaduanBinding binding = ItemAdminPengaduanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PengaduanViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PengaduanViewHolder holder, int position) {
        AdminPengaduan item = filteredPengaduan.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return filteredPengaduan.size();
    }

    public void filterByStatus(String status) {
        this.currentStatusFilter = status;
        applyFilters();
    }

    public void filterByKategori(String kategori) {
        this.currentKategoriFilter = kategori;
        applyFilters();
    }

    public void filterBySearch(String query) {
        this.currentSearchQuery = query.toLowerCase();
        applyFilters();
    }

    private void applyFilters() {
        filteredPengaduan = allPengaduan.stream()
                .filter(p -> {
                    boolean statusMatch = currentStatusFilter.equals("Semua") || p.getStatus().equals(currentStatusFilter);
                    boolean kategoriMatch = currentKategoriFilter.equals("Semua") || 
                            (currentKategoriFilter.equals("Kembalikan Item") ? p.getKategori().equals("Keterlambatan") : p.getKategori().equals(currentKategoriFilter));
                    
                    boolean searchMatch = currentSearchQuery.isEmpty() ||
                            p.getKategori().toLowerCase().contains(currentSearchQuery) ||
                            p.getPelaporName().toLowerCase().contains(currentSearchQuery) ||
                            p.getItemName().toLowerCase().contains(currentSearchQuery);
                    
                    return statusMatch && kategoriMatch && searchMatch;
                })
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public void updateStatus(String id, String newStatus) {
        for (AdminPengaduan p : allPengaduan) {
            if (p.getId().equals(id)) {
                p.setStatus(newStatus);
                break;
            }
        }
        applyFilters();
    }

    class PengaduanViewHolder extends RecyclerView.ViewHolder {
        private ItemAdminPengaduanBinding binding;

        PengaduanViewHolder(ItemAdminPengaduanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AdminPengaduan p, int position) {
            binding.tvKategori.setText(p.getKategori());
            binding.tvPelaporItem.setText(p.getPelaporName() + " · " + p.getItemName());
            binding.tvIsiPengaduan.setText(p.getIsiPengaduan());
            binding.tvWaktu.setText(p.getWaktu());

            // Status Badge & Left Border
            binding.tvStatusBadge.setText(p.getStatus());
            int color;
            int bgColor;
            
            switch (p.getStatus()) {
                case "Baru":
                    color = Color.parseColor("#EF4444");
                    bgColor = Color.parseColor("#FEE2E2");
                    binding.btnProses.setVisibility(View.VISIBLE);
                    binding.btnSelesai.setVisibility(View.GONE);
                    break;
                case "Diproses":
                    color = Color.parseColor("#F59E0B");
                    bgColor = Color.parseColor("#FEF3C7");
                    binding.btnProses.setVisibility(View.GONE);
                    binding.btnSelesai.setVisibility(View.VISIBLE);
                    break;
                default: // Selesai
                    color = Color.parseColor("#16A34A");
                    bgColor = Color.parseColor("#DCFCE7");
                    binding.btnProses.setVisibility(View.GONE);
                    binding.btnSelesai.setVisibility(View.GONE);
                    break;
            }
            
            binding.viewPriorityBorder.setBackgroundColor(color);
            binding.tvStatusBadge.setTextColor(color);
            binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(bgColor));

            // Kategori Icon & Bg
            int iconRes = R.drawable.ic_items; // Default
            int iconBg = R.drawable.bg_pengaduan_icon_blue;
            int tintColor = Color.parseColor("#1D4ED8");

            switch (p.getKategori()) {
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
            
            binding.flIconBg.setBackgroundResource(iconBg);
            binding.ivCategoryIcon.setImageTintList(ColorStateList.valueOf(tintColor));

            // Listeners
            binding.btnProses.setOnClickListener(v -> listener.onProsesClick(p, position));
            binding.btnSelesai.setOnClickListener(v -> listener.onSelesaiClick(p, position));
            binding.btnHubungi.setOnClickListener(v -> listener.onHubungiClick(p));
            binding.getRoot().setOnClickListener(v -> listener.onCardClick(p));
        }
    }
}
