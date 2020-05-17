package com.fussyvegan.scanner.model.accountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassResult {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private String data;

    public ForgotPassResult() {
    }

    public ForgotPassResult(String message, String data) {
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
