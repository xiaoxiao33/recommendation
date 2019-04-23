package com.se.Model;

import com.se.util.InvitationStatus;
import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class Invitation {
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

    @Column(name = "message")
    String message;

    @Column(name = "restaurant")
    String restaurant;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    InvitationStatus status;
}
