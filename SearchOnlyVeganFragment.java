package com.fussyvegan.pro.fastFood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.pro.API2Client;
import com.fussyvegan.pro.APIInterface;
import com.fussyvegan.pro.OnListFragmentInteractionListener;
import com.fussyvegan.pro.ProductFragment;
import com.fussyvegan.pro.activity.MainActivity;
import com.fussyvegan.pro.adapter.ProductSearchAdapter;
import com.fussyvegan.pro.model.KeySearch;
import com.fussyvegan.pro.model.Product;
import com.fussyvegan.pro.model.Resource;
import com.fussyvegan.pro.Constant;
import com.fussyvegan.pro.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchOnlyVeganFragment extends Fragment {
    // TODO: Customize parameter argument names
//    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String NAME_CHAIN = "NameChain";
    private static final String NAME_COUNTRY = "Country";

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<Product> mProducts;
    MainActivity activity;
    ListView ltvProduct;
    View header;
    String mNameChain;
    String mNameCountry;
    ProductSearchAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchOnlyVeganFragment() {
        mProducts = new ArrayList<>();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchOnlyVeganFragment newInstance(String nameChain, String country) {
        SearchOnlyVeganFragment fragment = new SearchOnlyVeganFragment();
        Bundle args = new Bundle();
        args.putString(NAME_CHAIN, nameChain);
        args.putString(NAME_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mNameChain = getArguments().getString(NAME_CHAIN);
            mNameCountry = getArguments().getString(NAME_COUNTRY);

        }

        activity = (MainActivity) this.getActivity();
        mProducts = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        mAdapter = new ProductSearchAdapter(mProducts, false);
        ltvProduct.setAdapter(mAdapter);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                ProductFragment fragment = new ProductFragment();
                fragment.product = mProducts.get(position);
                String tag = "ProductFragment";
                activity.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });

        return view;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KeySearch search) {
        fetchProducts(search.getKey());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNameChain != null && mNameChain.length() > 1) {
            fetchProducts(mNameChain);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }


    public void fetchProducts(String keyword) {
        apiInterface = API2Client.getClientProNew().create(APIInterface.class);
        Call<Resource> call;
        if (mNameChain != null &&  mNameChain.length()  > 1) {
            call = apiInterface.doGetResponseBySearchFastFoodOnlyVeganName(Constant.API_KEY, "","VEGAN",mNameCountry,mNameChain);
        } else {
            call = apiInterface.doGetResponseBySearchOnlyVegan(Constant.API_KEY, keyword,mNameCountry);
        }
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
                Log.d("TAG", "status: " + response.code());
                dialog.dismiss();
                Resource resource = response.body();
                mProducts.clear();
                mProducts.addAll(resource.getProducts());
                Collections.sort(mProducts, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                //Log.d("TAG","name:" + mProducts.get(2).getName());
                mAdapter.updateData(mProducts);

            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
