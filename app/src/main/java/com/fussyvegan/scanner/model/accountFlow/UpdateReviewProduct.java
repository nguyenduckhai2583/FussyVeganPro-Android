package com.fussyvegan.scanner.model.accountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateReviewProduct {

    @SerializedName("rating_point")
    @Expose
    private float ratingPoint;
    @SerializedName("rating_note")
    @Expose
    private String ratingNote;
    @SerializedName("rating_title")
    @Expose
    private String ratingTitle;
    @SerializedName("rating_id")
    @Expose
    private int ratingId;

    public UpdateReviewProduct(float ratingPoint, String ratingNote, String ratingTitle, int ratingId) {
        this.ratingPoint = ratingPoint;
        this.ratingNote = ratingNote;
        this.ratingTitle = ratingTitle;
        this.ratingId = ratingId;
    }
}
