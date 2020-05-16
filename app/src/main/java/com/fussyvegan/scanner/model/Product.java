
package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Product extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("vegan_status")
    @Expose
    private String veganStatus;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("link_photo")
    @Expose
    private String linkPhoto;
    @SerializedName("link_barcode")
    @Expose
    private String linkBarcode;
    @SerializedName("man_info")
    @Expose
    private String manInfo;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("product_ingredients")
    @Expose
    private String Ingredient;
    @SerializedName("animal_test_status")
    @Expose
    private String Animal;
    @SerializedName("company_status")
    @Expose
    private String Manvegan;
    @SerializedName("palm_oil_status")
    @Expose
    private String Palm;
    @SerializedName("link_website")
    @Expose
    private String Addinfo;
    @SerializedName("palm")
    @Expose
    private String prodpalm;
    @SerializedName("gmo")
    @Expose
    private String gmo;
    @SerializedName("gluten")
    @Expose
    private String gluten;
    @SerializedName("nut")
    @Expose
    private String nut;
    @SerializedName("soy")
    @Expose
    private String soy;
    @SerializedName("avg_price")
    @Expose
    private String avgPrice;
    @SerializedName("price_per")
    @Expose
    private String pricePer;
    @SerializedName("price_level")
    @Expose
    private String priceLevel;
    @SerializedName("price_comparison")
    @Expose
    private String priceCompare;
    @SerializedName("link_vegan")
    @Expose
    private String linkVegan;
    @SerializedName("link_palm")
    @Expose
    private String linkPalm;
    @SerializedName("link_gmo")
    @Expose
    private String linkGmo;
    @SerializedName("link_gluten")
    @Expose
    private String linkGluten;
    @SerializedName("link_nut")
    @Expose
    private String linkNut;
    @SerializedName("link_soy")
    @Expose
    private String linkSoy;
    @SerializedName("link_special")
    @Expose
    private String linkSpecial;
    @SerializedName("link_price")
    @Expose
    private String linkPrice;
    @SerializedName("special_detail")
    @Expose
    private String SpecialDetail;
    @SerializedName("link_map")
    @Expose
    private String linkMap;

    @SerializedName("certified")
    @Expose
    private String certified;
    @SerializedName("special_title")
    @Expose
    private String specialTitle;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("last_update")
    @Expose
    private String LastUpdate;
    @SerializedName("country")
    @Expose
    private String Country;
    @SerializedName("alt_name")
    @Expose
    private String AltName;

    public Product(){

    }

    public Product(int id, String name, String barcode) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
    }


    protected Product(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        barcode = in.readString();
        description = in.readString();
        veganStatus = in.readString();
        explanation = in.readString();
        linkPhoto = in.readString();
        linkBarcode = in.readString();
        manInfo = in.readString();
        companyName = in.readString();
        Ingredient = in.readString();
        Animal = in.readString();
        Manvegan = in.readString();
        Palm = in.readString();
        Addinfo = in.readString();
        prodpalm = in.readString();
        gmo = in.readString();
        gluten = in.readString();
        nut = in.readString();
        soy = in.readString();
        avgPrice = in.readString();
        pricePer = in.readString();
        priceLevel = in.readString();
        priceCompare = in.readString();
        linkVegan = in.readString();
        linkPalm = in.readString();
        linkGmo = in.readString();
        linkGluten = in.readString();
        linkNut = in.readString();
        linkSoy = in.readString();
        linkSpecial = in.readString();
        linkPrice = in.readString();
        SpecialDetail = in.readString();
        linkMap = in.readString();
        certified = in.readString();
        specialTitle = in.readString();
        category = in.readString();
        LastUpdate = in.readString();
        Country = in.readString();
        AltName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(barcode);
        dest.writeString(description);
        dest.writeString(veganStatus);
        dest.writeString(explanation);
        dest.writeString(linkPhoto);
        dest.writeString(linkBarcode);
        dest.writeString(manInfo);
        dest.writeString(companyName);
        dest.writeString(Ingredient);
        dest.writeString(Animal);
        dest.writeString(Manvegan);
        dest.writeString(Palm);
        dest.writeString(Addinfo);
        dest.writeString(prodpalm);
        dest.writeString(gmo);
        dest.writeString(gluten);
        dest.writeString(nut);
        dest.writeString(soy);
        dest.writeString(avgPrice);
        dest.writeString(pricePer);
        dest.writeString(priceLevel);
        dest.writeString(priceCompare);
        dest.writeString(linkVegan);
        dest.writeString(linkPalm);
        dest.writeString(linkGmo);
        dest.writeString(linkGluten);
        dest.writeString(linkNut);
        dest.writeString(linkSoy);
        dest.writeString(linkSpecial);
        dest.writeString(linkPrice);
        dest.writeString(SpecialDetail);
        dest.writeString(linkMap);
        dest.writeString(certified);
        dest.writeString(specialTitle);
        dest.writeString(category);
        dest.writeString(LastUpdate);
        dest.writeString(Country);
        dest.writeString(AltName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public void copy(Product p){
        this.id = p.id;
        this.name = p.name;
        this.description = p.description;
        this.barcode = p.barcode;
        this.explanation = p.explanation;
        this.linkPhoto = p.linkPhoto;
        this.manInfo = p.manInfo;
        this.companyName = p.companyName;
        this.linkBarcode = p.linkBarcode;
        this.veganStatus = p.veganStatus;
        this.Ingredient = p.Ingredient;
        this.Animal = p.Animal;
        this.Manvegan = p.Manvegan;
        this.Palm = p.Palm;
        this.Addinfo = p.Addinfo;
        this.prodpalm = p.prodpalm;
        this.gmo = p.gmo;
        this.gluten = p.gluten;
        this.nut = p.nut;
        this.soy = p.soy;
        this.avgPrice = p.avgPrice;
        this.pricePer = p.pricePer;
        this.priceLevel = p.priceLevel;
        this.priceCompare = p.priceCompare;
        this.linkVegan = p.linkVegan;
        this.linkPalm = p.linkPalm;
        this.linkGmo = p.linkGmo;
        this.linkGluten = p.linkGluten;
        this.linkNut = p.linkNut;
        this.linkSoy = p.linkSoy;
        this.linkSpecial = p.linkSpecial;
        this.linkPrice= p.linkPrice;
        this.SpecialDetail= p.SpecialDetail;
        this.linkMap= p.linkMap;
        this.certified= p.certified;
        this.specialTitle= p.specialTitle;
        this.category= p.category;
        this.LastUpdate= p.LastUpdate;
        this.Country= p.Country;
        this.AltName= p.AltName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialDetail() { return SpecialDetail; }

    public void setSpecialDetail(String SpecialDetail) { this.SpecialDetail = SpecialDetail; }

    public String getVeganStatus() {
        return veganStatus;
    }

    public void setVeganStatus(String veganStatus) {
        this.veganStatus = veganStatus;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getMainInfo() {
        return manInfo;
    }

    public void setLinkBarcode(String linkBarcode) {
        this.linkBarcode = linkBarcode;
    }


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }


    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String Ingredient) {
        this.Ingredient = Ingredient;
    }

    public String getAnimal() {
        return Animal;
    }

    public void setAnimal(String Animal) {
        this.Animal = Animal;
    }

    public String getManvegan() {
        return Manvegan;
    }

    public void setManvegan(String Manvegan) {
        this.Manvegan = Manvegan;
    }

    public String getPalm() { return Palm; }

    public void setPalm(String Palm) {
        this.Palm = Palm;
    }

    public String getAddinfo() {
        return Addinfo;
    }

    public void setAddinfo(String Addinfo) {
        this.Addinfo = Addinfo;
    }

    public String getProdpalm() {
        return prodpalm;
    }

    public void setProdpalm(String prodpalm) {
        this.prodpalm = prodpalm;
    }

    public String getGmo() {
        return gmo;
    }

    public void setGmo(String gmo) {
        this.gmo = gmo;
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

    public void setSoy(String Soy) {
        this.soy = soy;
    }

    public String getavgPrice() {
        return avgPrice;
    }

    public void setavgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getpricePer() {
        return pricePer;
    }

    public void setpricePer(String pricePer) {
        this.pricePer = pricePer;
    }

    public String getpriceLevel() {
        return priceLevel;
    }

    public void setpriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getpriceCompare() {
        return priceCompare;
    }

    public void setpriceCompare(String priceCompare) {
        this.priceCompare = priceCompare;
    }

    public String getlinkVegan() {
        return linkVegan;
    }

    public void setlinkVegan(String linkVegan) {
        this.linkVegan = linkVegan;
    }

    public String getlinkPalm() {
        return linkPalm;
    }

    public void setlinkPalm(String linkPalm) {
        this.linkPalm = linkPalm;
    }

    public String getlinkGmo() {
        return linkGmo;
    }

    public void setlinkGmo(String linkGmo) {
        this.linkGmo = linkGmo;
    }

    public String getlinkGluten() {
        return linkGluten;
    }

    public void setlinkGluten(String linkGluten) {
        this.linkGluten = linkGluten;
    }

    public String getlinkNut() {
        return linkNut;
    }

    public void setlinkNut(String linkNut) {
        this.linkNut = linkNut;
    }

    public String getlinkSoy() { return linkSoy; }

    public void setlinkSoy(String linkSoy) { this.linkSoy = linkSoy; }

    public String getlinkPrice() {
        return linkPrice;
    }

    public void setlinkPrice(String linkPrice) {
        this.linkPrice = linkPrice;
    }

    public String getlinkSpecial() {
        return linkSpecial;
    }

    public void setlinkSpecial(String linkSpecial) {
        this.linkSpecial = linkSpecial;
    }

    public String getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(String linkMap) {
        this.linkMap = linkMap;
    }

    public String getLinkBarcode() {
        return linkBarcode;
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String LastUpdate) {
        this.LastUpdate = LastUpdate;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getAltName() {
        return AltName;
    }

    public void setAltName(String AltName) {
        this.AltName = AltName;
    }
}
