package com.csu.microblog.model.microblog;

import lombok.Data;


public class Microblog {
    private long microblogId;

    public long getMicroblogId() {
        return microblogId;
    }

    public void setMicroblogId(long microblogId) {
        this.microblogId = microblogId;
    }
}
