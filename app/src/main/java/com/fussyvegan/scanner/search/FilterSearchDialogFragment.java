package com.fussyvegan.scanner.search;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.SearchFragment;
import com.fussyvegan.scanner.activity.MainActivity;

public class FilterSearchDialogFragment extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ImageView imgBack;

    private CheckBox cbNoPalmOil;
    private CheckBox cbNoGMO;
    private CheckBox cbGlutenFree;
    private CheckBox cbNutFree;
    private CheckBox cbSoyFree;
    private CheckBox cbVegan;

    private boolean isNoPalmOil = false;
    private boolean isNoGMO = false;
    private boolean isGlutenFree = false;
    private boolean isNutFree = false;
    private boolean isSoyFree = false;
    private boolean isVeganCompany = false;
    private boolean isClear = false;

    public static FilterSearchDialogFragment filterSearchDialogFragment;

    public static FilterSearchDialogFragment newInstance() {
        if (filterSearchDialogFragment == null) {
            filterSearchDialogFragment = new FilterSearchDialogFragment();
        }

        return filterSearchDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_filter_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvClearFilter = view.findViewById(R.id.tv_clear_filter);
        Button btnApplyFilter = view.findViewById(R.id.btnApplyFilterSearch);

        imgBack = view.findViewById(R.id.imgBack);

        cbNoPalmOil = view.findViewById(R.id.cb_no_palm_oil);
        cbNoGMO = view.findViewById(R.id.cb_no_gmo);
        cbGlutenFree = view.findViewById(R.id.cb_gluten);
        cbNutFree = view.findViewById(R.id.cb_nut_free);
        cbSoyFree = view.findViewById(R.id.cb_soy_free);
        cbVegan = view.findViewById(R.id.cb_vegan_company);

        tvClearFilter.setOnClickListener(this);
        btnApplyFilter.setOnClickListener(this);

        cbNoPalmOil.setChecked(isNoPalmOil);
        cbNoGMO.setChecked(isNoGMO);
        cbGlutenFree.setChecked(isGlutenFree);
        cbNutFree.setChecked(isNutFree);
        cbSoyFree.setChecked(isSoyFree);
        cbVegan.setChecked(isVeganCompany);

        cbNoPalmOil.setOnCheckedChangeListener(this);
        cbNoGMO.setOnCheckedChangeListener(this);
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
                if (getFragmentManager() != null) {
                    SearchFragment searchFragment = null;
                    searchFragment = (SearchFragment) getFragmentManager().findFragmentByTag("SearchFragment");
                    if (searchFragment != null) {
                        updateCheckbox();
                        checkCheckboxStatus();
                        searchFragment.updateFilter(isClear, isNoPalmOil, isNoGMO, isGlutenFree, isNutFree, isSoyFree, isVeganCompany);
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
        cbNoGMO.setChecked(false);
        cbGlutenFree.setChecked(false);
        cbNutFree.setChecked(false);
        cbSoyFree.setChecked(false);
        cbVegan.setChecked(false);

        isClear = true;

        isNoPalmOil = false;
        isNoGMO = false;
        isGlutenFree = false;
        isNutFree = false;
        isSoyFree = false;
        isVeganCompany = false;
    }

    private void updateCheckbox() {
        isNoPalmOil = cbNoPalmOil.isChecked();
        isNoGMO = cbNoGMO.isChecked();
        isGlutenFree = cbGlutenFree.isChecked();
        isNutFree = cbNutFree.isChecked();
        isSoyFree = cbSoyFree.isChecked();
        isVeganCompany = cbVegan.isChecked();
    }

    private void checkCheckboxStatus() {
        if (!isNoPalmOil && !isNoGMO && !isGlutenFree && !isNutFree && !isSoyFree && !isVeganCompany) {
            isClear = true;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isClear = false;
    }
}