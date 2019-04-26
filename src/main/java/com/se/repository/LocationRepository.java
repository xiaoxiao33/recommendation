package com.se.repository;

import com.se.model.UserLocation;
import java.util.List;

public interface LocationRepository {

    /**
     *
     * @param date "yyyy-mm-dd-hh-mm" already subtract 20 min from current time
     * @return all entries with updated time that is within 20 minutes of date.
     */
    public List<UserLocation> getAllLocation(String date);

    /**
     *
     * @param uid
     * @return true if userlocation table already contains location record of this user
     */
    public boolean exist(int uid);

    /**
     *
     * @param latitude
     * @param longitude
     * @param userId
     */
    public boolean addUserLocation(double latitude, double longitude, int userId, String time);


    /**
     *
     * @param latitude
     * @param longitude
     * @param userId
     */

    public boolean updateUserLocation(double latitude, double longitude, int userId, String time);

}
