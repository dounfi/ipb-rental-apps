package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ipb.rental.databinding.FragmentAdminEditItemBinding;

public class AdminEditItemBottomSheet extends BottomSheetDialogFragment {

    private FragmentAdminEditItemBinding binding;
    private AdminItem item;
    private OnItemEditedListener listener;

    public interface OnItemEditedListener {
        void onItemEdited(AdminItem updatedItem);
    }

    public static AdminEditItemBottomSheet newInstance(AdminItem item, OnItemEditedListener listener) {
        AdminEditItemBottomSheet fragment = new AdminEditItemBottomSheet();
        fragment.item = item;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminEditItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSpinners();
        fillData();

        binding.btnSaveChange.setOnClickListener(v -> {
            item.setName(binding.etEditName.getText().toString());
            item.setCategory(binding.spEditCategory.getSelectedItem().toString());
            item.setPricePerDay(binding.etEditPrice.getText().toString());
            item.setCondition(binding.spEditCondition.getSelectedItem().toString());
            item.setStatus(binding.spEditStatus.getSelectedItem().toString());
            item.setDescription(binding.etEditDescription.getText().toString());

            if (listener != null) {
                listener.onItemEdited(item);
            }
            dismiss();
        });

        binding.btnCancelEdit.setOnClickListener(v -> dismiss());
    }

    private void setupSpinners() {
        String[] categories = {"Fotografi", "Elektronik", "Audio", "Drone", "Olahraga", "Akademik"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        binding.spEditCategory.setAdapter(catAdapter);

        String[] conditions = {"Sangat Baik", "Baik", "Cukup"};
        ArrayAdapter<String> condAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, conditions);
        binding.spEditCondition.setAdapter(condAdapter);

        String[] statuses = {"Tersedia", "Disewa", "Diblokir", "Nonaktif"};
        ArrayAdapter<String> statAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        binding.spEditStatus.setAdapter(statAdapter);
    }

    private void fillData() {
        if (item == null) return;
        binding.etEditName.setText(item.getName());
        binding.etEditPrice.setText(item.getPricePerDay());
        binding.etEditDescription.setText(item.getDescription());

        setSpinnerValue(binding.spEditCategory, item.getCategory());
        setSpinnerValue(binding.spEditCondition, item.getCondition());
        setSpinnerValue(binding.spEditStatus, item.getStatus());
    }

    private void setSpinnerValue(android.widget.Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
