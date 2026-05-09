package com.ipb.rental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.FragmentBuatLaporanBinding;

public class BuatLaporanFragment extends Fragment {

    private FragmentBuatLaporanBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuatLaporanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnKirimLaporan.setOnClickListener(v -> {
            String deskripsi = binding.etDeskripsi.getText().toString().trim();
            if (deskripsi.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi deskripsi laporan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Laporan berhasil dikirim!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnUpload.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Fitur upload akan segera hadir", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
