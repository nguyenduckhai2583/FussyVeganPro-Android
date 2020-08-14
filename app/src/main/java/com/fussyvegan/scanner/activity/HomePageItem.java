package com.fussyvegan.scanner.activity;


import androidx.fragment.app.Fragment;

public class HomePageItem {
    private Fragment fragment;

    public HomePageItem(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
