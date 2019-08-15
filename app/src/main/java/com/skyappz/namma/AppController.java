package com.skyappz.namma;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public static SharedPreferences sharedpreferences;
    public static String Shared_pref = "namma";
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;
    private static Context sContext;
    public static String USER_ID = "user_id", PASSWORD = "password", EMAIL = "email", PHONE_NUMBER = "phone_number",
            FIRSTNAME = "firstname", LASTNAME = "lastname", LOGIN_TYPE = "login_type", USER_TYPE = "user_type", DEVICE_ID = "device_id", IMAGE_URL = "image_url",
            MESSAGE_COUNT = "message_count",SIGNUPNAME="signupname",SIGNUPEMAIL="signupemail",SIGNUPPWD="signupPWd",SIGNUPHONE="signupphone",SIGNUPPROFILECREETAE="signupprofilecreate"
            ,S_USERID="Suserid",S_SIGNUPPOS="PROFILEPOS",DOB="DOB",RELIGION="RELIGION",MOTHER_LANGUVSGE="LANGUVAGE",GENDER="GENDER",AGE="AGE";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /*public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }*/

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Runtime.getRuntime().gc();
    }

    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static void set_userid(Context context, String userid) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER_ID, userid);
        editor.apply();
    }


    public static String get_userid(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(USER_ID, null);
    }
    public static void set_signupname(Context context, String signupname) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SIGNUPNAME, signupname);
        editor.apply();
    }


    public static String get_signupname(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SIGNUPNAME, null);
    }

    public static void set_signupemail(Context context, String signupemail) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SIGNUPEMAIL, signupemail);
        editor.apply();
    }


    public static String get_signupemail(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SIGNUPEMAIL, null);
    }

    public static void set_signuppwd(Context context, String signuppwd) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SIGNUPPWD, signuppwd);
        editor.apply();
    }


    public static String get_signuppwd(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SIGNUPHONE, null);
    }

    public static void set_signupphone(Context context, String signupphone) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SIGNUPHONE, signupphone);
        editor.apply();
    }


    public static String get_signuphone(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SIGNUPHONE, null);
    }
    public static void set_signupprofilecreate(Context context, String signupprofile) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(SIGNUPPROFILECREETAE, signupprofile);
        editor.apply();
    }


    public static String get_signupprofilevetae(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(SIGNUPPROFILECREETAE, null);
    }
    public static void set_signupprofilecreatepos(Context context, int signupprofilePos) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(S_SIGNUPPOS, signupprofilePos);
        editor.apply();
    }


    public static Integer get_signupprofilevetaepos(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getInt(S_SIGNUPPOS, 0);
    }

    public static void set_dob(Context context, String dob) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(DOB, dob);
        editor.apply();
    }


    public static String get_dob(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(DOB, null);
    }
    public static void set_religion(Context context, String religion) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(RELIGION, religion);
        editor.apply();
    }


    public static String get_religion(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(RELIGION, null);
    }

    public static void set_mothertongue(Context context, String religion) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MOTHER_LANGUVSGE, religion);
        editor.apply();
    }


    public static String get_mothertounge(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(MOTHER_LANGUVSGE, null);
    }

    public static void set_gender(Context context, String religion) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(GENDER, religion);
        editor.apply();
    }


    public static String get_gender(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(GENDER, null);
    }

    public static void set_age(Context context, String religion) {
        sharedpreferences = context.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(AGE, religion);
        editor.apply();
    }


    public static String get_age(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(Shared_pref, Context.MODE_PRIVATE);
        return sharedpreferences.getString(AGE, null);
    }



}
