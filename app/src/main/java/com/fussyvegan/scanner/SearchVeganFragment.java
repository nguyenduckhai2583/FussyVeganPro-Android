package com.fussyvegan.scanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductAdapter;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.Resource;
import com.fussyvegan.scanner.model.Status;

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
public class SearchVeganFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<Product> products;
    MainActivity activity;
    ListView ltvProduct;
    View header;
    SearchView searchView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchVeganFragment() {
        products = new ArrayList<>();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchVeganFragment newInstance(int columnCount) {
        SearchVeganFragment fragment = new SearchVeganFragment();
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
        if (activity.searchScope.equals("barcode")) {
            fetchProducts(activity.keyword);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchvegan, container, false);
        header = getLayoutInflater().inflate(R.layout.search_header, null);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        final ProductAdapter adapter = new ProductAdapter( products, false);
        ltvProduct.setAdapter(adapter);
        ltvProduct.addHeaderView(header);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", position + "  click");
                if(position>0){
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("product", products.get(position - 1));
                    startActivity(intent);
                }

            }
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String keyword = query.replace("'", "’");
                fetchProducts(keyword);
                Log.d("TAG", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                Log.d("TAG", newText);
                return false;
            }
        });

        TabHost host = view.findViewById(R.id.tabScope);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Search Vegan Only Products");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Search Vegan Only");
        host.addTab(spec);


        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(final String tabId) {
                Log.d("TAG", tabId + "xxx");
                searchScope = tabId;
            }
        });

        activity.visibleBackItem(true);
        activity.showNothing();
        activity.invalidateOptionsMenu();

        if (activity.searchScope.equals("barcode")) {
            host.setCurrentTab(1);
            fetchProducts(activity.keyword);
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        searchView.clearFocus();
    }

    public void fetchProducts(String keyword) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Resource> call = null;
        if (searchScope.equals("search")) {
            call = apiInterface.doGetResponseBySearchOnlyVegan(Constant.API_KEY, keyword, "VEGAN");
        } else if (searchScope.equals("barcode")) {
            call = apiInterface.doGetResponseByBarcode(Constant.API_KEY, keyword);
        }
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
                Log.d("TAG", "status: " + response.code());
                dialog.dismiss();
                Resource resource = response.body();
                Status status = resource.getStatus();
                products.clear();
                products = resource.getProducts();
                Collections.sort(products, new Comparator<Product>() {
                    public int compare(Product p1, Product p2) {
                        return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                    }
                });
                //Log.d("TAG","name:" + products.get(2).getName());
                ProductAdapter adapter = new ProductAdapter( products, false);
                ltvProduct.setAdapter(adapter);
                TextView txvResult = header.findViewById(R.id.txvResult);
                txvResult.setText("Search Result: " + products.size() + " products");
            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
