package com.ipb.rental;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemChatListBinding;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final List<ChatContact> contacts;
    private final OnChatClickListener listener;

    public interface OnChatClickListener {
        void onChatClick(ChatContact contact);
    }

    public ChatListAdapter(List<ChatContact> contacts, OnChatClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatListBinding binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatContact contact = contacts.get(position);
        holder.binding.tvContactName.setText(contact.getName());
        holder.binding.tvLastMessage.setText(contact.getLastMessage());
        holder.binding.tvChatTime.setText(contact.getTime());

        // Avatar Initial
        if (contact.getName() != null && !contact.getName().isEmpty()) {
            holder.binding.tvAvatarInitial.setText(String.valueOf(contact.getName().charAt(0)).toUpperCase());
        }
        holder.binding.viewAvatarBg.setBackgroundTintList(ColorStateList.valueOf(contact.getAvatarColor()));

        // Unread Badge
        if (contact.getUnreadCount() > 0) {
            holder.binding.tvUnreadBadge.setVisibility(View.VISIBLE);
            holder.binding.tvUnreadBadge.setText(String.valueOf(contact.getUnreadCount()));
        } else {
            holder.binding.tvUnreadBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onChatClick(contact));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemChatListBinding binding;
        public ViewHolder(ItemChatListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}