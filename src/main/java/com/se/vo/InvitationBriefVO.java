package com.se.vo;

/**
 * This class represents an invitation item shown on front end invitation page.
 */

public class InvitationBriefVO {
    int invitationId;
    int senderId;
    int receiverId;
    //String address;  // Change to longitude and altitude later to display on google map
    double latitude;
    double longitude;
    String start;
    String end;

    public InvitationBriefVO(InvitationVO invitationVO) {
        this.invitationId = invitationVO.getInvitationId();
        this.senderId = invitationVO.getSenderId();
        this.receiverId = invitationVO.getReceiverId();
        //this.address = invitationVO.getAddress();
        this.latitude = invitationVO.getLatitude();
        this.longitude = invitationVO.getLongitude();
        this.start = invitationVO.getStart();
        this.end = invitationVO.getEnd();
    }
}
