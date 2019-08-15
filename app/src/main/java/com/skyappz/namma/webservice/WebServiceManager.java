package com.skyappz.namma.webservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.ResponseEntities.GetAllPlansEntity;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.ResponseEntities.LoginResponse;
import com.skyappz.namma.ResponseEntities.Setpartner;
import com.skyappz.namma.ResponseEntities.SignUpResponse;
import com.skyappz.namma.ResponseEntities.StateListEntity;
import com.skyappz.namma.ResponseEntities.UserListEntity;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.model.BuyPlan;
import com.skyappz.namma.model.OTPRequest;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WebServiceManager {


    //3D:54:BF:04:B9:C2:8D:B8:DF:73:87:7A:17:60:E9:37:5B:9D:69:94 production sha1

    //9B:2D:27:86:70:B9:CD:60:9A:D7:9E:2D:01:2B:4B:81:EB:93:DA:29

    private final ProgressDialog pDialog;
    Context context;
    /*Request codes*/
    public static int REQUEST_CODE_LOGIN = 1001;
    public static int REQUEST_CODE_SIGNUP = 1002;
    public static int REQUEST_CODE_SEND_OTP = 1003;
    public static int REQUEST_CODE_VERIFY_OTP = 1004;
    public static int REQUEST_CODE_UPDATE_PASSWORD = 1005;
    public static int REQUEST_CODE_USER_DETAILS = 1006;
    public static int REQUEST_CODE_GET_ALL_PLAN__DETAILS = 1007;
    public static int REQUEST_CODE_BUY_PLAN = 1008;
    public static int REQUEST_CODE_UPDATE_USER = 1009;
    public static int REQUEST_CODE_GET_TODAY_MATCHES = 1010;
    public static int REQUEST_CODE_GET_TODAY_MATCHES_recommend = 1013;
    public static int REQUEST_CODE_GET_TODAY_MATCHES_perimium = 1014;
    public static int REQUEST_CODE_GET_STATES = 1011;
    public static int REQUEST_CODE_GET_CASTE = 1012;
    /*URLs*/
    private static final String URL_LOGIN = "https://nammamatrimony.in/api/login.php?";
    private static final String URL_SIGNUP = "nammamatrimony.in/api/signup.php";
    private static final String URL_SEND_OTP = "nammamatrimony.in/api/sendotp.php";
    private static final String URL_VERIFY_OTP = "nammamatrimony.in/api/verifyotp.php";
    private static final String URL_UPDATE_PASSWORD = "nammamatrimony.in/api/update_password.php";
    private static final String URL_UPDATE_USER = "nammamatrimony.in/api/update.php";
    private static final String URL_STORE_DEVICE_ID = "http://silvermatrimony.com/api/user/updateDeviceId?";
    private static final String URL_GET_USER_DETAILS = "nammamatrimony.in/api/getuser.php?user_id=";
    private static final String URL_GET_PLAN_DETAILS = "nammamatrimony.in/api/allplan.php";
    private static final String URL_BUY_PLAN = "nammamatrimony.in/api/buyplan.php";
    private static final String URL_GET_TODAY_MATCHES = "nammamatrimony.in/api/todaymatches.php?user_id=";
    private static final String URL_GET_STATES = "nammamatrimony.in/api/getstate.php";
    private static final String URL_SET_PARTNER = "nammamatrimony.in/api/setpartner.php";
    private static final String URL_GET_CASTE = "nammamatrimony.in/api/getcaste.php";

    private Preferences preferences;

    public WebServiceManager(Context context) {
        this.context = context;
        pDialog = new ProgressDialog(this.context);
        preferences = new Preferences(context);
        //pDialog.setCancelable(false);
    }

    public void login(final User user, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "login";
        String url = URL_LOGIN + "email=" + user.getEmail() + "&password=" + user.getPassword();
        pDialog.setMessage("login...");
        //pDialog.show();
        Log.e("logg",url);
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        LoginResponse loginResponse = new LoginResponse();
                        try {
                            String res = response.toString();
                            Log.e("response--login",res);
                            loginResponse = new Gson().fromJson(res, LoginResponse.class);
                            if (loginResponse != null) {
                                if (loginResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_LOGIN, 0, loginResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                                }
                            } else {
                                loginResponse = new LoginResponse();
                                loginResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            loginResponse = new LoginResponse();
                            loginResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                        } catch (JsonParseException e) {
                            loginResponse = new LoginResponse();
                            loginResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                webServiceListener.onProgressEnd();
                NetworkResponse response = error.networkResponse;
                LoginResponse loginResponse = new LoginResponse();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        loginResponse = new Gson().fromJson(res, LoginResponse.class);
                        if (loginResponse != null && loginResponse.getMsg() != null) {
                            webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                        } else {
                            loginResponse = new LoginResponse();
                            loginResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                        }

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        loginResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        loginResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                    } catch (JsonParseException e) {
                        loginResponse = new LoginResponse();
                        loginResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                    }
                } else {
                    loginResponse.setMsg("Internal Server Error");
                    webServiceListener.onFailure(REQUEST_CODE_LOGIN, 0, loginResponse);
                }
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void signUp(final User user, final AppCompatImageView ivUser, final WebServiceListener webServiceListener) {
        // loading or check internet connection or something...
        // ... then
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "signUp";
        String url = URL_SIGNUP;
        pDialog.setMessage("Registering...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        SignUpResponse signUpResponse = new SignUpResponse();
                        try {
                            String res = new String(response.data);
                            Log.e("signup--response",res);
                            signUpResponse = new Gson().fromJson(res, SignUpResponse.class);
                            if (signUpResponse != null) {
                                if (signUpResponse.getStatus().equalsIgnoreCase("true")) {
                                    Log.e("stringg","sucess");
                                    webServiceListener.onSuccess(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                                } else {
                                    Log.e("stringg","failiure");
                                    webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                                }
                            } else {
                                signUpResponse = new SignUpResponse();
                                signUpResponse.setMsg("Internal Server Error");


                                webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            signUpResponse = new SignUpResponse();
                            signUpResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                        } catch (JsonParseException e) {
                            signUpResponse = new SignUpResponse();
                            signUpResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        SignUpResponse signUpResponse = new SignUpResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                signUpResponse = new Gson().fromJson(res, SignUpResponse.class);
                                if (signUpResponse != null && signUpResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                                } else {
                                    signUpResponse = new SignUpResponse();
                                    signUpResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                signUpResponse.setMsg("Internall Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                signUpResponse.setMsg("Internal Serverr Error");
                                webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                            } catch (JsonParseException e) {
                                signUpResponse = new SignUpResponse();
                                signUpResponse.setMsg("Internal Server Errorr");
                                webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                            }
                        } else {
                            signUpResponse.setMsg("Internal Server Errorr");
                            webServiceListener.onFailure(REQUEST_CODE_SIGNUP, 0, signUpResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("profile_for", user.getProfile_created_for());
                params.put("name", user.getName());
                params.put("email", user.getEmail());
//                params.put("name", user.getName());
                params.put("password", user.getPassword());
                params.put("mobile_number", user.getMobile_number());
                params.put("login_type","0");
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (ivUser != null) {
                    long imagename = System.currentTimeMillis();
                    Bitmap bitmap = ((BitmapDrawable) ivUser.getDrawable()).getBitmap();
                    params.put("user_image", new DataPart(imagename + ".png", Utils.getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void sendOTP(final String mobile_number, final String type, final WebServiceListener webServiceListener) {
        // loading or check internet connection or something...
        // ... then
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "sendOTP";
        String url = URL_SEND_OTP;
        pDialog.setMessage("Sending OTP...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        BaseResponse otpResponse = new BaseResponse();
                        try {
                            String res = new String(response.data);
                            otpResponse = new Gson().fromJson(res, BaseResponse.class);
                            if (otpResponse != null) {
                                if (otpResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_SEND_OTP, 0, otpResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, otpResponse);
                                }
                            } else {
                                otpResponse = new BaseResponse();
                                otpResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, otpResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            otpResponse = new BaseResponse();
                            otpResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, otpResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        BaseResponse baseResponse = new BaseResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                baseResponse = new Gson().fromJson(res, BaseResponse.class);
                                if (baseResponse != null && baseResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                                } else {
                                    baseResponse = new BaseResponse();
                                    baseResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                            } catch (JsonParseException e) {
                                baseResponse = new BaseResponse();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                            }
                        } else {
                            baseResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_SEND_OTP, 0, baseResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile_number",mobile_number);
                params.put("type", type);
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void verifyOTP(final OTPRequest otpRequest, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "verifyOTP";
        String url = URL_VERIFY_OTP;
        pDialog.setMessage("Verifying OTP...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        BaseResponse otpResponse = new BaseResponse();
                        try {
                            String res = new String(response.data);
                            otpResponse = new Gson().fromJson(res, BaseResponse.class);
                            if (otpResponse != null) {
                                if (otpResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_VERIFY_OTP, 0, otpResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, otpResponse);
                                }
                            } else {
                                otpResponse = new BaseResponse();
                                otpResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, otpResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            otpResponse = new BaseResponse();
                            otpResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, otpResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        BaseResponse baseResponse = new BaseResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                baseResponse = new Gson().fromJson(res, BaseResponse.class);
                                if (baseResponse != null && baseResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                                } else {
                                    baseResponse = new BaseResponse();
                                    baseResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                            } catch (JsonParseException e) {
                                baseResponse = new BaseResponse();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                            }
                        } else {
                            baseResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_VERIFY_OTP, 0, baseResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile_number", otpRequest.getMobile_number());
                params.put("type", otpRequest.getType());
                params.put("otp", otpRequest.getOTP());
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void updatePassword(final User user, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        Log.e("updatepwd",user.getEmail() + "," + user.getPassword());
        String tag_json_obj = "updatePassword";
        String url = URL_UPDATE_PASSWORD;
        pDialog.setMessage("Updating Password...");
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();

                        webServiceListener.onProgressEnd();
                        BaseResponse otpResponse = new BaseResponse();
                        try {
                            String res = new String(response.data);
                            Log.e("test update res",res);
                            otpResponse = new Gson().fromJson(res, BaseResponse.class);
                            Log.e("test update res",otpResponse.getStatus());
                            if (otpResponse != null) {
                                if (otpResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_UPDATE_PASSWORD, 0, otpResponse);
                                    Log.e("flag","true");
                                } else {
                                    Log.e("flag","false");
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, otpResponse);
                                }
                            } else {
                                otpResponse = new BaseResponse();
                                otpResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, otpResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            otpResponse = new BaseResponse();
                            otpResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, otpResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        BaseResponse baseResponse = new BaseResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                baseResponse = new Gson().fromJson(res, BaseResponse.class);
                                if (baseResponse != null && baseResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                                } else {
                                    baseResponse = new BaseResponse();
                                    baseResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                            } catch (JsonParseException e) {
                                baseResponse = new BaseResponse();
                                baseResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                            }
                        } else {
                            baseResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_PASSWORD, 0, baseResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void getUserDetails(final String user_id, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getUserDetails";
        String url = URL_GET_USER_DETAILS + user_id;
        pDialog.setMessage("Loading User Details...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        GetUserDetailsResponse getUserDetailsResponse = new GetUserDetailsResponse();
                        try {
                            String res = response.toString();
                            getUserDetailsResponse = new Gson().fromJson(res, GetUserDetailsResponse.class);
                            if (getUserDetailsResponse != null) {
                                if (getUserDetailsResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                                }
                            } else {
                                getUserDetailsResponse = new GetUserDetailsResponse();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            getUserDetailsResponse = new GetUserDetailsResponse();
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                        } catch (JsonParseException e) {
                            getUserDetailsResponse = new GetUserDetailsResponse();
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                webServiceListener.onProgressEnd();
                NetworkResponse response = error.networkResponse;
                GetUserDetailsResponse getUserDetailsResponse = new GetUserDetailsResponse();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        getUserDetailsResponse = new Gson().fromJson(res, GetUserDetailsResponse.class);
                        if (getUserDetailsResponse != null && getUserDetailsResponse.getMsg() != null) {
                            webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                        } else {
                            getUserDetailsResponse = new GetUserDetailsResponse();
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                        }

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        getUserDetailsResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        getUserDetailsResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                    } catch (JsonParseException e) {
                        getUserDetailsResponse = new GetUserDetailsResponse();
                        getUserDetailsResponse.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                    }
                } else {
                    getUserDetailsResponse.setMsg("Internal Server Error");
                    webServiceListener.onFailure(REQUEST_CODE_USER_DETAILS, 0, getUserDetailsResponse);
                }
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void getAllCaste(final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getAllCaste";
        String url = URL_GET_CASTE;
        pDialog.setMessage("Loading caste...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        GetAllPlansEntity getAllPlansEntity = new GetAllPlansEntity();
                        try {
                            String res = response.toString();
                            getAllPlansEntity = new Gson().fromJson(res, GetAllPlansEntity.class);
                            if (getAllPlansEntity != null) {
                                if (getAllPlansEntity.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_GET_CASTE, 0, getAllPlansEntity);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_GET_CASTE, 0, getAllPlansEntity);
                                }
                            } else {
                                getAllPlansEntity = new GetAllPlansEntity();
                                getAllPlansEntity.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_GET_CASTE, 0, getAllPlansEntity);
                            }
                        } catch (JsonSyntaxException e) {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_CASTE, 0, getAllPlansEntity);
                        } catch (JsonParseException e) {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_CASTE, 0, getAllPlansEntity);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                webServiceListener.onProgressEnd();
                NetworkResponse response = error.networkResponse;
                GetAllPlansEntity getAllPlansEntity = new GetAllPlansEntity();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        getAllPlansEntity = new Gson().fromJson(res, GetAllPlansEntity.class);
                        if (getAllPlansEntity != null && getAllPlansEntity.getMsg() != null) {
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        } else {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        }

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    } catch (JsonParseException e) {
                        getAllPlansEntity = new GetAllPlansEntity();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    }
                } else {
                    getAllPlansEntity.setMsg("Internal Server Error");
                    webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                }
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }


    public void getAllPlanDetails(final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getAllPlanDetails";
        String url = URL_GET_PLAN_DETAILS;
        pDialog.setMessage("Loading Plan Details...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        GetAllPlansEntity getAllPlansEntity = new GetAllPlansEntity();
                        try {
                            String res = response.toString();
                            getAllPlansEntity = new Gson().fromJson(res, GetAllPlansEntity.class);
                            if (getAllPlansEntity != null) {
                                if (getAllPlansEntity.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                                }
                            } else {
                                getAllPlansEntity = new GetAllPlansEntity();
                                getAllPlansEntity.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                            }
                        } catch (JsonSyntaxException e) {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        } catch (JsonParseException e) {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                webServiceListener.onProgressEnd();
                NetworkResponse response = error.networkResponse;
                GetAllPlansEntity getAllPlansEntity = new GetAllPlansEntity();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        getAllPlansEntity = new Gson().fromJson(res, GetAllPlansEntity.class);
                        if (getAllPlansEntity != null && getAllPlansEntity.getMsg() != null) {
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        } else {
                            getAllPlansEntity = new GetAllPlansEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                        }

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    } catch (JsonParseException e) {
                        getAllPlansEntity = new GetAllPlansEntity();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                    }
                } else {
                    getAllPlansEntity.setMsg("Internal Server Error");
                    webServiceListener.onFailure(REQUEST_CODE_GET_ALL_PLAN__DETAILS, 0, getAllPlansEntity);
                }
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void buyPlan(final BuyPlan buyPlan, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "buyPlan";
        String url = URL_BUY_PLAN;
        pDialog.setMessage("Purchasing Plan...");
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        BaseResponse buyPlanResponse = new BaseResponse();
                        try {
                            String res = new String(response.data);
                            buyPlanResponse = new Gson().fromJson(res, BaseResponse.class);
                            if (buyPlanResponse != null) {
                                if (buyPlanResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                                }
                            } else {
                                buyPlanResponse = new BaseResponse();
                                buyPlanResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            buyPlanResponse = new BaseResponse();
                            buyPlanResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        BaseResponse buyPlanResponse = new BaseResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                buyPlanResponse = new Gson().fromJson(res, BaseResponse.class);
                                if (buyPlanResponse != null && buyPlanResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                                } else {
                                    buyPlanResponse = new BaseResponse();
                                    buyPlanResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                buyPlanResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                buyPlanResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                            } catch (JsonParseException e) {
                                buyPlanResponse = new BaseResponse();
                                buyPlanResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                            }
                        } else {
                            buyPlanResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_BUY_PLAN, 0, buyPlanResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", buyPlan.getUser_id());
                params.put("plan_id", buyPlan.getPlan_id());
                params.put("transaction_id", buyPlan.getTransaction_id());
                params.put("paymentmode", buyPlan.getPaymentmode());
                params.put("activedate", buyPlan.getActivedate());
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void updateUserDetails(final HashMap<String, String> params, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "updateUserDetails";
        String url = URL_UPDATE_USER;
        pDialog.setMessage("Updating User Details...");
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        GetUserDetailsResponse getUserDetailsResponse = new GetUserDetailsResponse();
                        try {
                            String res = new String(response.data);
                            getUserDetailsResponse = new Gson().fromJson(res, GetUserDetailsResponse.class);
                            if (getUserDetailsResponse != null) {
                                if (getUserDetailsResponse.getStatus().equalsIgnoreCase("true")) {
                                    webServiceListener.onSuccess(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                }
                            } else {
                                getUserDetailsResponse = new GetUserDetailsResponse();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            }
                        } catch (JsonSyntaxException e) {
                            getUserDetailsResponse = new GetUserDetailsResponse();
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        GetUserDetailsResponse getUserDetailsResponse = new GetUserDetailsResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                getUserDetailsResponse = new Gson().fromJson(res, GetUserDetailsResponse.class);
                                if (getUserDetailsResponse != null && getUserDetailsResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                } else {
                                    getUserDetailsResponse = new GetUserDetailsResponse();
                                    getUserDetailsResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            } catch (JsonParseException e) {
                                getUserDetailsResponse = new GetUserDetailsResponse();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            }
                        } else {
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void setpartnerddetails(final HashMap<String, String> params, final WebServiceListener webServiceListener) {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "setUserDetails";
        String url = URL_SET_PARTNER;
        pDialog.setMessage("Updating partner Details...");
        webServiceListener.onProgressStart();

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        Setpartner setpartner = new Setpartner();
                        try {
                            String res = new String(response.data);
                            Log.e("setpartner -- res",res);
                            setpartner = new Gson().fromJson(res, Setpartner.class);
                            if (setpartner != null) {
                                if (setpartner.getStatus().equalsIgnoreCase("true")) {
                                    Log.e("result","sucessess");
                                    webServiceListener.onSuccess(REQUEST_CODE_UPDATE_USER, 0, setpartner);
                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, setpartner);
                                }
                            } else {
                                setpartner = new Setpartner();
                                setpartner.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, setpartner);
                            }
                        } catch (JsonSyntaxException e) {
                            setpartner = new Setpartner();
                            setpartner.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, setpartner);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        NetworkResponse response = error.networkResponse;
                        GetUserDetailsResponse getUserDetailsResponse = new GetUserDetailsResponse();
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                getUserDetailsResponse = new Gson().fromJson(res, GetUserDetailsResponse.class);
                                if (getUserDetailsResponse != null && getUserDetailsResponse.getMsg() != null) {
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                } else {
                                    getUserDetailsResponse = new GetUserDetailsResponse();
                                    getUserDetailsResponse.setMsg("Internal Server Error");
                                    webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                                }

                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            } catch (JsonParseException e) {
                                getUserDetailsResponse = new GetUserDetailsResponse();
                                getUserDetailsResponse.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                            }
                        } else {
                            getUserDetailsResponse.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_UPDATE_USER, 0, getUserDetailsResponse);
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(volleyMultipartRequest, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }

    public void getTodayMatches(final String type, String user_id, final WebServiceListener webServiceListener) {
        String tag_json_obj = "getTodayMatches";
        String url = URL_GET_TODAY_MATCHES+ user_id +"&type="+type+"&parameter=age,gender,height,weight,caste,sub_caste,marital_status,mother_tongue,raasi,star,having_dosham,occupation,physical_status,nationality,country,home_city,monthly_income,paadham,educatio" ;
        pDialog.setMessage("Loading Today matches...");
        //pDialog.show();
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        UserListEntity userListEntity = new UserListEntity();
                        try {
                            String res = Utils.DUMMY_TODAY_MATCHES;
                            userListEntity = new Gson().fromJson(res, UserListEntity.class);
                            if (userListEntity != null) {
                                if (userListEntity.getStatus().equalsIgnoreCase("true")) {
                                    if (type.equalsIgnoreCase("today_matches")){
                                        webServiceListener.onSuccess(REQUEST_CODE_GET_TODAY_MATCHES, 0, userListEntity);
                                    }else  if (type.equalsIgnoreCase("premium_matches")){
                                        webServiceListener.onSuccess(REQUEST_CODE_GET_TODAY_MATCHES_perimium, 0, userListEntity);
                                    }else {
                                        webServiceListener.onSuccess(REQUEST_CODE_GET_TODAY_MATCHES_recommend, 0, userListEntity);
                                    }

                                } else {
                                    webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, userListEntity);
                                }
                            } else {
                                userListEntity = new UserListEntity();
                                userListEntity.setMsg("Internal Server Error");
                                webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, userListEntity);
                            }
                        } catch (JsonSyntaxException e) {
                            userListEntity = new UserListEntity();
                            userListEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, userListEntity);
                        } catch (JsonParseException e) {
                            userListEntity = new UserListEntity();
                            userListEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, userListEntity);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //pDialog.dismiss();
                webServiceListener.onProgressEnd();
                NetworkResponse response = error.networkResponse;
                UserListEntity getAllPlansEntity = new UserListEntity();
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        getAllPlansEntity = new Gson().fromJson(res, UserListEntity.class);
                        if (getAllPlansEntity != null && getAllPlansEntity.getMsg() != null) {
                            webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                        } else {
                            getAllPlansEntity = new UserListEntity();
                            getAllPlansEntity.setMsg("Internal Server Error");
                            webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                        }

                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                    } catch (JsonParseException e) {
                        getAllPlansEntity = new UserListEntity();
                        getAllPlansEntity.setMsg("Internal Server Error");
                        webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                    }
                } else {
                    getAllPlansEntity.setMsg("Internal Server Error");
                    webServiceListener.onFailure(REQUEST_CODE_GET_TODAY_MATCHES, 0, getAllPlansEntity);
                }
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }


    public void getStates(final WebServiceListener webServiceListener) {
        String tag_json_obj = "getStates";
        pDialog.setMessage("Loading States...");
        final int requestCode = REQUEST_CODE_GET_STATES;
        //pDialog.show();
        webServiceListener.onProgressStart();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL_GET_STATES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //pDialog.dismiss();
                        webServiceListener.onProgressEnd();
                        StateListEntity stateListEntity = new StateListEntity();
                        try {
                            String res = response.toString();
                            stateListEntity = new Gson().fromJson(res, StateListEntity.class);
                            if (stateListEntity != null) {
                                if (stateListEntity.getStatus().equalsIgnoreCase("true")) {

                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        if (Utils.isConnected((Activity) context)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            webServiceListener.isNetworkAvailable(false);
        }
    }
}

