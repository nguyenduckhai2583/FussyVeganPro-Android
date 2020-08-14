package com.fussyvegan.scanner.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GroupFavorite implements Parcelable {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("authentication_id")
    int authentication_id;

    @SerializedName("created_at")
    String created_at;

    public GroupFavorite() {
    }

    public GroupFavorite(int id, String name, int authentication_id, String created_at) {
        this.id = id;
        this.name = name;
        this.authentication_id = authentication_id;
        this.created_at = created_at;
    }

    protected GroupFavorite(Parcel in) {
        id = in.readInt();
        name = in.readString();
        authentication_id = in.readInt();
        created_at = in.readString();
    }

    public static final Creator<GroupFavorite> CREATOR = new Creator<GroupFavorite>() {
        @Override
        public GroupFavorite createFromParcel(Parcel in) {
            return new GroupFavorite(in);
        }

        @Override
        public GroupFavorite[] newArray(int size) {
            return new GroupFavorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthentication_id() {
        return authentication_id;
    }

    public void setAuthentication_id(int authentication_id) {
        this.authentication_id = authentication_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(authentication_id);
        parcel.writeString(created_at);
    }
}
