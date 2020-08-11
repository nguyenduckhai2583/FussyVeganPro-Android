package com.fussyvegan.scanner.search;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ResortsFragment;
import com.fussyvegan.scanner.SearchFragment;


public class FilterSearchResortFragment extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView imgBack;

    private CheckBox cbNoPalmOil;
    private CheckBox cbGlutenFree;
    private CheckBox cbNutFree;
    private CheckBox cbSoyFree;
    private CheckBox cbVegan;

    private boolean isHostel = false;
    private boolean isBedAndBreakfast = false;
    private boolean isBushCamp = false;
    private boolean isResort = false;
    private boolean isSafariLodge = false;
    private boolean isClear = false;

    public static FilterSearchResortFragment filterSearchResortFragment;

    public static FilterSearchResortFragment newInstance() {
        if (filterSearchResortFragment == null) {
            filterSearchResortFragment = new FilterSearchResortFragment();
        }

        return filterSearchResortFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_search_resort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvClearFilter = view.findViewById(R.id.tv_clear_filter);
        Button btnApplyFilter = view.findViewById(R.id.btnApplyFilterSearch);

        imgBack = view.findViewById(R.id.imgBack);

        cbNoPalmOil = view.findViewById(R.id.cb_no_palm_oil);
        cbGlutenFree = view.findViewById(R.id.cb_gluten);
        cbNutFree = view.findViewById(R.id.cb_nut_free);
        cbSoyFree = view.findViewById(R.id.cb_soy_free);
        cbVegan = view.findViewById(R.id.cb_vegan_company);

        tvClearFilter.setOnClickListener(this);
        btnApplyFilter.setOnClickListener(this);

        cbVegan.setChecked(isHostel);
        cbNoPalmOil.setChecked(isBedAndBreakfast);
        cbGlutenFree.setChecked(isBushCamp);
        cbNutFree.setChecked(isResort);
        cbSoyFree.setChecked(isSafariLodge);

        cbNoPalmOil.setOnCheckedChangeListener(this);
        cbGlutenFree.setOnCheckedChangeListener(this);
        cbNutFree.setOnCheckedChangeListener(this);
        cbSoyFree.setOnCheckedChangeListener(this);
        cbVegan.setOnCheckedChangeListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_clear_filter:
                setCheckboxClear();
                break;
            case R.id.btnApplyFilterSearch:
                Log.e("thanh cong","thanh cong");
                if (getFragmentManager() != null) {
                    ResortsFragment resortsFragment = null;
                    resortsFragment = (ResortsFragment) getFragmentManager().findFragmentByTag("ResortsFragment");
                    if (resortsFragment != null) {
                        updateCheckbox();
                        checkCheckboxStatus();
                        resortsFragment.updateFilter(isClear, isHostel, isBedAndBreakfast, isBushCamp, isResort, isSafariLodge);
                    }
                }
                getDialog().dismiss();
                break;
            case R.id.imgBack:
                dismiss();
                break;
        }
    }

    private void setCheckboxClear() {
        cbNoPalmOil.setChecked(false);
        cbGlutenFree.setChecked(false);
        cbNutFree.setChecked(false);
        cbSoyFree.setChecked(false);
        cbVegan.setChecked(false);

        isClear = true;

        isHostel = false;
        isBedAndBreakfast = false;
        isBushCamp = false;
        isResort = false;
        isSafariLodge = false;
    }


    private void updateCheckbox() {
        isHostel = cbVegan.isChecked();
        isBedAndBreakfast = cbNoPalmOil.isChecked();
        isBushCamp = cbGlutenFree.isChecked();
        isResort = cbNutFree.isChecked();
        isSafariLodge = cbSoyFree.isChecked();

    }

    private void checkCheckboxStatus() {
        if (!isHostel && !isBedAndBreakfast && !isBushCamp && !isResort && !isSafariLodge) {
            isClear = true;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isClear = false;
    }
}