package com.fussyvegan.scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.adapter.ProductAdapter;
import com.fussyvegan.scanner.model.Product;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;


public class FavoriteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    private List<Product> products;
    private ListView ltvProduct;
    MainActivity activity;
    public boolean isEdit;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FavoriteFragment newInstance(int columnCount) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        activity = (MainActivity)this.getActivity();

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Product> rrProducts = realm.where(Product.class).findAll();
        if(rrProducts != null && rrProducts.size() > 0) {
            products = new ArrayList(rrProducts);
        } else {
            products = new ArrayList<>();
        }
        Log.i("TAG",products.size()+"size ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ltvProduct = view.findViewById(R.id.ltvFavoriteProduct);
        setupListView();
        activity.showOnlyEdit();
        activity.invalidateOptionsMenu();
        activity.visibleBackItem(true);
        return view;
    }

    public void setupListView(){
        ProductAdapter adapter = new ProductAdapter(products, false);
        ltvProduct.setAdapter(adapter);
        ltvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG",position+"  click");
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product", products.get(position));
                startActivity(intent);
            }
        });
    }

    public void edit(){
        isEdit = !isEdit;
        ProductAdapter adapter = new ProductAdapter(products, isEdit );
        ltvProduct.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(Product product);
//    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Product product);
    }
}
