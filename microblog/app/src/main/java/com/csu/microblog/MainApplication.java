package com.csu.microblog;

import android.app.Application;

public class MainApplication extends Application {

    private boolean login;
    private long account;

    @Override
    public void onCreate() {
        super.onCreate();
        setLogin(false);
        setAccount(0);
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
