package com.fussyvegan.scanner.container;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fussyvegan.scanner.AdvancedSearchMenuFragment;
import com.fussyvegan.scanner.BaseContainerFragment;
import com.fussyvegan.scanner.ProductFragment;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ScanFragment;

public class ProductsContainerFragment extends BaseContainerFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_container, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new AdvancedSearchMenuFragment(),false);

    }


    @Override
    public void resetPageViewPager() {
        // Handler when need to


    }

    public Fragment getCurrentFragment() {
        return getChildFragmentManager().findFragmentById(R.id.frameLayoutContainer);
    }
}

