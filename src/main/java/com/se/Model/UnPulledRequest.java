package com.se.Model;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class UnPulledRequest {
    @Id
    @Column(name = "sender")
    private int sender;

    @Id
    @Column(name = "receiver")
    private int receiver;  // unique to the user

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;
}