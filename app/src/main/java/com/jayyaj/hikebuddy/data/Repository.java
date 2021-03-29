package com.jayyaj.hikebuddy.data;

import android.net.wifi.WpsInfo;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jayyaj.hikebuddy.controller.AppController;
import com.jayyaj.hikebuddy.model.Park;
import com.jayyaj.hikebuddy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getPark(final AsyncResponse callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utils.PARKS_URL, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Park park = new Park();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    parkList.add(park);
                }
                if (callBack != null) {
                    callBack.processPark(parkList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, e -> {
            e.printStackTrace();
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}