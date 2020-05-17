package com.fussyvegan.scanner.model.accountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostReviewResult {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private Data data;

    public PostReviewResult() {
    }

    public PostReviewResult(String message, Data data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
