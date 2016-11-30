package com.example.user.navigatelifesaver;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Asus on 29/11/2016.
 *
 * Created to convert location fro json to java object
 */
public class Loc {
    private double latitude;
    private double longtitude;

    public Loc(double latitude, double longtitude){
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
    public LatLng toLatLng(){
        return new LatLng(latitude, longtitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
