package com.fussyvegan.scanner.advance;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.fussyvegan.scanner.BaseFragment;
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
public class VeganAlcoholFragment extends BaseFragment {

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
    public VeganAlcoholFragment() {


    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VeganAlcoholFragment newInstance(int columnCount) {
        VeganAlcoholFragment fragment = new VeganAlcoholFragment();
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

        category.add(new Category("Ale ", "ic_ale"));
        category.add(new Category("Lager", "ic_lager"));
        category.add(new Category("Ginger Beer", "ic_ginger_beer"));
        mDatas.add(new SubCategory("Beer", category));
        List<Category> category1 = new ArrayList<>();

        category1.add(new Category("Apple Cider", "ic_apple_cider"));
        category1.add(new Category("Fruit Flavoured Cider", "ic_fruit_cider"));
        mDatas.add(new SubCategory("Cider", category1));
        List<Category> category2 = new ArrayList<>();

        category2.add(new Category("Brandy", "ic_brandy"));
        category2.add(new Category("Gin ", "ic_gin"));
        category2.add(new Category("Rum ", "ic_rum"));
        category2.add(new Category("Tequila", "ic_tequila"));
        category2.add(new Category("Vodka", "ic_vodka"));
        category2.add(new Category("Whiskey", "ic_whiskey"));
        mDatas.add(new SubCategory("Spirits", category2));
        List<Category> category3 = new ArrayList<>();

        category3.add(new Category("Cabernet Sauvignon", "ic_cabernet_sauvignon"));
        category3.add(new Category("Malbec", "ic_malbec"));
        category3.add(new Category("Merlot", "ic_merlot"));
        category3.add(new Category("Nebbiolo", "ic_nebbiolo"));
        category3.add(new Category("Pinot Noir", "ic_pinot_noir"));
        category3.add(new Category("Sangiovese", "ic_sangiovese"));
        category3.add(new Category("Shiraz", "ic_shiraz"));
        mDatas.add(new SubCategory("Red Wine", category3));
        List<Category> category4 = new ArrayList<>();

        category4.add(new Category("Chardonnay", "ic_chardonnay"));
        category4.add(new Category("Pinot Gris", "ic_pinot_gris"));
        category4.add(new Category("Riesling", "ic_riesling"));
        category4.add(new Category("Sauvignon Blanc", "ic_sauvignon_blanc"));
        category4.add(new Category("Semillon", "ic_semillon"));
        category4.add(new Category("verdelho", "ic_verdelho"));
        mDatas.add(new SubCategory("White Wine", category4));
        List<Category> category5 = new ArrayList<>();

        category5.add(new Category("Rose Wine", "ic_rosewine"));
        category5.add(new Category("Sparkling Wine", "ic_sparkling_wine"));
        mDatas.add(new SubCategory("Other Wine", category5));
        List<Category> category6 = new ArrayList<>();

        category6.add(new Category("Bitters", "ic_bitters"));
        category6.add(new Category("Cocktail Mixes", "ic_cocktail_mix"));
        mDatas.add(new SubCategory("Other Drinks", category6));

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
                replaceFragment(R.id.frameLayoutContainer, fragment, true);

//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
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
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
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
