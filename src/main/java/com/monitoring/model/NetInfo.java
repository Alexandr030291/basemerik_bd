package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NetInfo {
    private long receive;
    private long transmit;

    @JsonCreator
    public NetInfo(@JsonProperty("receive") long receive,
                   @JsonProperty("transmit") long transmit) {
        this.receive = receive;
        this.transmit = transmit;
    }

    public long getreceive() {
        return receive;
    }

    public long gettransmit() {
        return transmit;
    }

    @Override
    public String toString(){
        return "net:{bust:"+receive+",transmit:"+transmit+"}";
    }
}
