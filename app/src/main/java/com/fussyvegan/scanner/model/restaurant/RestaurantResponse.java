package com.fussyvegan.scanner.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantResponse {

    @SerializedName("status")
    StatusRestaurant status;

    @Expose
    @SerializedName("products")
    List<Restaurant> list;

    @SerializedName("paginate")
    PaginateRestaurant paginate;

    public RestaurantResponse() {
    }

    public RestaurantResponse(StatusRestaurant status, List<Restaurant> list, PaginateRestaurant paginate) {
        this.status = status;
        this.list = list;
        this.paginate = paginate;
    }

    public StatusRestaurant getStatus() {
        return status;
    }

    public void setStatus(StatusRestaurant status) {
        this.status = status;
    }

    public List<Restaurant> getList() {
        return list;
    }

    public void setList(List<Restaurant> list) {
        this.list = list;
    }

    public PaginateRestaurant getPaginate() {
        return paginate;
    }

    public void setPaginate(PaginateRestaurant paginate) {
        this.paginate = paginate;
    }
}
