package com.se.vo;

import com.se.util.InvitationStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an invitation object stored in database
 */


public class InvitationVO {
    @Getter
    @Setter
    int invitationId;

    @Getter
    @Setter
    int senderId;

    @Getter
    @Setter
    int receiverId;

    @Getter
    @Setter
    String address; // Change to longitude and altitude later to display on google map

    @Getter
    @Setter
    String start;

    @Getter
    @Setter
    String end;

    @Getter
    @Setter
    InvitationStatus status;
}
