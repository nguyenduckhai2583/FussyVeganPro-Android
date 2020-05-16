package com.fussyvegan.scanner.model.AccountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class forgotPassResult {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private String data;

    public forgotPassResult() {
    }

    public forgotPassResult(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
