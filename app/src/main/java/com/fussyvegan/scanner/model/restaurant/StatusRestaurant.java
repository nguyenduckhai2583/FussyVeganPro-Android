package com.fussyvegan.scanner.model.restaurant;

import com.google.gson.annotations.SerializedName;

public class StatusRestaurant {

    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String msg;

    public StatusRestaurant() {
    }

    public StatusRestaurant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
