package com.fussyvegan.scanner.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.dialog.BottomSheetListFavorite;
import com.fussyvegan.scanner.dialog.DialogCheckInformation;
import com.fussyvegan.scanner.model.ProductAirline;
import com.fussyvegan.scanner.model.favorite.FavoriteType;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import github.nisrulz.screenshott.ScreenShott;
import io.realm.Realm;

public class ProductAirlineDetailActivity extends AppCompatActivity {

    private static final String FAVORITE = "favorite";

    ImageView imgCamera;
    ImageView imgListWish;
    ImageView imgWebsite;

    private Bitmap bitmap;
    private final static String[] requestWritePermission =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ProductAirline productAirline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_airline_detail);
        initView();
        handOnClick();
    }

    private void initView(){
        Intent intent = getIntent();
        productAirline = intent.getParcelableExtra("product airline");
        Integer imageAirline = intent.getIntExtra("image airline", 1);
        ImageView imgAirline= findViewById(R.id.imgProductAirlineDetail);
        //imgAirline.setImageResource(imageAirline);
        Picasso.get().load(productAirline.getAirlineLogo()).into(imgAirline);
        TextView mealCode = findViewById(R.id.tv_meal_code);
        mealCode.setText(productAirline.getMealCode());
        TextView mealName = findViewById(R.id.tv_meal_name);
        mealName.setText(productAirline.getMealName());
        TextView lastUpdated = findViewById(R.id.tv_time);
        lastUpdated.setText(productAirline.getLastUpdate());
        TextView veganOption = findViewById(R.id.tv_vegan_option);
        veganOption.setText(productAirline.getVeganOptions());

        imgCamera = findViewById(R.id.imgCamera);
        imgListWish = findViewById(R.id.imgListWish);
        imgWebsite = findViewById(R.id.imgWebsite);
    }

    private void handOnClick(){

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
                final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(ProductAirlineDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (bitmap != null) {
                    if (hasWritePermission) {
                        saveScreenshot();
                    } else {
                        RuntimePermissionUtil.requestPermission(ProductAirlineDetailActivity.this, requestWritePermission, 100);

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

        imgWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productAirline.getLinkWebsite() != null && productAirline.getLinkWebsite().contains("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productAirline.getLinkWebsite()));
                    startActivity(intent);
                } else {
                    Toast.makeText(ProductAirlineDetailActivity.this, "Weblink Not Available", Toast.LENGTH_SHORT).show();
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
                    .saveScreenshotToPicturesFolder(ProductAirlineDetailActivity.this, bitmap, "date");
            // Display a toast
            Toast.makeText(ProductAirlineDetailActivity.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToFavorite() {
        if (!SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
            DialogCheckInformation dialogCheckInformation = new DialogCheckInformation(this);
            dialogCheckInformation.show();
        } else {
            showBottomSheet();
        }
    }

    private void showBottomSheet() {
        BottomSheetListFavorite bottomSheetListFavorite = new BottomSheetListFavorite();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FAVORITE, new FavoriteType(Constant.FAVOR_AIRLINE, productAirline.getId(), -1));
        bottomSheetListFavorite.setArguments(bundle);
        bottomSheetListFavorite.show(getSupportFragmentManager(), "Dialog");
    }
}