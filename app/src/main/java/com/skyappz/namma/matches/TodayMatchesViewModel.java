package com.skyappz.namma.matches;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.skyappz.namma.ResponseEntities.UserListEntity;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TodayMatchesViewModel extends ViewModel implements WebServiceListener {

    private WebServiceManager webServiceManager;
    public MutableLiveData<ArrayList<User>> users = new MutableLiveData<ArrayList<User>>();

    public Activity activity;

    public TodayMatchesViewModel() {

    }

    public LiveData<ArrayList<User>> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users.postValue(users);
    }

    public void getTodayMatches(String user_id) {
        webServiceManager = new WebServiceManager(activity);
//        webServiceManager.getTodayMatches(user_id, this);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        UserListEntity userListEntity = (UserListEntity) response;
//        users.postValue((ArrayList<User>) Arrays.asList(userListEntity.getUsers()));
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        if (requestCode == WebServiceManager.REQUEST_CODE_GET_TODAY_MATCHES) {
            Utils.showToast(activity, ((UserListEntity) response).getMsg());
        }
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
}
