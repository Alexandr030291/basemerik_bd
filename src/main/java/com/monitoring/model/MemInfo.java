package com.monitoring.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemInfo {
    private long free;
    private long total;

    @JsonCreator
    public MemInfo(@JsonProperty("free") long free,
                   @JsonProperty("total") long total) {
        this.free = free;
        this.total = total;
    }

    public long getFree() {
        return free;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public String toString(){
        return "mem:{free:"+free+",total:"+total+"}";
    }
}