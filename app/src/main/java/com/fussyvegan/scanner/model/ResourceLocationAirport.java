package com.fussyvegan.scanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceLocationAirport {
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("products")
    @Expose
    private List<LocationAirport> products = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<LocationAirport> getProducts() {
        return products;
    }

    public void setProducts(List<LocationAirport> products) {
        this.products = products;
    }
}
