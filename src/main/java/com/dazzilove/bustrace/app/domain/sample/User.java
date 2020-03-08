package com.dazzilove.bustrace.app.domain.sample;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private String userId;
    private String userPwd;
    private String name;
    private String authType;

    public User(String userId, String name, String authType) {
        super();
        this.userId = userId;
        this.name = name;
        this.authType = authType;
    }
}
