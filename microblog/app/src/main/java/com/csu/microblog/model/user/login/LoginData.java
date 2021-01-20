package com.csu.microblog.model.user.login;

import lombok.Data;


public class LoginData {
    private boolean result;
    private UserInfo userInfo;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
