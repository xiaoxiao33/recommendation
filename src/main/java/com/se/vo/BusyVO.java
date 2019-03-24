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
    int recordId;

    @Column(name = "u_id")
    int userId;

    @Column(name = "start_time")
    String startTime;

    @Column(name = "end_time")
    String endTime;
}