package com.fussyvegan.scanner;

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
public class SearchFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String filterCondition = "NO";
    private String filterConditionVegan = "VEGAN";
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<Product> products;
    List<Product> filterProducts;
    MainActivity activity;
    ListView ltvProduct;
    View header;
    SearchView searchView;
    private ProductAdapter adapter;
    private TextView txvResult;

    private boolean isNoPalmOil = false;
    private boolean isNoGMO = false;
    private boolean isGlutenFree = false;
    private boolean isNutFree = false;
    private boolean isSoyFree = false;
    private boolean isVeganCompany = false;
    private boolean isClear = true;
    private boolean isHasFilter = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
        products = new ArrayList<>();
        filterProducts = new ArrayList<>();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchFragment newInstance(int columnCount) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        header = getLayoutInflater().inflate(R.layout.search_header, null);
        txvResult = header.findViewById(R.id.txvResult);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        adapter = new ProductAdapter(products, false);
        ltvProduct.setAdapter(adapter);
        ltvProduct.addHeaderView(header);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    if (isHasFilter) {
                        intent.putExtra("product", filterProducts.get(position - 1));
                        intent.putExtra("category", 1);
                    } else {
                        intent.putExtra("product", products.get(position - 1));
                        intent.putExtra("category", 1);
                    }

                    startActivity(intent);
                }

            }
        });

        //fetchProducts(activity.keyword);

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
        TabHost.TabSpec spec = host.newTabSpec("search");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Name");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("barcode");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Barcode");
        host.addTab(spec);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(final String tabId) {
                Log.d("TAG", tabId + "xxx");
                searchScope = tabId;
            }
        });

        activity.visibleBackItem(false);
        activity.showNothing();
        activity.invalidateOptionsMenu();
        activity.showFilterSearchOnly();

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

    private void setResultCount(List<Product> products) {
        String resultSuffix = products.size() == 1 ? " product" : " products";
        txvResult.setText("Search Result: " + products.size() + resultSuffix);
    }

    public void updateFilter(boolean isClear, boolean isNoPalmOil, boolean isNoGMO, boolean isGlutenFree, boolean isNutFree, boolean isSoyFree, boolean isVeganCompany) {
        filterProducts.clear();

        boolean filterCorrect = false;
        boolean ignoreCheck = false;

        this.isClear = isClear;
        this.isNoPalmOil = isNoPalmOil;
        this.isNoGMO = isNoGMO;
        this.isGlutenFree = isGlutenFree;
        this.isNutFree = isNutFree;
        this.isSoyFree = isSoyFree;
        this.isVeganCompany = isVeganCompany;

        if (products.size() > 0) {
            if (isClear) {
                adapter.updateData(products);
                setResultCount(products);
                isHasFilter = false;
            } else {
                isHasFilter = true;
                for (int i = 0; i < products.size(); i++) {

                    filterCorrect = false;
                    ignoreCheck = false;

                    if (isNoPalmOil && !ignoreCheck) {
                        if (products.get(i).getProdpalm().equals(filterCondition)) {
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (isNoGMO && !ignoreCheck) {
                        if (products.get(i).getGmo().equals(filterCondition)) {
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (isGlutenFree && !ignoreCheck) {
                        if (products.get(i).getGluten().equals(filterCondition)) {
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (isNutFree && !ignoreCheck) {
                        if (products.get(i).getNut().equals(filterCondition)) {
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (isSoyFree && !ignoreCheck) {
                        if (products.get(i).getSoy().equals(filterCondition)) {
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (isVeganCompany && !ignoreCheck) {
                        if (products.get(i).getVeganStatus().equals(filterConditionVegan)) {
                            Log.d("vagen status", products.get(i).getVeganStatus() + "------");
                            filterCorrect = true;
                        } else {
                            filterCorrect = false;
                            ignoreCheck = true;
                        }
                    }

                    if (filterCorrect) {
                        filterProducts.add(products.get(i));
                    }
                }
            }


            if (isHasFilter) {
                adapter.updateData(filterProducts);
                setResultCount(filterProducts);
            } else {
                adapter.updateData(products);
                setResultCount(products);
            }
        } else {
            setResultCount(products);
        }
    }

    public void fetchProducts(String keyword) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Resource> call = null;

        if (searchScope.equals("search")) {
            call = apiInterface.doGetResponseBySearch(Constant.API_KEY, keyword);
        } else if (searchScope.equals("barcode")) {
            call = apiInterface.doGetResponseByBarcode(Constant.API_KEY, keyword);
        }

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        call.enqueue(new Callback<Resource>() {
            @Override
            public void onResponse(Call<Resource> call, Response<Resource> response) {
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

                updateFilter(isClear, isNoPalmOil, isNoGMO, isGlutenFree, isNutFree, isSoyFree, isVeganCompany);
            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
                dialog.dismiss();
                Log.d("Filter", "Filter size when fail -> " + t.getMessage());
            }
        });
    }

    public boolean getIsNoPalmOi() {
        return isNoPalmOil;
    }

    public boolean getIsNoGMO() {
        return isNoGMO;
    }

    public boolean getIsGlutenFree() {
        return isGlutenFree;
    }

    public boolean getIsNutFree() {
        return isNutFree;
    }

    public boolean getIsSoyFree() {
        return isSoyFree;
    }

    public boolean getIsVeganCompany() {
        return isVeganCompany;
    }

}
