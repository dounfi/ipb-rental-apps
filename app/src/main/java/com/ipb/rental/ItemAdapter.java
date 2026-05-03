package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ipb.rental.databinding.ItemCardBinding;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items = new ArrayList<>();
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEditClick(Item item, int position);
        void onNonaktifkanClick(Item item, int position);
        void onHapusClick(Item item, int position);
    }

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void updateList(List<Item> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() { return items.size(); }
            @Override
            public int getNewListSize() { return newList.size(); }
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
            }
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Item oldItem = items.get(oldItemPosition);
                Item newItem = newList.get(newItemPosition);
                return oldItem.getName().equals(newItem.getName()) &&
                        oldItem.getStatus().equals(newItem.getStatus()) &&
                        oldItem.getPricePerDay().equals(newItem.getPricePerDay());
            }
        });
        items.clear();
        items.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.binding.tvItemName.setText(item.getName());
        holder.binding.tvRentCount.setText(item.getRentCount());
        holder.binding.tvPriceFull.setText(item.getPricePerDay());
        holder.binding.tvPriceShort.setText(item.getShortPrice());
        holder.binding.tvStatusBadge.setText(item.getStatus());

        // Badge Status Logic
        switch (item.getStatus()) {
            case "Tersedia":
                holder.binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E1F5EE")));
                holder.binding.tvStatusBadge.setTextColor(Color.parseColor("#0F6E56"));
                break;
            case "Disewa":
                holder.binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEF3C7")));
                holder.binding.tvStatusBadge.setTextColor(Color.parseColor("#92400E"));
                break;
            default:
                holder.binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F3F4F6")));
                holder.binding.tvStatusBadge.setTextColor(Color.parseColor("#6B7280"));
        }

        // Dynamic Nonaktifkan Button Logic
        if (item.getStatus().equals("Tersedia") || item.getStatus().equals("Disewa")) {
            holder.binding.btnNonaktifkan.setText("Nonaktifkan");
            holder.binding.btnNonaktifkan.setTextColor(Color.parseColor("#16A34A"));
            holder.binding.btnNonaktifkan.setIconTint(ColorStateList.valueOf(Color.parseColor("#16A34A")));
            holder.binding.btnNonaktifkan.setIconResource(R.drawable.ic_check_circle);
        } else {
            holder.binding.btnNonaktifkan.setText("Aktifkan");
            holder.binding.btnNonaktifkan.setTextColor(Color.parseColor("#EF4444"));
            holder.binding.btnNonaktifkan.setIconTint(ColorStateList.valueOf(Color.parseColor("#EF4444")));
            holder.binding.btnNonaktifkan.setIconResource(R.drawable.ic_cancel);
        }

        // Listeners
        holder.binding.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(item, holder.getAdapterPosition());
        });

        holder.binding.btnNonaktifkan.setOnClickListener(v -> {
            if (listener != null) listener.onNonaktifkanClick(item, holder.getAdapterPosition());
        });

        holder.binding.btnHapus.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(v.getContext(), R.style.CustomAlertDialog)
                    .setTitle("Hapus Item?")
                    .setMessage("Item \"" + item.getName() + "\" akan dihapus permanen dan tidak bisa dikembalikan. Apakah kamu yakin?")
                    .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Ya, Hapus", (dialog, which) -> {
                        if (listener != null) listener.onHapusClick(item, holder.getAdapterPosition());
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public ViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
