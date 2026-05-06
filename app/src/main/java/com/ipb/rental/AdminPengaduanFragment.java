package com.ipb.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.ipb.rental.databinding.FragmentAdminPengaduanBinding;
import java.util.ArrayList;
import java.util.List;

public class AdminPengaduanFragment extends Fragment implements AdminPengaduanAdapter.OnPengaduanActionListener {

    private FragmentAdminPengaduanBinding binding;
    private AdminPengaduanAdapter adapter;
    private List<AdminPengaduan> pengaduanList;
    private TextView currentActiveChip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminPengaduanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupRecyclerView();
        setupFilters();
        setupSearch();
    }

    private void setupData() {
        pengaduanList = new ArrayList<>();
        pengaduanList.add(new AdminPengaduan("1", "Kondisi Item", "Ahmad Sony", "sony@apps.ipb.ac.id", "Sony WH-1000XM5", "Headphone yang diterima tidak sesuai deskripsi. Ada bagian yang rusak di bagian ear cup kanan. Saya sudah foto kondisi kerusakannya.", "Baru", "8 jam lalu"));
        pengaduanList.add(new AdminPengaduan("2", "Keterlambatan", "Hendra Wijaya", "hendra@apps.ipb.ac.id", "GoPro Hero 12", "Pemilik terlambat menyerahkan item lebih dari 3 jam dari waktu yang disepakati, tanpa konfirmasi terlebih dahulu.", "Diproses", "1 hari lalu"));
        pengaduanList.add(new AdminPengaduan("3", "Refund DP", "Maya Port", "maya@apps.ipb.ac.id", "DJI Mini 4 Pro", "Saya membatalkan pemesanan lebih dari 24 jam sebelum sewa, namun DP sebesar Rp 90.000 belum dikembalikan setelah 5 hari.", "Baru", "1 hari lalu"));
        pengaduanList.add(new AdminPengaduan("4", "Sengketa Barang", "Budi Santoso", "budi@apps.ipb.ac.id", "iPad Pro 11", "Penyewa mengakui menghilangkan item namun pemilik tidak menerima. Butuh mediasi admin untuk penyelesaian.", "Diproses", "2 hari lalu"));
        pengaduanList.add(new AdminPengaduan("5", "Rating Tidak Adil", "Fajar N", "fajar@apps.ipb.ac.id", "Portable Projector", "Penyewa memberi rating 1 bintang tanpa alasan jelas. Item dikembalikan tepat waktu dan dalam kondisi baik.", "Selesai", "3 hari lalu"));
        pengaduanList.add(new AdminPengaduan("6", "Barang Rusak", "Maya Sari", "maya@apps.ipb.ac.id", "Canon EOS R6", "Kamera dikembalikan dalam layar retak. Pemilik meminta ganti rugi namun penyewa tidak mau konfirmasi.", "Baru", "4 hari lalu"));
        pengaduanList.add(new AdminPengaduan("7", "Keterlambatan", "Ahmad Rizky", "rizky@apps.ipb.ac.id", "Sony Alpha A7 III", "Penyewa belum mengembalikan item sejak 2 hari dari jadwal selesai. Tidak bisa dihubungi.", "Diproses", "5 hari lalu"));
        pengaduanList.add(new AdminPengaduan("8", "Refund DP", "Siti Nurhaliza", "siti@apps.ipb.ac.id", "MacBook Pro 14", "Booking dibatalkan oleh pemilik mendadak H-1. Saya sudah transfer DP namun belum ada kepastian pengembalian.", "Selesai", "1 minggu lalu"));
    }

    private void setupRecyclerView() {
        adapter = new AdminPengaduanAdapter(pengaduanList, this);
        binding.recyclerViewPengaduan.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPengaduan.setAdapter(adapter);
    }

    private void setupFilters() {
        currentActiveChip = binding.chipSemua;

        binding.chipSemua.setOnClickListener(v -> setActiveChip(binding.chipSemua, "Semua", false));
        binding.chipBaru.setOnClickListener(v -> setActiveChip(binding.chipBaru, "Baru", false));
        binding.chipDiproses.setOnClickListener(v -> setActiveChip(binding.chipDiproses, "Diproses", false));
        binding.chipSelesai.setOnClickListener(v -> setActiveChip(binding.chipSelesai, "Selesai", false));
        binding.chipKembalikanItem.setOnClickListener(v -> setActiveChip(binding.chipKembalikanItem, "Kembalikan Item", true));
    }

    private void setActiveChip(TextView chip, String value, boolean isKategori) {
        currentActiveChip.setBackgroundResource(R.drawable.bg_chip_inactive_admin);
        currentActiveChip.setTextColor(Color.parseColor("#6B7280"));
        currentActiveChip.setTypeface(null, android.graphics.Typeface.NORMAL);

        chip.setBackgroundResource(R.drawable.bg_chip_active_admin);
        chip.setTextColor(Color.WHITE);
        chip.setTypeface(null, android.graphics.Typeface.BOLD);

        currentActiveChip = chip;
        
        if (isKategori) {
            adapter.filterByKategori(value);
        } else {
            adapter.filterByStatus(value);
        }
    }

    private void setupSearch() {
        binding.etSearchPengaduan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterBySearch(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onProsesClick(AdminPengaduan item, int position) {
        adapter.updateStatus(item.getId(), "Diproses");
        Toast.makeText(getContext(), "Status diperbarui ke Diproses", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelesaiClick(AdminPengaduan item, int position) {
        adapter.updateStatus(item.getId(), "Selesai");
        Toast.makeText(getContext(), "Pengaduan selesai ditangani", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHubungiClick(AdminPengaduan item) {
        Toast.makeText(getContext(), "Hubungi " + item.getPelaporName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardClick(AdminPengaduan item) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_admin, AdminPengaduanDetailFragment.newInstance(item))
                .addToBackStack("pengaduan_detail")
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
