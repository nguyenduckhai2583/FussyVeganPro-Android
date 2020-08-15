package com.fussyvegan.scanner.search;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fussyvegan.scanner.R;

import org.greenrobot.eventbus.EventBus;


public class FilterSearchResortFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imgBack;

    private RadioButton cbNoPalmOil;
    private RadioButton cbGlutenFree;
    private RadioButton cbNutFree;
    private RadioButton cbSoyFree;
    private RadioButton cbVegan;
    private RadioGroup radioGroup;

    private boolean isHostel = false;
    private boolean isBedAndBreakfast = false;
    private boolean isBushCamp = false;
    private boolean isResort = false;
    private boolean isSafariLodge = false;
    private boolean isClear = false;

    public static FilterSearchResortFragment filterSearchResortFragment;
    private String mFilter;

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
        radioGroup = view.findViewById(R.id.radioGroup);

        tvClearFilter.setOnClickListener(this);
        btnApplyFilter.setOnClickListener(this);

        cbVegan.setChecked(isHostel);
        cbNoPalmOil.setChecked(isBedAndBreakfast);
        cbGlutenFree.setChecked(isBushCamp);
        cbNutFree.setChecked(isResort);
        cbSoyFree.setChecked(isSafariLodge);

//
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isHostel = false;
                isBedAndBreakfast = false;
                isBushCamp = false;
                isResort = false;
                isSafariLodge = false;
                switch (checkedId) {
                    case R.id.cb_no_palm_oil:
                        isBedAndBreakfast = true;
                        mFilter = "bed and breakfast";
                        break;
                    case R.id.cb_gluten:
                        isBushCamp = true;
                        mFilter = "bush camp";

                        break;
                    case R.id.cb_nut_free:
                        isResort = true;
                        mFilter = "resort";

                        break;
                    case R.id.cb_soy_free:
                        isSafariLodge = true;
                        mFilter = "safari lodge";
                        break;
                    case R.id.cb_vegan_company:
                        isHostel = true;
                        mFilter = "hostel";
                        break;
                }

            }
        });


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
                mFilter = null;
                break;
            case R.id.btnApplyFilterSearch:
                checkCheckboxStatus();
                EventBus.getDefault().post(new ResortFilter(mFilter, isClear));
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




    private void checkCheckboxStatus() {
        if (!isHostel && !isBedAndBreakfast && !isBushCamp && !isResort && !isSafariLodge) {
            isClear = true;
        }
    }


}