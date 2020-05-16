package com.fussyvegan.scanner.model.AccountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAccount {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private data data;

    public UserAccount() {
    }

    public UserAccount(String message, com.fussyvegan.scanner.model.AccountFlow.data data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.fussyvegan.scanner.model.AccountFlow.data getData() {
        return data;
    }

    public void setData(com.fussyvegan.scanner.model.AccountFlow.data data) {
        this.data = data;
    }
}
