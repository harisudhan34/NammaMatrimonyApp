package com.skyappz.namma.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.LoginResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.activities.MainDashboard;
import com.skyappz.namma.editprofile.EditProfileActivity;
import com.skyappz.namma.listeners.SocialLoginListener;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import org.json.JSONException;
import org.json.JSONObject;


public class PasswordFragment extends Fragment implements View.OnClickListener, SocialLoginListener, WebServiceListener {

    private View rootView;
    AppCompatEditText tetPassword;
    AppCompatTextView tvForgetPassword;
    AppCompatButton btnLogin;
    String password;
    private User user;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    Preferences preferences;
    String errorMsg;
    boolean isValidated = true;
    DotProgressBar pbDot;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    AppCompatRadioButton rbOTP,rbpassword;

    public PasswordFragment() {
        // Required empty public constructor
    }

    public static PasswordFragment newInstance() {
        PasswordFragment fragment = new PasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        user = ((AuthenticationActivity) mActivity).getUser();
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_password, container, false);
        initViews(rootView);

        return rootView;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.login_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_signup:
                moveToSignUpFragment();
                return true;
            default:
                return false;
        }
    }*/

    /*private void moveToSignUpFragment() {
        ((AuthenticationActivity) mActivity).setFragment(MainActivity.INDEX_SIGNUP_FRAGMENT, null);
    }*/

    private void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        rbpassword = rootView.findViewById(R.id.rbPassword);
        rbpassword.setChecked(true);
        rbOTP = rootView.findViewById(R.id.rbOTP);
        rbOTP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    moveToOTPPage();
                }
            }
        });
        tetPassword = rootView.findViewById(R.id.tetPassword);
        tvForgetPassword = rootView.findViewById(R.id.tvForgetPassword);
        tvForgetPassword.setOnClickListener(this);
        pbDot = rootView.findViewById(R.id.pbDot);
        btnLogin = rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                user = getUserDataFromInput();
                if (Utils.isConnected(mActivity)) {
                    if (isValidated()) {
                        login(user);
                    } else {
                        Utils.showAlert(mActivity, errorMsg);
                    }
                } else {
                    Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
                }
                break;
            case R.id.tvForgetPassword:
                Log.e("emaill",user.getEmail());
                moveToForgotPasswordPage();
                break;
            default:
                break;
        }
    }

    private void moveToForgotPasswordPage() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_FORGET_PASSWORD_FRAGMENT, null);
    }

    private void moveToOTPPage() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_FORGET_PASSWORD_FRAGMENT, null);
    }

    /*private void onPasswordVisibiltyChanged(boolean isPasswordShown) {
        if (!isPasswordShown) {
            tetPassword.setTransformationMethod(new PasswordTransformationMethod());
            ivEyeIcon.setImageResource(R.drawable.eye);
        } else {
            tetPassword.setTransformationMethod(null);
            ivEyeIcon.setImageResource(R.drawable.eye_hide);
        }
        tetPassword.setSelection(tetPassword.length());
    }*/

    private void moveToSignUpFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_SIGNUP_FRAGMENT, null);
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
        password = tetPassword.getText().toString();
        user.setPassword(password);
        return user;
    }

    private User getUserDataFromInput(GoogleSignInAccount account) {
        password = account.getEmail();
        password = "";
        user = new User();
        user.setPassword(password);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }

    private User getUserDataFromInput(JSONObject object) {
        try {
            password = object.getString("email");
            password = "";
            user = new User();
            user.setPassword(password);
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
        LoginResponse loginResponse = (LoginResponse) response;
        user = loginResponse.getUser();
        Log.e("useridd",user.getUser_id());
//        preferences.setLoginned(true);
        user.setPassword(password);
        preferences.updateUser(user);
        moveToHomePage(user.getUser_id());
        //((AuthenticationActivity) mActivity).changeMenuOnLogin(true);
    }

    private void moveToHomePage(String userid) {
//        Log.e("resighter",String.valueOf(preferences.isRegistered()));
//        if (preferences.isRegistered()){
//            Intent intent = new Intent(mActivity, MainDashboard.class);
//            preferences.setuser_id(userid);
//            intent.putExtra("userid",userid);
//            mActivity.startActivity(intent);
//        }else {
            Intent intent = new Intent(mActivity, MainDashboard.class);
//            preferences.setuser_id(userid);
        AppController.set_userid(getActivity(),userid);
            intent.putExtra("userid",userid);
            mActivity.startActivity(intent);
       // }

    }


    private void moveToUpdateUserPage() {
        Intent intent = new Intent(mActivity, EditProfileActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
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
      /*  if (userType != null && (userType == User.TYPE_CUSTOMER || userType == User.TYPE_ADMIN)) {

        } else {
            isValidated = false;
            errorMsg = "Please select the user type";
        }*/
        if (password != null && password.length() > 0) {

        } else {
            isValidated = false;
            errorMsg = "Email ID should not be empty";
            return isValidated;
        }
        if (password != null && password.length() > 0) {

        } else {
            isValidated = false;
            errorMsg = "Password should not be empty";
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
