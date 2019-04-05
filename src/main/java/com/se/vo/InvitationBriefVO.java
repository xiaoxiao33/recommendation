package com.se.vo;


/**
 * This class represents an invitation item shown on front end invitation page.
 */

public class InvitationBriefVO {
    public int invitationId;
    public int senderId;
    public int receiverId;
    //String address;  // Change to longitude and altitude later to display on google map
    public double latitude;
    public double longitude;
    public String start;
    public String end;

    public InvitationBriefVO(){}

    public InvitationBriefVO(InvitationVO invitationVO) {
        this.invitationId = invitationVO.invitationId;
        this.senderId = invitationVO.senderId;
        this.receiverId = invitationVO.receiverId;
        //this.address = invitationVO.getAddress();
        this.latitude = invitationVO.latitude;
        this.longitude = invitationVO.longitude;
        this.start = invitationVO.start;
        this.end = invitationVO.end;
    }
}
