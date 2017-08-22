package com.example.user.mobileproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2016-05-30.
 */
public class shelterClass implements Parcelable {
    String region;
    String region2;
    String name;
    String type;
    String gender;
    String address;

    public shelterClass(){}

    public shelterClass(String region,String region2,String name,String type,String gender,String address)
    {
        this.region = region;
        this.region2 = region2;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.address = address;
    }

    protected shelterClass(Parcel in) {
        region = in.readString();
        region2 = in.readString();
        name = in.readString();
        type = in.readString();
        gender = in.readString();
        address = in.readString();
    }

    public static final Creator<shelterClass> CREATOR = new Creator<shelterClass>() {
        @Override
        public shelterClass createFromParcel(Parcel in) {
            return new shelterClass(in);
        }

        @Override
        public shelterClass[] newArray(int size) {
            return new shelterClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(region);
        dest.writeString(region2);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(gender);
        dest.writeString(address);
    }
}
