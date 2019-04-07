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

    @Column(name = "passwd")
    private String password;

    @Column(name = "email")
    private String email;

}




