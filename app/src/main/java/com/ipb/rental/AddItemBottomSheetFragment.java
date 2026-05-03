package com.ipb.rental;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddItemBottomSheetFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_bottom_sheet_add_item, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
            }
        });
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setSkipCollapsed(true);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spCategory = view.findViewById(R.id.sp_category);
        Spinner spCondition = view.findViewById(R.id.sp_condition);
        Spinner spStatus = view.findViewById(R.id.sp_status);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        String[] categories = {"Fotografi", "Elektronik", "Olahraga", "Musik", "Lainnya"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(catAdapter);

        String[] conditions = {"Sangat Baik", "Baik", "Cukup"};
        ArrayAdapter<String> condAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, conditions);
        spCondition.setAdapter(condAdapter);

        String[] statuses = {"Tersedia", "Tidak Tersedia"};
        ArrayAdapter<String> statAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        spStatus.setAdapter(statAdapter);

        btnCancel.setOnClickListener(v -> dismiss());
    }
}