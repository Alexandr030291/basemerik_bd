package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameInfo {
    private int ip;
    private String name;

    @JsonCreator
    public NameInfo(@JsonProperty("name") String name) {
        this.name = name;
    }

    public NameInfo(int ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public int getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }


}
