package com.fussyvegan.scanner.model.favorite;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FavoriteType implements Parcelable {

    @SerializedName("favoriteable_type")
    int favoriteable_type;

    @SerializedName("favoriteable_id")
    int favoriteable_id;

    @SerializedName("group_id")
    int group_id;

    public FavoriteType() {
    }

    public FavoriteType(int favoriteable_type, int favoriteable_id, int group_id) {
        this.favoriteable_type = favoriteable_type;
        this.favoriteable_id = favoriteable_id;
        this.group_id = group_id;
    }

    protected FavoriteType(Parcel in) {
        favoriteable_type = in.readInt();
        favoriteable_id = in.readInt();
        group_id = in.readInt();
    }

    public static final Creator<FavoriteType> CREATOR = new Creator<FavoriteType>() {
        @Override
        public FavoriteType createFromParcel(Parcel in) {
            return new FavoriteType(in);
        }

        @Override
        public FavoriteType[] newArray(int size) {
            return new FavoriteType[size];
        }
    };

    public int getFavoriteable_type() {
        return favoriteable_type;
    }

    public void setFavoriteable_type(int favoriteable_type) {
        this.favoriteable_type = favoriteable_type;
    }

    public int getFavoriteable_id() {
        return favoriteable_id;
    }

    public void setFavoriteable_id(int favoriteable_id) {
        this.favoriteable_id = favoriteable_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(favoriteable_type);
        parcel.writeInt(favoriteable_id);
        parcel.writeInt(group_id);
    }
}
