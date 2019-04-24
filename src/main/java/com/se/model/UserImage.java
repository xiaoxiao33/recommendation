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
public class UserImage {
    @Id
    @Column(name = "u_id")
    private int id;

    @Lob
    @Column(name = "image")
    private Byte[] image;
}
