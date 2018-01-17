package com.example.calvin.motiontracker.model;

import com.google.gson.annotations.SerializedName;

public final class Location {

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
}
