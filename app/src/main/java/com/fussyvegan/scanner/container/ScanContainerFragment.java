package com.fussyvegan.scanner.container;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.fussyvegan.scanner.BaseContainerFragment;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.ScanFragment;


public class ScanContainerFragment extends BaseContainerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_container, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(new ScanFragment(),false);

    }

    @Override
    public void resetPageViewPager() {
        // Handler when need to
    }


}

