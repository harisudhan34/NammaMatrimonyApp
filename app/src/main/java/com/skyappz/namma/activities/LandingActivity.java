package com.skyappz.namma.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skyappz.namma.R;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import java.util.ArrayList;
import java.util.List;


public class LandingActivity extends AppCompatActivity implements WebServiceListener, View.OnClickListener {
    Preferences preferences;
    WebServiceManager webServiceManager;
    User user;
    private static final int PERMISSION_REQUESTS = 1;
    AppCompatButton btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.getFacebokKeyHash(this);
        setContentView(R.layout.activity_landing);
        initViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToAuthenticationPage("login");
                finish();
            }
        }, 2*500); // wait for 5 seconds

       /* KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (km.isKeyguardSecure()) {

            Intent i = km.createConfirmDeviceCredentialIntent("Authentication required", "password");
            startActivityForResult(i, CODE_AUTHENTICATION_VERIFICATION);
        } else
            Toast.makeText(this, "No any security setup done by user(pattern or password or pin or fingerprint", Toast.LENGTH_SHORT).show();
*/
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
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


    private void moveToMainPage() {
        finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    private void moveToAuthenticationPage(String purpose) {
        //finish();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra("purpose", purpose);
        startActivity(intent);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                moveToAuthenticationPage("login");
                break;
            case R.id.btnSignUp:
                moveToAuthenticationPage("signup");
                break;
            default:
                break;
        }

    }

}
