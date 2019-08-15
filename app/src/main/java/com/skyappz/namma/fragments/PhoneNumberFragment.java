package com.skyappz.namma.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.skyappz.namma.R;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.listeners.SocialLoginListener;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import org.json.JSONObject;


public class PhoneNumberFragment extends Fragment implements View.OnClickListener, SocialLoginListener, WebServiceListener {
    private User user;
    private Activity mActivity;
    private View rootView;
    WebServiceManager webServiceManager;
    AppCompatTextView phonenumber_ed;
    Preferences preferences;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    DotProgressBar pbDot;
    AppCompatButton btnNext;
    String mobile;
    private AppCompatTextView tvSignUp;
    AppCompatTextView tvErrorMsg;
    String errorMsg;
    boolean isValidated = true;
    public PhoneNumberFragment() {
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
    public static PhoneNumberFragment newInstance() {
        PhoneNumberFragment fragment = new PhoneNumberFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_phone_number, container, false);
        initViews(rootView);
        return rootView;
    }
    private void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        phonenumber_ed = rootView.findViewById(R.id.tetEmail);
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg);
        pbDot = rootView.findViewById(R.id.pbDot);
        btnNext = rootView.findViewById(R.id.btnNext);
        tvSignUp = rootView.findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSignUp:
                moveToSignUpFragment();
                break;

            case R.id.btnNext:
                user = getUserDataFromInput();
                ((AuthenticationActivity) mActivity).setUser(user);
                if (Utils.isConnected(mActivity)) {
                    if (isValidated()) {
                        tvErrorMsg.setText("");
//                        movePasswordFragment();
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
                errorMsg = "Mobile Number should not be 10 digit";
            }else {
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
}
