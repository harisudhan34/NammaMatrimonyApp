package com.skyappz.namma.model;

import com.skyappz.namma.ResponseEntities.BaseResponse;

public class BuyPlan extends BaseResponse {

    private String user_id, plan_id, transaction_id, paymentmode, activedate;

    public BuyPlan(String user_id, String plan_id, String transaction_id, String paymentmode, String activedate) {
        this.user_id = user_id;
        this.plan_id = plan_id;
        this.transaction_id = transaction_id;
        this.paymentmode = paymentmode;
        this.activedate = activedate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getActivedate() {
        return activedate;
    }

    public void setActivedate(String activedate) {
        this.activedate = activedate;
    }
}
