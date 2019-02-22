package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an instance of user profile
 */

public class UserProfile {

    public UserProfile(UserInfo userInfo) {
        id = userInfo.getId();
        username = userInfo.getUsername();
    }
    @Getter
    @Setter
    private String id;  // Primary Key for finding a user.

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private String major;

    @Getter
    @Setter
    private String age;

    @Getter
    @Setter
    private String year;
}
