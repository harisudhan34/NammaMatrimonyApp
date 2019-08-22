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

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTexImage2D;


public class ForgetPasswordFragment extends Fragment implements View.OnClickListener, WebServiceListener {

    private View rootView;
    AppCompatEditText tetPassword, tetConfirmPassword;
    AppCompatTextView tvErrorMsg;
    AppCompatButton btnUpdate;
    String password, confirmPassword;
    private User user;
    GifImageView progress;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    Preferences preferences;
    String errorMsg;
    boolean isValidated = true;
    DotProgressBar pbDot;
    String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        rootView = inflater.inflate(R.layout.fragment_forget_password, container, false);
        initViews(rootView);
//        Log.e("email",user.getEmail());
        return rootView;
    }

    private void initViews(View rootView) {
        progress=(GifImageView)rootView.findViewById(R.id.progress);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        tetPassword = rootView.findViewById(R.id.tetPassword);
        tetConfirmPassword = rootView.findViewById(R.id.tetConfirmPassword);
        tvErrorMsg = rootView.findViewById(R.id.tvErrorMsg);
        pbDot = rootView.findViewById(R.id.pbDot);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                progress.setVisibility(View.VISIBLE);
                user = getUserDataFromInput();
                if (Utils.isConnected(mActivity)) {
                    if (isValidated()) {

                        webServiceManager.updatePassword(AppController.get_userid(getActivity()),user, this);
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

    private void moveToEmailFragment() {
        ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_EMAIL_FRAGMENT, null);
        getActivity().finish();
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
        confirmPassword = tetConfirmPassword.getText().toString();
        user.setPassword(password);

        user.setDevice_id(preferences.getDeviceId());
        return user;
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
        progress.setVisibility(View.GONE);
        moveToEmailFragment();
        Utils.showToast(mActivity, "Password Updated Successfully!");
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        if (requestCode == WebServiceManager.REQUEST_CODE_LOGIN) {
            if (response != null) {
                BaseResponse forgetPasswordResponse = (BaseResponse) response;
                Utils.showAlert(mActivity, forgetPasswordResponse.getMsg());
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
        if (password != null && password.length() > 0) {

        } else {
            progress.setVisibility(View.GONE);
            isValidated = false;
            errorMsg = "Password should not be empty";
            return isValidated;
        }
        if (!password.matches(PASSWORD_PATTERN)){
            errorMsg = "";
            progress.setVisibility(View.GONE);
            Utils.showAlert(mActivity,"Password must contain min 6 character, mix of upper and lower case letters as well as digits and one special charecter");
            return false;
        }
        if (confirmPassword != null && confirmPassword.length() > 0) {

        } else {
            progress.setVisibility(View.GONE);
            isValidated = false;
            errorMsg = "Confirm Password should not be empty";
            return isValidated;
        }
        if (!confirmPassword.matches(PASSWORD_PATTERN)){
            errorMsg = "";
            progress.setVisibility(View.GONE);
            Utils.showAlert(mActivity,"Password must contain min 6 character, mix of upper and lower case letters as well as digits and one special charecter");
            return false;
        }
        if (password.equalsIgnoreCase(confirmPassword)) {

        } else {
            isValidated = false;
            progress.setVisibility(View.GONE);
            errorMsg = "Password and Confirm Password must be same";
            return isValidated;
        }
        return isValidated;
    }
}
