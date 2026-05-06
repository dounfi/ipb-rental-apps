package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemAdminItemCardBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminItemAdapter extends RecyclerView.Adapter<AdminItemAdapter.ItemViewHolder> {

    private List<AdminItem> allItems;
    private List<AdminItem> filteredItems;
    private String currentCategoryFilter = "Semua";
    private String currentSearchQuery = "";
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AdminItem item);
    }

    public AdminItemAdapter(List<AdminItem> items, OnItemClickListener listener) {
        this.allItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminItemCardBinding binding = ItemAdminItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        AdminItem item = filteredItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void filterByCategory(String category) {
        this.currentCategoryFilter = category;
        applyFilters();
    }

    public void filterBySearch(String query) {
        this.currentSearchQuery = query.toLowerCase();
        applyFilters();
    }

    private void applyFilters() {
        filteredItems = allItems.stream()
                .filter(item -> {
                    boolean categoryMatch = currentCategoryFilter.equals("Semua") || 
                            (currentCategoryFilter.startsWith("Semua") && currentCategoryFilter.contains("84") ? true : item.getCategory().equals(currentCategoryFilter));
                    
                    // Special case for "Semua (84)" chip
                    if (currentCategoryFilter.startsWith("Semua")) categoryMatch = true;

                    boolean searchMatch = currentSearchQuery.isEmpty() ||
                            item.getName().toLowerCase().contains(currentSearchQuery) ||
                            item.getOwnerName().toLowerCase().contains(currentSearchQuery) ||
                            item.getCategory().toLowerCase().contains(currentSearchQuery);
                    
                    return categoryMatch && searchMatch;
                })
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }
    
    public void updateItem(AdminItem updatedItem) {
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getId().equals(updatedItem.getId())) {
                allItems.set(i, updatedItem);
                break;
            }
        }
        applyFilters();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemAdminItemCardBinding binding;

        ItemViewHolder(ItemAdminItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AdminItem item) {
            binding.tvItemName.setText(item.getName());
            binding.tvItemPrice.setText("Rp " + item.getPricePerDay() + "/hari");
            binding.tvRating.setText(item.getRating() + "★");
            
            // Assuming imageRes is provided, if not use a placeholder
            if (item.getImageRes() != 0) {
                binding.ivItemPhoto.setImageResource(item.getImageRes());
            }

            binding.tvStatusBadge.setText(item.getStatus());
            switch (item.getStatus()) {
                case "Tersedia":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DCFCE7")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#16A34A"));
                    break;
                case "Disewa":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEF3C7")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#92400E"));
                    break;
                case "Diblokir":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEE2E2")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#DC2626"));
                    break;
                case "Nonaktif":
                    binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F3F4F6")));
                    binding.tvStatusBadge.setTextColor(Color.parseColor("#6B7280"));
                    break;
            }

            binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
