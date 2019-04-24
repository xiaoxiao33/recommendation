package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
@Table(name = "userimage")
public class UserImage {
//    public UserImage (int id, byte[] image) {
//        this.id = id;
//        this.image = image;
//    }
    @Id
    @Column(name = "u_id")
    private int id;

    @Lob
    @Column(name = "image")
    private byte[] image;
}
