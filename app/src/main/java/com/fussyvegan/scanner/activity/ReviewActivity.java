package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.Product;

public class ReviewActivity extends AppCompatActivity {

    ImageView reviewImg_Back;
    TextView reviewTxt_Clear;
    AppCompatRatingBar reviewRatingBar;
    EditText reviewEdt_Email, reviewEdt_Name, reviewEdt_Title, reviewEdt_Review;
    Button reviewBtn_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        addContent();
        addEvent();

        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");
        reviewEdt_Review.setText(String.valueOf(product.getId()));
    }

    private void addEvent() {
        reviewImg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        reviewTxt_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewEdt_Email.getText().clear();
                reviewEdt_Name.getText().clear();
                reviewEdt_Title.getText().clear();
                reviewEdt_Review.getText().clear();
                reviewRatingBar.setRating(0);
            }
        });
    }

    private void addContent() {
        reviewImg_Back = findViewById(R.id.reviewImg_Back);
        reviewTxt_Clear = findViewById(R.id.reviewTxt_Clear);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
        reviewEdt_Name = findViewById(R.id.reviewEdt_Name);
        reviewEdt_Email = findViewById(R.id.reviewEdt_Email);
        reviewEdt_Title = findViewById(R.id.reviewEdt_Title);
        reviewEdt_Review = findViewById(R.id.reviewEdt_Review);
        reviewBtn_Submit = findViewById(R.id.reviewBtn_Submit);
    }
}
