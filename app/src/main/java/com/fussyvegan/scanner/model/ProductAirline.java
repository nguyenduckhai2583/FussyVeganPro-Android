package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ProductAirline extends RealmObject implements Parcelable {

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
    private String veganOptions;
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

    public ProductAirline(){}

    protected ProductAirline(Parcel in) {
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
        veganOptions = in.readString();
        phone = in.readString();
        airlineLogo = in.readString();
        linkWebsite = in.readString();
        email = in.readString();
        facebookLink = in.readString();
        country = in.readString();
        search = in.readString();
    }

    public static final Creator<ProductAirline> CREATOR = new Creator<ProductAirline>() {
        @Override
        public ProductAirline createFromParcel(Parcel in) {
            return new ProductAirline(in);
        }

        @Override
        public ProductAirline[] newArray(int size) {
            return new ProductAirline[size];
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
        dest.writeString(veganOptions);
        dest.writeString(phone);
        dest.writeString(airlineLogo);
        dest.writeString(linkWebsite);
        dest.writeString(email);
        dest.writeString(facebookLink);
        dest.writeString(country);
        dest.writeString(search);
    }

    public void copy(ProductAirline p) {
        this.id = p.id;
        this.category = p.category;
        this.airline = p.airline;
        this.lastUpdate = p.lastUpdate;
        this.mealCode = p.mealCode;
        this.mealName = p.mealName;
        this.veganOptions = p.veganOptions;
        this.phone = p.phone;
        this.airlineLogo = p.airlineLogo;
        this.linkWebsite = p.linkWebsite;
        this.email = p.email;
        this.facebookLink = p.facebookLink;
        this.country = p.country;
        this.search = p.search;

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

    public String getVeganOptions() {
        return veganOptions;
    }

    public void setVeganOptions(String veganOptions) {
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

    public static Creator<ProductAirline> getCREATOR() {
        return CREATOR;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", airline='" + airline + '\'' +
                ", last_update='" + lastUpdate + '\'' +
                ", meal_code='" + mealCode + '\'' +
                ", meal_name='" + mealName + '\'' +
                ", vegan_options='" + veganOptions + '\'' +
                ", phone='" + phone + '\'' +
                ", airline_logo='" + airline + '\'' +
                ", link_website='" + linkWebsite + '\'' +
                ", email='" + email + '\'' +
                ", facebook_link='" + facebookLink + '\'' +
                ", country='" + country + '\'' +
                ", search='" + search + '\'' +
                '}';
    }

}
