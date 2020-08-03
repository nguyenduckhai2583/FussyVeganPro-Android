package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ProductAriline extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("airline")
    @Expose
    private String airline;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("meal_code")
    @Expose
    private String mealCode;
    @SerializedName("meal_name")
    @Expose
    private String mealName;
    @SerializedName("vegan_options")
    @Expose
    private Integer veganOptions;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("airline_logo")
    @Expose
    private String airlineLogo;
    @SerializedName("link_website")
    @Expose
    private String linkWebsite;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("facebook_link")
    @Expose
    private String facebookLink;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("search")
    @Expose
    private String search;

    public ProductAriline(){}

    protected ProductAriline(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        category = in.readString();
        airline = in.readString();
        lastUpdate = in.readString();
        mealCode = in.readString();
        mealName = in.readString();
        if (in.readByte() == 0) {
            veganOptions = null;
        } else {
            veganOptions = in.readInt();
        }
        phone = in.readString();
        airlineLogo = in.readString();
        linkWebsite = in.readString();
        email = in.readString();
        facebookLink = in.readString();
        country = in.readString();
        search = in.readString();
    }

    public static final Creator<ProductAriline> CREATOR = new Creator<ProductAriline>() {
        @Override
        public ProductAriline createFromParcel(Parcel in) {
            return new ProductAriline(in);
        }

        @Override
        public ProductAriline[] newArray(int size) {
            return new ProductAriline[size];
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
        dest.writeString(airline);
        dest.writeString(lastUpdate);
        dest.writeString(mealCode);
        dest.writeString(mealName);
        if (veganOptions == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(veganOptions);
        }
        dest.writeString(phone);
        dest.writeString(airlineLogo);
        dest.writeString(linkWebsite);
        dest.writeString(email);
        dest.writeString(facebookLink);
        dest.writeString(country);
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

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getMealCode() {
        return mealCode;
    }

    public void setMealCode(String mealCode) {
        this.mealCode = mealCode;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Integer getVeganOptions() {
        return veganOptions;
    }

    public void setVeganOptions(Integer veganOptions) {
        this.veganOptions = veganOptions;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(String airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getLinkWebsite() {
        return linkWebsite;
    }

    public void setLinkWebsite(String linkWebsite) {
        this.linkWebsite = linkWebsite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public static Creator<ProductAriline> getCREATOR() {
        return CREATOR;
    }
}
