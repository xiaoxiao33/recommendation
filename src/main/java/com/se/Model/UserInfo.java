package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Entity
@Table(name="youhan.userinfo")
public class UserInfo {


    @Getter
    @Setter
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "u_id")
    private int id;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;  // unique to the user

    @Getter
    @Setter
    @Column(name = "passwd")
    private String password;



}


