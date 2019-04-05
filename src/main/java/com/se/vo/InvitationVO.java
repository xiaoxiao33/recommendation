package com.se.vo;

import com.se.Model.Invitation;
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
public class InvitationVO {

    public int invitationId;

    public int senderId;

    public int receiverId;

    public String start;

    public String end;

    public double latitude;

    public double longitude;

    public InvitationStatus status;

    public InvitationVO(Invitation entity) {
        this.invitationId = entity.getInvitationId();
        this.senderId = entity.getSenderId();
        this.receiverId = entity.getReceiverId();
        this.start = entity.getStart();
        this.end = entity.getEnd();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.status = entity.getStatus();
    }
}
