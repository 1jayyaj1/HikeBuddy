package com.jayyaj.hikebuddy.data;

import com.jayyaj.hikebuddy.model.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);
}
