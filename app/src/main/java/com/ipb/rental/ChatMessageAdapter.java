package com.ipb.rental;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemChatReceivedBinding;
import com.ipb.rental.databinding.ItemChatSentBinding;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    private final List<ChatMessage> messages;

    public ChatMessageAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).isSentByMe()) {
            return TYPE_SENT;
        } else {
            return TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SENT) {
            ItemChatSentBinding binding = ItemChatSentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SentViewHolder(binding);
        } else {
            ItemChatReceivedBinding binding = ItemChatReceivedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ReceivedViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).bind(message);
        } else {
            ((ReceivedViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatSentBinding binding;

        SentViewHolder(ItemChatSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatMessage message) {
            binding.tvMessage.setText(message.getMessage());
            binding.tvTime.setText(message.getTime());
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatReceivedBinding binding;

        ReceivedViewHolder(ItemChatReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatMessage message) {
            binding.tvMessage.setText(message.getMessage());
            binding.tvTime.setText(message.getTime());
        }
    }
}
