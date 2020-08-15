
package com.fussyvegan.scanner.model;

import com.fussyvegan.scanner.model.restaurant.PaginateRestaurant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resource {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("paginate")
    PaginateRestaurant paginate;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public PaginateRestaurant getPaginate() {
        return paginate;
    }

    public void setPaginate(PaginateRestaurant paginate) {
        this.paginate = paginate;
    }
}
