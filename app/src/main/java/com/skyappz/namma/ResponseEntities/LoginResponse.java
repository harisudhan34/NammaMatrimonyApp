package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.model.User;

public class LoginResponse {

    private User user;

    private String status;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User User) {
        this.user = User;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [user = " + user + ", status = " + status + "]";
    }


}