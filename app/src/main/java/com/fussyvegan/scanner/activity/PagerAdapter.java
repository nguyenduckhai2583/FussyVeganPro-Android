package com.fussyvegan.scanner.activity;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<HomePageItem> tabItems = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, Context context, List<HomePageItem> items) {
        super(fm);
        this.tabItems = items;

    }

    @Override
    public Fragment getItem(final int position) {
        return tabItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabItems.size();
    }


}


