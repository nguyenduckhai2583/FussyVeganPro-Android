package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductReview   implements Parcelable {

    @SerializedName("rating_title")
    @Expose
    private String title;

    @SerializedName("rating_note")
    @Expose
    private String review;

    @SerializedName("rating_point")
    @Expose
    private int rating;

    @SerializedName("created_at")
    @Expose
    private String date;

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("id")
    @Expose
    private int id;

    public ProductReview() {
    }

    public ProductReview(String title, String review, int rating, String date, String username, int id) {
        this.title = title;
        this.review = review;
        this.rating = rating;
        this.date = date;
        this.username = username;
        this.id = id;
    }

    protected ProductReview(Parcel in) {
        title = in.readString();
        review = in.readString();
        rating = in.readInt();
        date = in.readString();
        username = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(review);
        dest.writeInt(rating);
        dest.writeString(date);
        dest.writeString(username);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductReview> CREATOR = new Creator<ProductReview>() {
        @Override
        public ProductReview createFromParcel(Parcel in) {
            return new ProductReview(in);
        }

        @Override
        public ProductReview[] newArray(int size) {
            return new ProductReview[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
