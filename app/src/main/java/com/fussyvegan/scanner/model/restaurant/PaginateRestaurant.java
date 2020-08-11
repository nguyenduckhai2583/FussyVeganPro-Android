package com.fussyvegan.scanner.model.restaurant;

import com.google.gson.annotations.SerializedName;

public class PaginateRestaurant {

    @SerializedName("per_page")
    int per_page;

    @SerializedName("prev")
    int prev;

    @SerializedName("next")
    int next;

    @SerializedName("total_page")
    int total_page;

    @SerializedName("total")
    int total;

    public PaginateRestaurant() {
    }

    public PaginateRestaurant(int per_page, int prev, int next, int total_page, int total) {
        this.per_page = per_page;
        this.prev = prev;
        this.next = next;
        this.total_page = total_page;
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
