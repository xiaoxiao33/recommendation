package com.se.Model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Entity
public class PulledRequest implements Serializable {
    @GeneratedValue(generator="auto")
    @Id
    @Column(name = "sender")
    private int sender;

    @GeneratedValue(generator="auto")
    @Id
    @Column(name = "receiver")
    private int receiver;  // unique to the user

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "state")
    private char state;
}
