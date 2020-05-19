package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.accountFlow.PostReviewResult;
import com.fussyvegan.scanner.model.accountFlow.ReviewProduct;
import com.fussyvegan.scanner.model.accountFlow.UpdateReviewProduct;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    ImageView reviewImg_Back;
    TextView reviewTxt_Clear;
    AppCompatRatingBar reviewRatingBar;
    EditText reviewEdt_Email, reviewEdt_Name, reviewEdt_Title, reviewEdt_Review;
    Button reviewBtn_Submit;
    APIInterface apiInterface;
    int productId;
    int categoryId;
    ProductReview mProductReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        addContent();
        addEvent();

        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");
        productId = product.getId();
         mProductReview = intent.getParcelableExtra("review");
        categoryId = intent.getIntExtra("category", 1);
        if (mProductReview != null) {
            updateUIReview();
        }

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

    private void updateUIReview() {
        reviewEdt_Email.setText(mProductReview.getUsername());
        reviewEdt_Name.setText(mProductReview.getUsername());
        reviewEdt_Title.setText(mProductReview.getTitle());
        reviewEdt_Review.setText(mProductReview.getReview());
        reviewRatingBar.setRating(mProductReview.getRating());
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
        reviewBtn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductReview != null) {
                    updateReview();
                } else {
                    postReview();

                }
            }
        });
    }

    private void postReview() {

        String token = SharedPrefs.getInstance().get(Constant.ACCESS_TOKEN, String.class);
        ReviewProduct reviewProduct = new ReviewProduct(String.valueOf(reviewRatingBar.getRating()),
                productId, categoryId, reviewEdt_Review.getText().toString(), reviewEdt_Title.getText().toString());
        apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<PostReviewResult> call = apiInterface.postReviewProduct(token, reviewProduct);
        call.enqueue(new Callback<PostReviewResult>() {
            @Override
            public void onResponse(Call<PostReviewResult> call, Response<PostReviewResult> response) {
                Log.d(TAG, String.valueOf(response.code()));
                finish();

            }

            @Override
            public void onFailure(Call<PostReviewResult> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }


    private void updateReview() {

        String token = SharedPrefs.getInstance().get(Constant.ACCESS_TOKEN, String.class);
        UpdateReviewProduct updateProduct = new UpdateReviewProduct(mProductReview.getRating(),
                mProductReview.getReview(), mProductReview.getTitle(), mProductReview.getId());
        apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<PostReviewResult> call = apiInterface.updateReviewProduct(token, updateProduct);
        call.enqueue(new Callback<PostReviewResult>() {
            @Override
            public void onResponse(Call<PostReviewResult> call, Response<PostReviewResult> response) {
                Log.d(TAG, String.valueOf(response.code()));
                finish();

            }

            @Override
            public void onFailure(Call<PostReviewResult> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
