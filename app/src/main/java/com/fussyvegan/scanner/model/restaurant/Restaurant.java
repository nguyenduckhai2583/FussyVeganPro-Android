package com.fussyvegan.scanner.model.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("rest_vegan_status")
    private String rest_vegan_status;

    @SerializedName("cuisine_type")
    private String cuisine_type;

    @SerializedName("location")
    private String location;

    @SerializedName("country")
    private String country;

    @SerializedName("region")
    private String region;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("link_applemap")
    private String link_applemap;

    @SerializedName("link_googlemap")
    private String link_googlemap;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("link_photo")
    private String link_photo;

    @SerializedName("link_photo_small")
    private String link_photo_small;

    @SerializedName("link_website")
    private String link_website;

    @SerializedName("hours")
    private String hours;

    @SerializedName("keywords")
    private String keywords;

    @SerializedName("search")
    private String search;

    @SerializedName("count_rating")
    private float count_rating;

    @SerializedName("avg_rating")
    private float avg_rating;

    public Restaurant() {
    }

    public Restaurant(int id, String category, String name, String description, String rest_vegan_status, String cuisine_type, String location, String country, String region, String latitude, String longitude, String link_applemap, String link_googlemap, String phone, String email, String link_photo, String link_photo_small, String link_website, String hours, String keywords, String search, float count_rating, float avg_rating) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.rest_vegan_status = rest_vegan_status;
        this.cuisine_type = cuisine_type;
        this.location = location;
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.link_applemap = link_applemap;
        this.link_googlemap = link_googlemap;
        this.phone = phone;
        this.email = email;
        this.link_photo = link_photo;
        this.link_photo_small = link_photo_small;
        this.link_website = link_website;
        this.hours = hours;
        this.keywords = keywords;
        this.search = search;
        this.count_rating = count_rating;
        this.avg_rating = avg_rating;
    }

    protected Restaurant(Parcel in) {
        id = in.readInt();
        category = in.readString();
        name = in.readString();
        description = in.readString();
        rest_vegan_status = in.readString();
        cuisine_type = in.readString();
        location = in.readString();
        country = in.readString();
        region = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        link_applemap = in.readString();
        link_googlemap = in.readString();
        phone = in.readString();
        email = in.readString();
        link_photo = in.readString();
        link_photo_small = in.readString();
        link_website = in.readString();
        hours = in.readString();
        keywords = in.readString();
        search = in.readString();
        count_rating = in.readFloat();
        avg_rating = in.readFloat();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRest_vegan_status() {
        return rest_vegan_status;
    }

    public void setRest_vegan_status(String rest_vegan_status) {
        this.rest_vegan_status = rest_vegan_status;
    }

    public String getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(String cuisine_type) {
        this.cuisine_type = cuisine_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLink_applemap() {
        return link_applemap;
    }

    public void setLink_applemap(String link_applemap) {
        this.link_applemap = link_applemap;
    }

    public String getLink_googlemap() {
        return link_googlemap;
    }

    public void setLink_googlemap(String link_googlemap) {
        this.link_googlemap = link_googlemap;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink_photo() {
        return link_photo;
    }

    public void setLink_photo(String link_photo) {
        this.link_photo = link_photo;
    }

    public String getLink_photo_small() {
        return link_photo_small;
    }

    public void setLink_photo_small(String link_photo_small) {
        this.link_photo_small = link_photo_small;
    }

    public String getLink_website() {
        return link_website;
    }

    public void setLink_website(String link_website) {
        this.link_website = link_website;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public float getCount_rating() {
        return count_rating;
    }

    public void setCount_rating(float count_rating) {
        this.count_rating = count_rating;
    }

    public float getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(float avg_rating) {
        this.avg_rating = avg_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(category);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(rest_vegan_status);
        parcel.writeString(cuisine_type);
        parcel.writeString(location);
        parcel.writeString(country);
        parcel.writeString(region);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(link_applemap);
        parcel.writeString(link_googlemap);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(link_photo);
        parcel.writeString(link_photo_small);
        parcel.writeString(link_website);
        parcel.writeString(hours);
        parcel.writeString(keywords);
        parcel.writeString(search);
        parcel.writeFloat(count_rating);
        parcel.writeFloat(avg_rating);
    }
}
