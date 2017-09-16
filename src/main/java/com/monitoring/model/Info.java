package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Info {
    private CPUInfo cpuInfo;
    private MemInfo memInfo;
    private NetInfo netInfo;
    private long rps;
    private long time;
    private int ip;

    @JsonCreator
    public Info(@JsonProperty("cpu") CPUInfo cpuInfo,
                @JsonProperty("mem") MemInfo memInfo,
                @JsonProperty("net") NetInfo netInfo,
                @JsonProperty("rps") long rps,
                @JsonProperty("time") long time) {
        this.cpuInfo = cpuInfo;
        this.memInfo = memInfo;
        this.netInfo = netInfo;
        this.rps = rps;
        this.time = time;
    }

    public Info(CPUInfo cpuInfo,
                MemInfo memInfo,
                NetInfo netInfo,
                long rps,
                long time,
                int ip) {
        this.cpuInfo = cpuInfo;
        this.memInfo = memInfo;
        this.netInfo = netInfo;
        this.rps = rps;
        this.time = time;
        this.ip = ip;
    }


    public CPUInfo getCpuInfo() {
        return cpuInfo;
    }

    public long getTime(){
        return time;
    }

    public MemInfo getMemInfo() {
        return memInfo;
    }

    public NetInfo getNetInfo() {
        return netInfo;
    }

    public long getRps() {
        return rps;
    }

    public int getIp() {
        return ip;
    }
/*
    public String toString(){
        return "{\"ip\" : " + ip +", " +cpuInfo.toString()+","+memInfo.toString()+","+netInfo.toString() + ", rps : " + rps + " , time :" + time + "}";
    }*/
}
