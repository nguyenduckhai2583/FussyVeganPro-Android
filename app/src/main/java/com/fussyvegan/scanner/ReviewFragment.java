package com.fussyvegan.scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.LoginActivity;
import com.fussyvegan.scanner.activity.ReviewActivity;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.adapter.ProductReviewAdapter;
import com.fussyvegan.scanner.model.ProductReview;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.fussyvegan.scanner.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class ReviewFragment extends Fragment {

    private static final String RESTAURANT = "restaurant";
    private static final String RESTAURANT_REVIEW = "review";
    private static final String LIST_REVIEW = "list_review";
    private static final String RESTAURANT_CATEGORY = "category";
    public static final int RESTAURANT_CATEGORY_ID = 4;
    private Restaurant restaurant;

    TextView tvTotalReview, tvRateFiveStar, tvRateFourStar, tvRateThreeStar, tvRateTwoStar,
            tvRateOneStar, tvWriteReview;
    RecyclerView recyclerViewReview;
    ProductReviewAdapter adapter;
    ArrayList<ProductReview> list = new ArrayList<>();
    boolean isReview;
    float rating;
    int count_rating;
    private ProductReview reviewProduct;
    ProgressDialog dialog;
    private OnRestaurantClickListener listener;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable(RESTAURANT);
            list = getArguments().getParcelableArrayList(LIST_REVIEW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        listener = (OnRestaurantClickListener) getActivity();

        addContent(view);
        addEvent();
        prepareValue();

        return view;
    }

    private void prepareValue() {
        setRating(list);
        checkIsReview(list);
        adapter.updateData(list);
    }

    private void addContent(View view) {
        tvTotalReview = view.findViewById(R.id.tvTotalReview);
        tvRateFiveStar = view.findViewById(R.id.tvRateFiveStar);
        tvRateFourStar = view.findViewById(R.id.tvRateFourStar);
        tvRateThreeStar = view.findViewById(R.id.tvRateThreeStar);
        tvRateTwoStar = view.findViewById(R.id.tvRateTwoStar);
        tvRateOneStar = view.findViewById(R.id.tvRateOneStar);
        tvWriteReview = view.findViewById(R.id.tvWriteReview);
        recyclerViewReview = view.findViewById(R.id.recyclerViewReview);

        tvTotalReview.setText(String.valueOf(restaurant.getAvg_rating()));

        adapter = new ProductReviewAdapter(getActivity());
        recyclerViewReview.setAdapter(adapter);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void addEvent() {
        tvWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefs.getInstance().get(Constant.IS_LOGIN, Boolean.class)) {
                    Intent intentProduct = new Intent(getActivity(), ReviewActivity.class);
                    intentProduct.putExtra(RESTAURANT, restaurant);
                    intentProduct.putExtra(RESTAURANT_CATEGORY, RESTAURANT_CATEGORY_ID);

                    if (isReview) {
                        intentProduct.putExtra(RESTAURANT_REVIEW, reviewProduct);
                    }

                    startActivityForResult(intentProduct, Constant.LAUNCH_REVIEW_ACTIVITY);
                } else {
                    Intent loginScreen = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginScreen);
                }
            }
        });
    }

    private void getReview(int idProduct, int idType) {
        dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        list.clear();
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<Reviews> call = apiInterface.getReviewProduct(token, idProduct, idType);
        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (!response.body().getData().isEmpty()) {
                    setRating(response.body().getData());
                    checkIsReview(response.body().getData());
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    private void setRating(List<ProductReview> productReviews) {
        tvTotalReview.setText(getTotalNumberPoint(productReviews));
        tvRateFiveStar.setText(String.valueOf(getNumberPoint(productReviews, 5)));
        tvRateFourStar.setText(String.valueOf(getNumberPoint(productReviews, 4)));
        tvRateThreeStar.setText(String.valueOf(getNumberPoint(productReviews, 3)));
        tvRateTwoStar.setText(String.valueOf(getNumberPoint(productReviews, 2)));
        tvRateOneStar.setText(String.valueOf(getNumberPoint(productReviews, 1)));
    }

    private String getTotalNumberPoint(List<ProductReview> productReviews) {
        float number = 0;
        for (int i = 0; i < productReviews.size(); i++) {
            number = number + productReviews.get(i).getRating();
        }

        count_rating = productReviews.size();
        rating = (float) (Math.round(number/count_rating*10) / 10.0);
        restaurant.setAvg_rating(rating);
        restaurant.setCount_rating(count_rating);
        listener.onClick(restaurant);

        return String.valueOf(rating);
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

    private void checkIsReview(List<ProductReview> productReviews) {
        for (int i = 0; i < productReviews.size(); i++) {

            if (productReviews.get(i).getUsername().equals(SharedPrefs.getInstance().get(Constant.USER_NAME, String.class))) {
                isReview = true;
                reviewProduct = productReviews.get(i);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.LAUNCH_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                getReview(restaurant.getId(), RESTAURANT_CATEGORY_ID);
            }
        }
    }
}