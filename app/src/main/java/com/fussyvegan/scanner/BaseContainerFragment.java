package com.fussyvegan.scanner;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public abstract class BaseContainerFragment extends Fragment {

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());

        }
        transaction.replace(R.id.frameLayoutContainer, fragment);
        transaction.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }


    public boolean popFragment() {
        boolean isPop = false;
        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            isPop = true;
            fm.popBackStack();
        }
        return isPop;
    }

    public int numberFragment() {
        FragmentManager fm = getChildFragmentManager();
        return fm.getBackStackEntryCount();
    }

    public abstract void resetPageViewPager();
}
