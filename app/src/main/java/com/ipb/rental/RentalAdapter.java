package com.ipb.rental;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class RentalAdapter extends RecyclerView.Adapter<RentalAdapter.ViewHolder> {

    private List<RentalItem> items;
    private String currentMode; // "penyewa" atau "pemilik"
    private OnRentalActionListener listener;

    public interface OnRentalActionListener {
        void onChatClick(RentalItem item);
        void onDetailClick(RentalItem item, String mode);
        void onBatalkanClick(RentalItem item);
        void onKonfirmasiClick(RentalItem item);
    }

    public RentalAdapter(List<RentalItem> items, String mode, OnRentalActionListener listener) {
        this.items = items;
        this.currentMode = mode;
        this.listener = listener;
    }

    public void setMode(String mode) {
        this.currentMode = mode;
    }

    public void updateList(List<RentalItem> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rental_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RentalItem item = items.get(position);

        holder.tvItemName.setText(item.getItemName());
        holder.ivImage.setImageResource(item.getImageRes());
        holder.tvPrice.setText(item.getTotalPrice() + " " + item.getPriceLabel());
        holder.tvDate.setText(item.getDateRange());

        if (currentMode.equals("penyewa")) {
            holder.tvOwner.setText("dari " + item.getPersonName());
        } else {
            holder.tvOwner.setText("Oleh " + item.getPersonName());
        }

        // Status Logic
        switch (item.getStatus()) {
            case "Aktif":
                holder.viewIndicator.setBackgroundColor(Color.parseColor("#22C55E"));
                holder.tvStatusBadge.setText("Aktif");
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_aktif);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#16A34A"));
                
                holder.btnChat.setVisibility(View.VISIBLE);
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnBatalkan.setVisibility(View.GONE);
                holder.btnKonfirmasi.setVisibility(View.GONE);
                break;

            case "Pending":
                holder.viewIndicator.setBackgroundColor(Color.parseColor("#F59E0B"));
                holder.tvStatusBadge.setText("Pending");
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_pending);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#92400E"));

                if (currentMode.equals("penyewa")) {
                    holder.btnBatalkan.setVisibility(View.VISIBLE);
                    holder.btnKonfirmasi.setVisibility(View.GONE);
                } else {
                    holder.btnKonfirmasi.setVisibility(View.VISIBLE);
                    holder.btnBatalkan.setVisibility(View.GONE);
                }
                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnChat.setVisibility(View.GONE);
                break;

            case "Selesai":
                holder.viewIndicator.setBackgroundColor(Color.parseColor("#0F6E56"));
                holder.tvStatusBadge.setText("Selesai");
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_selesai);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#0F6E56"));

                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnChat.setVisibility(View.GONE);
                holder.btnBatalkan.setVisibility(View.GONE);
                holder.btnKonfirmasi.setVisibility(View.GONE);
                break;

            case "Dibatalkan":
                holder.viewIndicator.setBackgroundColor(Color.parseColor("#EF4444"));
                holder.tvStatusBadge.setText("Dibatalkan");
                holder.tvStatusBadge.setBackgroundResource(R.drawable.bg_status_batal);
                holder.tvStatusBadge.setTextColor(Color.parseColor("#DC2626"));

                holder.btnDetail.setVisibility(View.VISIBLE);
                holder.btnChat.setVisibility(View.GONE);
                holder.btnBatalkan.setVisibility(View.GONE);
                holder.btnKonfirmasi.setVisibility(View.GONE);
                break;
        }

        // Listeners
        holder.btnChat.setOnClickListener(v -> listener.onChatClick(item));
        holder.btnDetail.setOnClickListener(v -> listener.onDetailClick(item, currentMode));
        holder.btnBatalkan.setOnClickListener(v -> listener.onBatalkanClick(item));
        holder.btnKonfirmasi.setOnClickListener(v -> listener.onKonfirmasiClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View viewIndicator;
        ShapeableImageView ivImage;
        TextView tvItemName, tvOwner, tvStatusBadge, tvDate, tvPrice;
        MaterialButton btnChat, btnDetail, btnBatalkan, btnKonfirmasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIndicator = itemView.findViewById(R.id.viewStatusIndicator);
            ivImage = itemView.findViewById(R.id.ivRentalImage);
            tvItemName = itemView.findViewById(R.id.tvRentalItemName);
            tvOwner = itemView.findViewById(R.id.tvRentalOwner);
            tvStatusBadge = itemView.findViewById(R.id.tvRentalStatusBadge);
            tvDate = itemView.findViewById(R.id.tvRentalDate);
            tvPrice = itemView.findViewById(R.id.tvRentalPrice);
            btnChat = itemView.findViewById(R.id.btnRentalChat);
            btnDetail = itemView.findViewById(R.id.btnRentalDetail);
            btnBatalkan = itemView.findViewById(R.id.btnRentalBatalkan);
            btnKonfirmasi = itemView.findViewById(R.id.btnRentalKonfirmasi);
        }
    }
}
