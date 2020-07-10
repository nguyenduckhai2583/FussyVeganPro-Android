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
import android.widget.Filter;
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
public class ProductKeywordFragment extends Fragment {
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
    private SearchView searchView;
    ProductSearchAdapter mAdapter;
    ArrayList<Product> products = new ArrayList<>();
    final ArrayList<Rate> rates = new ArrayList<>();
    Reviews review= new Reviews();


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductKeywordFragment newInstance(String nameChain, String country) {
        ProductKeywordFragment fragment = new ProductKeywordFragment();
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
        Log.e("key search", mKeySearch);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product", mProducts.get(position));
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

        //ltvProduct.setAdapter(mAdapter);
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
        //fetchProducts(search.getKey());
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
//        searchView.clearFocus();
//        searchView.setQuery("", false);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    public void fetchProducts(String keyword) {
        mProducts.clear();
        apiInterface = API2Client.getClientPro(false).create(APIInterface.class);
        Call<Resource> call = apiInterface.doGetResponseByKeyword(Constant.API_KEY, keyword);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
                mProducts.clear();
                products.clear();
                Log.d("TAG", "status: " + response.code());
                dialog.dismiss();
                Resource resource = response.body();
                if (resource.getProducts() != null) {
                    products.addAll(resource.getProducts());
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
                    if(review == null) {
                        getReview(mProducts.get(0).getId(), 1);
                    }
                    Log.e("rate", String.valueOf(rates.size()));
                    //products = deepClone();
                    //mProducts.clear();
                    Log.e("Product2", String.valueOf(mProducts.size()));

//                    ProductSearchAdapter adapter =  new ProductSearchAdapter( mProducts, false);
//                    ltvProduct.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getReview(final int idProduct, final int idType) {
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        //Log.e("count", String.valueOf(count));
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);
        Call<Reviews> call = apiInterface.getReviewProduct(token, idProduct, idType);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                dialog.dismiss();
                if(response.body() == null) getReview(idProduct, idType);
                //Log.d(TAG, String.valueOf(response.code()));
                if (!response.body().getData().isEmpty()) {
                    review = response.body();
                    setOverallRatingReview(response.body().getData());
                    //productReviews.addAll(response.body().getData());
                    Log.e("idProduct", String.valueOf(idProduct));

                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.d("ProductAdapter", t.getMessage());
            }
        });

    }

    private void setOverallRatingReview(List<ProductReview> data) {
        int numReview = data.size();

        float number = 0;
        for (int i = 0; i < data.size(); i++) {
            number = number + data.get(i).getRating();
        }

        float aveRating = number / data.size();

        rates.add(new Rate(aveRating, numReview));
        Log.e("setOverallRatingReview", numReview + ", " + aveRating);
    }
}
