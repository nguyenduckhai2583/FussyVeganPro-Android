package com.fussyvegan.scanner.model.favorite;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListFavoriteResponse {

    @SerializedName("message")
    String message;

    @SerializedName("data")
    List<GroupFavorite> list;

    public ListFavoriteResponse() {
    }

    public ListFavoriteResponse(String message, ArrayList<GroupFavorite> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GroupFavorite> getList() {
        return list;
    }

    public void setList(ArrayList<GroupFavorite> list) {
        this.list = list;
    }
}
