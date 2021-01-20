package com.csu.microblog.model.user.login;

import lombok.Data;


public class UserInfo {
    private long account;
    private String message;

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
