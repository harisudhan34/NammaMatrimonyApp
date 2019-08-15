package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.model.User;

public class GetUserDetailsResponse {

    private String msg;

    private User User;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [msg = " + msg + ", User = " + User + ", status = " + status + "]";
    }
}
