package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.ProductAirline;

public class ProductAirlineDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_airline_detail);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        ProductAirline productAirline = intent.getParcelableExtra("product airline");
        Integer imageAirline = intent.getIntExtra("image airline", 1);
        ImageView imgAirline= findViewById(R.id.imgProductAirlineDetail);
        imgAirline.setImageResource(imageAirline);
        TextView mealCode = findViewById(R.id.tv_meal_code);
        mealCode.setText(productAirline.getMealCode());
        TextView mealName = findViewById(R.id.tv_meal_name);
        mealName.setText(productAirline.getMealName());
        TextView lastUpdated = findViewById(R.id.tv_time);
        lastUpdated.setText(productAirline.getLastUpdate());
        TextView veganOption = findViewById(R.id.tv_vegan_option);
        veganOption.setText(productAirline.getVeganOptions());
    }
}