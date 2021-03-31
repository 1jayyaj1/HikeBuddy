package com.jayyaj.hikebuddy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.jayyaj.hikebuddy.R;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    public CustomInfoWindow(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = layoutInflater.inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView parkName = view.findViewById(R.id.infoTitle);
        TextView parkState = view.findViewById(R.id.infoState);

        parkName.setText(marker.getTitle());
        parkState.setText(marker.getSnippet());
        return view;
    }
}
