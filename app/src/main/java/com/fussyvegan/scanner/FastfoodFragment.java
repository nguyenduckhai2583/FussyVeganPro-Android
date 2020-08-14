package com.fussyvegan.scanner;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.scanner.adapter.SettingAdapter;
import com.fussyvegan.scanner.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FastfoodFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String NAME_COUNTRY = "Country";
    private static final String NAME_OF_COUNTRY = "NameCountry";
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
    public FastfoodFragment() {
        settings.add("Australia");
        settings.add("Canada");
        settings.add("New Zealand");
        settings.add("United Kingdom");
        settings.add("USA");

        icLink.add(R.drawable.ic_flag_australia);
        icLink.add(R.drawable.ic_flag_ca);
        icLink.add(R.drawable.ic_flag_nz);
        icLink.add(R.drawable.ic_flag_uk);
        icLink.add(R.drawable.ic_flag_usa);



    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FastfoodFragment newInstance(int columnCount) {
        FastfoodFragment fragment = new FastfoodFragment();
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

    String getCountry(int position) {
        switch (position) {
            case 0:
                return "Australia";
            case 1:
                return "Canada";
            case 2:
                return "New Zealand";
            case 3:
                return "UK";
            case 4:
                return "USA";
            default:
                return "Australia";

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        adapter = new SettingAdapter(settings, icLink);
        ltvSetting = view.findViewById(R.id.ltvSetting);
        ltvSetting.setAdapter(adapter);
        ltvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                String tag = "FastfoodAUFragment";
                ChainFastFoodFragment fragment = new ChainFastFoodFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, getCountry(position));
                args.putString(NAME_OF_COUNTRY, settings.get(position));
                fragment.setArguments(args);
                replaceFragment(R.id.frameLayoutContainer, fragment,true);

//                if(position == 0){
//                    String tag = "FastfoodAUFragment";
//                    FastfoodAUFragment fragment = new FastfoodAUFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
//                } else if(position == 1){
//                    String tag = "FastfoodUSFragment";
//                    FastfoodUSFragment fragment = new FastfoodUSFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
//                } else if(position == 2){
//                    String tag = "FastfoodUKFragment";
//                    FastfoodUKFragment fragment = new FastfoodUKFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
//                } else if(position == 3){
//                    String tag = "FastfoodNZFragment";
//                    FastfoodNZFragment fragment = new FastfoodNZFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
//                } else if(position == 4){
//                    String tag = "FastfoodCAFragment";
//                    FastfoodCAFragment fragment = new FastfoodCAFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, fragment, tag).addToBackStack(tag).commit();
//                }
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
