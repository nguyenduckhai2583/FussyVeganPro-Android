package com.fussyvegan.scanner;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Toan Nguyen T.  on 9/22/18
 */
public class BaseFragment extends Fragment {

    public void replaceFragment(int viewId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(viewId, fragment);
        transaction.commitAllowingStateLoss();
        getFragmentManager().executePendingTransactions();
    }
}
