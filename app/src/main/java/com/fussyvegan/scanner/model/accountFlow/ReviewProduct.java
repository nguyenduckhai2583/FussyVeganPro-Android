package com.fussyvegan.scanner.model.accountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewProduct {

    @SerializedName("rating_point")
    @Expose
    private String ratingPoint;

    @SerializedName("ratingable_id")
    @Expose
    private int ratingableId;

    @SerializedName("ratingable_type")
    @Expose
    private int ratingableType;
    @SerializedName("rating_note")
    @Expose
    private String ratingNote;
    @SerializedName("rating_title")
    @Expose
    private String ratingTitle;

    public ReviewProduct(String ratingPoint, int ratingableId, int ratingableType, String ratingNote, String ratingTitle) {
        this.ratingPoint = ratingPoint;
        this.ratingableId = ratingableId;
        this.ratingableType = ratingableType;
        this.ratingNote = ratingNote;
        this.ratingTitle = ratingTitle;
    }
}
