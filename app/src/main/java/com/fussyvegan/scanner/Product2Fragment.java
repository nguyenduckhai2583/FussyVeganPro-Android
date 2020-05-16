package com.fussyvegan.scanner;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.squareup.picasso.Picasso;


public class Product2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public Product product;

    MainActivity activity;

    public Product2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Product2Fragment newInstance(String param1, String param2) {
        Product2Fragment fragment = new Product2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        activity = (MainActivity)this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product2, container, false);
        ImageView imgProduct = view.findViewById( R.id.imgProductDetail );
        if(!product.getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkPhoto())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }



        ImageView imgVeganstatus = view.findViewById( R.id.imgVeganstatus );
        if(!product.getlinkVegan().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkVegan())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgVeganstatus);
        }

        ImageView imgPalm = view.findViewById( R.id.imgPalm );
        if(!product.getlinkPalm().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkPalm())
                    .placeholder(R.drawable.ic_palm_unknown)
                    .into(imgPalm);
        }

        ImageView imgGmo = view.findViewById( R.id.imgGmo );
        if(!product.getlinkGmo().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkGmo())
                    .placeholder(R.drawable.ic_gmo_unknown)
                    .into(imgGmo);
        }

        ImageView imgGluten = view.findViewById( R.id.imgGluten );
        if(!product.getlinkGluten().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkGluten())
                    .placeholder(R.drawable.ic_gluten_unknown)
                    .into(imgGluten);
        }

        ImageView imgNut = view.findViewById( R.id.imgNut );
        if(!product.getlinkNut().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkNut())
                    .placeholder(R.drawable.ic_nut_unknown)
                    .into(imgNut);
        }

        ImageView imgSoy = view.findViewById( R.id.imgSoy );
        if(!product.getlinkSoy().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkSoy())
                    .placeholder(R.drawable.ic_soy_unknown)
                    .into(imgSoy);
        }



        TextView txvTitle = view.findViewById( R.id.txvTitle);



        SpannableString text2Span = new SpannableString(
                product.getName().toUpperCase() + "\n\n");

        int lName2 = product.getName().length();







        int indexName2 = 0;





        float title2Size = 1.3f;
        float vegan2Size = 2.5f;
        float strong2Size = 1.6f;
        float content2Size = 1.2f;


        //NAME
        text2Span.setSpan(new ForegroundColorSpan(Color.DKGRAY), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2Span.setSpan(new RelativeSizeSpan(strong2Size), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2Span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        txvTitle.setText(text2Span);
        txvTitle.setMovementMethod(new ScrollingMovementMethod());





        TextView txvDetail = view.findViewById( R.id.txvDetail );

        String titleDesc = "Last Verified";
        String titleWeb = "Alternative Names";



        SpannableString textSpan = new SpannableString(
                product.getExplanation() + "\n\n" +
                        titleDesc + "\n" +
                        product.getDescription() + "\n\n" +
                        titleWeb + "\n" +
                        product.getAltName() + "\n\n" + "\n\n\n\n\n\n\n");


        int lStatus = product.getVeganStatus().length();
        int lExpl = product.getExplanation().length();
        int lDesc = product.getDescription().length();
        int lWeb = product.getAltName().length();





        int lTDesc = titleDesc.length() + 3;
        int lTWeb = titleWeb.length() + 3;






        int indexExpl = 0;
        int indexDesc = indexExpl + lExpl + lTDesc;
        int indexWeb = indexDesc + lDesc + lTWeb;





        float titleSize = 1.3f;
        float veganSize = 2.5f;
        float strongSize = 1.6f;
        float contentSize = 1.2f;


        //EXPLANATION
        textSpan.setSpan(new ForegroundColorSpan(Color.BLACK), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //**DESCRIPTION
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexExpl + lExpl, indexExpl + lExpl+lTDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexExpl + lExpl, indexExpl + lExpl+lTDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //DESCRIPTION
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexDesc, indexDesc + lDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //**INGREDIENTS
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexDesc + lDesc, indexDesc + lDesc+lTWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexDesc + lDesc, indexDesc + lDesc+lTWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //INGREDIENTS
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexWeb, indexWeb + lWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



        txvDetail.setText(textSpan);
        txvDetail.setMovementMethod(new ScrollingMovementMethod());
        activity.visibleBackItem(true);
        activity.invalidateOptionsMenu();
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
