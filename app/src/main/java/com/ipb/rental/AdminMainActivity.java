package com.ipb.rental;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.ipb.rental.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {

    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(new AdminDashboardFragment());
            binding.bottomNavAdmin.setSelectedItemId(R.id.nav_admin_dashboard);
        }

        binding.bottomNavAdmin.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_admin_dashboard) {
                fragment = new AdminDashboardFragment();
            } else if (id == R.id.nav_admin_users) {
                fragment = new AdminUsersFragment();
            } else if (id == R.id.nav_admin_item) {
                fragment = new AdminItemFragment();
            } else if (id == R.id.nav_admin_transaksi) {
                fragment = new AdminTransaksiFragment();
            } else if (id == R.id.nav_admin_pengaduan) {
                fragment = new AdminPengaduanFragment();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_admin, fragment)
                .commit();
    }
}
