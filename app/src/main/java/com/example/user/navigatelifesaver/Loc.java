package com.example.user.navigatelifesaver;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Asus on 29/11/2016.
 *
 * Created to convert location fro json to java object
 */
public class Loc {
    private double latitude;
    private double longitude;

    public Loc(){
        latitude = 0;
        longitude = 0;
    }
    public Loc(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public LatLng toLatLng(){
        return new LatLng(latitude, longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
