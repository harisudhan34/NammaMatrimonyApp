package com.skyappz.namma.ResponseEntities;

public class BaseResponse {
    private  String user_id;
    private String status;

    private String msg;

    private String status_code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setuser_id(String userid) {
        this.user_id = userid;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", userid='" + user_id + '\'' +
                ", status_code='" + status_code + '\'' +
                '}';
    }
}
