package com.jayyaj.hikebuddy.data;

import android.net.wifi.WpsInfo;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jayyaj.hikebuddy.controller.AppController;
import com.jayyaj.hikebuddy.model.Activities;
import com.jayyaj.hikebuddy.model.EntranceFees;
import com.jayyaj.hikebuddy.model.Images;
import com.jayyaj.hikebuddy.model.OperatingHours;
import com.jayyaj.hikebuddy.model.Park;
import com.jayyaj.hikebuddy.model.StandardHours;
import com.jayyaj.hikebuddy.model.Topics;
import com.jayyaj.hikebuddy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getPark(final AsyncResponse callBack, String stateCode) {
        String stateUrl = Utils.getParksUrl(stateCode);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, stateUrl, null, response -> {
            try {
                parkList.clear();
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Park park = new Park();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));
                    park.setDescription(jsonObject.getString("description"));
                    park.setDesignation(jsonObject.getString("designation"));
                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    JSONArray jsonImageList = jsonObject.getJSONArray("images");
                    List<Images> imageList = new ArrayList<>();

                    for (int j = 0; j < jsonImageList.length(); j++) {
                        Images images = new Images();
                        images.setCredit(jsonImageList.getJSONObject(j).getString("credit"));
                        images.setTitle(jsonImageList.getJSONObject(j).getString("title"));
                        images.setAltText(jsonImageList.getJSONObject(j).getString("altText"));
                        images.setUrl(jsonImageList.getJSONObject(j).getString("url"));

                        imageList.add(images);
                    }
                    park.setImages(imageList);


                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));

                    JSONArray jsonActivities = jsonObject.getJSONArray("activities");
                    List<Activities> activitiesList = new ArrayList<>();

                    for (int j = 0; j < jsonActivities.length(); j++) {
                        Activities activities = new Activities();
                        activities.setId(jsonActivities.getJSONObject(j).getString("id"));
                        activities.setName(jsonActivities.getJSONObject(j).getString("name"));

                        activitiesList.add(activities);
                    }
                    park.setActivities(activitiesList);


                    JSONArray jsonEntranceFees = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFeesList = new ArrayList<>();

                    for (int j = 0; j < jsonEntranceFees.length(); j++) {
                        EntranceFees entranceFees = new EntranceFees();
                        entranceFees.setCost(jsonEntranceFees.getJSONObject(j).getString("cost"));
                        entranceFees.setTitle(jsonEntranceFees.getJSONObject(j).getString("title"));
                        entranceFees.setDescription(jsonEntranceFees.getJSONObject(j).getString("description"));

                        entranceFeesList.add(entranceFees);
                    }
                    park.setEntranceFees(entranceFeesList);


                    JSONArray jsonTopics = jsonObject.getJSONArray("topics");
                    List<Topics> topicsList = new ArrayList<>();
                    for (int j = 0; j < jsonTopics.length() ; j++) {
                        Topics topics = new Topics();
                        topics.setId(jsonTopics.getJSONObject(j).getString("id"));
                        topics.setName(jsonTopics.getJSONObject(j).getString("name"));
                        topicsList.add(topics);

                    }
                    park.setTopics(topicsList);


                    JSONArray jsonOperatingHours = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHoursList = new ArrayList<>();

                    for (int j = 0; j < jsonOperatingHours.length() ; j++) {
                        OperatingHours operatingHours = new OperatingHours();
                        operatingHours.setDescription(jsonOperatingHours.getJSONObject(j).getString("description"));
                        StandardHours standardHours = new StandardHours();
                        JSONObject hours = jsonOperatingHours.getJSONObject(j).getJSONObject("standardHours");

                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setSunday(hours.getString("sunday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));
                        operatingHours.setStandardHours(standardHours);

                        operatingHoursList.add(operatingHours);
                    }
                    park.setOperatingHours(operatingHoursList);

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