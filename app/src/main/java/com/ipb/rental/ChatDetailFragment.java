package com.ipb.rental;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentChatDetailBinding;
import java.util.ArrayList;
import java.util.List;

public class ChatDetailFragment extends Fragment {

    private FragmentChatDetailBinding binding;
    private ChatContact contact;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            contact = (ChatContact) getArguments().getSerializable("contact");
            if (contact != null) {
                setupUI();
                setupRecyclerView();
                loadSampleMessages();
            }
        }

        binding.btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        
        binding.btnSend.setOnClickListener(v -> {
            String text = binding.etMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                sendMessage(text);
                binding.etMessage.setText("");
            }
        });
    }

    private void setupUI() {
        binding.tvContactName.setText(contact.getName());
        binding.tvStatus.setText(contact.getStatusText());
        binding.tvAvatarInitial.setText(String.valueOf(contact.getName().charAt(0)).toUpperCase());
        binding.viewAvatarBg.setBackgroundTintList(ColorStateList.valueOf(contact.getAvatarColor()));

        binding.tvItemName.setText(contact.getItemName());
        binding.tvItemDetails.setText(contact.getItemPrice() + " • " + contact.getItemDateRange());
    }

    private void setupRecyclerView() {
        adapter = new ChatMessageAdapter(messages);
        binding.recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewMessages.setAdapter(adapter);
    }

    private void loadSampleMessages() {
        messages.add(new ChatMessage("1", "Halo kak, MacBook Pro-nya masih tersedia untuk tanggal 1-4 Maret?", "08:30", true));
        messages.add(new ChatMessage("2", "Halo! Masih ada kak, silakan diorder.", "08:35", false));
        messages.add(new ChatMessage("3", "Sudah saya order ya kak, mohon diproses.", "09:00", true));
        messages.add(new ChatMessage("4", contact.getLastMessage(), contact.getTime(), false));
        adapter.notifyDataSetChanged();
        binding.recyclerViewMessages.scrollToPosition(messages.size() - 1);
    }

    private void sendMessage(String text) {
        messages.add(new ChatMessage(String.valueOf(messages.size() + 1), text, "Sekarang", true));
        adapter.notifyItemInserted(messages.size() - 1);
        binding.recyclerViewMessages.scrollToPosition(messages.size() - 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
