package com.se.vo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class BusyVO {
    @Id
    @GeneratedValue(generator="increment")
    @Column(name = "id")
    public int recordId;

    @Column(name = "u_id")
    public int userId;

    @Column(name = "start_time")
    public  String startTime;

    @Column(name = "end_time")
   public  String endTime;
}
