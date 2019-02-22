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

    @Id
    @Getter
    @Setter
    @GeneratedValue(generator="increment")
    @Column(name = "u_id")
    private int u_id;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;  // unique to the user

    @Getter
    @Setter
    @Column(name = "passwd")
    private String passwd;



}


