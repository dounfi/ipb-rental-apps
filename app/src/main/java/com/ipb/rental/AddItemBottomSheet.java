package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ipb.rental.databinding.FragmentAddItemBinding;

public class AddItemBottomSheet extends BottomSheetDialogFragment {

    private FragmentAddItemBinding binding;

    public interface OnItemAddedListener {
        void onItemAdded(Item item);
    }

    private OnItemAddedListener listener;

    public void setOnItemAddedListener(OnItemAddedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSpinners();

        binding.btnSimpan.setOnClickListener(v -> {
            String name = binding.etNamaItem.getText().toString().trim();
            String price = binding.etHarga.getText().toString().trim();

            if (name.isEmpty()) {
                binding.etNamaItem.setError("Nama item wajib diisi");
                return;
            }
            if (price.isEmpty()) {
                binding.etHarga.setError("Harga wajib diisi");
                return;
            }

            // Logic to create item and notify listener
            if (listener != null) {
                Item newItem = new Item(
                        String.valueOf(System.currentTimeMillis()),
                        name,
                        binding.spKategori.getSelectedItem().toString(),
                        R.drawable.ic_logo_placeholder,
                        binding.spStatus.getSelectedItem().toString(),
                        "0.0",
                        "0x disewa",
                        "Rp " + price + "/hari",
                        price,
                        binding.spKondisi.getSelectedItem().toString(),
                        binding.etDeskripsi.getText().toString()
                );
                listener.onItemAdded(newItem);
            }
            dismiss();
        });

        binding.btnBatal.setOnClickListener(v -> dismiss());
    }

    private void setupSpinners() {
        String[] categories = {"Fotografi", "Elektronik", "Audio", "Alat Olahraga", "Hobi"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        binding.spKategori.setAdapter(catAdapter);

        String[] conditions = {"Sangat Baik", "Baik", "Cukup", "Kurang"};
        ArrayAdapter<String> condAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, conditions);
        binding.spKondisi.setAdapter(condAdapter);

        String[] status = {"Tersedia", "Disewa", "Nonaktif"};
        ArrayAdapter<String> statAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, status);
        binding.spStatus.setAdapter(statAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
