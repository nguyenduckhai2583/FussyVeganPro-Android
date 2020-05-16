package com.fussyvegan.scanner.model.AccountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class data {

    @SerializedName("user")
    @Expose
    private user user;

    @SerializedName("token")
    @Expose
    private String token;

    public data() {
    }

    public data(com.fussyvegan.scanner.model.AccountFlow.user user, String token) {
        this.user = user;
        this.token = token;
    }

    public com.fussyvegan.scanner.model.AccountFlow.user getUser() {
        return user;
    }

    public void setUser(com.fussyvegan.scanner.model.AccountFlow.user user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
