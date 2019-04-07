package com.se.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import com.se.model.UserInfo;
/**
 * This class represents an instance of user profile
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class UserProfile {

    public UserProfile(UserInfo userInfo) {
        id = userInfo.getId();
        email = userInfo.getEmail();
        gender="unknown";
        username=userInfo.getEmail();
        description="unknown";
        major="unknow";
        age=-1;
        year="unknow";
        shared_gps = "F";
        availability = 'F';
    }

    // Foreign key dependency not set yet
    @Id
    @GeneratedValue(generator="increment")
    @Column(name="u_id")
    private int id;  // Primary Key for finding a user.

    @Column(name = "username")
    private String username;

    @Column(name = "gender")
    private String gender;

    @Column(name = "major")
    private String major;


    @Column(name = "u_age")
    private int age;

    @Column(name = "u_year")
    private String year;

    @Column(name = "email")
    private String email;

    @Column(name = "shared_gps")
    private String shared_gps;

    @Column(name = "description")
    private String description;

    @Column(name = "availability")
    private char availability;

    @Column(name = "college")
    private String college;
  
    public int getId() {
        return id;
    }
    public String getUsername() {return username;}

}
