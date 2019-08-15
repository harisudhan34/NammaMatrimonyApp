package com.skyappz.namma.listeners;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public interface OTPListener {
    public void onError(String e);

    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token);

    public void onOTPVerified(FirebaseUser user);

    public void onSMSReceived(String smsCode);
}
