package com.jayyaj.hikebuddy;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jayyaj.hikebuddy.data.AsyncResponse;
import com.jayyaj.hikebuddy.data.Repository;
import com.jayyaj.hikebuddy.model.Park;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.mapsNavButton) {
                //Selected map
                mMap.clear();   //Clears map to avoid memory leak
                selectedFragment = mapFragment;
                mapFragment.getMapAsync(this);  //Updates map

            } else if (id == R.id.parksNavButton){
                //Selected list
                selectedFragment = ParksFragment.newInstance();
            }
            //.replace() will replace the current fragment with the selected fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map, selectedFragment)
                    .commit();
            return true;
        });
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
        mMap = googleMap;

        Repository.getPark(parks -> {
            for (Park park: parks) {
                LatLng parkLatLng = new LatLng(Double.parseDouble(park.getLatitude()), Double.parseDouble(park.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(parkLatLng).title("Marker in " + park.getFullName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(parkLatLng));

                Log.d("Parks", String.valueOf(park.getFullName()));
            }
        });
    }
}