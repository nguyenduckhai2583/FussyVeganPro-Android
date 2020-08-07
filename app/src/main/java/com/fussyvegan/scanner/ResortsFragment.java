package com.fussyvegan.scanner;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.LocationAirlineAdapter;
import com.fussyvegan.scanner.adapter.ResortAdapter;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.Resort;
import com.fussyvegan.scanner.model.Resource;
import com.fussyvegan.scanner.model.ResourceLocationAirport;
import com.fussyvegan.scanner.model.ResourceResort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResortsFragment extends Fragment {

    private static final String NAME_COUNTRY= "country";

    private String mNameCountry;

    TextView numberResorts;
    ListView lvResorts;

    List<Resort> resorts;

    APIInterface apiInterface;

    MainActivity activity;

    SearchView searchView;

    public ResortsFragment() {
        resorts=new ArrayList<>();
    }

    public static ResortsFragment newInstance(String param1, String param2) {
        ResortsFragment fragment = new ResortsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameCountry = getArguments().getString(NAME_COUNTRY);
        }
        fetchResort("", mNameCountry);
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resorts, container, false);
        TextView nameResort= view.findViewById(R.id.tv_name_resort);
        nameResort.setText(mNameCountry+" Resorts");
        numberResorts = view.findViewById(R.id.tvNumResortsFound);
        lvResorts = view.findViewById(R.id.ltvResorts);

        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();
        activity.showFilterSearchResortOnly();

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchResort(s, mNameCountry);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    public void fetchResort(String search,String keyword) {
        Log.e("Keyword", keyword);
        apiInterface = APIResort.getClient().create(APIInterface.class);
        Call<ResourceResort> call = null;
        call = apiInterface.getResorts(search,keyword);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<ResourceResort>() {
            @Override
            public void onResponse(Call<ResourceResort> call, Response<ResourceResort> response) {
                dialog.dismiss();
                Log.d("TAG","status: " + response.code());

                ResourceResort resource = response.body();
                // Log.d("TAG","body: " + response.body().toString());
                resorts.clear();
                resorts = resource.getProducts();
                Collections.sort(resorts, new Comparator<Resort>(){
                    public int compare(Resort p1, Resort p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                Log.e("TAG", resorts.toString());
                numberResorts.setText(String.valueOf(resorts.size()));
                ResortAdapter adapter = new ResortAdapter(resorts);
                lvResorts.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<ResourceResort> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        searchView.clearFocus();
    }

}