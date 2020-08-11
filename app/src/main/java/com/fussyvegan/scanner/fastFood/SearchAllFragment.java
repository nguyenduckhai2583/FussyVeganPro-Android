package com.fussyvegan.scanner.fastFood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.scanner.API2Client;
import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.Constant;
import com.fussyvegan.scanner.OnListFragmentInteractionListener;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductSearchAdapter;
import com.fussyvegan.scanner.model.KeySearch;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.Resource;

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
public class SearchAllFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String NAME_CHAIN = "NameChain";
    private static final String NAME_COUNTRY = "Country";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<Product> mProducts = new ArrayList<>();
    MainActivity activity;
    ListView ltvProduct;
    private String mNameChain;
    private String mNameCountry;
    ProductSearchAdapter mAdapter;


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchAllFragment newInstance(String nameChain, String country) {
        SearchAllFragment fragment = new SearchAllFragment();
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
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product", mProducts.get(position));
                intent.putExtra("country", true);
                intent.putExtra("category", 2);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
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
        if (mNameChain != null && mNameChain.length() > 1) {
            call = apiInterface.doGetResponseBySearchFastFoodName(Constant.API_KEY, "", mNameCountry, mNameChain);
        } else {
            call = apiInterface.doGetResponseBySearchFastFood(Constant.API_KEY, keyword, mNameCountry);
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
                if (mProducts != null &&
                        !mProducts.isEmpty()) {
                    Collections.sort(mProducts, new Comparator<Product>() {
                        public int compare(Product p1, Product p2) {
                            return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                        }
                    });
                }
                mAdapter.updateData(mProducts);
            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
