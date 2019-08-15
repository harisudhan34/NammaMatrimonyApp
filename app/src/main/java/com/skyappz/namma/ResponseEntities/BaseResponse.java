package com.skyappz.namma.ResponseEntities;

public class BaseResponse {

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
                ", status_code='" + status_code + '\'' +
                '}';
    }
}
