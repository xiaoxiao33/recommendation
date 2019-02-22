package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents an instance of user profile
 */
@Entity
@Table(name="public.userprofile")
public class UserProfile {

    public UserProfile(UserInfo userInfo) {
        u_id = userInfo.getId();
        username = userInfo.getUsername();
    }
    @Id
    @Getter
    @Setter
    @Column(name = "u_id")
    private int u_id;  // Primary Key for finding a user.

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "gender")
    private int gender;

    @Getter
    @Setter
    @Column(name = "major")
    private String major;

    @Getter
    @Setter
    @Column(name = "age")
    private int age;

    @Getter
    @Setter
    @Column(name = "year")
    private String year;
}
