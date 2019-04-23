package com.se.vo;

import com.se.Model.Invitation;
import com.se.util.InvitationStatus;

/**
 * This class represents an invitation object stored in database
 */
public class InvitationVO {

    public int invitationId;

    public int senderId;

    public int receiverId;

    public String start;

    public String end;

    public double latitude;

    public double longitude;

    public String restaurant;

    public String message;

    public InvitationStatus status;

    public InvitationVO(){}

    public InvitationVO(Invitation entity) {
        this.invitationId = entity.getInvitationId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.start = entity.getStart();
        this.end = entity.getEnd();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.status = entity.getStatus();
        this.message = entity.getMessage();
        this.restaurant = entity.getRestaurant();
    }
}
