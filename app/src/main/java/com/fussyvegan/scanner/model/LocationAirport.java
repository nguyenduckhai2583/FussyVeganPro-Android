package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LocationAirport extends RealmObject implements Parcelable {
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
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("terminal")
    @Expose
    private String terminal;
    @SerializedName("airport_name")
    @Expose
    private String airport_name;
    @SerializedName("airport_code")
    @Expose
    private String airport_code;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("link_website")
    @Expose
    private String link_website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("link_map")
    @Expose
    private String link_map;
    @SerializedName("link_photo")
    @Expose
    private String link_photo;
    @SerializedName("link_photo_small")
    @Expose
    private String link_photo_small;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("rest_vegan_status")
    @Expose
    private String rest_vegan_status;
    @SerializedName("cuisine_type")
    @Expose
    private String cuisine_type;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("link_googlemap")
    @Expose
    private String link_googlemap;

    public LocationAirport(){}

    protected LocationAirport(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        category = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        hours = in.readString();
        phone = in.readString();
        terminal = in.readString();
        airport_name = in.readString();
        airport_code = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        region = in.readString();
        country = in.readString();
        link_website = in.readString();
        email = in.readString();
        link_map = in.readString();
        link_photo = in.readString();
        link_photo_small = in.readString();
        search = in.readString();
        rest_vegan_status = in.readString();
        cuisine_type = in.readString();
        keywords = in.readString();
        link_googlemap = in.readString();
    }

    public static final Creator<LocationAirport> CREATOR = new Creator<LocationAirport>() {
        @Override
        public LocationAirport createFromParcel(Parcel in) {
            return new LocationAirport(in);
        }

        @Override
        public LocationAirport[] newArray(int size) {
            return new LocationAirport[size];
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
        dest.writeString(hours);
        dest.writeString(phone);
        dest.writeString(terminal);
        dest.writeString(airport_name);
        dest.writeString(airport_code);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(region);
        dest.writeString(country);
        dest.writeString(link_website);
        dest.writeString(email);
        dest.writeString(link_map);
        dest.writeString(link_photo);
        dest.writeString(link_photo_small);
        dest.writeString(search);
        dest.writeString(rest_vegan_status);
        dest.writeString(cuisine_type);
        dest.writeString(keywords);
        dest.writeString(link_googlemap);
    }

    public void copy(LocationAirport p) {
        this.id = p.id;
        this.category = p.category;
        this.name = p.name;
        this.description = p.description;
        this.location = p.location;
        this.hours = p.hours;
        this.phone = p.phone;
        this.terminal = p.terminal;
        this.airport_name = p.airport_name;
        this.airport_code = p.airport_code;
        this.latitude = p.latitude;
        this.longitude = p.longitude;
        this.region = p.region;
        this.country = p.country;
        this.link_website = p.link_website;
        this.email = p.email;
        this.link_map = p.link_map;
        this.link_photo = p.link_photo;
        this.link_photo_small = p.link_photo_small;
        this.search = p.search;
        this.rest_vegan_status = p.rest_vegan_status;
        this.cuisine_type = p.cuisine_type;
        this.keywords = p.keywords;
        this.link_googlemap = p.link_googlemap;
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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getAirport_code() {
        return airport_code;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
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

    public String getLink_website() {
        return link_website;
    }

    public void setLink_website(String link_website) {
        this.link_website = link_website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink_map() {
        return link_map;
    }

    public void setLink_map(String link_map) {
        this.link_map = link_map;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLink_googlemap() {
        return link_googlemap;
    }

    public void setLink_googlemap(String link_googlemap) {
        this.link_googlemap = link_googlemap;
    }

    public static Creator<LocationAirport> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +"\n"+
                ", category='" + category + '\'' + "\n"+
                ", name='" + name + '\'' +"\n"+
                ", description='" + description + '\'' +"\n"+
                ", location='" + location + '\'' +"\n"+
                ", hours='" + hours + '\'' +"\n"+
                ", phone='" + phone + '\'' +"\n"+
                ", terminal='" + terminal + '\'' +"\n"+
                ", airport_name='" + airport_name + '\'' +"\n"+
                ", airport_code='" + airport_code + '\'' +"\n"+
                ", latitude='" + latitude + '\'' +"\n"+
                ", longitude='" + longitude + '\'' +"\n"+
                ", region='" + region + '\'' +"\n"+
                ", country='" + country + '\'' +"\n"+
                ", link_website='" + link_website + '\'' +"\n"+
                ", email='" + email + '\'' +"\n"+
                ", link_map='" + link_map + '\'' +"\n"+
                ", link_photo='" + link_photo + '\'' +"\n"+
                ", link_photo_small='" + link_photo_small + '\'' +"\n"+
                ", search='" + search + '\'' +"\n"+
                ", rest_vegan_status='" + rest_vegan_status + '\'' +"\n"+
                ", cuisine_type='" + cuisine_type + '\'' +"\n"+
                ", keywords='" + keywords + '\'' +"\n"+
                ", link_googlemap='" + link_googlemap + '\'' +
                '}'+"\n\n";
    }
}
