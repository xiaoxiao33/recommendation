package com.se.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Builder
public class UserInfo {

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String username;  // unique to the user
    @Getter
    @Setter
    private String password;


}


