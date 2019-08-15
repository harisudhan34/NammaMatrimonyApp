package com.skyappz.namma.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.ResponseEntities.SignUpResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.model.OTPRequest;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;


public class OTPFragment extends Fragment implements View.OnClickListener, WebServiceListener {

    private View rootView;
    AppCompatEditText tetOTP;
    AppCompatButton btnSubmit;
    String otp;
    AppCompatTextView tvErrorMsg;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    Preferences preferences;
    private AppCompatTextView otpMessage;
    String errorMsg;
    boolean isValidated = true;
    DotProgressBar pbDot;
    User user=new User();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    OTPRequest otpRequest;

    public OTPFragment() {
        // Required empty public constructor
    }

    public static OTPFragment newInstance() {
        OTPFragment fragment = new OTPFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
        otpRequest = (OTPRequest) getArguments().getSerializable("otpRequest");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_otp, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        otpMessage = rootView.findViewById(R.id.otpMessage);
        tetOTP = rootView.findViewById(R.id.tetOTP);
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg);
        pbDot = rootView.findViewById(R.id.pbDot);
        btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                otp = tetOTP.getText().toString();
                otpRequest.setOTP(otp);
                if (isValidated()) {
                    if (!Utils.isEmpty(otp)) {
                        webServiceManager.verifyOTP(otpRequest, this);
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setHasOptionsMenu(true);
        } else {
            setHasOptionsMenu(false);
        }
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {

        if (requestCode == WebServiceManager.REQUEST_CODE_SIGNUP) {
            SignUpResponse loginResponse = (SignUpResponse) response;
            user = loginResponse.getUser();
            Log.e("useridd",user.getUser_id());
            moveToHomePage(user.getUser_id());
//            login(user);
        }
        if (requestCode == WebServiceManager.REQUEST_CODE_VERIFY_OTP) {
            BaseResponse otpResponse = (BaseResponse) response;
            preferences.setLoginned(true);
            if (otpResponse.getStatus().equalsIgnoreCase("true")) {
                Utils.showToast(mActivity, "OTP Verified");
                moveToNextPage(otpRequest);
            }
    }

}
    private void moveToHomePage(String userid) {
        Intent intent = new Intent(mActivity, HomeActivity.class);
        intent.putExtra("userid",userid);
        mActivity.startActivity(intent);
    }

    private void signUp() {
        Log.e("aa","signup1");
//        webServiceManager.signUp(null, this);
    }
    private void moveToNextPage(OTPRequest otpRequest) {
        if (otpRequest.getType().equalsIgnoreCase(OTPRequest.SIGNUP)) {
            signUp();
        } else if (otpRequest.getType().equalsIgnoreCase(OTPRequest.SIGNIN)) {
        } else if (otpRequest.getType().equalsIgnoreCase(OTPRequest.PASSWORD_UPDATE)) {
            ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_EMAIL_FRAGMENT, null);
        }
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        if (requestCode == WebServiceManager.REQUEST_CODE_VERIFY_OTP) {
            if (response != null) {
                BaseResponse otpResponse = (BaseResponse) response;
                Utils.showAlert(mActivity, otpResponse.getMsg());
            } else {
                Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.internal_server_error));
            }
        }
        if (requestCode == WebServiceManager.REQUEST_CODE_SIGNUP) {
            if (response != null) {
                SignUpResponse loginResponse = (SignUpResponse) response;
                Utils.showAlert(mActivity, loginResponse.getMsg());
            } else {
                Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.internal_server_error));
            }
        }
    }

    @Override
    public void isNetworkAvailable(boolean flag) {

    }

    @Override
    public void onProgressStart() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onProgressEnd() {
        //pbDot.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private boolean isValidated() {
        isValidated = true;
        if (otp != null && otp.length() > 0) {

        } else {
            isValidated = false;
            errorMsg = "OTP should not be empty";
            return isValidated;
        }
        return isValidated;
    }
}
