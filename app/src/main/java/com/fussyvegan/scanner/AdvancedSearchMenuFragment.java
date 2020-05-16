package com.fussyvegan.scanner;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class AdvancedSearchMenuFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    SettingAdapter adapter;
    List<String> settings = new ArrayList<>();
    List<Integer> icLink = new ArrayList<>();
    ListView ltvSetting;
    MainActivity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AdvancedSearchMenuFragment() {
        settings.add("Search Ingredients");
        settings.add("Recently Added");
        settings.add("Supermarket Specials");
        settings.add("Vegan Grocery Guide");
        settings.add("Vegan Alcohol Guide");
        settings.add("Vegan Beauty Guide");
        icLink.add(R.drawable.ic_search_ingredient);
        icLink.add(R.drawable.ic_calendar);
        icLink.add(R.drawable.ic_special);
        icLink.add(R.drawable.ic_products);
        icLink.add(R.drawable.ic_alcohol);
        icLink.add(R.drawable.ic_makeup);

    }

    // TODO: Customize parameter initialization
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        adapter = new SettingAdapter(settings,icLink);
        ltvSetting = view.findViewById(R.id.ltvSetting);
        ltvSetting.setAdapter(adapter);
        ltvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");

                if (position == 0) {
                    String tag = "IngredientsFragment";
                    IngredientsFragment fragment = new IngredientsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME_SEARCH, settings.get(position));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                }  else if (position == 1) {
                    String tag = "RecentFragment";
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.ARG_NAME_SEARCH, "recent");
                    bundle.putString(Constant.NAME_COUNTRY, settings.get(position));
                    ProductKeywordFragment fragment = new ProductKeywordFragment();
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                } else if (position == 2) {
                    String tag = "SupermarketSpecialsFragment";
                    SupermarketSpecialsFragment fragment = new SupermarketSpecialsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME_SEARCH, settings.get(position));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                } else if (position == 3) {
                    String tag = "VeganGroceryFragment";
                    VeganGroceryFragment fragment = new VeganGroceryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME_SEARCH, settings.get(position));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                } else if (position == 4) {
                    String tag = "VeganAlcoholFragment";

                    VeganAlcoholFragment fragment = new VeganAlcoholFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME_SEARCH, settings.get(position));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                }  else if (position == 5) {
                    String tag = "VeganBeauty";
                    VeganBeautyFragment fragment = new VeganBeautyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME_SEARCH, settings.get(position));
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
                }
            }
        });

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return view;
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
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
