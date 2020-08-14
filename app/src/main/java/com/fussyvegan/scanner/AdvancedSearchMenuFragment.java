package com.fussyvegan.scanner;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.SettingAdapter;
import com.fussyvegan.scanner.advance.ProductKeywordFragment;
import com.fussyvegan.scanner.advance.SupermarketSpecialsFragment;
import com.fussyvegan.scanner.advance.VeganAlcoholFragment;
import com.fussyvegan.scanner.advance.VeganBeautyFragment;
import com.fussyvegan.scanner.advance.VeganGroceryFragment;

import java.util.ArrayList;
import java.util.List;

import static com.fussyvegan.scanner.Constant.ARG_NAME_SEARCH;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AdvancedSearchMenuFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    SettingAdapter adapter;
    List<String> settings = new ArrayList<>();
    List<Integer> icLink = new ArrayList<>();
    ListView ltvSetting;
    MainActivity activity;

    LinearLayout llAllProducts, llSearchIngredients, llRecentAdded, llSuperMarketSpecial,
            llGroceryGuide, llVeganAlcohol, llVeganBeautyGuide;

    public AdvancedSearchMenuFragment() { }

    @SuppressWarnings("unused")
    public static AdvancedSearchMenuFragment newInstance(int columnCount) {
        AdvancedSearchMenuFragment fragment = new AdvancedSearchMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_search, container, false);

        initView(view);
        llAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "SearchFragment";
                SearchFragment fragment = new SearchFragment();

                replaceFragment(R.id.frameLayoutContainer, fragment,true);
//                activity.getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();

            }
        });

        llSearchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "IngredientsFragment";
                IngredientsFragment fragment = new IngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_NAME_SEARCH, "Search Ingredients");
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);

            }
        });

        llRecentAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "RecentFragment";
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ARG_NAME_SEARCH, "recent");
                bundle.putString(Constant.NAME_COUNTRY, "Recently Added");
                ProductKeywordFragment fragment = new ProductKeywordFragment();
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);

            }
        });

        llSuperMarketSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "SupermarketSpecialsFragment";
                SupermarketSpecialsFragment fragment = new SupermarketSpecialsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_NAME_SEARCH, "Supermarket Specials");
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);


            }
        });
        llGroceryGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "VeganGroceryFragment";
                VeganGroceryFragment fragment = new VeganGroceryFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_NAME_SEARCH, "Vegan Grocery Guide");
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);
            }
        });

        llVeganAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = "VeganAlcoholFragment";

                VeganAlcoholFragment fragment = new VeganAlcoholFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_NAME_SEARCH, "Vegan Alcohol Guide");
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);

            }
        });

        llVeganBeautyGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "VeganBeauty";
                VeganBeautyFragment fragment = new VeganBeautyFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_NAME_SEARCH, "Vegan Beauty Guide");
                fragment.setArguments(bundle);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);
            }
        });


        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return view;
    }

    private void initView (View view){
        llAllProducts = view.findViewById(R.id.lnAllProducts);
        llSearchIngredients = view.findViewById(R.id.lnSearchIngredients);
        llRecentAdded = view.findViewById(R.id.lnRecentlyAdded);
        llSuperMarketSpecial = view.findViewById(R.id.lnSuperMarketSpecials);
        llGroceryGuide = view.findViewById(R.id.lnVeganGroceryGuide);
        llVeganAlcohol = view.findViewById(R.id.lnVeganAlcoholGuide);
        llVeganBeautyGuide = view.findViewById(R.id.lnVeganBeautyGuide);
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach () {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
