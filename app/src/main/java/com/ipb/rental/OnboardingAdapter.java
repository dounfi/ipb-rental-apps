package com.ipb.rental;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> items;

    public OnboardingAdapter(List<OnboardingItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_onboarding_page, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        OnboardingItem item = items.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDescription());
        holder.ivIllustration.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivIllustration;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivIllustration = itemView.findViewById(R.id.iv_illustration);
        }
    }
}