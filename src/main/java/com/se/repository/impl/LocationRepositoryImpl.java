package com.se.repository.impl;

import com.se.repository.LocationRepository;
import com.se.vo.UserBriefVO;

import java.util.List;

public class LocationRepositoryImpl implements LocationRepository {


    /**
     *
     * @param date "yyyy-mm-dd-hh-mm"
     * @return all entries with updated time that is within 20 minutes of date.
     */
//    public List<UserLocation> getAllLocation(String date) {
//        return null;
//    }


    /**
     *
     * @param latitude
     * @param longitude
     * @param userId
     */
    public void addUserLocation(double latitude, double longitude, int userId) {
        return;
    }


    /**
     *
     * @param latitude
     * @param longitude
     * @param userId
     */

    public void updateUserLocation(double latitude, double longitude, int userId) {
        return;
    }
}
