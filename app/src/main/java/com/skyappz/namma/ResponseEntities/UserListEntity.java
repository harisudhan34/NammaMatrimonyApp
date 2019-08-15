package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.model.User;

public class UserListEntity {

    private String msg;

    private User[] Users;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User[] getUsers() {
        return Users;
    }

    public void setUsers(User[] Users) {
        this.Users = Users;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [msg = " + msg + ", Today_Matches = " + Users + ", status = " + status + "]";
    }
}
