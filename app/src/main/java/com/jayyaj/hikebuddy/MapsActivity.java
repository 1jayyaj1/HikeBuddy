package com.jayyaj.hikebuddy;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jayyaj.hikebuddy.adapter.CustomInfoWindow;
import com.jayyaj.hikebuddy.data.AsyncResponse;
import com.jayyaj.hikebuddy.data.Repository;
import com.jayyaj.hikebuddy.model.Park;
import com.jayyaj.hikebuddy.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    private CardView searchCard;
    private EditText stateCode;
    private ImageButton searchButton;
    private String codeSearched = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        parkViewModel = new ViewModelProvider(MapsActivity.this)
                .get(ParkViewModel.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchCard = findViewById(R.id.searchCard);
        stateCode = findViewById(R.id.floatingStateValue);
        searchButton = findViewById(R.id.floatingSearchButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            //Selected map
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.mapsNavButton) {
                if (searchCard.getVisibility() == View.GONE) {
                    searchCard.setVisibility(View.VISIBLE);
                }
                parkList.clear(); //Clears parklist to avoid memory leak
                mMap.clear();   //Clears map to avoid memory leak
                selectedFragment = mapFragment;
                mapFragment.getMapAsync(this);  //Updates map

            } else if (id == R.id.parksNavButton){
                //Selected lists
                searchCard.setVisibility(View.GONE);
                selectedFragment = ParksFragment.newInstance();
            }
            //.replace() will replace the current fragment with the selected fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map, selectedFragment)
                    .commit();
            return true;
        });
        searchButton.setOnClickListener(v -> {
            parkList.clear();
            String stateCodeText = stateCode.getText().toString().trim();
            if (!TextUtils.isEmpty(stateCodeText)) {
                codeSearched = stateCodeText;
                parkViewModel.selectCode(stateCodeText);
                onMapReady(mMap);   //Refresh map
                stateCode.setText("");
            }
            hideSoftKeyboard(v);
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
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);

        parkList = new ArrayList<>();

        getCurrentStatePark();
    }

    private void getCurrentStatePark() {
        mMap.clear(); //Clears the map
        Repository.getPark(parks -> {
            parkList = parks;
            for (Park park: parks) {
                LatLng parkLatLng = new LatLng(Double.parseDouble(park.getLatitude()), Double.parseDouble(park.getLongitude()));
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(parkLatLng)
                        .title(park.getFullName())
                        .icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_VIOLET
                        ))
                        .snippet(park.getStates());

                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(park);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parkLatLng,5));
            }
            parkViewModel.setSelectedParks(parkList);
        }, codeSearched);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        searchCard.setVisibility(View.GONE);
        moveToDetails(marker);
    }

    private void moveToDetails(Marker marker) {
        parkViewModel.selectPark((Park) marker.getTag());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map, new DetailsFragment())
                .commit();
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}