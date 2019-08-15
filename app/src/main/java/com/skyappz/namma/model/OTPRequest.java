package com.skyappz.namma.model;

import java.io.Serializable;

public class OTPRequest implements Serializable {

    public static String SIGNUP = "signup";

    public static String SIGNIN = "signin";

    public static String PASSWORD_UPDATE = "password_update";

    private String OTP;

    private String mobile_number;

    private String type;

    public OTPRequest(String OTP, String mobile_number, String type) {
        this.OTP = OTP;
        this.mobile_number = mobile_number;
        this.type = type;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
