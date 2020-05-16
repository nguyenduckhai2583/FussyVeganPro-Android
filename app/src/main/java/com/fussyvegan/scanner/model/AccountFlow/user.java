package com.fussyvegan.scanner.model.AccountFlow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class user {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("role_id")
    @Expose
    private int role_id;

    @SerializedName("deny_access")
    @Expose
    private int deny_access;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("authentication_id")
    @Expose
    private int authentication_id;

    @SerializedName("is_deleted")
    @Expose
    private int is_deleted;

    @SerializedName("username")
    @Expose
    private String username;

    public user() {
    }

    public user(String name, int role_id, int deny_access, String email, int authentication_id, int is_deleted, String username) {
        this.name = name;
        this.role_id = role_id;
        this.deny_access = deny_access;
        this.email = email;
        this.authentication_id = authentication_id;
        this.is_deleted = is_deleted;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getDeny_access() {
        return deny_access;
    }

    public void setDeny_access(int deny_access) {
        this.deny_access = deny_access;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuthentication_id() {
        return authentication_id;
    }

    public void setAuthentication_id(int authentication_id) {
        this.authentication_id = authentication_id;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }
}
