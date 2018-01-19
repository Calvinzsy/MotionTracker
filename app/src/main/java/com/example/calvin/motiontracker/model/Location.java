package com.example.calvin.motiontracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class Location implements Parcelable {

    @SerializedName("lat")
    private final double latitude;

    @SerializedName("lng")
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    private Location(Parcel parcel) {
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

        @Override
        public Location createFromParcel(Parcel parcel) {
            return new Location(parcel);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
