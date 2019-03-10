package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "u_id")
    private int id;

    @Column(name = "username")
    private String username;  // unique to the user

    @Column(name = "passwd")
    private String password;

    @Column(name = "email")
    private String email;

}

/*@Builder
@Entity
@Table(name="public.userinfo")
public class UserInfo {


    @Getter
    @Setter
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "u_id")
    @Builder.Default
    private int id=1;

    @Getter
    @Setter
    @Column(name = "username")
    @Builder.Default
    private String username="user";  // unique to the user

    @Getter
    @Setter
    @Column(name = "passwd")
    @Builder.Default
    private String password="123";

}*/


