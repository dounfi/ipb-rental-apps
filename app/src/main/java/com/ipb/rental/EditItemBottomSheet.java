package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ipb.rental.databinding.FragmentEditItemBinding;
import java.util.Arrays;

public class EditItemBottomSheet extends BottomSheetDialogFragment {

    private FragmentEditItemBinding binding;
    private Item item;
    private int position;
    private OnItemEditedListener listener;

    public interface OnItemEditedListener {
        void onItemEdited(Item updatedItem, int position);
    }

    public static EditItemBottomSheet newInstance(Item item, int position) {
        EditItemBottomSheet sheet = new EditItemBottomSheet();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        args.putInt("position", position);
        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            item = (Item) getArguments().getSerializable("item");
            position = getArguments().getInt("position");
        }

        if (item != null) {
            binding.tvSubtitle.setText(item.getName());
            binding.etNamaItem.setText(item.getName());
            binding.etHarga.setText(item.getPricePerDay().replaceAll("[^0-9]", ""));
            binding.etDeskripsi.setText(item.getDescription());

            setupSpinners();
        }

        binding.btnSimpan.setOnClickListener(v -> {
            String nama = binding.etNamaItem.getText().toString().trim();
            String harga = binding.etHarga.getText().toString().trim();
            if (nama.isEmpty()) {
                binding.etNamaItem.setError("Nama item wajib diisi");
                return;
            }
            if (harga.isEmpty()) {
                binding.etHarga.setError("Harga wajib diisi");
                return;
            }

            item.setName(nama);
            item.setCategory(binding.spinnerKategori.getSelectedItem().toString());
            item.setPricePerDay("Rp " + harga + "/hari");
            item.setShortPrice(harga); // Assuming shortPrice is the numeric part
            item.setCondition(binding.spinnerKondisi.getSelectedItem().toString());
            item.setStatus(binding.spinnerStatus.getSelectedItem().toString());
            item.setDescription(binding.etDeskripsi.getText().toString().trim());

            if (listener != null) listener.onItemEdited(item, position);
            dismiss();
        });

        binding.btnBatal.setOnClickListener(v -> dismiss());
    }

    private void setupSpinners() {
        String[] kategoriOptions = {"Fotografi", "Elektronik", "Audio", "Olahraga", "Lainnya"};
        ArrayAdapter<String> kategoriAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, kategoriOptions);
        binding.spinnerKategori.setAdapter(kategoriAdapter);
        int kategoriIndex = Arrays.asList(kategoriOptions).indexOf(item.getCategory());
        if (kategoriIndex >= 0) binding.spinnerKategori.setSelection(kategoriIndex);

        String[] kondisiOptions = {"Sangat Baik", "Baik", "Cukup", "Rusak Ringan"};
        ArrayAdapter<String> kondisiAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, kondisiOptions);
        binding.spinnerKondisi.setAdapter(kondisiAdapter);
        int kondisiIndex = Arrays.asList(kondisiOptions).indexOf(item.getCondition());
        if (kondisiIndex >= 0) binding.spinnerKondisi.setSelection(kondisiIndex);

        String[] statusOptions = {"Tersedia", "Nonaktif"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, statusOptions);
        binding.spinnerStatus.setAdapter(statusAdapter);
        int statusIndex = Arrays.asList(statusOptions).indexOf(item.getStatus());
        if (statusIndex >= 0) binding.spinnerStatus.setSelection(statusIndex);
    }

    public void setOnItemEditedListener(OnItemEditedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
