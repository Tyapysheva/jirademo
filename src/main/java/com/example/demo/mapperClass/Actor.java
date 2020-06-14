package com.example.demo.mapperClass;

import java.util.Map;

public class Actor {

    private String id;
    private String displayName;
    private Map<String, String> actorUser;

    public String getAccountId() {
        return actorUser.getOrDefault("accountId", "");
    }
}
