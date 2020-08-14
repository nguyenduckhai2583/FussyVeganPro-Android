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
public class VeganBeautyFragment extends BaseFragment {

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
    public VeganBeautyFragment() {


    }


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VeganBeautyFragment newInstance(int columnCount) {
        VeganBeautyFragment fragment = new VeganBeautyFragment();
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

        category.add(new Category("Brushes", "ic_brush"));
        category.add(new Category("Sponges", "ic_sponge"));
        mDatas.add(new SubCategory("Brushes", category));
        List<Category> category1 = new ArrayList<>();

        category1.add(new Category("Blush", "ic_blush"));
        category1.add(new Category("Bronzer", "ic_bronzer"));
        category1.add(new Category("Contour & Highlight", "ic_contour"));
        mDatas.add(new SubCategory("Cheeks", category1));
        List<Category> category2 = new ArrayList<>();

        category2.add(new Category("Eye Brows", "ic_eyebrow"));
        category2.add(new Category("Eyeliner", "ic_eyeliner"));
        category2.add(new Category("Eye Shadow & Palettes", "ic_eyeshadow"));
        category2.add(new Category("False Lashes", "ic_lashes"));
        category2.add(new Category("Mascara", "ic_mascara"));
        mDatas.add(new SubCategory("Eyes", category2));
        List<Category> category3 = new ArrayList<>();

        category3.add(new Category("Bb & CC Cream", "ic_bbcream"));
        category3.add(new Category("Concealer", "ic_concealer"));
        category3.add(new Category("Face Palettes & Sets", "ic_face_palette"));
        category3.add(new Category("Foundation", "ic_foundation"));
        category3.add(new Category("Makeup Remover", "ic_makeup_remover"));
        category3.add(new Category("Prime & Set", "ic_primer"));
        category3.add(new Category("Powder", "ic_powder"));
        mDatas.add(new SubCategory("Face", category3));
        List<Category> category4 = new ArrayList<>();

        category4.add(new Category("Gel & Wax", "ic_hairgel"));
        category4.add(new Category("Hair Colour", "ic_haircolour"));
        category4.add(new Category("Heat Protection", "ic_heatprotect"));
        category4.add(new Category("Hair Spray", "ic_hairspray"));
        category4.add(new Category("Hair Treatment", "ic_hairtreatment"));
        category4.add(new Category("Mousse", "ic_mousse"));
        mDatas.add(new SubCategory("Hair Cair", category4));
        List<Category> category5 = new ArrayList<>();

        category5.add(new Category("Lip Gloss & Balm", "ic_lipgloss"));
        category5.add(new Category("Lip Liner", "ic_lipliner"));
        category5.add(new Category("Lipstick", "ic_lipstick"));
        category5.add(new Category("Liquid Lipstick", "ic_liquidlip"));
        mDatas.add(new SubCategory("Lips", category5));
        List<Category> category6 = new ArrayList<>();

        category6.add(new Category("Nail Polish", "ic_nailpolish"));
        category6.add(new Category("Nail Polish Remover", "ic_polish_remover"));
        mDatas.add(new SubCategory("Nails", category6));
        List<Category> category7 = new ArrayList<>();

        category7.add(new Category("Cleanser & Face Wash", "ic_face_cleanser"));
        category7.add(new Category("Moisturiser", "ic_moisturiser"));
        category7.add(new Category("Oils, Treatments & Masks", "ic_skin_treatment"));
        category7.add(new Category("Self Tanning", "ic_tan"));
        mDatas.add(new SubCategory("Skin Care", category7));
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
