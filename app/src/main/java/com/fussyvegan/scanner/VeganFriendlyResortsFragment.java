package com.fussyvegan.scanner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fussyvegan.scanner.activity.MainActivity;


public class VeganFriendlyResortsFragment extends Fragment {

    private static final String NAME_COUNTRY= "country";


    LinearLayout lnBotswana;
    LinearLayout lnKenya;
    LinearLayout lnSouthAfrica;
    LinearLayout lnTanzania;
    LinearLayout lnSeychelles;
    LinearLayout lnFiji;
    LinearLayout lnSamoa;
    LinearLayout lnTonga;
    LinearLayout lnVanuatu;

    MainActivity activity;


    public VeganFriendlyResortsFragment() {
    }

    public static VeganFriendlyResortsFragment newInstance(String param1, String param2) {
        VeganFriendlyResortsFragment fragment = new VeganFriendlyResortsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vegan_friendly_resorts, container, false);
        initView(view);
        handOnClick();

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        return  view;
    }

    private void handOnClick() {
        lnBotswana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Botswana");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnKenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Kenya");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnSouthAfrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "SouthAfrica");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnTanzania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Tanzania");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnSeychelles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Seychelles");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnFiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Fiji");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnSamoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Samoa");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnTonga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Tonga");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        lnVanuatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = "ResortsFragment";
                ResortsFragment fragment = new ResortsFragment();
                Bundle args = new Bundle();
                args.putString(NAME_COUNTRY, "Vanuatu");
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });
    }

    private void initView(View view) {
        lnBotswana = view.findViewById(R.id.lnBotswana);
        lnKenya = view.findViewById(R.id.lnKenya);
        lnSouthAfrica = view.findViewById(R.id.lnSouthAfrica);
        lnTanzania = view.findViewById(R.id.lnTanzania);
        lnSeychelles = view.findViewById(R.id.lnSeychelles);
        lnFiji = view.findViewById(R.id.lnFiji);
        lnSamoa = view.findViewById(R.id.lnSamoa);
        lnTonga = view.findViewById(R.id.lnTonga);
        lnVanuatu = view.findViewById(R.id.lnVanuatu);

    }
}