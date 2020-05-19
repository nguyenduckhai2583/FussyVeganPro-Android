package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
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
    //TextView tvNameCountry;
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
    ImageView imgCompany;
    ImageView imgAnimal;
    ImageView imgWebsite;
    ImageView imgSpecial;
    ImageView imgCertified;
    RelativeLayout llCompany;
    RelativeLayout lnReview;
    LinearLayout lnPrice;
    LinearLayout llSpecial;
    LinearLayout lnActionChange;
    RecyclerView recyclerViewReview;
    ProductReviewAdapter mApdater;
    private Bitmap bitmap;
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean isReview;
    private ProductReview reviewProduct;

    int mCategory;


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
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvManInfo = findViewById(R.id.tvManInfo);
        tvReview = findViewById(R.id.tvReview);
        lnPrice = findViewById(R.id.lnPrice);
        tvPrice = findViewById(R.id.tvPrice);
        textView6 = findViewById(R.id.textView6);
        tvPricePer = findViewById(R.id.tvPricePer);
    //    tvNameCountry = findViewById(R.id.tvNameCountry);
        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgMap = findViewById(R.id.imgMap);
        imgCompany = findViewById(R.id.imgCompany);
        imgAnimal = findViewById(R.id.imgAnimal);
        imgWebsite = findViewById(R.id.imgWebsite);
        imgSpecial = findViewById(R.id.imgSpecial);
        imgCertified = findViewById(R.id.imgCertified);
        llSpecial = findViewById(R.id.llSpecial);
        tvSpecial = findViewById(R.id.tvSpecial);
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
        txvDetail.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        product = intent.getParcelableExtra("product");
        mCategory = intent.getIntExtra("category", 1);

        tvTime.setText(product.getLastUpdate());
        tvCompanyName.setText(product.getCompanyName());
        tvManInfo.setText(product.getMainInfo());

        if (product.getavgPrice() != null && !product.getavgPrice().isEmpty()) {
            lnPrice.setVisibility(View.VISIBLE);

            tvPrice.setText(product.getavgPrice());
            tvPricePer.setText(product.getpricePer());
        } else {
            lnPrice.setVisibility(View.GONE);


        }

        txvTitle.setText(product.getName().toUpperCase());

        if (!product.getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkPhoto())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgProduct);
        }

        ImageView imgBarcode = findViewById(R.id.imgBarcode);
        if (product.getLinkBarcode() != null && !product.getLinkBarcode().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkBarcode())
                    .placeholder(R.drawable.ic_blank_barcode)
                    .into(imgBarcode);
        }

        ImageView imgVeganstatus = findViewById(R.id.imgVeganstatus);
        if (product.getlinkVegan() != null && !product.getlinkVegan().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkVegan())
                    .placeholder(R.drawable.ic_app_150)
                    .into(imgVeganstatus);
        }

        ImageView imgPalm = findViewById(R.id.imgPalm);
        if (!product.getlinkPalm().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkPalm())
                    .placeholder(R.drawable.ic_palm_unknown)
                    .into(imgPalm);
        }

        ImageView imgGmo = findViewById(R.id.imgGmo);
        if (!product.getlinkGmo().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkGmo())
                    .placeholder(R.drawable.ic_gmo_unknown)
                    .into(imgGmo);
        }

        ImageView imgGluten = findViewById(R.id.imgGluten);
        if (!product.getlinkGluten().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkGluten())
                    .placeholder(R.drawable.ic_gluten_unknown)
                    .into(imgGluten);
        }

        ImageView imgNut = findViewById(R.id.imgNut);
        if (!product.getlinkNut().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkNut())
                    .placeholder(R.drawable.ic_nut_unknown)
                    .into(imgNut);
        }

        ImageView imgSoy = findViewById(R.id.imgSoy);
        if (!product.getlinkSoy().isEmpty()) {
            Picasso.get()
                    .load(product.getlinkSoy())
                    .placeholder(R.drawable.ic_soy_unknown)
                    .into(imgSoy);
        }

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
        //    tvNameCountry.setVisibility(View.VISIBLE);
        //    tvNameCountry.setText(product.getCountry());
        } else {
        //    tvNameCountry.setVisibility(View.GONE);
        }
        if (product.getCategory().equals("ingredient")) {
            lnActionChange.setVisibility(View.GONE);
        } else {
        //    tvNameCountry.setVisibility(View.VISIBLE);
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


        if (product.getAnimal() != null && product.getAnimal().equals("NO ANIMAL TESTING")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_no_animal));

        } else if (product.getAnimal() != null && product.getAnimal().equals("UNKNOWN")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unknown_animal));
        } else if (product.getAnimal() != null && product.getAnimal().equals("CAUTION")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unknown_animal));
        } else if (product.getAnimal() != null && product.getAnimal().equals("DOES ANIMAL TESTING")) {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_animal_testing_no));
        } else {
            imgAnimal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unknown_animal));

        }
        //ANIMAL TESTING


        if (product.getManvegan() != null && product.getManvegan().equals("VEGAN")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man));
        } else if (product.getManvegan() != null && product.getManvegan().equals("OMNI")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_omi));
        } else if (product.getManvegan() != null && product.getManvegan().equals("NOT VEGAN")) {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_not_vegan));
        } else {
            imgCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_man_omi));

        }

        txvDetail.setText(textSpan);
        txvDetail.setMovementMethod(new ScrollingMovementMethod());
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
                llCompany.setVisibility(View.GONE);
                lnReview.setVisibility(View.GONE);
                txvDetail.setVisibility(View.VISIBLE);
                tvProductInfor.setSelected(true);
                tvCompanyInfor.setSelected(false);
                tvReview.setSelected(false);
                if (product.getSpecialDetail() != null && !product.getSpecialDetail().isEmpty()) {
                    llSpecial.setVisibility(View.VISIBLE);
                    tvSpecial.setText(product.getSpecialDetail());
                } else {
                    llSpecial.setVisibility(View.GONE);
                }

            }
        });

        tvCompanyInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView6.setText("Company information");
                llCompany.setVisibility(View.VISIBLE);
                llSpecial.setVisibility(View.GONE);
                txvDetail.setVisibility(View.GONE);
                lnReview.setVisibility(View.GONE);

                tvProductInfor.setSelected(false);
                tvReview.setSelected(false);

                tvCompanyInfor.setSelected(true);
            }
        });

        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefs.getInstance().get(Constant.IS_LOGIN,Boolean.class)) {
                    Intent intentProduct = new Intent(ProductDetailActivity.this, ReviewActivity.class);
                    intentProduct.putExtra("product", product);
                    intentProduct.putExtra("category", mCategory);

                    if (isReview) {
                        intentProduct.putExtra("review", reviewProduct);
                    }
                    tvProductInfor.setSelected(false);
                    tvCompanyInfor.setSelected(false);
                    tvReview.setSelected(true);
                    startActivityForResult(intentProduct,Constant.LAUNCH_REVIEW_ACTIVITY);
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
                llSpecial.setVisibility(View.GONE);
                txvDetail.setVisibility(View.GONE);
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

        float aveRating = number/data.size();

        tvSumRating.setText("("+String.valueOf(numReview)+")");
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
            if(resultCode == Activity.RESULT_OK){
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
