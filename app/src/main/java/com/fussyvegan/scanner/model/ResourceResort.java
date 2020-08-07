package com.fussyvegan.scanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceResort {
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("products")
    @Expose
    private List<Resort> products = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Resort> getProducts() {
        return products;
    }

    public void setProducts(List<Resort> products) {
        this.products = products;
    }
}
