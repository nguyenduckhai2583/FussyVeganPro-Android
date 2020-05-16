
package com.fussyvegan.scanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentAddFood {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("keySearch")
    @Expose
    private String keySearch;
    @SerializedName("url")
    @Expose
    private String url;

    public CurrentAddFood(String name,String keySearch, String url) {
        this.name = name;
        this.url = url;
        this.url = keySearch;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
