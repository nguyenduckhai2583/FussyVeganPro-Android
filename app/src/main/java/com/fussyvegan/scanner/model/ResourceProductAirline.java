package com.fussyvegan.scanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceProductAirline {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("products")
    @Expose
    private List<ProductAirline> products = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ProductAirline> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAirline> products) {
        this.products = products;
    }
}
