package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.ProductReviewAdapter;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import github.nisrulz.screenshott.ScreenShott;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;


public class ProductDetailActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = ProductDetailActivity.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Product product;

    AppCompatRatingBar rb_AveRating;
    TextView tvSumRating;

    MainActivity activity;
    TextView tvProductInfor;
    TextView tvCompanyInfor;
    TextView txvDetail;
    TextView txvTitle;
    TextView tvTime;
    TextView tvCompanyName;
    TextView tvManInfo;
    TextView tvReview;
    TextView tvPrice;
    TextView tvPricePer;
    TextView tvNameCountry;
    TextView textView6;
    TextView tvSpecial;
    // Review
    TextView tvWriteReview;
    TextView tvTotalReview;
    TextView tvRateFiveStar;
    TextView tvRateFourStar;
    TextView tvRateThreeStar;
    TextView tvRateTwoStar;
    TextView tvRateOneStar;
    ImageView imgCamera;
    ImageView imgListWish;
    ImageView imgMap;
    //ImageView imgCountry;
    ImageView imgCompany;
    ImageView imgAnimal;
    ImageView imgWebsite;
    ImageView imgSpecial;
    ImageView imgCertified;
    LinearLayout detail;
    LinearLayout llCountry;
    LinearLayout llCountryName;
    LinearLayout llCertified;
    LinearLayout llSpecial;
    LinearLayout llCompany;
    RelativeLayout lnReview;
    LinearLayout llProduct;
    LinearLayout lnActionChange;
    RecyclerView recyclerViewReview;
    ProductReviewAdapter mApdater;

    // product information
    private TextView tvBarCode;
    private TextView getTvSpecial;
    private TextView tvInfor;
    private TextView tvIngredients;
    private TextView tvCountry;
    private TextView tvPalm;
    private TextView tvGluten;
    private TextView tvNut;
    private TextView tvSoy;
    private TextView tvCertified;
    private ImageView imgBarcodeInfo;
    private ImageView imgSpecialInfo;
    private ImageView imgInfo;
    private ImageView imgIngredients;
    private ImageView imgCountryInfor;
    private ImageView imgPalmInfo;
    private ImageView imgGlutenInfo;
    private ImageView imgNutInfo;
    private ImageView imgSoyInfo;
    private ImageView imgCertifiedInfo;

    private View viewCompanyName;
    private View viewBarcode;
    private View viewSpecial;
    private View viewInfo;
    private View viewIngredients;
    private View viewCountry;
    private View viewCountryName;
    private View viewPalm;
    private View viewGluten;
    private View viewNut;
    private View viewSoy;
    private View viewCertified;

    private ImageView imgCountry;
    private TextView tvCompanyDetails;
    private TextView tvAnimalDetails;


    private Bitmap bitmap;
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean isReview;
    private ProductReview reviewProduct;

    int mCategory;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        rb_AveRating = findViewById(R.id.rb_AveRating);
        tvSumRating = findViewById(R.id.tvSumRating);
        ImageView imgProduct = findViewById(R.id.imgProductDetail);
        tvProductInfor = findViewById(R.id.tvProductInfor);
        tvCompanyInfor = findViewById(R.id.tvCompanyInfor);
        txvTitle = findViewById(R.id.txvTitle);
        txvDetail = findViewById(R.id.txvDetail);
        tvWriteReview = findViewById(R.id.tvWriteReview);
        tvTime = findViewById(R.id.tvTime);
        llCompany = findViewById(R.id.llCompany);
        lnReview = findViewById(R.id.lnReview);
        tvCompanyName = findViewById(R.id.txvDetailCompanyName);
        // tvManInfo = findViewById(R.id.tvManInfo);
        tvReview = findViewById(R.id.tvReview);
        textView6 = findViewById(R.id.textView6);
        tvNameCountry = findViewById(R.id.tvNameCountry);
        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgMap = findViewById(R.id.imgMap);
        //imgCountry = findViewById(R.id.imgCountry);
        imgCompany = findViewById(R.id.imgCompany);
        imgAnimal = findViewById(R.id.imgAnimal);
        imgWebsite = findViewById(R.id.imgWebsite);
        imgSpecial = findViewById(R.id.imgSpecial);
        imgCertified = findViewById(R.id.imgCertified);
        llSpecial = findViewById(R.id.linearLayoutSpecial);
        llCertified= findViewById(R.id.linearLayoutCertified);
        llCountry = findViewById(R.id.linearLayoutCountry);
        llCountryName = findViewById(R.id.linearLayoutCountryName);


        llProduct = findViewById(R.id.llProduct);
        tvBarCode = findViewById(R.id.tvBarcode);
        tvSpecial = findViewById(R.id.tvSpecial);
        tvInfor = findViewById(R.id.tvInfor);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvCountry = findViewById(R.id.tvCountry);
        tvPalm = findViewById(R.id.tvPalm);
        tvGluten = findViewById(R.id.tvGluten);
        tvNut = findViewById(R.id.tvNut);
        tvSoy = findViewById(R.id.tvSoy);
        tvCertified = findViewById(R.id.tvCertified);
        imgBarcodeInfo = findViewById(R.id.imgBarcodeInfo);
        imgSpecialInfo = findViewById(R.id.imgSpecialInfo);
        imgInfo = findViewById(R.id.imgInfo);
        imgIngredients = findViewById(R.id.imgIngredients);
        imgCountryInfor = findViewById(R.id.imgCountryInfo);
        imgPalmInfo = findViewById(R.id.imgPalmInfo);
        imgGlutenInfo = findViewById(R.id.imgGlutenInfo);
        imgNutInfo = findViewById(R.id.imgNutInfo);
        imgSoyInfo = findViewById(R.id.imgSoyInfo);
        imgCertifiedInfo = findViewById(R.id.imgCertifiedInfo);

        detail = findViewById(R.id.linearLayoutDetail);
        viewCompanyName = findViewById(R.id.view_company_name);
        viewBarcode = findViewById(R.id.view_barcode);
        viewCertified = findViewById(R.id.view_certified);
        viewIngredients = findViewById(R.id.view_ingredients);
        viewInfo = findViewById(R.id.view_info);
        viewCountry = findViewById(R.id.view_country);
        viewCountryName = findViewById(R.id.view_country_name);
        viewPalm = findViewById(R.id.view_palm);
        viewGluten = findViewById(R.id.view_gluten);
        viewNut = findViewById(R.id.view_nut);
        viewSoy = findViewById(R.id.view_soy);
        viewSpecial = findViewById(R.id.view_special);

        imgCountry = findViewById(R.id.imgCountry);
        tvCompanyDetails = findViewById(R.id.tvCompanyDetails);
        tvAnimalDetails = findViewById(R.id.tvAnimalDetails);

        tvCompanyDetails = findViewById(R.id.tvCompanyDetails);

        lnActionChange = findViewById(R.id.lnActionChange);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        tvTotalReview = findViewById(R.id.tvTotalReview);
        tvRateFiveStar = findViewById(R.id.tvRateFiveStar);
        tvRateFourStar = findViewById(R.id.tvRateFourStar);
        tvRateThreeStar = findViewById(R.id.tvRateThreeStar);
        tvRateTwoStar = findViewById(R.id.tvRateTwoStar);
        tvRateOneStar = findViewById(R.id.tvRateOneStar);

        tvProductInfor.setSelected(true);
        handleOnClick();
        llCompany.setVisibility(View.GONE);
//        txvDetail.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        product = intent.getParcelableExtra("product");
        mCategory = intent.getIntExtra("category", 1);
        Log.e(TAG, product.toString() + mCategory);


        // info product
        setProductInfo();


        //llProduct.setVisibility(View.GONE);
        tvTime.setText(product.getLastUpdate());
        // tvManInfo.setText(product.getMainInfo());
        if(product.getCompanyName()!= null&& !product.getCompanyName().isEmpty())
            tvCompanyName.setText(product.getCompanyName());
        else{
            tvCompanyName.setVisibility(View.GONE);
            viewCompanyName.setVisibility(View.GONE);
        }


        txvTitle.setText(product.getName().toUpperCase());

        if (!product.getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkPhoto())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }

//        ImageView imgBarcode = findViewById(R.id.imgBarcode);
//        if (product.getLinkBarcode() != null && !product.getLinkBarcode().isEmpty()) {
//            Picasso.get()
//                    .load(product.getLinkBarcode())
//                    .placeholder(R.drawable.ic_blank_barcode)
//                    .into(imgBarcode);
//        }

        ImageView imgVeganstatus = findViewById(R.id.imgVeganstatus);

        if(product.getVeganStatus().equals("VEGAN")){
          imgVeganstatus.setImageResource(R.drawable.vegan);
        } else if(product.getVeganStatus().equals("NOT VEGAN")){
            imgVeganstatus.setImageResource(R.drawable.notvegan);
        } else imgVeganstatus.setImageResource(R.drawable.caution);





        if (product.getSpecialTitle() != null && product.getSpecialTitle().contains("special")) {
            imgSpecial.setVisibility(View.VISIBLE);
        }

        if (product.getCertified() != null && product.getCertified().equals("vegan_australia")) {
            imgCertified.setVisibility(View.VISIBLE);
        }

        if (product.getSpecialDetail() != null && !product.getSpecialDetail().isEmpty()) {
            llSpecial.setVisibility(View.VISIBLE);
            tvSpecial.setText(product.getSpecialDetail());
        }

        if (product.getLinkMap() == null) {
            imgMap.setVisibility(View.GONE);
        } else {
            if (product.getLinkMap().isEmpty() || !product.getLinkMap().contains("http")) {
                imgMap.setVisibility(View.GONE);

            }
        }

        if (product.getCategory().equals("fastfood")) {
            tvNameCountry.setVisibility(View.VISIBLE);
            tvNameCountry.setText(product.getCountry());
        } else {
            tvNameCountry.setVisibility(View.GONE);
        }
        if (product.getCategory().equals("ingredient")) {
            lnActionChange.setVisibility(View.GONE);
            tvSumRating.setVisibility(View.GONE);
            rb_AveRating.setVisibility(View.GONE);

        } else {
            tvNameCountry.setVisibility(View.VISIBLE);
        }


        TextView txvDetail = findViewById(R.id.txvDetail);
        String titleExpl = "Explanation";
        String titleDesc = "";
        String titleWeb = "Ingredients";
        String titleCompanyName = "Company Name";
        String titleAnimal = "Company Animal Testing";
        String titleMan = "Company Info";
        String titleManvegan = "Company Vegan Status";


        SpannableString textSpan = new SpannableString(
//                titleExpl + "\n" +
                product.getExplanation() + "\n\n" +
                        titleDesc + "\n" +
                        "" + "\n\n" +
                        titleWeb + "\n" +
                        product.getIngredient() + "\n\n");


        int lStatus = product.getVeganStatus().length();
        int lExpl = product.getExplanation().length();
        int lDesc = "".length();
        int lWeb = product.getIngredient() != null ? product.getIngredient().length() : 0;


//        int lTExpl = titleExpl.length() + 3;
        int lTDesc = titleDesc.length() + 3;
        int lTWeb = titleWeb.length() + 3;
        int lTCompanyName = titleCompanyName.length() + 3;


        int indexExpl = 0;
        int indexDesc = indexExpl + lExpl + lTDesc;
        int indexWeb = indexDesc + lDesc + lTWeb;


        float titleSize = 1.3f;
        float veganSize = 2.5f;
        float strongSize = 1.6f;
        float contentSize = 1.2f;
        float hideSize = 0f;


        //EXPLANATION
        textSpan.setSpan(new ForegroundColorSpan(Color.BLACK), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexExpl, indexExpl + lExpl, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //**DESCRIPTION
        textSpan.setSpan(new RelativeSizeSpan(hideSize), indexExpl + lExpl, indexExpl + lExpl + lTDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexExpl + lExpl, indexExpl + lExpl + lTDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //DESCRIPTION
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexDesc, indexDesc + lDesc, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //**INGREDIENTS
        textSpan.setSpan(new RelativeSizeSpan(titleSize), indexDesc + lDesc, indexDesc + lDesc + lTWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), indexDesc + lDesc, indexDesc + lDesc + lTWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //INGREDIENTS
        textSpan.setSpan(new RelativeSizeSpan(contentSize), indexWeb, indexWeb + lWeb, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // product information
        setProductInfo();

        // company info
        if(product.getMainInfo() == null && product.getCountry() == null){
            llCountryName.setVisibility(View.GONE);
            viewCountryName.setVisibility(View.GONE);
        } else if(product.getCountry() != null) {
            setFlagForCountry();
        }
        else if(product.getMainInfo() != null && product.getCountry() == null){
            setFlagForCompanyInfo();
        }

        setManvegan();
        setAnimalTesting();

        initRecyclerView();
        getReview(product.getId(), mCategory);

    }


    private void initRecyclerView() {
        mApdater = new ProductReviewAdapter(this);
        recyclerViewReview.setAdapter(mApdater);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setRating(List<ProductReview> productReviews) {
        tvTotalReview.setText(getTotalNumberPoint(productReviews));
        tvRateFiveStar.setText(String.valueOf(getNumberPoint(productReviews, 5)));
        tvRateFourStar.setText(String.valueOf(getNumberPoint(productReviews, 4)));
        tvRateThreeStar.setText(String.valueOf(getNumberPoint(productReviews, 3)));
        tvRateTwoStar.setText(String.valueOf(getNumberPoint(productReviews, 2)));
        tvRateOneStar.setText(String.valueOf(getNumberPoint(productReviews, 1)));
    }

    private int getNumberPoint(List<ProductReview> productReviews, int point) {
        int number = 0;
        for (int i = 0; i < productReviews.size(); i++) {

            if (productReviews.get(i).getRating() == point) {
                number++;
            }
        }
        return number;

    }

    private void setProductInfo() {

        // product information
        Log.e(TAG, String.valueOf(product.getBarcode() == null));
        if (product.getBarcode() == null || product.getBarcode().isEmpty()) {
            tvBarCode.setVisibility(View.GONE);
            imgBarcodeInfo.setVisibility(View.GONE);
            viewBarcode.setVisibility(View.GONE);
        } else tvBarCode.setText(product.getBarcode());
        if (product.getExplanation() == null) {
            tvInfor.setVisibility(View.GONE);
            imgInfo.setVisibility(View.GONE);
            viewInfo.setVisibility(View.GONE);
        } else tvInfor.setText(product.getExplanation());

        if (product.getIngredient() == null || product.getIngredient().isEmpty() || product.getVeganStatus().equals("CAUTION")) {
            tvIngredients.setText("Ingredients not available");
            imgIngredients.setImageResource(R.drawable.ic_ingredients_unknown);
        } else tvIngredients.setText(product.getIngredient());

        if(product.getVeganStatus().equals("VEGAN")) {
            imgIngredients.setImageResource(R.drawable.ic_ingredient_vegan);
            imgInfo.setImageResource(R.drawable.ic_explanation_vegan);

        }
        if(product.getVeganStatus().equals("NOT VEGAN")) {
            imgIngredients.setImageResource(R.drawable.ic_ingredients_notvegan);
            imgInfo.setImageResource(R.drawable.ic_explanation_notvegan);
        }


        if (product.getDescription() == null) {
            llCountry.setVisibility(View.GONE);
            viewCountry.setVisibility(View.GONE);
        } else tvCountry.setText(product.getDescription());

        if(product.getProdpalm().equals("NO")){
            imgPalmInfo.setImageResource(R.drawable.ic_palm_free);
            tvPalm.setText("This product, menu item or ingredient does not contain palm oil.");
        } else if(product.getProdpalm().equals("YES")){
            imgPalmInfo.setImageResource(R.drawable.ic_palm);
            tvPalm.setText("CONTAINS PALM OIL.");
        }else {
            imgPalmInfo.setImageResource(R.drawable.ic_palm_unknown);
            tvPalm.setText("Palm oil status has not yet been verified.");
        }

        if(product.getGluten().equals("NO")){
            imgGlutenInfo.setImageResource(R.drawable.ic_gluten_free);
            tvGluten.setText("This product, menu item or ingredient does not contain gluten. Any potential cross contamination has not been considered in determining the gluten status.");
        } else if(product.getGluten().equals("YES")){
            imgGlutenInfo.setImageResource(R.drawable.ic_gluten);
            tvGluten.setText("CONTAINS GLUTEN.");
        }else {
            imgGlutenInfo.setImageResource(R.drawable.ic_gluten_unknown);
            tvGluten.setText("Gluten status has not yet been verified.");
        }

        if(product.getNut().equals("NO")){
            imgNutInfo.setImageResource(R.drawable.ic_nut_free);
            tvNut.setText("This product, menu item or ingredient does not contain peanuts or tree nuts. Any potential cross contamination has not been considered in determining the nut status.");
        } else if(product.getNut().equals("YES")){
            imgNutInfo.setImageResource(R.drawable.ic_nut);
            tvNut.setText("CONTAINS PEANUTS AND OR TREE NUTS.");
        }else {
            imgNutInfo.setImageResource(R.drawable.ic_nut_unknown);
            tvNut.setText("Nut status has not yet been verified.");
        }

        if(product.getSoy().equals("NO")){
            imgSoyInfo.setImageResource(R.drawable.ic_soy_free);
            tvSoy.setText("This product, menu item or ingredient does not contain soy. Any potential cross contamination has not been considered in determining the soy status.");
        } else if(product.getSoy().equals("YES")){
            imgSoyInfo.setImageResource(R.drawable.ic_soy);
            tvSoy.setText("CONTAINS SOY.");
        }else {
            imgSoyInfo.setImageResource(R.drawable.ic_soy_unknown);
            tvSoy.setText("Soy status has not yet been verified.");
        }



        if(product.getCertified()!= null && product.getCertified().equals("vegan_australia")){
            tvCertified.setText("This product is registered with the Vegan Australia Certified program and meets the criteria set out in the Vegan Australia Certified standard.");
        } else {
            llCertified.setVisibility(View.GONE);
            viewCertified.setVisibility(View.GONE);

        }

        if(product.getSpecialDetail() == null|| product.getSpecialDetail().isEmpty()){
            llSpecial.setVisibility(View.GONE);
            viewSpecial.setVisibility(View.GONE);
        } else {
            tvSpecial.setText(product.getSpecialDetail());
        }
        if (product.getLinkBarcode() != null && !product.getLinkBarcode().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkBarcode())
                    .placeholder(R.drawable.ic_blank_barcode)
                    .into(imgCountryInfor);
        }


    }

    private void setFlagForCompanyInfo() {
        if ( product.getMainInfo() != null && product.getMainInfo().contains("Australia") ) {
            imgCountry.setImageResource(R.drawable.ic_australia);
            tvNameCountry.setText("Australia");
        } else if (product.getMainInfo().contains("Bermuda") && product.getMainInfo() != null) {
            imgCountry.setImageResource(R.drawable.ic_bermuda);
            tvNameCountry.setText("Bermuda");
        } else if (product.getMainInfo().contains("Belgium") && product.getMainInfo() != null)
            {
            imgCountry.setImageResource(R.drawable.ic_belgium);
            tvNameCountry.setText("Belgium");
        } else if (product.getMainInfo().contains("Botswana") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_botswana);
            tvNameCountry.setText("Botswana");
        } else if (product.getMainInfo().contains("Brazil") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_brazil);
            tvNameCountry.setText("Brazil");
        } else if (product.getMainInfo().contains("China") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_china);
            tvNameCountry.setText("China");
        } else if (product.getMainInfo().contains("Canada") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_canada);
            tvNameCountry.setText("Canada");
        } else if (product.getMainInfo().contains("Denmark") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_denmark);
            tvNameCountry.setText("Denmark");
        } else if (product.getMainInfo().contains("Ethiopia") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_ethiopia);
            tvNameCountry.setText("Ethiopia");
        } else if (product.getMainInfo().contains("Fiji") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_fiji);
            tvNameCountry.setText("Fiji");
        } else if (product.getMainInfo().contains("France") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_france);
            tvNameCountry.setText("France");
        } else if (product.getMainInfo().contains("Germany") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_germany);
            tvNameCountry.setText("Germany");
        } else if (product.getMainInfo().contains("Greece") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_greece);
            tvNameCountry.setText("Greece");
        } else if (product.getMainInfo().contains("India") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_india);
            tvNameCountry.setText("India");
        } else if (product.getMainInfo().contains("Indonesia") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_indonesia);
            tvNameCountry.setText("Indonesia");
        } else if (product.getMainInfo().contains("Ireland") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_ireland);
            tvNameCountry.setText("Ireland");
        } else if (product.getMainInfo().contains("Italy") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_italy);
            tvNameCountry.setText("Italy");
        } else if (product.getMainInfo().contains("Japan") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_japan);
            tvNameCountry.setText("Japan");
        } else if (product.getMainInfo().contains("Kenya") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_kenya);
            tvNameCountry.setText("Kenya");
        } else if (product.getMainInfo().contains("Malaysia") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_malaysia);
            tvNameCountry.setText("Malaysia");
        } else if (product.getMainInfo().contains("Mexico") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_mexico);
            tvNameCountry.setText("Mexico");
        } else if (product.getMainInfo().contains("Netherlands") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_netherlands);
            tvNameCountry.setText("Netherlands");
        } else if (product.getMainInfo().contains("New Zealand") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_newzealand);
            tvNameCountry.setText("New Zealand");
        } else if (product.getMainInfo().contains("Norway") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_norway);
            tvNameCountry.setText("Norway");
        } else if (product.getMainInfo().contains("Philippines") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_philippines);
            tvNameCountry.setText("Philippines");
        } else if (product.getMainInfo().contains("Papua New Guinea") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_papua_new_guinea);
            tvNameCountry.setText("Papua New Guinea");
        } else if (product.getMainInfo().contains("Samoa") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_samoa);
            tvNameCountry.setText("Samoa");
        } else if (product.getMainInfo().contains("Seychelles") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_seychelles);
            tvNameCountry.setText("Seychelles");
        } else if (product.getMainInfo().contains("Singapore") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_singapore);
            tvNameCountry.setText("Singapore");
        } else if (product.getMainInfo().contains("South Africa") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_south_africa);
            tvNameCountry.setText("South Africa");
        } else if (product.getMainInfo().contains("South Korea") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_south_korea);
            tvNameCountry.setText("South Korea");
        } else if (product.getMainInfo().contains("Spain") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_spain);
            tvNameCountry.setText("Spain");
        } else if (product.getMainInfo().contains("Sweden") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_sweden);
            tvNameCountry.setText("Sweden");
        } else if (product.getMainInfo().contains("Switzerland") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_switzerland);
            tvNameCountry.setText("Switzerland");
        } else if (product.getMainInfo().contains("Taiwan") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_taiwan);
            tvNameCountry.setText("Taiwan");
        } else if (product.getMainInfo().contains("Tanzania") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_tanzania);
            tvNameCountry.setText("Tanzania");
        } else if (product.getMainInfo().contains("Thailand") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_thailand);
            tvNameCountry.setText("Thailand");
        } else if (product.getMainInfo().contains("Tonga") && product.getMainInfo() != null)
                {
            imgCountry.setImageResource(R.drawable.ic_tonga);
            tvNameCountry.setText("Tonga");
        } else if (product.getMainInfo().contains("Uganda") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_uganda);
            tvNameCountry.setText("Uganda");
        } else if (product.getMainInfo().contains("United Kingdom") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_uk);
            tvNameCountry.setText("United Kingdom");
        } else if (product.getMainInfo().contains("USA") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_usa);
            tvNameCountry.setText("USA");
        } else if (product.getMainInfo().contains("Vanuatu") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_vanuatu);
            tvNameCountry.setText("Vanuatu");
        } else if (product.getMainInfo().contains("Vietnam") && product.getMainInfo() != null)
                 {
            imgCountry.setImageResource(R.drawable.ic_vietnam);
            tvNameCountry.setText("Vietnam");
        } else if (product.getMainInfo().contains("Zambia") && product.getMainInfo() != null)
               {
            imgCountry.setImageResource(R.drawable.ic_zambia);
            tvNameCountry.setText("Zambia");
        } else {
            llCountryName.setVisibility(View.GONE);
            viewCountryName.setVisibility(View.GONE);
        }
    }

    private void setFlagForCountry(){
        if (product.getCountry().equals("Australia")) {
            imgCountry.setImageResource(R.drawable.ic_australia);
            tvNameCountry.setText("Australia");
        } else if ( product.getCountry().equals("Bermuda")) {
            imgCountry.setImageResource(R.drawable.ic_bermuda);
            tvNameCountry.setText("Bermuda");
        } else if ( product.getCountry().equals("Belgium")) {
            imgCountry.setImageResource(R.drawable.ic_belgium);
            tvNameCountry.setText("Belgium");
        } else if (product.getCountry().equals("Botswana")) {
            imgCountry.setImageResource(R.drawable.ic_botswana);
            tvNameCountry.setText("Botswana");
        } else if ( product.getCountry().equals("Brazil")) {
            imgCountry.setImageResource(R.drawable.ic_brazil);
            tvNameCountry.setText("Brazil");
        } else if ( product.getCountry().equals("China")) {
            imgCountry.setImageResource(R.drawable.ic_china);
            tvNameCountry.setText("China");
        } else if ( product.getCountry().equals("Canada")) {
            imgCountry.setImageResource(R.drawable.ic_canada);
            tvNameCountry.setText("Canada");
        } else if ( product.getCountry().equals("Denmark")) {
            imgCountry.setImageResource(R.drawable.ic_denmark);
            tvNameCountry.setText("Denmark");
        } else if ( product.getCountry().equals("Ethiopia")) {
            imgCountry.setImageResource(R.drawable.ic_ethiopia);
            tvNameCountry.setText("Ethiopia");
        } else if ( product.getCountry().equals("Fiji")){
            imgCountry.setImageResource(R.drawable.ic_fiji);
            tvNameCountry.setText("Fiji");
        } else if ( product.getCountry().equals("France")) {
            imgCountry.setImageResource(R.drawable.ic_france);
            tvNameCountry.setText("France");
        } else if ( product.getCountry().equals("Germany")) {
            imgCountry.setImageResource(R.drawable.ic_germany);
            tvNameCountry.setText("Germany");
        } else if ( product.getCountry().equals("Greece")) {
            imgCountry.setImageResource(R.drawable.ic_greece);
            tvNameCountry.setText("Greece");
        } else if ( product.getCountry().equals("India")) {
            imgCountry.setImageResource(R.drawable.ic_india);
            tvNameCountry.setText("India");
        } else if ( product.getCountry().equals("Indonesia")) {
            imgCountry.setImageResource(R.drawable.ic_indonesia);
            tvNameCountry.setText("Indonesia");
        } else if ( product.getCountry().equals("Ireland")) {
            imgCountry.setImageResource(R.drawable.ic_ireland);
            tvNameCountry.setText("Ireland");
        } else if ( product.getCountry().equals("Italy")) {
            imgCountry.setImageResource(R.drawable.ic_italy);
            tvNameCountry.setText("Italy");
        } else if ( product.getCountry().equals("Japan")) {
            imgCountry.setImageResource(R.drawable.ic_japan);
            tvNameCountry.setText("Japan");
        } else if (product.getCountry().equals("Kenya")) {
            imgCountry.setImageResource(R.drawable.ic_kenya);
            tvNameCountry.setText("Kenya");
        } else if ( product.getCountry().equals("Malaysia")) {
            imgCountry.setImageResource(R.drawable.ic_malaysia);
            tvNameCountry.setText("Malaysia");
        } else if ( product.getCountry().equals("Mexico")) {
            imgCountry.setImageResource(R.drawable.ic_mexico);
            tvNameCountry.setText("Mexico");
        } else if ( product.getCountry().equals("Netherlands")) {
            imgCountry.setImageResource(R.drawable.ic_netherlands);
            tvNameCountry.setText("Netherlands");
        } else if ( product.getCountry().equals("New Zealand")) {
            imgCountry.setImageResource(R.drawable.ic_newzealand);
            tvNameCountry.setText("New Zealand");
        } else if ( product.getCountry().equals("Norway")) {
            imgCountry.setImageResource(R.drawable.ic_norway);
            tvNameCountry.setText("Norway");
        } else if ( product.getCountry().equals("Philippines")) {
            imgCountry.setImageResource(R.drawable.ic_philippines);
            tvNameCountry.setText("Philippines");
        } else if ( product.getCountry().equals("Papua New Guinea")) {
            imgCountry.setImageResource(R.drawable.ic_papua_new_guinea);
            tvNameCountry.setText("Papua New Guinea");
        } else if ( product.getCountry().equals("Samoa")) {
            imgCountry.setImageResource(R.drawable.ic_samoa);
            tvNameCountry.setText("Samoa");
        } else if ( product.getCountry().equals("Seychelles")) {
            imgCountry.setImageResource(R.drawable.ic_seychelles);
            tvNameCountry.setText("Seychelles");
        } else if ( product.getCountry().equals("Singapore")) {
            imgCountry.setImageResource(R.drawable.ic_singapore);
            tvNameCountry.setText("Singapore");
        } else if ( product.getCountry().equals("South Africa")) {
            imgCountry.setImageResource(R.drawable.ic_south_africa);
            tvNameCountry.setText("South Africa");
        } else if ( product.getCountry().equals("South Korea")) {
            imgCountry.setImageResource(R.drawable.ic_south_korea);
            tvNameCountry.setText("South Korea");
        } else if ( product.getCountry().equals("Spain")) {
            imgCountry.setImageResource(R.drawable.ic_spain);
            tvNameCountry.setText("Spain");
        } else if (product.getCountry().equals("Sweden")) {
            imgCountry.setImageResource(R.drawable.ic_sweden);
            tvNameCountry.setText("Sweden");
        } else if ( product.getCountry().equals("Switzerland")) {
            imgCountry.setImageResource(R.drawable.ic_switzerland);
            tvNameCountry.setText("Switzerland");
        } else if ( product.getCountry().equals("Taiwan")) {
            imgCountry.setImageResource(R.drawable.ic_taiwan);
            tvNameCountry.setText("Taiwan");
        } else if ( product.getCountry().equals("Tanzania")) {
            imgCountry.setImageResource(R.drawable.ic_tanzania);
            tvNameCountry.setText("Tanzania");
        } else if ( product.getCountry().equals("Thailand")) {
            imgCountry.setImageResource(R.drawable.ic_thailand);
            tvNameCountry.setText("Thailand");
        } else if ( product.getCountry().equals("Tonga")) {
            imgCountry.setImageResource(R.drawable.ic_tonga);
            tvNameCountry.setText("Tonga");
        } else if ( product.getCountry().equals("Uganda")) {
            imgCountry.setImageResource(R.drawable.ic_uganda);
            tvNameCountry.setText("Uganda");
        } else if ( product.getCountry().equals("United Kingdom")) {
            imgCountry.setImageResource(R.drawable.ic_uk);
            tvNameCountry.setText("United Kingdom");
        } else if (product.getCountry().equals("USA")) {
            imgCountry.setImageResource(R.drawable.ic_usa);
            tvNameCountry.setText("USA");
        } else if ( product.getCountry().equals("Vanuatu")) {
            imgCountry.setImageResource(R.drawable.ic_vanuatu);
            tvNameCountry.setText("Vanuatu");
        } else if ( product.getCountry().equals("Vietnam")) {
            imgCountry.setImageResource(R.drawable.ic_vietnam);
            tvNameCountry.setText("Vietnam");
        } else if (product.getCountry().equals("Zambia")) {
            imgCountry.setImageResource(R.drawable.ic_zambia);
            tvNameCountry.setText("Zambia");
        } else {
            llCountryName.setVisibility(View.GONE);
            viewCountryName.setVisibility(View.GONE);
        }
    }

    private void setManvegan() {
        if (product.getManvegan() != null && product.getManvegan().equals("VEGAN")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man));
            tvCompanyDetails.setText("Manufactures only vegan products.");
        } else if (product.getManvegan() != null && product.getManvegan().equals("OMNI")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_omi));
            tvCompanyDetails.setText("Manufactures vegan and non vegan products.");
        } else if (product.getManvegan() != null && product.getManvegan().equals("NOT VEGAN")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_not_vegan));
            tvCompanyDetails.setText("Manufactures only non vegan products");
        } else {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_omi));
            tvCompanyDetails.setText("Manufactures vegan and non vegan products.");
        }
    }

    private void setAnimalTesting() {
        if (product.getAnimal() != null && product.getAnimal().equals("NO ANIMAL TESTING")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_no_animal));
            tvAnimalDetails.setText("This company does not do any animal testing, nor are they knowingly complicit in any third party animal testing of their products.");
        } else if (product.getAnimal() != null && product.getAnimal().equals("DOES ANIMAL TESTING")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_animal_testing_no));
            tvAnimalDetails.setText("This company either does animal testing, hires third parties to do animal testing or is knowingly complicit in third parties doing animal testing of their products.");
        } else {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unknown_animal));
            tvAnimalDetails.setText("Unknown animal testing policy.");
        }
        //ANIMAL TESTING
    }

    private String getTotalNumberPoint(List<ProductReview> productReviews) {
        float number = 0;
        for (int i = 0; i < productReviews.size(); i++) {
            number = number + productReviews.get(i).getRating();

        }
        return String.format("%.1f", number / productReviews.size());

    }


    private void handleOnClick() {
        tvProductInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView6.setText("Product information");
                llProduct.setVisibility(View.VISIBLE);
                llCompany.setVisibility(View.GONE);
                lnReview.setVisibility(View.GONE);
//                txvDetail.setVisibility(View.VISIBLE);
                tvProductInfor.setSelected(true);
                tvCompanyInfor.setSelected(false);
                tvReview.setSelected(false);
                //  llSpecial.setVisibility(View.VISIBLE);
                if (product.getSpecialDetail() != null && !product.getSpecialDetail().isEmpty()) {
                    llSpecial.setVisibility(View.VISIBLE);
                    //tvSpecial.setText(product.getSpecialDetail());
                } else {
                    //llSpecial.setVisibility(View.GONE);
                }

            }
        });

        tvCompanyInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView6.setText("Company information");
                llCompany.setVisibility(View.VISIBLE);
                //llSpecial.setVisibility(View.GONE);
                llProduct.setVisibility(View.GONE);
                //  txvDetail.setVisibility(View.GONE);
                lnReview.setVisibility(View.GONE);

                tvProductInfor.setSelected(false);
                tvReview.setSelected(false);

                //tvCompanyName.setVisibility(View.VISIBLE);
                tvCompanyInfor.setSelected(true);
            }
        });

        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
                    Intent intentProduct = new Intent(ProductDetailActivity.this, ReviewActivity.class);
                    intentProduct.putExtra("product", product);
                    intentProduct.putExtra("category", mCategory);

                    if (isReview) {
                        intentProduct.putExtra("review", reviewProduct);
                    }
                    tvProductInfor.setSelected(false);
                    tvCompanyInfor.setSelected(false);
                    tvReview.setSelected(true);
                    startActivityForResult(intentProduct, Constant.LAUNCH_REVIEW_ACTIVITY);
                } else {
                    Intent loginScreen = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(loginScreen);
                }


            }

        });
        tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView6.setText("Rating and Reviews");
                tvProductInfor.setSelected(false);
                tvCompanyInfor.setSelected(false);
                tvReview.setSelected(true);
                llCompany.setVisibility(View.GONE);
                llProduct.setVisibility(View.GONE);
                // llSpecial.setVisibility(View.GONE);
                //  txvDetail.setVisibility(View.GONE);
                lnReview.setVisibility(View.VISIBLE);

            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
                final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(ProductDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    } else {
                        RuntimePermissionUtil.requestPermission(ProductDetailActivity.this, requestWritePermission, 100);

                    }
                }
            }

        });

        imgListWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorite();

            }
        });

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getLinkMap() != null && product.getLinkMap().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getLinkMap()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "No Map Link Available", Toast.LENGTH_SHORT).show();

                }
            }
        });


        imgWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getAddinfo() != null && product.getAddinfo().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getAddinfo()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void saveScreenshot() {
        // Save the screenshot
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = df.format(Calendar.getInstance().getTime());

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(ProductDetailActivity.this, bitmap, "date");
            // Display a toast
            Toast.makeText(ProductDetailActivity.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getReview(int idProduct, int idType) {
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<Reviews> call = apiInterface.getReviewProduct(token, idProduct, idType);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {

                Log.d(TAG, String.valueOf(response.code()));
                if (!response.body().getData().isEmpty()) {
                    mApdater.updateData(response.body().getData());
                    setRating(response.body().getData());
                    checkIsReview(response.body().getData());
                    setOverallRatingReview(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.d(TAG, t.getMessage());
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

        if(numReview == 0) tvSumRating.setText("No" + " Rating");
        else if(numReview==1)tvSumRating.setText("1" + " Rating");
        else tvSumRating.setText(numReview + " Ratings");
        rb_AveRating.setRating(aveRating);
    }

    private void checkIsReview(List<ProductReview> productReviews) {
        for (int i = 0; i < productReviews.size(); i++) {

            if (productReviews.get(i).getUsername().equals(SharedPrefs.getInstance().get(Constant.USER_NAME, String.class))) {
                isReview = true;
                reviewProduct = productReviews.get(i);
                break;
            }
        }

    }

    public void addToFavorite() {
        Toast.makeText(this, "Added to My List", Toast.LENGTH_SHORT).show();
        Realm realm = Realm.getDefaultInstance();
        Product p = realm.where(Product.class).equalTo("id", product.getId()).findFirst();
        realm.beginTransaction();
        if (p == null) {
            p = realm.createObject(Product.class); // Create a new object
        }
        p.copy(product);
        realm.commitTransaction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.LAUNCH_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                getReview(product.getId(), mCategory);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {

                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RuntimePermissionUtil.RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            saveScreenshot();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(ProductDetailActivity.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }

}
