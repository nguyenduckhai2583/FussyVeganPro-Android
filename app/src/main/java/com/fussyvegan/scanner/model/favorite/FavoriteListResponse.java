package com.fussyvegan.scanner.model.favorite;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavoriteListResponse {

    @SerializedName("message")
    String message;

    @SerializedName("data")
    ArrayList<Favorite> list;

    public FavoriteListResponse() {
    }

    public FavoriteListResponse(String message, ArrayList<Favorite> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Favorite> getList() {
        return list;
    }

    public void setList(ArrayList<Favorite> list) {
        this.list = list;
    }
}
