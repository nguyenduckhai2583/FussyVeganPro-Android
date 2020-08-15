
package com.fussyvegan.scanner.model;

import java.util.ArrayList;
import java.util.List;

public class FilterRestaurant {

    ArrayList<CuisineType> cuisineType;
    int distance;

    public FilterRestaurant() {
    }

    public ArrayList<CuisineType> getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(ArrayList<CuisineType> cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
