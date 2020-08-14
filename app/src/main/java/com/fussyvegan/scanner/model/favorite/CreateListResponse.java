package com.fussyvegan.scanner.model.favorite;

import com.google.gson.annotations.SerializedName;

public class CreateListResponse {

    @SerializedName("message")
    String message;

    @SerializedName("data")
    String data;

    public CreateListResponse() {
    }

    public CreateListResponse(String message, String data) {
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
