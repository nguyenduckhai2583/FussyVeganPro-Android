package com.fussyvegan.scanner.model.accountFlow;

import com.fussyvegan.scanner.model.ProductReview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<ProductReview> datas;

    public Reviews() {
    }

    public String getMessage() {
        return message;
    }

    public List<ProductReview> getData() {
        return datas;
    }
}
