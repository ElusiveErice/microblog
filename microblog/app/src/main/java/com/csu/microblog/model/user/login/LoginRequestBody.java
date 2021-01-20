package com.csu.microblog.model.user.login;

import lombok.Data;


public class LoginRequestBody {
    private long account;
    private String password;

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
