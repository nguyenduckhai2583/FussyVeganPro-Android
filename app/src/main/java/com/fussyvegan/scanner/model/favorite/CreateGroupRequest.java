package com.fussyvegan.scanner.model.favorite;

import com.google.gson.annotations.SerializedName;

public class CreateGroupRequest {
    @SerializedName("name")
    String name;

    public CreateGroupRequest(String name) {
        this.name = name;
    }

    public CreateGroupRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
