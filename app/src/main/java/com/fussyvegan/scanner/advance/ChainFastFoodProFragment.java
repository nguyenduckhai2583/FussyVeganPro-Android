package com.fussyvegan.scanner.advance;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.BaseFragment;
import com.fussyvegan.scanner.OnListFragmentInteractionListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.ChainFoodAdapter;
import com.fussyvegan.scanner.model.ChainFastFood;

import java.util.ArrayList;
import java.util.List;

import static com.fussyvegan.scanner.Constant.ARG_NAME_SEARCH;
import static com.fussyvegan.scanner.Constant.ARG_POSITION_CLICK;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChainFastFoodProFragment extends BaseFragment implements ChainFoodAdapter.IChainListener {
    // TODO: Customize parameter argument names
    private static final String NAME_COUNTRY = "Country";
    private static final String NAME_OF_COUNTRY = "NameCountry";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<ChainFastFood> mChains = new ArrayList<>();
    MainActivity activity;
    RecyclerView recyclerView;
    RelativeLayout rlSearchAll;
    TextView tvNameCategory;
    View header;
    SearchView searchView;
    ChainFoodAdapter mAdapter;
    String keyWord;
    String mNameChain;
    String mNameOfCountry;
    private int position = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameChain = getArguments().getString(ARG_NAME_SEARCH);
            position = getArguments().getInt(ARG_POSITION_CLICK);
            mNameOfCountry = getArguments().getString(NAME_OF_COUNTRY);
        }
        activity = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_fastfood_pro, container, false);
        recyclerView = view.findViewById(R.id.rcNameChain);
        tvNameCategory = view.findViewById(R.id.tvNameCategory);
        mAdapter = new ChainFoodAdapter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        tvNameCategory.setText(mNameChain);
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
        mChains.clear();
        switch (position){
            case 0 :
                initData();
                break;
            case 1 :
                initData();
                break;
            case 2 :
                initData();
                break;
            case 3 :
                initData();
                break;
        }

    }

    void initData() {
        mChains.add(new ChainFastFood("Beef", "ic_bakers_delight"));
        mChains.add(new ChainFastFood("chicken", "ic_boost_juice"));
        mChains.add(new ChainFastFood("fish", "ic_baskin_robbins"));
        mChains.add(new ChainFastFood("pork", "ic_bcc_cinema"));
        mAdapter.updateData(mChains);
    }




    @Override
    public void onClickChange(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME_SEARCH, mChains.get(position).getName());
        ProductKeywordFragment fragment = new ProductKeywordFragment();
        fragment.setArguments(bundle);
        String tag = "ProductFragment";
        replaceFragment(R.id.frameLayoutContainer, fragment, true);

    }
}
