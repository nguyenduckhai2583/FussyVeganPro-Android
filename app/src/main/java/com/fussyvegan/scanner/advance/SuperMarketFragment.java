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
import com.fussyvegan.scanner.OnListFragmentInteractionListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.ChainFoodAdapter;
import com.fussyvegan.scanner.model.ChainFastFood;

import java.util.ArrayList;
import java.util.List;

import static com.fussyvegan.scanner.Constant.ARG_NAME_SEARCH;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SuperMarketFragment extends Fragment implements ChainFoodAdapter.IChainListener {
    // TODO: Customize parameter argument names
    private static final String NAME_COUNTRY = "Country";
    private static final String NAME_OF_COUNTRY = "NameCountry";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<ChainFastFood> mCurrent = new ArrayList<>();
    MainActivity activity;
    RecyclerView recyclerView;
    RelativeLayout rlSearchAll;
    TextView tvNameCountry;
    View header;
    SearchView searchView;
    ChainFoodAdapter mAdapter;
    String keyWord;
    String mNameCountry;
    String mNameOfCountry;
    List<String> mKeySearch = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameCountry = getArguments().getString(NAME_COUNTRY);
            mNameOfCountry = getArguments().getString(NAME_OF_COUNTRY);
        }
        activity = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_add, container, false);
        recyclerView = view.findViewById(R.id.rcNameChain);
        mAdapter = new ChainFoodAdapter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
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
        mCurrent.clear();
        mKeySearch.clear();
        initData();
        initDataSearch();
    }


    void initData() {
        mCurrent.add(new ChainFastFood("All Current Specials ", "ic_ale"));
        mCurrent.add(new ChainFastFood("Coles", "ic_lager"));
        mCurrent.add(new ChainFastFood("Woolworths", "ic_ginger_beer"));
        mAdapter.updateData(mCurrent);
    }

    void initDataSearch() {
        mKeySearch.add("current special");
        mKeySearch.add("coles");
        mKeySearch.add("woolworths");
    }

    // Change Data name and icon follow country.


    @Override
    public void onClickChange(int position) {
//       
        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME_SEARCH, mKeySearch.get(position));
        bundle.putString(NAME_COUNTRY, mCurrent.get(position).getName());
        ProductKeywordFragment fragment = new ProductKeywordFragment();
        fragment.setArguments(bundle);
        String tag = "ProductFragment";
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
    }
}
