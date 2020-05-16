package com.fussyvegan.scanner.fastFood;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PageAdapter extends FragmentStatePagerAdapter {
    String mNameChains;
    String mNameCountry;

    public PageAdapter(FragmentManager fm, String nameChains, String country) {
        super(fm);
        mNameChains = nameChains;
        mNameCountry = country;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return SearchAllFragment.newInstance(mNameChains,mNameCountry);
        } else {
            return SearchOnlyVeganFragment.newInstance(mNameChains,mNameCountry);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "All";
        } else {
            return "Vegan Only";
        }
    }
}
