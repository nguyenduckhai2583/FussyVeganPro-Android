package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.ProductReview;

import java.util.ArrayList;
import java.util.List;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.myHolder> {

    List<ProductReview> mList = new ArrayList<>();
    Context context;

    public ProductReviewAdapter( Context context) {
        this.context = context;
    }
    public void updateData( List<ProductReview> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_review, viewGroup, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        myHolder.itemReview_Title.setText(mList.get(i).getTitle());
        myHolder.itemReview_Review.setText(mList.get(i).getReview());
        myHolder.itemReview_Date.setText(mList.get(i).getDate());
        myHolder.itemReview_Username.setText(mList.get(i).getUsername());
        myHolder.itemReview_RatingStar.setRating(mList.get(i).getRating());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        TextView itemReview_Date, itemReview_Username, itemReview_Review, itemReview_Title;
        AppCompatRatingBar itemReview_RatingStar;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            itemReview_Title = itemView.findViewById(R.id.itemReview_Title);
            itemReview_Date = itemView.findViewById(R.id.itemReview_Date);
            itemReview_Username = itemView.findViewById(R.id.itemReview_Username);
            itemReview_Review = itemView.findViewById(R.id.itemReview_Review);
            itemReview_RatingStar = itemView.findViewById(R.id.itemReview_RatingStar);
        }
    }
}
