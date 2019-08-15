package com.skyappz.namma.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.LoginResponse;
import com.skyappz.namma.ResponseEntities.SignUpResponse;
import com.skyappz.namma.listeners.OTPListener;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.UserDetailList;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.OTPService;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ProfileDetailFragment extends Fragment implements View.OnClickListener, WebServiceListener, OTPListener {


    private View rootView;
    AppCompatEditText tetPassword, etAboutMyself, tetConfirmPassword, tetName, tetEmail, tetPhoneNumber, etNoOfChildren;
    AppCompatButton btnUpdate;
    AppCompatTextView tvDOB;
    private String aboutMyself, name, searchingFor, motherTongue, maritalStatus, noOfChildren, physicalStatus, height, weight, bodyType, complexionType, foodHabit, drinkingHabit, smokingHabit, mobileNumber, whatsappNumber, parentsNumber, parentsWhatsappNumber, livingCountry, homeCountry, livingState, homeState, livingCity, homeCity, degree, job, jobType, religion, caste, star, subCaste, raasi, dosham, password, confirmPassword, firstName, lastName, phoneNumber, email, gender = "male", profileFor, dob;
    private User user;
    private Activity mActivity;
    WebServiceManager webServiceManager;
    private Preferences preferences;
    private String loginMode;
    DotProgressBar pbDot;
    private AppCompatEditText etOTP;
    private Dialog otpVerifyDialog;
    OTPService otpService;
    private String verificationId;
    private AlertDialog.Builder signupDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String errorMsg;
    AppCompatSpinner spProfilesFor, spReligions, spCastes, spRaasi, spDoshams, spHomeState, spLivingState, spHomeCity, spLivingCity, spHomeCountry, spLivingCountry, spJobTypes, spCities, spStates, spNationality, spEducation, spJobs, spEmployed, spMotherTongue, spMaritalStatuses, spPhysicalStatuses, spBodyTypes, spComplexions, spEatingHabits, spDrinkingHabits, spSmokingHabits;
    private ArrayAdapter<String> profilesAdapter, religionsAdapter, countryAdapter, languageAdapter, maritalStatusAdapter, physicalStatusAdapter, bodyTypeAdapter, complexionAdapter, eatingHabitsAdapter, smokingHabitsAdapter, drinkingHabitsAdapter, casteAdapter, raasiAdapter, doshamAdapter, homeStateAdapter, livingStateAdapter, stateAdapter, cityAdapter, homeCityAdapter, livingCityAdapter, homeCountryAdapter, livingCountryAdapter, degreeAdapter, jobsAdapter, jobTypesAdapter;
    public ArrayList<String> profilesFor = new ArrayList<>();
    public ArrayList<String> religions = new ArrayList<>();
    RadioGroup rgGender, rgSearchingFor;
    private Date dobDate;
    ArrayList<String> castes, raasis, doshams, jobTypes, cities, states, countries, degrees, jobs, companies, languages, maritalStatuses, physicalStatuses, bodyTypes, complexions, eatingHabits, drinkingHabits, smokingHabits;
    UserDetailList userDetailList;

    public ProfileDetailFragment() {
        // Required empty public constructor
    }

    public static ProfileDetailFragment newInstance() {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        userDetailList = new UserDetailList();
        webServiceManager = new WebServiceManager(mActivity);
        preferences = new Preferences(mActivity);
        FirebaseApp.initializeApp(mActivity);
        otpService = new OTPService(mActivity, this);
        loadInputData();
    }

    private void loadInputData() {
        profilesFor = userDetailList.getProfilesFor();
        religions.add(mActivity.getString(R.string.religion_select_hint));
        religions.addAll(userDetailList.getReligions());
        cities = userDetailList.getCities();
        states = userDetailList.getStates();
        countries = userDetailList.getCountries();
        degrees = userDetailList.getDegrees();
        jobs = userDetailList.getJobs();
        companies = userDetailList.getCompanies();
        languages = userDetailList.getLanguages();
        maritalStatuses = userDetailList.getMaritalStatuses();
        physicalStatuses = userDetailList.getPhysicalStatuses();
        bodyTypes = userDetailList.getBodyTypes();
        complexions = userDetailList.getComplexions();
        eatingHabits = userDetailList.getEatingHabits();
        drinkingHabits = userDetailList.getDrinkingHabits();
        smokingHabits = userDetailList.getSmokingHabits();
        castes = userDetailList.getCaste();
        raasis = userDetailList.getRaasi();
        doshams = userDetailList.getDoshams();
        jobTypes = userDetailList.getJobTypes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        initViews(rootView);
        return rootView;
    }


    private void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(false);
        etAboutMyself = rootView.findViewById(R.id.etAboutMyself);
        etNoOfChildren = rootView.findViewById(R.id.etNoOfChildren);
        tetPassword = rootView.findViewById(R.id.tetPassword);
        tetConfirmPassword = rootView.findViewById(R.id.tetConfirmPassword);
        tetName = rootView.findViewById(R.id.etName);
        tvDOB = rootView.findViewById(R.id.tvDOB);
        tvDOB.setOnClickListener(this);
        tetEmail = rootView.findViewById(R.id.tetEmail);
        tetPhoneNumber = rootView.findViewById(R.id.tetPhoneNumber);
        btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        pbDot = rootView.findViewById(R.id.pbDot);
        rgGender = rootView.findViewById(R.id.rgGender);
        rgSearchingFor = rootView.findViewById(R.id.rgGroomBride);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMale) {
                    gender = "male";
                } else {
                    gender = "female";
                }
            }
        });
        rgSearchingFor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbBride) {
                    searchingFor = "bride";
                } else if (checkedId == R.id.rbGroom) {
                    searchingFor = "groom";
                }
            }
        });
        spProfilesFor = rootView.findViewById(R.id.spProfilesFor);
        spReligions = rootView.findViewById(R.id.spMotherTongue);
        spProfilesFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    profileFor = profilesFor.get(position);
                } else {
                    profileFor = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spReligions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    religion = religions.get(position);
                } else {
                    religion = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        profilesAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, profilesFor);
        religionsAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, religions);
        spProfilesFor.setAdapter(profilesAdapter);
        spReligions.setAdapter(religionsAdapter);
        spNationality = rootView.findViewById(R.id.spNationality);
        countryAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, countries);
        spNationality.setAdapter(countryAdapter);
        spMotherTongue = rootView.findViewById(R.id.spMotherTongue);
        spMotherTongue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                motherTongue = languages.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                motherTongue = null;
            }
        });
        languageAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, languages);
        spMotherTongue.setAdapter(languageAdapter);
        spMaritalStatuses = rootView.findViewById(R.id.spMaritalStatus);
        spMaritalStatuses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalStatus = maritalStatuses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                maritalStatus = null;
            }
        });
        maritalStatusAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, maritalStatuses);
        spMaritalStatuses.setAdapter(maritalStatusAdapter);
        spPhysicalStatuses = rootView.findViewById(R.id.spPhysicalStatus);
        physicalStatusAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, physicalStatuses);
        spPhysicalStatuses.setAdapter(physicalStatusAdapter);
        spBodyTypes = rootView.findViewById(R.id.spBodyType);
        bodyTypeAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, bodyTypes);
        spBodyTypes.setAdapter(bodyTypeAdapter);
        spComplexions = rootView.findViewById(R.id.spComplexion);
        complexionAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, complexions);
        spComplexions.setAdapter(complexionAdapter);
        spEatingHabits = rootView.findViewById(R.id.spEatingHabits);
        eatingHabitsAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, eatingHabits);
        spEatingHabits.setAdapter(eatingHabitsAdapter);
        spSmokingHabits = rootView.findViewById(R.id.spSmokingHabits);
        smokingHabitsAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, smokingHabits);
        spSmokingHabits.setAdapter(smokingHabitsAdapter);
        spDrinkingHabits = rootView.findViewById(R.id.spDrinkingHabits);
        drinkingHabitsAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, drinkingHabits);
        spDrinkingHabits.setAdapter(drinkingHabitsAdapter);
        spCastes = rootView.findViewById(R.id.spCaste);
        casteAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, castes);
        spCastes.setAdapter(casteAdapter);
        spRaasi = rootView.findViewById(R.id.spRaasi);
        raasiAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, raasis);
        spRaasi.setAdapter(raasiAdapter);
        spDoshams = rootView.findViewById(R.id.spDosham);
        doshamAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, doshams);
        spDoshams.setAdapter(doshamAdapter);
        spHomeState = rootView.findViewById(R.id.spHomeState);
        homeStateAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, states);
        spHomeState.setAdapter(homeStateAdapter);
        spLivingState = rootView.findViewById(R.id.spLivingState);
        livingStateAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, states);
        spLivingState.setAdapter(livingStateAdapter);
        spHomeCity = rootView.findViewById(R.id.spHomeCity);
        homeCityAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, cities);
        spHomeCity.setAdapter(homeCityAdapter);
        spLivingCity = rootView.findViewById(R.id.spLivingCity);
        livingCityAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, cities);
        spLivingCity.setAdapter(livingCityAdapter);
        spHomeCountry = rootView.findViewById(R.id.spHomeCountry);
        homeCountryAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, countries);
        spHomeCountry.setAdapter(homeCountryAdapter);
        spLivingCountry = rootView.findViewById(R.id.spLivingCountry);
        livingCountryAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, countries);
        spLivingCountry.setAdapter(livingCountryAdapter);
        spJobTypes = rootView.findViewById(R.id.spEmployedIn);
        jobTypesAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, jobTypes);
        spJobTypes.setAdapter(jobTypesAdapter);
        spEducation = rootView.findViewById(R.id.spEducation);
        degreeAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, degrees);
        spEducation.setAdapter(degreeAdapter);
        spJobs = rootView.findViewById(R.id.spOccupation);
        jobsAdapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item, jobs);
        spJobs.setAdapter(jobsAdapter);

        otpVerifyDialog = new Dialog(mActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                user = getUserDataFromInput();
                if (Utils.isConnected(mActivity)) {
                    if (isInputValidated(user)) {
                        errorMsg = "";
                        signUp(user);
                        //showOTPRequestAlert();
                    } else {
                        Utils.showToast(mActivity, errorMsg);
                    }
                } else {
                    Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
                }
                break;

            case R.id.tvDOB:
                openDatePicker();
                break;
            default:
                break;
        }
    }

    private boolean isInputValidated(User user) {

        if (Utils.isEmpty(user.getProfile_created_for())) {
            errorMsg = "Please select any one from Profile for!";
            return false;
        }
        if (Utils.isEmpty(user.getName())) {
            errorMsg = "Please enter a name!";
            return false;
        }
        if (Utils.isEmpty(user.getGender())) {
            errorMsg = "Please select a gender!";
            return false;
        }
        if (Utils.isEmpty(user.getDob())) {
            errorMsg = "Please set a Date of Birth!";
            return false;
        }
        if (Utils.isEmpty(user.getReligion())) {
            errorMsg = "Please select a religion!";
            return false;
        }
        if (Utils.isEmpty(user.getEmail())) {
            errorMsg = "Email is empty!";
            return false;
        }
        if (Utils.isEmpty(user.getMobile_number())) {
            errorMsg = "Phone number is empty!";
            return false;
        }
        if (Utils.isEmpty(user.getPassword())) {
            errorMsg = "Password is empty!";
            return false;
        }
        if (Utils.isEmpty(confirmPassword)) {
            errorMsg = "Confirm Password is empty!";
            return false;
        }
        if (!user.getPassword().equals(confirmPassword)) {
            errorMsg = "Password and Confirm Password should be same!";
            return false;
        }

        return true;
    }

    private void showOTPRequestAlert() {
        final Dialog otpVerifyDialog = new Dialog(mActivity);
        otpVerifyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        otpVerifyDialog.setContentView(R.layout.dialog_otp_request);
        AppCompatTextView tvMessage = otpVerifyDialog.findViewById(R.id.tvMessage);
        final AppCompatEditText tetMobileNumber = otpVerifyDialog.findViewById(R.id.tetMobileNumber);
        tetMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = tetMobileNumber.getText().toString();
                user.setMobile_number(phoneNumber);
                tetPhoneNumber.setText(phoneNumber);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AppCompatButton btnCancel = otpVerifyDialog.findViewById(R.id.btnCancel);
        AppCompatButton btnSendOTP = otpVerifyDialog.findViewById(R.id.btnSend);

        if (!Utils.isEmpty(user.getMobile_number())) {
            tetMobileNumber.setText(user.getMobile_number());
            tvMessage.setText("You will get an OTP to the below number to verify.");
        } else {
            tvMessage.setText("Enter your Mobile Number and click Send Button to get the OTP");
        }
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+91" + tetMobileNumber.getText().toString();
                if (!Utils.isEmpty(phoneNumber)) {
                    otpService.startPhoneNumberVerification(phoneNumber, ProfileDetailFragment.this);
                    onProgressStart();
                    otpVerifyDialog.dismiss();
                } else {
                    Utils.showToast(mActivity, "Please Enter Mobile Number");
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerifyDialog.dismiss();
            }
        });

        otpVerifyDialog.show();
    }

    private void createVerifyOTPDialog() {
        otpVerifyDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        otpVerifyDialog.setContentView(R.layout.otp_dialog);
        etOTP = otpVerifyDialog.findViewById(R.id.etOTP);
        AppCompatButton btnVerify = otpVerifyDialog.findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOTP.getText().toString();
                otpService.verifyPhoneNumberWithCode(verificationId, otp);
                onProgressStart();
            }
        });
        otpVerifyDialog.show();
    }

    private void showOTPDialog() {
        otpVerifyDialog.show();
    }

    private User getUserDataFromInput() {
        aboutMyself = etAboutMyself.getText().toString();
        name = tetName.getText().toString();
        // gender calculate
        // searching for selected
        profileFor = (String) spProfilesFor.getSelectedItem().toString();
        // gender, searching for , dob, mother tongue, marital status done
        noOfChildren = etNoOfChildren.getText().toString();

        password = tetPassword.getText().toString();
        confirmPassword = tetConfirmPassword.getText().toString();
        phoneNumber = tetPhoneNumber.getText().toString();
        loginMode = User.LOGIN_MODE_NORMAL;
        email = tetEmail.getText().toString();
        user = new User();
        user.setName(firstName);
        user.setEmail(email);
        user.setPassword(password);
        user.setProfile_created_for(profileFor);
        user.setGender(gender);
        user.setDob(dob);
        user.setReligion(religion);
        user.setMobile_number(phoneNumber);
        user.setDevice_id(preferences.getDeviceId());
        return user;
    }

    private void signUp(User user) {
//        webServiceManager.signUp( null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        if (requestCode == WebServiceManager.REQUEST_CODE_SIGNUP) {
            user = ((SignUpResponse) response).getUser();
            user.setPassword(password);
            login(user);
        }
    }

    private void moveToMainActivity() {
        mActivity.finish();
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        if (response != null) {
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
    public void onError(String e) {
        onProgressEnd();
        Utils.showAlert(mActivity, e);
    }

    @Override
    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        onProgressEnd();
        this.verificationId = verificationId;
        createVerifyOTPDialog();
    }

    @Override
    public void onOTPVerified(FirebaseUser firebaseUser) {
        onProgressEnd();
        otpVerifyDialog.dismiss();
        signupDialog.show();
    }

    @Override
    public void onSMSReceived(String smsCode) {
        createVerifyOTPDialog();
        etOTP.setText(smsCode);
        etOTP.setEnabled(false);
    }

    public void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (mYear - year >= 16) {
                            dob = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            tvDOB.setText("Date of Birth: " + dob);
                        } else {
                            dob = null;
                            Utils.showToast(mActivity, mActivity.getResources().getString(R.string.dob_insufficient));
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
