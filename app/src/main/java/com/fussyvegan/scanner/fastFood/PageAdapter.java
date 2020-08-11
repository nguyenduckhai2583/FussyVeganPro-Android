package com.fussyvegan.scanner.fastFood;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


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
