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
public class VeganGroceryFragment extends Fragment {

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
    public VeganGroceryFragment() {


    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VeganGroceryFragment newInstance(int columnCount) {
        VeganGroceryFragment fragment = new VeganGroceryFragment();
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

        category.add(new Category("Bagels", "ic_bagels"));
        category.add(new Category("Bread Loaves", "ic_bread_loaves"));
        category.add(new Category("Crumpets & Muffins", "ic_crumpets"));
        category.add(new Category("Garlic & Herb Bread", "ic_garlic_bread"));
        category.add(new Category("Pizza Bases", "ic_pizza_base"));
        category.add(new Category("Rolls & Buns", "ic_rolls"));
        category.add(new Category("Wraps & Flatbreads", "ic_wraps"));
        mDatas.add(new SubCategory("Bread", category));
        List<Category> category1 = new ArrayList<>();

        category1.add(new Category("Cookies & Sweet Biscuits", "ic_cookie"));
        category1.add(new Category("Chips", "ic_chips"));
        category1.add(new Category("Chocolate", "ic_chocolate"));
        category1.add(new Category("Lollies", "ic_lollies"));
        category1.add(new Category("Savory Biscuits", "ic_biscuits"));
        category1.add(new Category("Snack Bars", "ic_snackbar"));
        mDatas.add(new SubCategory("Confectionery & Snacks", category1));
        List<Category> category2 = new ArrayList<>();

        category2.add(new Category("Butter", "ic_butter"));
        category2.add(new Category("Cheese", "ic_cheese"));
        category2.add(new Category("Egg", "ic_egg"));
        category2.add(new Category("Ice Cream", "ic_icecream"));
        category2.add(new Category("Milk", "ic_milk"));
        category2.add(new Category("Yoghurt", "ic_yoghurt"));
        mDatas.add(new SubCategory("Dairy & Egg Replacements", category2));
        List<Category> category3 = new ArrayList<>();

        category3.add(new Category("Apple Juice", "ic_apple_juice"));
        category3.add(new Category("Orange Juice", "ic_orange_juice"));
        category3.add(new Category("Vegetable Juice", "ic_vegetable_juice"));
        category3.add(new Category("Other Juice", "ic_other_juice"));
        mDatas.add(new SubCategory("Juices", category3));
        List<Category> category4 = new ArrayList<>();

        category4.add(new Category("Household Cleaners", "ic_clean"));
        category4.add(new Category("Laundry", "ic_laundry"));
        mDatas.add(new SubCategory("Laundry & Cleaning", category4));
        List<Category> category5 = new ArrayList<>();

        category5.add(new Category("Beef", "ic_beef"));
        category5.add(new Category("Chicken", "ic_chicken"));
        category5.add(new Category("Fish & Seafood", "ic_fish"));
        category5.add(new Category("Pork", "ic_pork"));
        mDatas.add(new SubCategory("Meat Replacements", category5));
        List<Category> category6 = new ArrayList<>();

        category6.add(new Category("Baking & Cake Mixes", "ic_cake_mix"));
        category6.add(new Category("Cereals", "ic_cereal"));
        category6.add(new Category("Gravy", "ic_gravy"));
        category6.add(new Category("Noodles", "ic_noodles"));
        category6.add(new Category("Ready Made Meals", "ic_readymade"));
        mDatas.add(new SubCategory("Other Grocery", category6));
        List<Category> category7 = new ArrayList<>();

        category7.add(new Category("Body Wash & Soap", "ic_soap"));
        category7.add(new Category("Deodorant", "ic_deodorant"));
        category7.add(new Category("Hair Care", "ic_hair"));
        category7.add(new Category("Oral Hygiene", "ic_toothbrush"));
        category7.add(new Category("Shaving", "ic_shave"));
        mDatas.add(new SubCategory("Personal Hygiene", category7));
        List<Category> category8 = new ArrayList<>();

        category8.add(new Category("BBQ Sauce", "ic_bbq_sauce"));
        category8.add(new Category("Chilli Sauce", "ic_chilli_sauce"));
        category8.add(new Category("Ketchup", "ic_ketchup"));
        category8.add(new Category("Mustard", "ic_mustard"));
        category8.add(new Category("Pasta & Pizza Sauce", "ic_pasta_sauce"));
        category8.add(new Category("Recipe Bases", "ic_recipe_base"));
        category8.add(new Category("Soy & Asian Sauce", "ic_soy_sauce"));
        category8.add(new Category("Stir-fry Sauce", "ic_stirfry_sauce"));
        category8.add(new Category("Other Sauce", "ic_other_sauce"));
        mDatas.add(new SubCategory("Sauces", category8));






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
                String tag = "ProductKeywordFragment ";
                ProductKeywordFragment  fragment = new ProductKeywordFragment ();
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
