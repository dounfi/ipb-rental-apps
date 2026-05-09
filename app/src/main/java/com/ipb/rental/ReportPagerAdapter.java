package com.ipb.rental;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ReportPagerAdapter extends FragmentStateAdapter {

    public ReportPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new BuatLaporanFragment();
        } else {
            return new LaporanSayaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
