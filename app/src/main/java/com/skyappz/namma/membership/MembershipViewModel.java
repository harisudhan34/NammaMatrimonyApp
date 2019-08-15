package com.skyappz.namma.membership;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.ResponseEntities.GetAllPlansEntity;
import com.skyappz.namma.model.BuyPlan;
import com.skyappz.namma.model.Plan;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MembershipViewModel extends ViewModel implements WebServiceListener {
    private WebServiceManager webServiceManager;
    public MutableLiveData<List<Plan>> plans = new MutableLiveData<List<Plan>>();
    public MutableLiveData<BaseResponse> buyPlanResponse = new MutableLiveData<BaseResponse>();
    public Activity activity;

    public MembershipViewModel() {
    }

    public LiveData<List<Plan>> getPlan() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans.postValue(plans);
    }

    public LiveData<BaseResponse> getBuyPlan() {
        return buyPlanResponse;
    }

    public void setBuyPlan(BaseResponse buyPlanResponse) {
        this.buyPlanResponse.postValue(buyPlanResponse);
    }

    public void getAllPlans() {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.getAllPlanDetails(this);
    }

    public void buyPlan(BuyPlan buyPlan) {
        webServiceManager = new WebServiceManager(activity);
        webServiceManager.buyPlan(buyPlan, this);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        if (requestCode == WebServiceManager.REQUEST_CODE_GET_ALL_PLAN__DETAILS) {
            plans.postValue(Arrays.asList(((GetAllPlansEntity) response).getPlans()));
        } else if (requestCode == WebServiceManager.REQUEST_CODE_BUY_PLAN) {
            buyPlanResponse.postValue((BaseResponse) response);
        }
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        if (requestCode == WebServiceManager.REQUEST_CODE_GET_ALL_PLAN__DETAILS) {
            Utils.showToast(activity, ((GetAllPlansEntity) response).getMsg());
        } else if (requestCode == WebServiceManager.REQUEST_CODE_BUY_PLAN) {
            Utils.showToast(activity, ((BaseResponse) response).getMsg());
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
}
