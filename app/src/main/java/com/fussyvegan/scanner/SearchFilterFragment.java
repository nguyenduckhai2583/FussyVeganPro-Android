package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.model.KeySearch;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.fastFood.PageAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchFilterFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String NAME_COUNTRY = "Country";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<Product> products;
    MainActivity activity;
    ListView ltvProduct;
    ViewPager viewPager;
    TabLayout tabLayout;
    View header;
    SearchView searchView;
    private String mNameChains;
    private String mNameCountry;
    private TextView tvNameChain;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFilterFragment() {
        products = new ArrayList<>();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchFilterFragment newInstance(int columnCount) {
        SearchFilterFragment fragment = new SearchFilterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameChains = getArguments().getString("keySearch", "");
            mNameCountry = getArguments().getString(NAME_COUNTRY, "");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_filter, container, false);
        header = getLayoutInflater().inflate(R.layout.search_header, null);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        tvNameChain = view.findViewById(R.id.tvNameChain);
        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(mNameChains, false);
        if (mNameChains == null || mNameChains.isEmpty()) {
            searchView.setVisibility(View.VISIBLE);
            tvNameChain.setVisibility(View.GONE);
        } else {
            searchView.setVisibility(View.GONE);
            tvNameChain.setVisibility(View.VISIBLE);
            tvNameChain.setText(mNameChains);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String keyword = query.replace("'", "’");
                EventBus.getDefault().post(new KeySearch(keyword));
                Log.d("TAG", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG", newText);
                return false;
            }
        });
        PagerAdapter pagerAdapter = new PageAdapter(getFragmentManager(), mNameChains, mNameCountry);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        searchView.clearFocus();
    }

}
