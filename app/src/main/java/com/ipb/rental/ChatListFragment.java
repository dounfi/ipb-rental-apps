package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentChatListBinding;
import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {

    private FragmentChatListBinding binding;
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.recyclerViewChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        
        List<ChatContact> contacts = new ArrayList<>();
        // Sample Data
        contacts.add(new ChatContact("1", "Dimas P.", "Barang udah saya terima, kondisi oke!", "09:24", 2, Color.parseColor("#1C2B4A"), "Online · Penyewa Aktif", "MacBook Pro 14\"", "Rp 120.000/hari", "1—4 Mar"));
        contacts.add(new ChatContact("2", "Fajar N.", "Bisa perpanjang 2 hari lagi ga?", "Kemarin", 0, Color.parseColor("#0F6E56"), "Online · Penyewa Aktif", "Sony WH-1000XM5", "Rp 30.000/hari", "5—8 Mar"));
        contacts.add(new ChatContact("3", "Siti N.", "Ok noted, terima kasih ya kak!", "Sen", 0, Color.parseColor("#92400E"), "Online · Pemilik", "iPad Pro 11\"", "Rp 75.000/hari", "3—4 Mar"));
        contacts.add(new ChatContact("4", "Hendra W.", "Kapan bisa dipickup kak?", "Ming", 1, Color.parseColor("#D97706"), "Online · Pemilik", "Jas Lab Praktikum", "Rp 10.000/hari", "10 Feb"));
        contacts.add(new ChatContact("5", "Maya S.", "Oke siap, sampai besok!", "24 Feb", 0, Color.parseColor("#7C3AED"), "Online · Pemilik", "DJI Mini 4 Pro", "Rp 150.000/hari", "5—8 Mar"));

        adapter = new ChatListAdapter(contacts, contact -> {
            ChatDetailFragment detailFragment = new ChatDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("contact", contact);
            detailFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack("chat_list")
                    .commit();
        });
        
        binding.recyclerViewChatList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}