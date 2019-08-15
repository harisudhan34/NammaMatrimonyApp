package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.model.User;

public class SignUpResponse {

    private String msg;

    private User user;

    private String status;

    private String status_code;

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

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "msg='" + msg + '\'' +
                ", User=" + user +
                ", status='" + status + '\'' +
                ", status_code='" + status_code + '\'' +
                '}';
    }
}