package com.example.user.navigatelifesaver;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private boolean load = true;
    ArrayList<LatLng> locs;
    private GoogleMap mMap;
    private ServerRequest serverRequest = new ServerRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thread.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        while(load){

        }
        mMap = googleMap;
        for(int i = 0; i<locs.size();i++){
            Log.d("Adding marker", locs.get(i).toString());
            mMap.addMarker(new MarkerOptions()
                    .position(locs.get(i))
                    .title("Patient similiar to you")
                    .snippet(((GlobalVars) getApplicationContext()).getCategory()));
        }
        // Add a marker in Sydney and move the camera
        Location temp = getLastKnownLocation();
        LatLng mLoc = new LatLng(temp.getLatitude(),temp.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLoc,15));
    }
    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            try {

                locs =  serverRequest.get_locations( ((GlobalVars) getApplicationContext()).getCategory());
                load = false;
                Log.d("Loccs:" , locs.get(0).toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    // Private method return last known location
    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
}