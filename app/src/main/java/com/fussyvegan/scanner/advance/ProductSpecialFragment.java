package com.fussyvegan.scanner.advance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.API2Client;
import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.Constant;
import com.fussyvegan.scanner.OnListFragmentInteractionListener;
import com.fussyvegan.scanner.ProductFragment;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductSearchAdapter;
import com.fussyvegan.scanner.model.KeySearch;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.Rate;
import com.fussyvegan.scanner.model.Resource;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.utils.SharedPrefs;

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

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductSpecialFragment extends Fragment {
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
    TextView tvKeySearch;
    private String mKeySearch;
    private String mNameCountry;
    ProductSearchAdapter mAdapter;
    private SearchView searchView;
    ArrayList<Product> products = new ArrayList<>();



    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductSpecialFragment newInstance(String nameChain, String country) {
        ProductSpecialFragment fragment = new ProductSpecialFragment();
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
            mKeySearch = getArguments().getString(Constant.ARG_NAME_SEARCH);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_all_pro, container, false);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        tvKeySearch = view.findViewById(R.id.tvKeySearch);
        mAdapter = new ProductSearchAdapter(mProducts, false);
        ltvProduct.setAdapter(mAdapter);
        tvKeySearch.setText(mKeySearch);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product", mProducts.get(position ));
                startActivity(intent);
            }
        });

        fetchProducts(mKeySearch);

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String keyword = query.replace("'", "’");
                //fetchProducts(keyword);
                ArrayList<Product> listProducts = filter(keyword);
                Log.e("listProduct", listProducts.size() + ", produc: " + products.size() + ", " + keyword);
                mProducts.clear();
                mAdapter.updateData(listProducts);
                mProducts.addAll(listProducts);
                Log.e("Product1", String.valueOf(mProducts.size()));


                //Log.e("Product", String.valueOf(mProducts.size()));
                Log.d("TAG", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //mAdapter.updateData(mProducts);
//                ArrayList<Product> products = filter(newText);
//                mAdapter.updateData(products);;
                //mAdapter.getFilter().filter(newText);
                Log.d("TAG", newText);
                //mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        Log.e("produc special", "here");
        return view;
    }

    private ArrayList<Product> filter(String text) {
        ArrayList<Product> list = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().toLowerCase().contains(text.toLowerCase())||text.length()==0) {
                list.add(products.get(i));
            }
        }
        return list;
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
            fetchProducts(mKeySearch);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    public void fetchProducts(String special) {
        mProducts.clear();
        apiInterface = API2Client.getClientPro(false).create(APIInterface.class);
        Call<Resource> call = apiInterface.doGetResponseBySpecial(Constant.API_KEY, special);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
                Log.d("TAG", "status: " + response.code());
                dialog.dismiss();
                Resource resource = response.body();
                if(resource.getProducts()!=null){
                    mProducts.clear();
                    products.clear();
                    mProducts.addAll(resource.getProducts());
                    products.addAll(resource.getProducts());
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

            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
