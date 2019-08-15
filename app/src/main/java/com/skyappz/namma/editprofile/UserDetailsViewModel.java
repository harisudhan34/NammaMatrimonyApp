package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.model.User;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import java.util.HashMap;

public class UserDetailsViewModel extends ViewModel implements WebServiceListener {

    private WebServiceManager webServiceManager;
    public MutableLiveData<User> user = new MutableLiveData<User>();

    public Activity activity;

    public UserDetailsViewModel() {
//        webServiceManager = new WebServiceManager(activity);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.postValue(user);
    }

    public void getUserDetails(String user_id) {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.getUserDetails(user_id, this);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        user.postValue(((GetUserDetailsResponse) response).getUser());
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {

    }

    @Override
    public void isNetworkAvailable(boolean flag) {

    }

    @Override
    public void onProgressStart() {

    }

    @Override
    public void onProgressEnd() {

    }

    public void updateUser(HashMap<String, String> params, WebServiceListener webServiceListener) {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.updateUserDetails(params, webServiceListener);
    }
    public void getcaste( WebServiceListener webServiceListener) {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.getAllCaste(webServiceListener);
    }
    public void setpartner(HashMap<String, String> params, WebServiceListener webServiceListener) {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.setpartnerddetails(params, webServiceListener);
    }
}