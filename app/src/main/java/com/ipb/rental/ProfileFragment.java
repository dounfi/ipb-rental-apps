package com.ipb.rental;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ipb.rental.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnEditProfile.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EditProfileFragment())
                    .addToBackStack("profile")
                    .commit();
        });

        binding.menuHelpCenter.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ReportFragment())
                    .addToBackStack("profile")
                    .commit();
        });

        binding.btnLogout.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout?")
                    .setMessage("Yakin mau logout?")
                    .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Logout", (dialog, which) -> {
                        // Handle logout logic here
                        requireActivity().finish();
                    })
                    .show();
        });

        binding.menuAdminPanel.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AdminLoginActivity.class);
            startActivity(intent);
        });

        // Other menu clicks can be added here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
