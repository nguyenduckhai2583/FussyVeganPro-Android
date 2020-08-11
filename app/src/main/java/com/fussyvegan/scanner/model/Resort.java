package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Resort  extends RealmObject implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("link_applemap")
    @Expose
    private String link_applemap;
    @SerializedName("link_googlemap")
    @Expose
    private String link_googlemap;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("link_photo")
    @Expose
    private String link_photo;
    @SerializedName("link_photo_small")
    @Expose
    private String link_photo_small;
    @SerializedName("link_website")
    @Expose
    private String link_website;
    @SerializedName("stars")
    @Expose
    private String stars;
    @SerializedName("booking_com")
    @Expose
    private String booking_com;
    @SerializedName("expedia_com")
    @Expose
    private String expedia_com;
    @SerializedName("hotels_com")
    @Expose
    private String hotels_com;
    @SerializedName("price_range")
    @Expose
    private String price_range;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("search")
    @Expose
    private String search;

    public Resort(){}

    protected Resort(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        category = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        region = in.readString();
        country = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        link_applemap = in.readString();
        link_googlemap = in.readString();
        phone = in.readString();
        email = in.readString();
        link_photo = in.readString();
        link_photo_small = in.readString();
        link_website = in.readString();
        stars = in.readString();
        booking_com = in.readString();
        expedia_com = in.readString();
        hotels_com = in.readString();
        price_range = in.readString();
        keywords = in.readString();
        search = in.readString();
    }

    public static final Creator<Resort> CREATOR = new Creator<Resort>() {
        @Override
        public Resort createFromParcel(Parcel in) {
            return new Resort(in);
        }

        @Override
        public Resort[] newArray(int size) {
            return new Resort[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(region);
        dest.writeString(country);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(link_applemap);
        dest.writeString(link_googlemap);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(link_photo);
        dest.writeString(link_photo_small);
        dest.writeString(link_website);
        dest.writeString(stars);
        dest.writeString(booking_com);
        dest.writeString(expedia_com);
        dest.writeString(hotels_com);
        dest.writeString(price_range);
        dest.writeString(keywords);
        dest.writeString(search);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getBooking_com() {
        return booking_com;
    }

    public void setBooking_com(String booking_com) {
        this.booking_com = booking_com;
    }

    public String getExpedia_com() {
        return expedia_com;
    }

    public void setExpedia_com(String expedia_com) {
        this.expedia_com = expedia_com;
    }

    public String getHotels_com() {
        return hotels_com;
    }

    public void setHotels_com(String hotels_com) {
        this.hotels_com = hotels_com;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
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

    public static Creator<Resort> getCREATOR() {
        return CREATOR;
    }

    public void copy(Resort p) {
        this.id = p.id;
        this.category = p.category;
        this.name = p.name;
        this.description = p.description;
        this.location = p.location;
        this.country = p.country;
        this.region = p.region;
        this.latitude = p.latitude;
        this.longitude = p.longitude;
        this.link_applemap = p.link_applemap;
        this.link_googlemap = p.link_googlemap;
        this.phone = p.phone;
        this.email = p.email;
        this.link_photo = p.link_photo;
        this.link_photo_small = p.link_photo_small;
        this.link_website = p.link_website;
        this.stars = p.stars;
        this.booking_com = p.booking_com;
        this.expedia_com = p.expedia_com;
        this.hotels_com = p.hotels_com;
        this.price_range = p.price_range;
        this.keywords = p.keywords;
        this.search = p.search;
    }

}
