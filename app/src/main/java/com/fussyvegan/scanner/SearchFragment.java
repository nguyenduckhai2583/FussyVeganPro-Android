package com.fussyvegan.scanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductAdapter;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.Resource;
import com.fussyvegan.scanner.search.CustomEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    List<Product> products = new ArrayList<>();
    List<Product> filterProducts;
    MainActivity activity;
    ListView ltvProduct;
    View header;
    SearchView searchView;
    private ProductAdapter adapter;
    private TextView txvResult;

    private String vegan;
    private String noPalm;
    private String glutenFree;
    private String nutFree;
    private String soyFree;
    private int page = 1;
    private String mKeyword;
    private boolean mIsLoadMore;
    private TextView tvNumFilterActive;
    private TextView tvNumProductFound;
    private int mNumberFiler = 0;


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
        View view = inflater.inflate(R.layout.fragment_search_products, container, false);
        header = getLayoutInflater().inflate(R.layout.search_header, null);
        tvNumFilterActive = view.findViewById(R.id.tvNumFilterActive);
        tvNumProductFound = view.findViewById(R.id.tvNumProductFound);
        ltvProduct = view.findViewById(R.id.ltvProduct);
        adapter = new ProductAdapter(products, false);
        ltvProduct.setAdapter(adapter);
        ltvProduct.addHeaderView(header);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);

                    intent.putExtra("product", products.get(position - 1));
                    intent.putExtra("category", 1);

                    startActivity(intent);
                }

            }
        });


        ltvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (lastInScreen == totalItemCount && totalItemCount != 0) {
                    Log.d("vao dau chua", lastInScreen + "----1232321--" + totalItemCount);

                    if (!mIsLoadMore && products.size() >= 50) {
                        mIsLoadMore = true;
                        page = page + 1;
                        fetchProducts(mKeyword);
                    }
                }
            }
        });

        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.setQuery(activity.keyword, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mKeyword = query.replace("'", "’");
                fetchProducts(mKeyword);
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
        activity.showFilterSearchOnly();
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.keyword = "";
        activity.searchScope = "";
        searchView.clearFocus();
        EventBus.getDefault().unregister(this);
    }

    private void setResultCount(int number) {
        tvNumProductFound.setText(String.valueOf(number));
    }

    @Subscribe
    public void OnCustomEvent(CustomEvent event) {
        mNumberFiler = 0;
        if (event.checkBox2) {
            vegan = "VEGAN";
            mNumberFiler++;
        } else {
            vegan = null;
        }
        if (event.checkBox3) {
            noPalm = "NO";
            mNumberFiler++;

        } else {
            noPalm = null;
        }
        if (event.checkBox4) {
            glutenFree = "YES";
            mNumberFiler++;

        } else {
            glutenFree = null;

        }
        if (event.checkBox5) {
            mNumberFiler++;

            nutFree = "YES";
        } else {
            nutFree = null;

        }
        if (event.checkBox6) {
            mNumberFiler++;

            soyFree = "NO";
        } else {
            soyFree = null;

        }
        mIsLoadMore = false;
        fetchProducts(mKeyword);
        if (mNumberFiler > 0) {
            tvNumFilterActive.setText(String.valueOf(mNumberFiler));
        }

    }


    public void fetchProducts(String keyword) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Resource> call = null;

        if (searchScope.equals("search")) {
            call = apiInterface.getProductsPaginate(keyword, vegan, page, noPalm, glutenFree, nutFree, soyFree);
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
                if (resource.getProducts() != null) {
                    if (!mIsLoadMore) {
                        products.clear();
                    }
                    products.addAll(resource.getProducts());
                    adapter.updateData(products);
                    if (resource.getProducts().size() == 50) {
                        mIsLoadMore = false;
                    }
                    Collections.sort(products, new Comparator<Product>() {
                        public int compare(Product p1, Product p2) {
                            return p1.getName().compareToIgnoreCase(p2.getName()); // To compare string values
                        }
                    });
                    if (resource.getPaginate() != null) {
                        setResultCount(resource.getPaginate().getTotal());
                    }
                }
            }

            @Override
            public void onFailure(Call<Resource> call, Throwable t) {
                call.cancel();
                dialog.dismiss();
                Log.d("Filter", "Filter size when fail -> " + t.getMessage());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }


}
