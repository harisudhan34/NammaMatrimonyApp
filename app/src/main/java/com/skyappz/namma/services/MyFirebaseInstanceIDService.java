package com.skyappz.namma.services;

import android.util.Log;

import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.webservice.WebServiceManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    Preferences preferences;
    private WebServiceManager webServiceManager;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.d("Surendar", "OnTokenRefresh");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        if (refreshedToken != null && refreshedToken.length() > 0) {
            sendRegistrationToServer(refreshedToken);
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(AppConstants.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
       /* if (preferences.isLoginned()) {
            String userID = preferences.getUsers().getUser_id();
            webServiceManager = new WebServiceManager(getApplicationContext());
            Looper.prepare();
            webServiceManager.storeDeviceID(userID, token, new WebServiceListener() {
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
            });

        }*/
    }

    private void storeRegIdInPref(String token) {
        preferences = new Preferences(getApplicationContext());
        preferences.setDeviceId(token);
    }
}