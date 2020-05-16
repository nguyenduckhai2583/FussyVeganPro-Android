package com.fussyvegan.scanner.advance;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.fussyvegan.scanner.Constant;
import com.fussyvegan.scanner.OnListFragmentInteractionListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.model.Category;
import com.fussyvegan.scanner.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SupermarketSpecialsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    SubCategoryAdapter mAdapter;
    RecyclerView rcNameChain;
    TextView tvNameCountry;
    MainActivity activity;
    private String mKeySearch;
    List<SubCategory> mDatas = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SupermarketSpecialsFragment() {


    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SupermarketSpecialsFragment newInstance(int columnCount) {
        SupermarketSpecialsFragment fragment = new SupermarketSpecialsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mKeySearch = getArguments().getString(Constant.ARG_NAME_SEARCH);
        }
        activity = (MainActivity) this.getActivity();
    }

    void intiData() {
        mDatas.clear();
        List<Category> category = new ArrayList<>();

        category.add(new Category("All Specials", "ic_special"));
        category.add(new Category("Coles Specials", "ic_coles"));
        category.add(new Category("Woolworths Specials", "ic_woolworths"));
        mDatas.add(new SubCategory("Current Specials", category));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        rcNameChain = view.findViewById(R.id.rcNameChain);
        tvNameCountry = view.findViewById(R.id.tvNameCountry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        intiData();
        tvNameCountry.setText(mKeySearch);
        mAdapter = new SubCategoryAdapter(mDatas);
        rcNameChain.setLayoutManager(layoutManager);
        rcNameChain.setAdapter(mAdapter);
        mAdapter.setOnClick(new IClickSubCategoryListener() {
            @Override
            public void onClickChange(String name) {
                String tag = "ProductSpecialFragment ";
                ProductSpecialFragment  fragment = new ProductSpecialFragment ();
                Bundle args = new Bundle();
                args.putString(Constant.ARG_NAME_SEARCH, name);
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });
        rcNameChain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                rcNameChain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mAdapter.expandAllGroups();
            }
        });

//        ltvSetting.setAdapter(adapter);
//        ltvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("TAG", position + "  click");
//                String tag = "FastfoodAUFragment";
//                ChainFastFoodProFragment fragment = new ChainFastFoodProFragment();
//                Bundle args = new Bundle();
//                args.putString(Constant.ARG_NAME_SEARCH, settings.get(position));
//                fragment.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
//            }
//        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
