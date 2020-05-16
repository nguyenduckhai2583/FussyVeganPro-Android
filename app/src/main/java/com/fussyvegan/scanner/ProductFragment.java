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
import android.widget.Toast;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.R;
import com.squareup.picasso.Picasso;

import io.realm.Realm;


public class ProductFragment extends Fragment {
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

    public ProductFragment() {
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
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ImageView imgProduct = view.findViewById( R.id.imgProductDetail );
        if(!product.getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkPhoto())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }

        ImageView imgBarcode = view.findViewById( R.id.imgBarcode );
        if(product.getMainInfo()!=null && !product.getMainInfo().isEmpty()) {
            Picasso.get()
                    .load(product.getMainInfo())
                    .placeholder(R.drawable.ic_blank_barcode)
                    .into(imgBarcode);
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
        float strong2Size = 1.6f;


        //NAME
        text2Span.setSpan(new ForegroundColorSpan(Color.DKGRAY), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2Span.setSpan(new RelativeSizeSpan(strong2Size), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2Span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexName2, indexName2 + lName2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txvTitle.setText(text2Span);
        txvTitle.setMovementMethod(new ScrollingMovementMethod());



        TextView txvavgPrice = view.findViewById( R.id.txvavgPrice);



        SpannableString text4Span = new SpannableString(
                product.getavgPrice() );

        int lavgPrice = product.getavgPrice().length();







        int indexavgPrice = 0;





        float title4Size = 1.3f;
        float vegan4Size = 2.5f;
        float strong4Size = 1.6f;
        float content4Size = 1.2f;


        //AVG PRICE
        text4Span.setSpan(new ForegroundColorSpan(Color.BLACK), indexavgPrice, indexavgPrice + lavgPrice, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text4Span.setSpan(new RelativeSizeSpan(strong4Size), indexavgPrice, indexavgPrice + lavgPrice, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text4Span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexavgPrice, indexavgPrice + lavgPrice, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        txvavgPrice.setText(text4Span);
        txvavgPrice.setMovementMethod(new ScrollingMovementMethod());


        TextView txvpricePer = view.findViewById( R.id.txvpricePer);



        SpannableString text3Span = new SpannableString(
                product.getpricePer() );

        int lpricePer = product.getpricePer().length();







        int indexpricePer = 0;





        float title3Size = 1.3f;
        float vegan3Size = 2.5f;
        float strong3Size = 1.6f;
        float content3Size = 1.2f;


        //PRICE PER
        text3Span.setSpan(new ForegroundColorSpan(Color.DKGRAY), indexpricePer, indexpricePer + lpricePer, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text3Span.setSpan(new RelativeSizeSpan(content3Size), indexpricePer, indexpricePer + lpricePer, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text3Span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexpricePer, indexpricePer + lpricePer, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        txvpricePer.setText(text3Span);
        txvpricePer.setMovementMethod(new ScrollingMovementMethod());


        TextView txvSpecialDetail = view.findViewById( R.id.txvSpecialDetail);



        SpannableString text5Span = new SpannableString(
                product.getSpecialDetail() );

        int lSpecialDetail = product.getSpecialDetail().length();







        int indexSpecialDetail = 0;





        float title5Size = 1.3f;
        float vegan5Size = 2.5f;
        float strong5Size = 1.6f;
        float content5Size = 1.2f;


        //SPECIAL DETAIL
        text5Span.setSpan(new ForegroundColorSpan(Color.DKGRAY), indexSpecialDetail, indexSpecialDetail + lSpecialDetail, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text5Span.setSpan(new RelativeSizeSpan(content5Size), indexSpecialDetail, indexSpecialDetail + lSpecialDetail, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text5Span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexSpecialDetail, indexSpecialDetail + lSpecialDetail, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);




        txvSpecialDetail.setText(text5Span);
        txvSpecialDetail.setMovementMethod(new ScrollingMovementMethod());




        TextView txvDetail = view.findViewById( R.id.txvDetail );
        String titleExpl = "Explanation";
        String titleDesc = "Last Verified";
        String titleWeb = "Ingredients";
        String titleCompanyName = "Company Name";
        String titleAnimal = "Company Animal Testing";
        String titleMan = "Company Info";
        String titleManvegan = "Company Vegan Status";



        SpannableString textSpan = new SpannableString(
                product.getExplanation() + "\n\n" +
                        titleDesc + "\n" +
                        product.getLastUpdate() + "\n\n" +
                        titleWeb + "\n" +
                        product.getIngredient() + "\n\n" +
                        titleCompanyName + "\n" +
                        product.getCompanyName() + "\n\n" +
                        titleAnimal + "\n" +
                        product.getAnimal() + "\n\n" +
                        titleMan + "\n" +
                        product.getMainInfo() + "\n\n" +
                        titleManvegan + "\n" +
                        product.getManvegan() + "\n\n\n\n\n\n\n");


        int lStatus = product.getVeganStatus().length();
        int lExpl = product.getExplanation().length();
        int lDesc = product.getLastUpdate().length();
        int lWeb = product.getIngredient().length();
        int lCompanyName = product.getCompanyName().length();
        int lAnimal = product.getAnimal().length();
        int lMan = product.getMainInfo().length();
        int lManvegan = product.getManvegan().length();





        int lTDesc = titleDesc.length() + 3;
        int lTWeb = titleWeb.length() + 3;
        int lTCompanyName = titleCompanyName.length() + 3;
        int lTAnimal = titleAnimal.length() + 3;
        int lTMan = titleMan.length() + 3;
        int lTManvegan = titleManvegan.length() + 3;






        int indexExpl = 0;
        int indexDesc = indexExpl + lExpl + lTDesc;
        int indexWeb = indexDesc + lDesc + lTWeb;
        int indexCompanyName = indexWeb + lWeb + lTCompanyName;
        int indexAnimal = indexCompanyName+ lCompanyName + lTAnimal;
        int indexMan = indexAnimal + lAnimal + lTMan;
        int indexManvegan = indexMan + lMan + lTManvegan;





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

        //**COMPANY NAME
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexWeb + lWeb, indexWeb+ lWeb+lTWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexWeb + lWeb, indexWeb + lWeb+lTCompanyName, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //COMPANY NAME
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexCompanyName, indexCompanyName + lCompanyName, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //**ANIMAL TESTING
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexCompanyName + lCompanyName, indexCompanyName + lCompanyName+lTAnimal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexCompanyName + lCompanyName, indexCompanyName + lCompanyName+lTAnimal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int color2Status = 0x579F2BFF;

        if(product.getAnimal().equals("NO ANIMAL TESTING")){
            color2Status = 0xFF579F2B;
        } else if (product.getAnimal().equals("UNKNOWN")){
            color2Status = 0xFFF3AF22;
        } else if (product.getAnimal().equals("CAUTION")){
            color2Status = 0xFFF3AF22;
        } else if (product.getAnimal().equals("DOES ANIMAL TESTING")){
            color2Status = 0xFFBE2813;
        }

        //ANIMAL TESTING
        textSpan.setSpan(new ForegroundColorSpan(color2Status), indexAnimal, indexAnimal + lAnimal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(strongSize), indexAnimal, indexAnimal + lAnimal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexAnimal, indexAnimal + lAnimal, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //**MANUFACTURER INFO
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexAnimal + lAnimal, indexAnimal + lAnimal+lTMan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexAnimal + lAnimal, indexAnimal + lAnimal+lTMan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //MANUFACTURER INFO
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexMan, indexMan + lMan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //**MANUFACTURER VEGAN STATUS
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexMan + lMan, indexMan + lMan+lTManvegan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexMan + lMan, indexMan + lMan+lTManvegan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int color3Status = 0xFFF3AF22;

        if(product.getManvegan().equals("VEGAN")){
            color3Status = 0xFF579F2B;
        }  else if (product.getManvegan().equals("OMNI")){
            color3Status = 0xFFF3AF22;
        } else if (product.getManvegan().equals("NOT VEGAN")){
            color3Status = 0xFFBE2813;
        }


        //MANUFACTURER VEGAN STATUS
        textSpan.setSpan(new ForegroundColorSpan(color3Status), indexManvegan, indexManvegan + lManvegan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(strongSize), indexManvegan, indexManvegan + lManvegan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexManvegan, indexManvegan + lManvegan, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);





        txvDetail.setText(textSpan);
        txvDetail.setMovementMethod(new ScrollingMovementMethod());
        activity.visibleBackItem(true);
        activity.showOnlyFavorite();
        activity.invalidateOptionsMenu();
        return view;
    }

    public void addToFavorite(){
        Toast.makeText(getContext(), "Added to My List", Toast.LENGTH_SHORT).show();
        Realm realm = Realm.getDefaultInstance();
        Product p = realm.where(Product.class).equalTo("id", product.getId()).findFirst();
        realm.beginTransaction();
        if(p == null) {
            p = realm.createObject(Product.class); // Create a new object
        }
        p.copy(product);
        realm.commitTransaction();
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
