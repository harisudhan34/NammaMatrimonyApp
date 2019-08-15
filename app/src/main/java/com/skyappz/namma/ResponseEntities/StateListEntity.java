package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.database.States;

public class StateListEntity {

    private String msg;

    private String statuscode;

    private String status;

    private States[] states;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public States[] getStates() {
        return states;
    }

    public void setStates(States[] states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "ClassPojo [msg = " + msg + ", statuscode = " + statuscode + ", status = " + status + ", states = " + states + "]";
    }
}
