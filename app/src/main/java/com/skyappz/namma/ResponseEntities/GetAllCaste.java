package com.skyappz.namma.ResponseEntities;

import com.skyappz.namma.model.Plan;

public class GetAllCaste {
    private String msg;

    private String statuscode;

    private Plan[] plans;

    private String status;

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

    public Plan[] getPlans() {
        return plans;
    }

    public void setPlans(Plan[] plans) {
        this.plans = plans;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [msg = " + msg + ", statuscode = " + statuscode + ", plans = " + plans + ", status = " + status + "]";
    }
}
