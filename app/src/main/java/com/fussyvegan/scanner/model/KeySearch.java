
package com.fussyvegan.scanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeySearch {

    @SerializedName("key")
    @Expose
    private String key;

    public KeySearch(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
