package com.skyappz.namma.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.skyappz.namma.model.User;


/**
 * Created by user on 9/10/2015.
 */
public class Preferences {

    Context context;
    SharedPreferences preferences;
    public static String USER_ID = "user_id", PASSWORD = "password", EMAIL = "email", PHONE_NUMBER = "phone_number",
            FIRSTNAME = "firstname", LASTNAME = "lastname", LOGIN_TYPE = "login_type", USER_TYPE = "user_type", DEVICE_ID = "device_id", IMAGE_URL = "image_url",
            MESSAGE_COUNT = "message_count",SIGNUPNAME="signupname",SIGNUPEMAIL="signupemail",SIGNUPPWD="signupPWd",SIGNUPHONE="signupphone",SIGNUPPROFILECREETAE="signupprofilecreate"
            ,S_USERID="Suserid";

    boolean isRemembered, isRegistered, isLoginned;
    String deviceId,s_siignupname,s_signupEmail,s_signupPassword,s_signupPhone,s_signupProfilebby,s_userid;
    int messageCount;

    public Preferences(Context context) {

        this.context = context;
        preferences = context.getSharedPreferences("prefs", 0);
    }


    public boolean isRegistered() {
        isRegistered = preferences.getBoolean("isRegistered", false);

        return isRegistered;
    }

    public String getDeviceId() {
        deviceId = preferences.getString(DEVICE_ID, "");
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        preferences.edit().putString(DEVICE_ID, deviceId).apply();
        this.deviceId = deviceId;
    }

    public int getMessageCount() {
        messageCount = preferences.getInt(MESSAGE_COUNT, 0);
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        preferences.edit().putInt(MESSAGE_COUNT, messageCount).apply();
        this.messageCount = messageCount;
    }

    public void setRegistered(boolean isRegistered) {
        preferences.edit().putBoolean("isRegistered", isRegistered).apply();
        this.isRegistered = isRegistered;
    }


    public boolean isLoginned() {
        isLoginned = preferences.getBoolean("isLoginned", false);
        return isLoginned;
    }

    public void setLoginned(boolean isLoginned) {
        preferences.edit().putBoolean("isLoginned", isLoginned).apply();
        this.isLoginned = isLoginned;
    }

    public void updateUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, user.getUser_id());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.apply();

    }

    public User getUser() {
        User user = new User();
        user.setUser_id(preferences.getString(USER_ID, ""));
        user.setPassword(preferences.getString(PASSWORD, ""));
        user.setEmail(preferences.getString(EMAIL, ""));

        return user;
    }





//    public static void save_userid(Context context, String city) {
////        clear_data(context);
//        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(Shard_userid, city);
//        editor.apply();
//    }
//
//
//    public static String share_userid(Context ctx) {
//        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
//        return sharedpreferences.getString(Shard_userid, null);
//    }

    public String getsignupname() {

        s_siignupname = preferences.getString(SIGNUPNAME, "");
        return s_siignupname;
    }

    public void setSignupname(String name) {
        preferences.edit().putString(SIGNUPNAME, name).apply();
        this.s_siignupname = name;
    }

    public String getsignupemail() {
        s_signupEmail = preferences.getString(SIGNUPEMAIL, "");
        return s_signupEmail;
    }

    public void setSignupEmail(String email) {
        preferences.edit().putString(SIGNUPEMAIL, email).apply();
        this.s_signupEmail = email;
    }


    public String getsignuppassword() {
        s_signupPassword = preferences.getString(SIGNUPPWD, "");
        return s_signupPassword;
    }

    public void setsignuppassword(String pwd) {
        preferences.edit().putString(SIGNUPPWD, pwd).apply();
        this.s_signupPassword = pwd;
    }
    public String getsignuphone() {
        s_signupPhone = preferences.getString(SIGNUPHONE, "");
        return s_signupPhone;
    }

    public void setsignupphone(String phone) {
        preferences.edit().putString(SIGNUPHONE, phone).apply();
        this.s_signupPhone = phone;
    }
    public String getsignupProfileCreatedby() {
        s_signupProfilebby = preferences.getString(SIGNUPPROFILECREETAE, "");
        return s_signupProfilebby;
    }

    public void setSignupprofileCreatedby(String profilecreate) {
        preferences.edit().putString(SIGNUPPROFILECREETAE, profilecreate).apply();
        this.s_signupProfilebby = profilecreate;
    }

    public String getuser_id() {
        s_userid = preferences.getString(S_USERID, "");
        return s_userid;
    }

    public void setuser_id(String userid) {
        preferences.edit().putString(S_USERID, userid).apply();
        this.s_userid = userid;
    }

    public void clearAll() {
        preferences.edit().clear().commit();
    }
}
