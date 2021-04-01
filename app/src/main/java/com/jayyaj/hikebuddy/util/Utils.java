package com.jayyaj.hikebuddy.util;

public class Utils {
    public static String getParksUrl(String stateCode) {
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=vm1oHdDSeihgih6dz1dea8dLP2apO2ObS8CbuLq8";
    }
}
