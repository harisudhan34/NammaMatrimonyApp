package com.skyappz.namma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.skyappz.namma.R;
import com.skyappz.namma.fragments.EmailFragment;
import com.skyappz.namma.fragments.ForgetPasswordFragment;
import com.skyappz.namma.fragments.ForgotFragment;
import com.skyappz.namma.fragments.OTPFragment;
import com.skyappz.namma.fragments.PasswordFragment;
import com.skyappz.namma.fragments.PhoneNumberFragment;
import com.skyappz.namma.fragments.SignUpFragment;
import com.skyappz.namma.listeners.SocialLoginListener;
import com.skyappz.namma.model.OTPRequest;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "AuthenticationActivity";
    private static final int RC_SIGN_IN = 9001;


    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mLoginFacebookCallback;
    private LoginManager loginManager;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private SocialLoginListener socialLoginListener;

    public static final int INDEX_EMAIL_FRAGMENT = 2;
    public static final int INDEX_PASSWORD_FRAGMENT = 3;
    public static final int INDEX_SIGNUP_FRAGMENT = 4;
    public static final int INDEX_FORGET_PASSWORD_FRAGMENT = 5;
    public static final int INDEX_OTP_FRAGMENT = 6;
    public static final int INDEX_PHONENUMBER_FRAGMENT = 7;
    public static final int INDEX_FORGET_PASSWORD_MOBILE =8;
    public static final int INDEX_RESET_PASSWORD_FRAGMENT = 100;

    HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
    private int lastIndex;

    boolean isLoginned = false;
    Preferences preferences;
    private Fragment currentFragment;
    String purpose;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        preferences = new Preferences(this);
        purpose = getIntent().getStringExtra("purpose");
        initViews();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.addOnBackStackChangedListener(this);
        initialiseGoogleClient();
        initialiseFacebookLogin();
        if (purpose.equalsIgnoreCase("login")) {
            setFragment(INDEX_EMAIL_FRAGMENT, null);
        } else if (purpose.equalsIgnoreCase("signup")) {
            setFragment(INDEX_SIGNUP_FRAGMENT, null);
        }
    }


    private void initialiseFacebookLogin() {
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

        mLoginFacebookCallback =
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity", response.toString());

                                        try {
                                            //and fill them here unliked so.
                                            String str_facebook_id = object.getString("id");
                                            String str_email_id = object.getString("email");
                                            String str_name = object.getString("name");
                                            socialLoginListener.onFacebookLoginComplete(true, object);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            if (object != null) {
                                                socialLoginListener.onFacebookLoginComplete(false, object);
                                            }
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("", "");
                        socialLoginListener.onFacebookLoginComplete(false, null);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("", "");
                        socialLoginListener.onFacebookLoginComplete(false, null);
                    }
                }

        ;


        loginManager.registerCallback(callbackManager, mLoginFacebookCallback);
    }

    public void loginWithFacebook(SocialLoginListener socialLoginListener) {
        logoutWithFacebook();
        this.socialLoginListener = socialLoginListener;
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken == null)
            loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

    }

    public void logoutWithFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager loginManager = LoginManager.getInstance();
        if (loginManager != null)
            loginManager.logOut();
    }

    private void initialiseGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initViews() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("tool title");
        isLoginned = preferences.isLoginned();
    }

    /*@Override
    public void onBackPressed() {

        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof SignUpFragment) {
            setFragment(INDEX_BASIC_DETAILS, null);
        } else {
            finish();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void setFragment(int index, Object data) {
        //if (lastIndex != index) {
        lastIndex = index;
        Fragment newFragment = null;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        String fragmentTag = "";
        switch (index) {

            case INDEX_EMAIL_FRAGMENT:
//                getSupportActionBar().setTitle("Login");
                fragmentTag = "EmailFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EmailFragment.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_PASSWORD_FRAGMENT:
//                getSupportActionBar().setTitle("Password");
                fragmentTag = "PasswordFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = PasswordFragment.newInstance();
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_FORGET_PASSWORD_FRAGMENT:
//                getSupportActionBar().setTitle("Password");
                fragmentTag = "ForgetPasswordFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = ForgetPasswordFragment.newInstance();
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_SIGNUP_FRAGMENT:
//                getSupportActionBar().setTitle("Signup");
                fragmentTag = "SignUpFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
//                        fragmentTransaction.commit();
                } else {
                    newFragment = SignUpFragment.newInstance();
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_OTP_FRAGMENT:
//                getSupportActionBar().setTitle("OTP");
                fragmentTag = "OTPFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                } else {
                    newFragment = OTPFragment.newInstance();
                    if (data != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("otpRequest", (OTPRequest) data);
                        newFragment.setArguments(bundle);
                    }
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

                case INDEX_PHONENUMBER_FRAGMENT:
//                getSupportActionBar().setTitle("Phonenumber");
                fragmentTag = "PHONENUMBERFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                } else {
                    newFragment = PhoneNumberFragment.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_FORGET_PASSWORD_MOBILE:
//                getSupportActionBar().setTitle("Phonenumber");
                fragmentTag = "ForgotFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    //Fragment currentFragment = getCurrentFragment();
                    //fragmentTransaction.hide(currentFragment);
                    fragmentTransaction.add(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                } else {
                    newFragment = ForgotFragment.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            default:
                break;
        }
        /*} else {
            Utils.showToast(this, "same index");
        }*/
    }

    private Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.flFramentContainer);
    }

    private void logout() {
        User user = new User();
        preferences.updateUser(user);
        preferences.setLoginned(false);
        setFragment(INDEX_EMAIL_FRAGMENT, null);
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onClick(View v) {

    }

    public void changeMenuOnLogin(boolean isLoginned) {

    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (socialLoginListener != null) {
                socialLoginListener.onGoogleLoginComplete(acct);
                updateUI(true);
            }
        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean b) {
    }

    public void loginWithGoogle(SocialLoginListener socialLoginListener) {
        this.socialLoginListener = socialLoginListener;
        signOutFromGoogle();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOutFromGoogle() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {

                            }
                        });
            }
        }
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }
}
