package com.skyappz.namma.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.listeners.SocialLoginListener;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;


public class ForgotFragment extends Fragment implements View.OnClickListener, SocialLoginListener, WebServiceListener {
    private User user;
    private Activity mActivity;
    private View rootView;
    WebServiceManager webServiceManager;
    AppCompatEditText phonenumber_ed,signin_otp_ed;
    Preferences preferences;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    AppCompatButton btnNext,btn_otp_signin;
    String mobile,s_otp;
    AppCompatTextView tvErrorMsg;
    LinearLayout otp_layout,mobile_layout;
    String errorMsg;
    boolean isValidated = true;
    GifImageView progress;
    public ForgotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
//        user = ((AuthenticationActivity) mActivity).getUser();
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
    }
    public static ForgotFragment newInstance() {
        ForgotFragment fragment = new ForgotFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.forgot_fragment, container, false);
        initViews(rootView);
        return rootView;
    }
    private void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        otp_layout=(LinearLayout)rootView.findViewById(R.id.otp_layout);
        mobile_layout=(LinearLayout)rootView.findViewById(R.id.mobile_layout);
        mSwipeRefreshLayout.setEnabled(false);
        phonenumber_ed = rootView.findViewById(R.id.temobile);
        signin_otp_ed = rootView.findViewById(R.id.signin_otp_ed);
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        progress=(GifImageView)rootView.findViewById(R.id.progress);
        btn_otp_signin = rootView.findViewById(R.id.btn_otp_signin);
        btn_otp_signin.setOnClickListener(this);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_otp_signin:
                progress.setVisibility(View.VISIBLE);
                s_otp=signin_otp_ed.getText().toString();
                if (s_otp.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showToast(getActivity(),"Enter OTP");
                }else {
                    verifyOTP();
                }

                break;
            case R.id.btnNext:
                progress.setVisibility(View.VISIBLE);
                user = getUserDataFromInput();
                ((AuthenticationActivity) mActivity).setUser(user);
                if (Utils.isConnected(mActivity)) {
                    if (isValidated()) {
                        tvErrorMsg.setText("");
                        sendOTP("updatepassword");
                    } else {
                        tvErrorMsg.setText(errorMsg);
                    }
                } else {
                    Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
                }
                break;
            default:
                break;
        }
    }
    private void verifyOTP() {

        webServiceManager.verifyOTP(mobile, s_otp,"updatepassword", this);
    }

    private void sendOTP(String type) {

        webServiceManager.sendOTP(user.getMobile_number(), type, this);
    }


    private User getUserDataFromInput() {
        mobile = phonenumber_ed.getText().toString();
        user = new User();
        user.setMobile_number(mobile);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }

    private boolean isValidated() {
        isValidated = true;
        if (mobile != null && mobile.length() > 0) {

        } else {
            isValidated = false;
            if (mobile.length() < 10){
                progress.setVisibility(View.GONE);
                errorMsg = "Mobile Number should not be 10 digit";
            }else {
                progress.setVisibility(View.GONE);
                errorMsg = "Mobile Number should not be empty";
            }

            return isValidated;
        }

        return isValidated;
    }

    private void moveToSignUpFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_SIGNUP_FRAGMENT, null);
    }

    @Override
    public void onGoogleLoginComplete(GoogleSignInAccount googleSignInAccount) {

    }

    @Override
    public void onFacebookLoginComplete(boolean success, JSONObject response) {

    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
         if (requestCode == WebServiceManager.REQUEST_CODE_SEND_OTP) {
          mobile_layout.setVisibility(View.GONE);
          otp_layout.setVisibility(View.VISIBLE);
        }
         else if (requestCode == WebServiceManager.REQUEST_CODE_VERIFY_OTP) {
             BaseResponse otpResponse = (BaseResponse) response;
             AppController.set_userid(getActivity(),otpResponse.getUser_id());

             ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_FORGET_PASSWORD_FRAGMENT, null);
         }
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
         if (requestCode == WebServiceManager.REQUEST_CODE_SEND_OTP) {
            BaseResponse otpResponse = (BaseResponse) response;
            Utils.showAlert(mActivity, otpResponse.getMsg());
        }
        if (requestCode == WebServiceManager.REQUEST_CODE_VERIFY_OTP) {
            BaseResponse otpResponse = (BaseResponse) response;
            Utils.showAlert(mActivity, otpResponse.getMsg());
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
