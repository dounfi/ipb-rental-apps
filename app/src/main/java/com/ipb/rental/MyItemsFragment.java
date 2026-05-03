package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentMyItemsBinding;
import java.util.ArrayList;
import java.util.List;

public class MyItemsFragment extends Fragment {

    private FragmentMyItemsBinding binding;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadSampleData();

        binding.btnAddItem.setOnClickListener(v -> {
            AddItemBottomSheet bottomSheet = new AddItemBottomSheet();
            bottomSheet.setOnItemAddedListener(newItem -> {
                itemList.add(newItem);
                adapter.updateList(new ArrayList<>(itemList));
                updateCounts();
            });
            bottomSheet.show(getParentFragmentManager(), "AddItemBottomSheet");
        });
    }

    private void setupRecyclerView() {
        // Fix Scroll Bug
        binding.recyclerViewItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ItemAdapter();
        binding.recyclerViewItems.setAdapter(adapter);
        binding.recyclerViewItems.setNestedScrollingEnabled(false); // WAJIB
        binding.recyclerViewItems.setHasFixedSize(false);           // WAJIB false

        adapter.setOnItemActionListener(new ItemAdapter.OnItemActionListener() {
            @Override
            public void onEditClick(Item item, int position) {
                EditItemBottomSheet sheet = EditItemBottomSheet.newInstance(item, position);
                sheet.setOnItemEditedListener((updatedItem, pos) -> {
                    itemList.set(pos, updatedItem);
                    adapter.updateList(new ArrayList<>(itemList));
                    updateCounts();
                });
                sheet.show(getChildFragmentManager(), "EditItemBottomSheet");
            }

            @Override
            public void onNonaktifkanClick(Item item, int position) {
                if (item.getStatus().equals("Nonaktif")) {
                    item.setStatus("Tersedia");
                } else {
                    item.setStatus("Nonaktif");
                }
                adapter.notifyItemChanged(position);
                updateCounts();
            }

            @Override
            public void onHapusClick(Item item, int position) {
                itemList.remove(position);
                adapter.updateList(new ArrayList<>(itemList));
                updateCounts();
            }
        });
    }

    private void updateCounts() {
        binding.tvItemCount.setText(itemList.size() + " item");
        binding.tvTotalCount.setText(String.valueOf(itemList.size()));
        
        long activeCount = 0;
        long ongoingCount = 0;
        for (Item item : itemList) {
            if (item.getStatus().equals("Tersedia")) {
                activeCount++;
            } else if (item.getStatus().equals("Disewa")) {
                ongoingCount++;
            }
        }
        binding.tvActiveCount.setText(String.valueOf(activeCount));
        binding.tvOngoingCount.setText(String.valueOf(ongoingCount));
    }

    private void loadSampleData() {
        itemList = new ArrayList<>();
        itemList.add(new Item("1", "Sony Alpha A7 III", "Fotografi", R.drawable.ic_logo_placeholder, "Tersedia", "4.8", "12x disewa", "Rp 85.000/hari", "42.500", "Sangat Baik", "Sensor full-frame 24.2MP, video 4K HDR."));
        itemList.add(new Item("2", "MacBook Pro 14\"", "Elektronik", R.drawable.ic_logo_placeholder, "Disewa", "4.9", "9x disewa", "Rp 120.000/hari", "60.000", "Baik", "Apple M1 Pro chip, 16GB RAM."));
        itemList.add(new Item("3", "Sony WH-1000XM5", "Audio", R.drawable.ic_logo_placeholder, "Tersedia", "4.7", "20x disewa", "Rp 30.000/hari", "15.000", "Sangat Baik", "Industry-leading noise canceling."));
        adapter.updateList(new ArrayList<>(itemList));
        updateCounts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
