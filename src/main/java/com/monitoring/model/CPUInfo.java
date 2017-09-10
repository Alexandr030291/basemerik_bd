package com.monitoring.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CPUInfo {
    private long busy;
    private long work;

    @JsonCreator
    public CPUInfo(@JsonProperty("busy") long busy,
                   @JsonProperty("word") long work) {
        this.busy = busy;
        this.work = work;
    }

    public long getBusy() {
        return busy;
    }

    public long getWork() {
        return work;
    }

    @Override
    public String toString(){
        return "cpu:{bust:"+busy+",work:"+work+"}";
    }
}
