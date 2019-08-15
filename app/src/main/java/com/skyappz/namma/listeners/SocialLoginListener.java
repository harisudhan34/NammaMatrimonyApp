package com.skyappz.namma.listeners;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

/**
 * Created by Surendar.V on 10/6/2017.
 */

public interface SocialLoginListener {
    public void onGoogleLoginComplete(GoogleSignInAccount googleSignInAccount);

    public void onFacebookLoginComplete(boolean success, JSONObject response);
}
