package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        binding.btnSave.setOnClickListener(v -> {
            // Logic to save profile changes would go here
            Toast.makeText(getContext(), "Profil diperbarui", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });
        
        // Populate fields with current data if available
        populateFields();
    }

    private void populateFields() {
        binding.etFullName.setText("Erisa Salsabila");
        binding.etEmail.setText("erisa@apps.ipb.ac.id");
        binding.etPhone.setText("+6281234567890");
        binding.etFaculty.setText("FMIPA / Ilmu Komputer");
        binding.etNim.setText("G64123456");
        binding.etBio.setText("Mahasiswa IPB aktif. Sering menyewakan peralatan fotografi dan elektronik.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
