package com.ipb.rental;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ipb.rental.databinding.ItemAdminUserBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.UserViewHolder> {

    private List<AdminUser> allUsers;
    private List<AdminUser> filteredUsers;
    private String currentRoleFilter = "Semua";
    private String currentSearchQuery = "";
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(AdminUser user);
    }

    public AdminUserAdapter(List<AdminUser> users, OnUserClickListener listener) {
        this.allUsers = new ArrayList<>(users);
        this.filteredUsers = new ArrayList<>(users);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminUserBinding binding = ItemAdminUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        AdminUser user = filteredUsers.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return filteredUsers.size();
    }

    public void filterByRole(String role) {
        this.currentRoleFilter = role;
        applyFilters();
    }

    public void filterBySearch(String query) {
        this.currentSearchQuery = query.toLowerCase();
        applyFilters();
    }

    private void applyFilters() {
        filteredUsers = allUsers.stream()
                .filter(user -> {
                    boolean roleMatch = currentRoleFilter.equals("Semua") ||
                            (currentRoleFilter.equals("Diblokir") ? user.getStatus().equals("Diblokir") : user.getRole().equals(currentRoleFilter));
                    
                    boolean searchMatch = currentSearchQuery.isEmpty() ||
                            user.getName().toLowerCase().contains(currentSearchQuery) ||
                            user.getEmail().toLowerCase().contains(currentSearchQuery) ||
                            user.getNim().toLowerCase().contains(currentSearchQuery);
                    
                    return roleMatch && searchMatch;
                })
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }
    
    public void updateStatus(String userId, String newStatus) {
        for (AdminUser user : allUsers) {
            if (user.getId().equals(userId)) {
                user.setStatus(newStatus);
                break;
            }
        }
        applyFilters();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemAdminUserBinding binding;

        UserViewHolder(ItemAdminUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(AdminUser user) {
            binding.tvUserName.setText(user.getName());
            binding.tvUserEmail.setText(user.getEmail());
            binding.tvInitial.setText(String.valueOf(user.getName().charAt(0)));
            binding.avatarContainer.setBackgroundTintList(ColorStateList.valueOf(user.getAvatarColor()));

            binding.tvRoleBadge.setText(user.getRole());
            if (user.getRole().equals("Penyewa")) {
                binding.tvRoleBadge.setBackgroundResource(R.drawable.bg_badge_blue);
                binding.tvRoleBadge.setTextColor(Color.parseColor("#1D4ED8"));
            } else if (user.getRole().equals("Pemilik")) {
                binding.tvRoleBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D1FAE5")));
                binding.tvRoleBadge.setTextColor(Color.parseColor("#065F46"));
            } else if (user.getRole().equals("Admin")) {
                binding.tvRoleBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E8EDF4")));
                binding.tvRoleBadge.setTextColor(Color.parseColor("#1C2B4A"));
            }

            binding.tvStatusBadge.setText(user.getStatus());
            if (user.getStatus().equals("Aktif")) {
                binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DCFCE7")));
                binding.tvStatusBadge.setTextColor(Color.parseColor("#16A34A"));
            } else {
                binding.tvStatusBadge.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FEE2E2")));
                binding.tvStatusBadge.setTextColor(Color.parseColor("#DC2626"));
            }

            binding.getRoot().setOnClickListener(v -> listener.onUserClick(user));
        }
    }
}
