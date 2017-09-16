package com.monitoring.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LenMetric {
    private int len;

    @JsonCreator
    public LenMetric(@JsonProperty("len") int len) {
        this.len=len;

    }

    public int getLen() {
        return len;
    }
}
