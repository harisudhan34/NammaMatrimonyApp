package com.skyappz.namma.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.LoginResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.activities.MainDashboard;
import com.skyappz.namma.listeners.SocialLoginListener;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;


public class EmailFragment extends Fragment implements View.OnClickListener, SocialLoginListener, WebServiceListener {

    private View rootView;
    AppCompatEditText tetEmail,tetPassword;
    AppCompatButton btnNext;
    String email,password;;
    AppCompatTextView tvErrorMsg,tvForgetPassword,loginviaotp,register;
    private User user;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    Preferences preferences;
    private AppCompatTextView tvSignUp;
    String errorMsg;
    ProgressDialog dialog;
    boolean isValidated = true;
    DotProgressBar pbDot;
    GifImageView progress;
    private TextInputLayout input_layout_email, input_layout_password;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public EmailFragment() {
        // Required empty public constructor
    }

    public static EmailFragment newInstance() {
        EmailFragment fragment = new EmailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_email, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        input_layout_email = (TextInputLayout)rootView.findViewById(R.id.input_layout_email);
        input_layout_password = (TextInputLayout)rootView.findViewById(R.id.input_layout_password);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        progress=(GifImageView)rootView.findViewById(R.id.progress);
        tetEmail = rootView.findViewById(R.id.tetEmail);
        tetPassword = rootView.findViewById(R.id.tetpassword);
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg);
        pbDot = rootView.findViewById(R.id.pbDot);
        btnNext = rootView.findViewById(R.id.btnNext);
        tvSignUp = rootView.findViewById(R.id.tvSignUp);
        register = rootView.findViewById(R.id.register);
        tvForgetPassword = rootView.findViewById(R.id.tvForgetPassword);
        loginviaotp = rootView.findViewById(R.id.loginviaotp);
        tvForgetPassword.setOnClickListener(this);
        loginviaotp.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSignUp:
                moveToSignUpFragment();
                break;
            case R.id.register:
                moveToSignUpFragment();
                break;

            case R.id.btnNext:
                progress.setVisibility(View.VISIBLE);
//                dialog = new ProgressDialog(getActivity());
//                dialog.setMessage("please wait.");
//                dialog.setCancelable(false);
//                dialog.show();
                user = getUserDataFromInput();
                ((AuthenticationActivity) mActivity).setUser(user);
                if (Utils.isConnected(mActivity)) {
                    if (isValidated()) {
                        tvErrorMsg.setText("");
                        login(user);
                    } else {
                        tvErrorMsg.setText(errorMsg);
                    }
                } else {
                    Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
                }
                break;
            case R.id.tvForgetPassword:
//                Log.e("emaill",user.getEmail());
                moveToForgotPasswordPage();
                break;
            case R.id.loginviaotp:
                moveToOTPPage();
                break;
            default:
                break;
        }
    }
    private void moveToOTPPage() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_PHONENUMBER_FRAGMENT, null);
    }
    private void moveToForgotPasswordPage() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_FORGET_PASSWORD_MOBILE, null);
    }

    private void moveToSignUpFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_SIGNUP_FRAGMENT, null);
    }

    private void movePasswordFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_PASSWORD_FRAGMENT, null);
    }

    private void moveToForgetPasswordFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_FORGET_PASSWORD_FRAGMENT, null);
    }

    private void login(User user) {
        webServiceManager.login(user, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private User getUserDataFromInput() {
        email = tetEmail.getText().toString();
        password =tetPassword.getText().toString();
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }

    private User getUserDataFromInput(GoogleSignInAccount account) {
        email = account.getEmail();
        user = new User();
        user.setEmail(email);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }

    private User getUserDataFromInput(JSONObject object) {
        try {
            email = object.getString("email");
            user = new User();
            user.setEmail(email);
            user.setDevice_id(preferences.getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
            setHasOptionsMenu(true);
        } else {
            setHasOptionsMenu(false);
        }
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        LoginResponse loginResponse = (LoginResponse) response;
        user = loginResponse.getUser();
        Log.e("useridd",user.getUser_id());
//        preferences.setLoginned(true);
        user.setPassword(password);
        preferences.updateUser(user);
        Utils.showToast(mActivity, "Login success");
        moveToHomePage(user.getUser_id());
    }
    private void moveToHomePage(String userid) {
        Intent intent = new Intent(mActivity, MainDashboard.class);
        AppController.set_userid(getActivity(),userid);
        intent.putExtra("userid",userid);
        mActivity.startActivity(intent);
    }


    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        if (requestCode == WebServiceManager.REQUEST_CODE_LOGIN) {
            if (response != null) {
                LoginResponse loginResponse = (LoginResponse) response;
                if (loginResponse.getUser() != null) {
                    Utils.showAlert(mActivity, loginResponse.getMsg());
                } else {
                    Utils.showAlert(mActivity, loginResponse.getMsg());
                }
            } else {
                Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.internal_server_error));
            }
        }
    }

    private void showSignUpDialog() {

    }

    @Override
    public void isNetworkAvailable(boolean flag) {

    }

    @Override
    public void onProgressStart() {
        //pbDot.setVisibility(View.VISIBLE);
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
        if (email .equals("")) {
            progress.setVisibility(View.GONE);
            isValidated = false;
            errorMsg = "Email ID should not be empty";
            return isValidated;
        }else if (password.equals("")) {
            progress.setVisibility(View.GONE);
            isValidated = false;
            errorMsg = "password  should not be empty";
            return isValidated;
        }
        return isValidated;
    }

    @Override
    public void onGoogleLoginComplete(GoogleSignInAccount account) {

        if (account != null) {
            user = getUserDataFromInput(account);
            webServiceManager.login(user, this);
        } else {
            Utils.showAlert(mActivity, "Google login is failed. Its under development");
        }

    }

    @Override
    public void onFacebookLoginComplete(boolean success, JSONObject response) {

        if (success) {
            user = getUserDataFromInput(response);
            webServiceManager.login(user, this);
        } else {
            if (response != null) {
                Utils.showAlert(mActivity, response.toString());
            }
        }
    }
}
