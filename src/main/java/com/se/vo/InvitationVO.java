package com.se.vo;

import com.se.util.InvitationStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

/**
 * This class represents an invitation object stored in database
 */

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class InvitationVO {
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "id")
    int invitationId;

    @Column(name = "sender")
    int senderId;

    @Column(name = "receiver")
    int receiverId;

    //String address; // Change to longitude and altitude later to display on google map



    @Column(name = "start_time")
    String start;

    @Column(name = "end_time")
    String end;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    InvitationStatus status;
}
