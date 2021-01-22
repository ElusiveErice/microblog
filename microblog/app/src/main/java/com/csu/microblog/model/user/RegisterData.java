package com.csu.microblog.model.user;

import androidx.annotation.NonNull;

public class RegisterData {
    private boolean register;
    private String message;
    private long account;

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    @NonNull
    @Override
    public String toString() {
        return "register:" + register + ",account:" + account + ",account:" + message;
    }
}
