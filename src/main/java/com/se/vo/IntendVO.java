package com.se.vo;


public class IntendVO {
//    public int recordId;
    public int userId;
    public String startTime;
    public String endTime;
    public IntendVO(){}

    public IntendVO(int id, String start, String end) {
        this.userId = id;
        this.startTime = start;
        this.endTime = end;
    }
}
