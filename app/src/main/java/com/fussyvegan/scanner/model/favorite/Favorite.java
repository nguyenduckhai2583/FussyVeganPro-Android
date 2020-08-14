package com.fussyvegan.scanner.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Favorite  implements Parcelable {

    @SerializedName("id")
    int id;

    @SerializedName("authentication_id")
    int authentication_id;

    @SerializedName("group_id")
    int group_id;

    @SerializedName("favoriteable_type")
    int favoriteable_type;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("category")
    String category;

    @SerializedName("name")
    String name;

    @SerializedName("country")
    String country;

    @SerializedName("last_update")
    String last_update;

    @SerializedName("vegan_status")
    String vegan_status;

    @SerializedName("explanation")
    String explanation;

    @SerializedName("link_photo")
    String link_photo;

    @SerializedName("add_info")
    String add_info;

    @SerializedName("link_map")
    String link_map;

    @SerializedName("product_ingredients")
    String product_ingredients;

    @SerializedName("manufactured_country")
    String manufactured_country;

    @SerializedName("palm")
    String palm;

    @SerializedName("gluten")
    String gluten;

    @SerializedName("nut")
    String nut;

    @SerializedName("soy")
    String soy;

    @SerializedName("company_name")
    String company_name;

    @SerializedName("company_country")
    String company_country;

    @SerializedName("company_status")
    String company_status;

    @SerializedName("man_info")
    String man_info;

    @SerializedName("animal_test_status")
    String animal_test_status;

    @SerializedName("avg_price")
    String avg_price;

    @SerializedName("price_per")
    String price_per;

    @SerializedName("price_level")
    String price_level;

    @SerializedName("keywords")
    String keywords;

    @SerializedName("search")
    String search;

    @SerializedName("alt_name")
    String alt_name;

    @SerializedName("favorite_id")
    int favorite_id;

    @SerializedName("link_applemap")
    String link_applemap;

    @SerializedName("link_googlemap")
    String link_googlemap;

    @SerializedName("email")
    String email;

    @SerializedName("link_photo_small")
    String link_photo_small;

    @SerializedName("location")
    String location;

    @SerializedName("region")
    String region;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("phone")
    String phone;

    @SerializedName("link_website")
    String link_website;

    @SerializedName("stars")
    String stars;

    @SerializedName("booking_com")
    String booking_com;

    @SerializedName("expedia_com")
    String expedia_com;

    @SerializedName("hotels_com")
    String hotels_com;

    @SerializedName("price_range")
    String price_range;

    @SerializedName("description")
    String description;

    @SerializedName("rest_vegan_status")
    String rest_vegan_status;

    @SerializedName("cuisine_type")
    String cuisine_type;

    @SerializedName("airline")
    String airline;

    @SerializedName("meal_code")
    String meal_code;

    @SerializedName("meal_name")
    String meal_name;

    @SerializedName("vegan_options")
    String vegan_options;

    @SerializedName("airline_logo")
    String airline_logo;

    @SerializedName("facebook_link")
    String facebook_link;

    @SerializedName("terminal")
    String terminal;

    @SerializedName("airport_name")
    String airport_name;

    @SerializedName("airport_code")
    String airport_code;

    @SerializedName("hours")
    String hours;

    public Favorite() {
    }

    public Favorite(int id, int authentication_id, int group_id, int favoriteable_type, String created_at, String category, String name, String country, String last_update, String vegan_status, String explanation, String link_photo, String add_info, String link_map, String product_ingredients, String manufactured_country, String palm, String gluten, String nut, String soy, String company_name, String company_country, String company_status, String man_info, String animal_test_status, String avg_price, String price_per, String price_level, String keywords, String search, String alt_name, int favorite_id, String link_applemap, String link_googlemap, String email, String link_photo_small, String location, String region, String latitude, String longitude, String phone, String link_website, String stars, String booking_com, String expedia_com, String hotels_com, String price_range, String description, String rest_vegan_status, String cuisine_type, String airline, String meal_code, String meal_name, String vegan_options, String airline_logo, String facebook_link, String terminal, String airport_name, String airport_code, String hours) {
        this.id = id;
        this.authentication_id = authentication_id;
        this.group_id = group_id;
        this.favoriteable_type = favoriteable_type;
        this.created_at = created_at;
        this.category = category;
        this.name = name;
        this.country = country;
        this.last_update = last_update;
        this.vegan_status = vegan_status;
        this.explanation = explanation;
        this.link_photo = link_photo;
        this.add_info = add_info;
        this.link_map = link_map;
        this.product_ingredients = product_ingredients;
        this.manufactured_country = manufactured_country;
        this.palm = palm;
        this.gluten = gluten;
        this.nut = nut;
        this.soy = soy;
        this.company_name = company_name;
        this.company_country = company_country;
        this.company_status = company_status;
        this.man_info = man_info;
        this.animal_test_status = animal_test_status;
        this.avg_price = avg_price;
        this.price_per = price_per;
        this.price_level = price_level;
        this.keywords = keywords;
        this.search = search;
        this.alt_name = alt_name;
        this.favorite_id = favorite_id;
        this.link_applemap = link_applemap;
        this.link_googlemap = link_googlemap;
        this.email = email;
        this.link_photo_small = link_photo_small;
        this.location = location;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.link_website = link_website;
        this.stars = stars;
        this.booking_com = booking_com;
        this.expedia_com = expedia_com;
        this.hotels_com = hotels_com;
        this.price_range = price_range;
        this.description = description;
        this.rest_vegan_status = rest_vegan_status;
        this.cuisine_type = cuisine_type;
        this.airline = airline;
        this.meal_code = meal_code;
        this.meal_name = meal_name;
        this.vegan_options = vegan_options;
        this.airline_logo = airline_logo;
        this.facebook_link = facebook_link;
        this.terminal = terminal;
        this.airport_name = airport_name;
        this.airport_code = airport_code;
        this.hours = hours;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        authentication_id = in.readInt();
        group_id = in.readInt();
        favoriteable_type = in.readInt();
        created_at = in.readString();
        category = in.readString();
        name = in.readString();
        country = in.readString();
        last_update = in.readString();
        vegan_status = in.readString();
        explanation = in.readString();
        link_photo = in.readString();
        add_info = in.readString();
        link_map = in.readString();
        product_ingredients = in.readString();
        manufactured_country = in.readString();
        palm = in.readString();
        gluten = in.readString();
        nut = in.readString();
        soy = in.readString();
        company_name = in.readString();
        company_country = in.readString();
        company_status = in.readString();
        man_info = in.readString();
        animal_test_status = in.readString();
        avg_price = in.readString();
        price_per = in.readString();
        price_level = in.readString();
        keywords = in.readString();
        search = in.readString();
        alt_name = in.readString();
        favorite_id = in.readInt();
        link_applemap = in.readString();
        link_googlemap = in.readString();
        email = in.readString();
        link_photo_small = in.readString();
        location = in.readString();
        region = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        phone = in.readString();
        link_website = in.readString();
        stars = in.readString();
        booking_com = in.readString();
        expedia_com = in.readString();
        hotels_com = in.readString();
        price_range = in.readString();
        description = in.readString();
        rest_vegan_status = in.readString();
        cuisine_type = in.readString();
        airline = in.readString();
        meal_code = in.readString();
        meal_name = in.readString();
        vegan_options = in.readString();
        airline_logo = in.readString();
        facebook_link = in.readString();
        terminal = in.readString();
        airport_name = in.readString();
        airport_code = in.readString();
        hours = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthentication_id() {
        return authentication_id;
    }

    public void setAuthentication_id(int authentication_id) {
        this.authentication_id = authentication_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getFavoriteable_type() {
        return favoriteable_type;
    }

    public void setFavoriteable_type(int favoriteable_type) {
        this.favoriteable_type = favoriteable_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getVegan_status() {
        return vegan_status;
    }

    public void setVegan_status(String vegan_status) {
        this.vegan_status = vegan_status;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getLink_photo() {
        return link_photo;
    }

    public void setLink_photo(String link_photo) {
        this.link_photo = link_photo;
    }

    public String getAdd_info() {
        return add_info;
    }

    public void setAdd_info(String add_info) {
        this.add_info = add_info;
    }

    public String getLink_map() {
        return link_map;
    }

    public void setLink_map(String link_map) {
        this.link_map = link_map;
    }

    public String getProduct_ingredients() {
        return product_ingredients;
    }

    public void setProduct_ingredients(String product_ingredients) {
        this.product_ingredients = product_ingredients;
    }

    public String getManufactured_country() {
        return manufactured_country;
    }

    public void setManufactured_country(String manufactured_country) {
        this.manufactured_country = manufactured_country;
    }

    public String getPalm() {
        return palm;
    }

    public void setPalm(String palm) {
        this.palm = palm;
    }

    public String getGluten() {
        return gluten;
    }

    public void setGluten(String gluten) {
        this.gluten = gluten;
    }

    public String getNut() {
        return nut;
    }

    public void setNut(String nut) {
        this.nut = nut;
    }

    public String getSoy() {
        return soy;
    }

    public void setSoy(String soy) {
        this.soy = soy;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_country() {
        return company_country;
    }

    public void setCompany_country(String company_country) {
        this.company_country = company_country;
    }

    public String getCompany_status() {
        return company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
    }

    public String getMan_info() {
        return man_info;
    }

    public void setMan_info(String man_info) {
        this.man_info = man_info;
    }

    public String getAnimal_test_status() {
        return animal_test_status;
    }

    public void setAnimal_test_status(String animal_test_status) {
        this.animal_test_status = animal_test_status;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getPrice_per() {
        return price_per;
    }

    public void setPrice_per(String price_per) {
        this.price_per = price_per;
    }

    public String getPrice_level() {
        return price_level;
    }

    public void setPrice_level(String price_level) {
        this.price_level = price_level;
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

    public String getAlt_name() {
        return alt_name;
    }

    public void setAlt_name(String alt_name) {
        this.alt_name = alt_name;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink_photo_small() {
        return link_photo_small;
    }

    public void setLink_photo_small(String link_photo_small) {
        this.link_photo_small = link_photo_small;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getMeal_code() {
        return meal_code;
    }

    public void setMeal_code(String meal_code) {
        this.meal_code = meal_code;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getVegan_options() {
        return vegan_options;
    }

    public void setVegan_options(String vegan_options) {
        this.vegan_options = vegan_options;
    }

    public String getAirline_logo() {
        return airline_logo;
    }

    public void setAirline_logo(String airline_logo) {
        this.airline_logo = airline_logo;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(authentication_id);
        parcel.writeInt(group_id);
        parcel.writeInt(favoriteable_type);
        parcel.writeString(created_at);
        parcel.writeString(category);
        parcel.writeString(name);
        parcel.writeString(country);
        parcel.writeString(last_update);
        parcel.writeString(vegan_status);
        parcel.writeString(explanation);
        parcel.writeString(link_photo);
        parcel.writeString(add_info);
        parcel.writeString(link_map);
        parcel.writeString(product_ingredients);
        parcel.writeString(manufactured_country);
        parcel.writeString(palm);
        parcel.writeString(gluten);
        parcel.writeString(nut);
        parcel.writeString(soy);
        parcel.writeString(company_name);
        parcel.writeString(company_country);
        parcel.writeString(company_status);
        parcel.writeString(man_info);
        parcel.writeString(animal_test_status);
        parcel.writeString(avg_price);
        parcel.writeString(price_per);
        parcel.writeString(price_level);
        parcel.writeString(keywords);
        parcel.writeString(search);
        parcel.writeString(alt_name);
        parcel.writeInt(favorite_id);
        parcel.writeString(link_applemap);
        parcel.writeString(link_googlemap);
        parcel.writeString(email);
        parcel.writeString(link_photo_small);
        parcel.writeString(location);
        parcel.writeString(region);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(phone);
        parcel.writeString(link_website);
        parcel.writeString(stars);
        parcel.writeString(booking_com);
        parcel.writeString(expedia_com);
        parcel.writeString(hotels_com);
        parcel.writeString(price_range);
        parcel.writeString(description);
        parcel.writeString(rest_vegan_status);
        parcel.writeString(cuisine_type);
        parcel.writeString(airline);
        parcel.writeString(meal_code);
        parcel.writeString(meal_name);
        parcel.writeString(vegan_options);
        parcel.writeString(airline_logo);
        parcel.writeString(facebook_link);
        parcel.writeString(terminal);
        parcel.writeString(airport_name);
        parcel.writeString(airport_code);
        parcel.writeString(hours);
    }
}

