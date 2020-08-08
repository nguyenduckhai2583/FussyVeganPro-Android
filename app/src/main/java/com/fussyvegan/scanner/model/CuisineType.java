package com.fussyvegan.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CuisineType implements Parcelable {

    String type;

    boolean isChecked;

    public CuisineType() {
    }

    public CuisineType(String type, boolean isChecked) {
        this.type = type;
        this.isChecked = isChecked;
    }

    protected CuisineType(Parcel in) {
        type = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<CuisineType> CREATOR = new Creator<CuisineType>() {
        @Override
        public CuisineType createFromParcel(Parcel in) {
            return new CuisineType(in);
        }

        @Override
        public CuisineType[] newArray(int size) {
            return new CuisineType[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
