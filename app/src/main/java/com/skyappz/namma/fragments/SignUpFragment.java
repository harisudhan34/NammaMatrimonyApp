package com.skyappz.namma.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.FirebaseApp;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.ResponseEntities.LoginResponse;
import com.skyappz.namma.ResponseEntities.SignUpResponse;
import com.skyappz.namma.activities.AuthenticationActivity;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.model.OTPRequest;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.UserDetailList;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFragment extends Fragment implements View.OnClickListener, WebServiceListener, AdapterView.OnItemSelectedListener {


    private View rootView;
    TextInputEditText tetPassword, tetConfirmPassword, tetEmail, tetName, tetPhoneNumber;
    AppCompatButton btnSignUp;
    String password, confirmPassword, phoneNumber, email, name, profileCreateBy;
    int profile_create_position;
    private User user;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    private Preferences preferences;
    private String loginMode;
    private Handler h;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String errorMsg;
    RadioGroup gender;
    RadioButton radioMale,radioFemale;
    AppCompatCheckBox termsandcondition;
    String s_gender="Male",s_age;
    AppCompatSpinner spProfilesFor,sp_day,sp_month,sp_year;
    String  s_day,s_month,s_year,s_religion="",s_mothertounge="";
    private ArrayAdapter<String> profilesAdapter,dayadapter,monthAdapter,YearAdapter;
    public ArrayList<String> profilesFor = new ArrayList<>();
    public ArrayList<String> daylist = new ArrayList<>();
    public ArrayList<String> monthlist = new ArrayList<>();
    public ArrayList<String> yearlist = new ArrayList<>();
    AppCompatAutoCompleteTextView auto_religion,mothertongue_auto;
    String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
     String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    ProgressDialog dialog ;
    AppCompatTextView tvLogin;
    UserDetailList userDetailList;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
        FirebaseApp.initializeApp(mActivity);
        userDetailList = new UserDetailList();
        profilesFor = userDetailList.getProfilesFor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        initViews(rootView);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        return rootView;
    }


    private void initViews(View rootView) {
        termsandcondition=(AppCompatCheckBox)rootView.findViewById(R.id.termsandcondition);
        radioMale=(RadioButton)rootView.findViewById(R.id.radioMale);
        radioFemale=(RadioButton)rootView.findViewById(R.id.radioFemale);
        gender = (RadioGroup)rootView.findViewById(R.id.radioSex);
        mothertongue_auto=(AppCompatAutoCompleteTextView)rootView.findViewById(R.id.mothertongue_auto);
        auto_religion=(AppCompatAutoCompleteTextView)rootView.findViewById(R.id.auto_religion);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        tvLogin = rootView.findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
        tetPassword = rootView.findViewById(R.id.tetPassword);
        tetConfirmPassword = rootView.findViewById(R.id.tetConfirmPassword);
        tetEmail = rootView.findViewById(R.id.tetEmail);
        tetName = rootView.findViewById(R.id.tetName);
        tetPhoneNumber = rootView.findViewById(R.id.tetPhoneNumber);
        btnSignUp = rootView.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        spProfilesFor = rootView.findViewById(R.id.spProfilesFor);
        sp_day = rootView.findViewById(R.id.sp_day);
        sp_day.setOnItemSelectedListener(this);
        sp_month = rootView.findViewById(R.id.sp_month);
        sp_month.setOnItemSelectedListener(this);
        sp_year = rootView.findViewById(R.id.sp_year);
        sp_year.setOnItemSelectedListener(this);
        ArrayList<String> religojn = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, religojn);
        auto_religion.setAdapter(adapter);
        auto_religion.setOnItemClickListener(religionclick);
        h = new Handler();
//        new Thread(new Runnable() {
//            public void run() {
//                // DO NOT ATTEMPT TO DIRECTLY UPDATE THE UI HERE, IT WON'T WORK!
//                // YOU MUST POST THE WORK TO THE UI THREAD'S HANDLER
//                h.postDelayed(new Runnable() {
//                    public void run() {
//                        // Open the Spinner...
//                        spProfilesFor.performClick();
//                    }
//                }, 1);
//            }
//        }).start();

        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        CustomListAdapter adapter1 = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, motherTongues);
        mothertongue_auto.setAdapter(adapter1);
        mothertongue_auto.setOnItemClickListener(onItemClickListener);


        spProfilesFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    profileCreateBy = profilesFor.get(position);
                    profile_create_position=position;
                    AppController.set_signupprofilecreatepos(getActivity(),profile_create_position);
                } else {
                    profileCreateBy = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profilesAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, profilesFor);
        profilesAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spProfilesFor.setAdapter(profilesAdapter);

        daylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Day)));
        dayadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,daylist);
        dayadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_day.setAdapter(dayadapter);

        monthlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Month)));
        monthAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,monthlist);
        monthAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_month.setAdapter(monthAdapter);

        yearlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.year)));
        YearAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,yearlist);
        YearAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_year.setAdapter(YearAdapter);

    }
    private AdapterView.OnItemClickListener religionclick =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_religion = String.valueOf(adapterView.getItemAtPosition(i));
                    Log.e("religion",s_religion);
                }
            };

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    s_mothertounge=String.valueOf(adapterView.getItemAtPosition(i));

                }
            };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("please wait.");
                dialog.setCancelable(false);
                dialog.show();

                user = getUserDataFromInput();
                if (Utils.isConnected(mActivity)) {
                    if (isInputValidated(user)) {
                        errorMsg = "";

                        AppController.set_signupname(getActivity(),user.getName());
                        AppController.set_signupemail(getActivity(),user.getEmail());
                        AppController.set_signuppwd(getActivity(),user.getPassword());
                        AppController.set_signupphone(getActivity(),user.getMobile_number());
                        AppController.set_signupprofilecreate(getActivity(),user.getProfile_created_for());
                        AppController.set_gender(getActivity(),s_gender);
                        AppController.set_dob(getActivity(),s_day+"/"+s_month+"/"+s_year);
                        AppController.set_religion(getActivity(),s_religion);
                        AppController.set_mothertongue(getActivity(),s_mothertounge);
                       // sendOTP("signup");
                        signUp(user);
                        //showOTPRequestAlert();
                    } else {
                        dialog.dismiss();
                        Utils.showToast(mActivity, errorMsg);
                    }
                    //signUp(user);
                } else {
                    dialog.dismiss();
                    Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
                }
                break;

            case R.id.tvLogin:
                ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_EMAIL_FRAGMENT, null);
            default:
                break;
        }
    }

    private void sendOTP(String type) {

        webServiceManager.sendOTP(user.getMobile_number(), type, this);
    }

    private boolean isInputValidated(User user) {

        if (Utils.isEmpty(user.getProfile_created_for())) {
            dialog.dismiss();
            errorMsg = "Please select any one from Profile for!";
            return false;
        }
        if (Utils.isEmpty(user.getName())) {
            dialog.dismiss();
            errorMsg = "Name is empty!";
            return false;
        }
        if (s_gender.equals("")){
            dialog.dismiss();
            errorMsg = "Choose Gender";
            return false;
        }
        if (s_day.equals("DAY")){
            dialog.dismiss();
            errorMsg = "select Day";
            return false;
        }
        if (s_month.equals("MONTH")){
            dialog.dismiss();
            errorMsg = "Select Month";
            return false;
        }if (s_month.equals("YEAR")){
            dialog.dismiss();
            errorMsg = "Select Year";
            return false;
        }
        if (s_religion.equals("") ){
            dialog.dismiss();
            errorMsg = "Select Religion!";
            return false;
        }
        if (s_mothertounge.equals("")){
            dialog.dismiss();
            errorMsg = "Select Mother Tongue!";
            return false;
        }
        if (Utils.isEmpty(user.getMobile_number())) {
            dialog.dismiss();
            errorMsg = "Phone number is empty!";
            return false;
        }
        if(validatePhoneNumber(user.getMobile_number())==false) {
            dialog.dismiss();
            errorMsg = "Enter Valid  Mobile Number!";
            return false;
        }
        if (Utils.isEmpty(user.getEmail())) {
            dialog.dismiss();
            errorMsg = "Email is empty!";
            return false;
        }
        if (!user.getEmail().matches(EMAIL_REGEX)){
            dialog.dismiss();
            errorMsg = "Enter Valid Email!";
            return false;
        }
        if (!Utils.isEmailValid(user.getEmail())) {
            dialog.dismiss();
            errorMsg = "Enter a valid email!";
            return false;
        }
        if (Utils.isEmpty(user.getPassword())) {
            dialog.dismiss();
            errorMsg = "Password is empty!";
            return false;
        }
        if (!user.getPassword().matches(PASSWORD_PATTERN)){
            dialog.dismiss();
            errorMsg = "";
            Utils.showAlert(mActivity,"Password must contain min 6 character, mix of upper and lower case letters as well as digits and one special charecter");
            return false;
        }
//        if (Utils.isEmpty(confirmPassword)) {
//            errorMsg = "Confirm Password is empty!";
//            return false;
//        }
//        if (!user.getPassword().equals(confirmPassword)) {
//            errorMsg = "Password and Confirm Password should be same!";
//            return false;
//        }
        if (!termsandcondition.isChecked()) {
            errorMsg = "kindly check terms & condition";
            return false;
        }



        return true;
    }

    private User getUserDataFromInput() {
        email = tetEmail.getText().toString();
        name = tetName.getText().toString();
        password = tetPassword.getText().toString();
        confirmPassword = tetConfirmPassword.getText().toString();
        phoneNumber = tetPhoneNumber.getText().toString();
        loginMode = User.LOGIN_MODE_NORMAL;
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setMobile_number(phoneNumber);
        user.setProfile_created_for(profileCreateBy);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }



    public static boolean passwordCharValidation(String passwordEd) {
        String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[@_.]).*$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(passwordEd);
        if (!passwordEd.matches(".*\\d.*") || !matcher.matches()) {
            return true;
        }
        return false;
    }
    private static boolean validatePhoneNumber(String phoneNo)
    {
//validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}"))
        {
            if(phoneNo.startsWith("9") || phoneNo.startsWith("8") || phoneNo.startsWith("7") || phoneNo.startsWith("6"))
                return true;
            else
                return false;

        }

//validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
//validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
            return
                    true;
//validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
            return true;
// strats in 9,8,7

            //  else if (phoneNo.startsWith("9") ||  phoneNo.startsWith("8") || phoneNo.startsWith("7"))

//return false if nothing matches the input
        else return false;
    }
    private void signUp(User user) {
        webServiceManager.signUp( user,null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Log.e("aaa","aaaa");
        dialog.dismiss();
        if (requestCode == WebServiceManager.REQUEST_CODE_SIGNUP) {
            SignUpResponse loginResponse = (SignUpResponse) response;
            user = loginResponse.getUser();
            Log.e("useridd",user.getUser_id());
            moveToHomePage(user.getUser_id());
            login(user);
        } else if (requestCode == WebServiceManager.REQUEST_CODE_LOGIN) {
//            preferences.setLoginned(true);
            user = ((LoginResponse) response).getUser();
            user.setPassword(password);
            preferences.updateUser(user);

        } else if (requestCode == WebServiceManager.REQUEST_CODE_SEND_OTP) {
            BaseResponse otpResponse = (BaseResponse) response;
            Utils.showToast(mActivity, otpResponse.getMsg());
            OTPRequest otpRequest = new OTPRequest("", user.getMobile_number(), OTPRequest.SIGNUP);
            ((AuthenticationActivity) mActivity).setFragment(AuthenticationActivity.INDEX_OTP_FRAGMENT, otpRequest);
        }
    }
    private void moveToHomePage(String userid) {
        Intent intent = new Intent(mActivity, HomeActivity.class);
//        preferences.setuser_id(userid);
        AppController.set_userid(getActivity(),userid);
        intent.putExtra("userid",userid);
        mActivity.startActivity(intent);
    }


    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        if (response != null) {
            dialog.dismiss();
            if (requestCode == WebServiceManager.REQUEST_CODE_SIGNUP) {
                SignUpResponse signUpResponse = (SignUpResponse) response;
                if (signUpResponse.getUser() != null) {
                    Utils.showAlert(mActivity, signUpResponse.getMsg());
                } else {
                    Utils.showAlert(mActivity, signUpResponse.getMsg());
                }
            } else if (requestCode == WebServiceManager.REQUEST_CODE_LOGIN) {
                LoginResponse loginResponse = (LoginResponse) response;
                if (loginResponse.getUser() != null) {
                    Utils.showAlert(mActivity, loginResponse.getMsg());
                } else {
                    Utils.showAlert(mActivity, loginResponse.getMsg());
                }
            } else if (requestCode == WebServiceManager.REQUEST_CODE_SEND_OTP) {
                BaseResponse otpResponse = (BaseResponse) response;
                Utils.showAlert(mActivity, otpResponse.getMsg());
            }
        } else {
            LoginResponse loginResponse = (LoginResponse) response;
            Utils.showAlert(mActivity, loginResponse.getMsg());
        }

    }

    private void login(User user) {
        User loginUser = new User();
        loginUser.setEmail(user.getEmail());
        loginUser.setUser_id(user.getUser_id());
        loginUser.setPassword(user.getPassword());
        loginUser.setDevice_id(preferences.getDeviceId());
        webServiceManager.login(loginUser, this);
    }

    @Override
    public void isNetworkAvailable(boolean flag) {
        Utils.showAlert(mActivity, "No internet!");
    }

    @Override
    public void onProgressStart() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        //pbDot.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressEnd() {
        //pbDot.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.sp_day:
                s_day=sp_day.getSelectedItem().toString();
                break;
            case R.id.sp_month:
                s_month=sp_month.getSelectedItem().toString();
                break;
            case R.id.sp_year:
                if (radioMale.isChecked()) {
                    s_gender = radioMale.getText().toString();

                }else {
                    s_gender = radioFemale.getText().toString();


                }
                s_year=sp_year.getSelectedItem().toString();
                Log.e("gender","aaaa"+s_gender);
                if (!s_year.equalsIgnoreCase("YEAR")){
                    int dobyear=Integer.parseInt(s_year);
                    int age1=year-dobyear;
                    s_age=String.valueOf(age1);
                    if (s_gender.equalsIgnoreCase("Male")){
                        if (age1 <= 21){
                            Utils.showAlert(getActivity(),"Not Eligible to Register male ");
                        }
                    }else {
                        if (age1 <= 18){
                            Utils.showAlert(getActivity(),"Not Eligible to Register Female");
                        }
                    }

                    Log.e("age",String.valueOf(age1));
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
