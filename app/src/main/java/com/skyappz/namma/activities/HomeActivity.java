package com.skyappz.namma.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.skyappz.namma.R;
import com.skyappz.namma.adapter.RightSideMenuAdapter;
import com.skyappz.namma.dashboard.DashboardFragment;
import com.skyappz.namma.editprofile.EditAboutFamily;
import com.skyappz.namma.editprofile.EditAboutMyselfDetails;
import com.skyappz.namma.editprofile.EditBasicDetails;
import com.skyappz.namma.editprofile.EditEducation;
import com.skyappz.namma.editprofile.EditFamilyDetails;
import com.skyappz.namma.editprofile.EditHabitsDetails;
import com.skyappz.namma.editprofile.EditPartnerDetails;
import com.skyappz.namma.editprofile.EditPartnerPreferenceDetails;
import com.skyappz.namma.editprofile.EditPersonalDetails;
import com.skyappz.namma.editprofile.EditReligionDetails;
import com.skyappz.namma.editprofile.EditResidencyAddress;
import com.skyappz.namma.editprofile.UploadCoverrphoto;
import com.skyappz.namma.editprofile.UploadHoroscope;
import com.skyappz.namma.editprofile.UploadId;
import com.skyappz.namma.editprofile.UploadPhoto;
import com.skyappz.namma.editprofile.UploadPhotogallery;
import com.skyappz.namma.editprofile.UserDetailsViewModel;
import com.skyappz.namma.fragments.SettingsFragment;
import com.skyappz.namma.membership.MembershipFragment;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.skyappz.namma.editprofile.EditBasicDetails.selectmother_tongu;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String userid;
    private static final int CODE_AUTHENTICATION_VERIFICATION = 100;
    public static final int INDEX_BASIC_DETAILS = 0;
    public static final int INDEX_PERSONAL_DETAILS = 1;
    public static final int INDEX_EDUCATION_DETAILS = 5;
    public static final int INDEX_RELIGION_DETAILS = 2;
    public static final int INDEX_HABITUAL_DETAILS = 4;
    public static final int INDEX_FAMILY_DETAILS = 3;
    public static final int INDEX_ABOUTFAMILY = 9;
    public static final int INDEX_ABOUT_MYSELF = 7;
    public static final int INDEX_ABOUT_MY_PARTNER = 8;
    public static final int INDEX_RESINDING_ADDRESS = 6;
    public static final int INDEX_PARTNER_PREFERENCE = 10;
    public static final int INDEX_UPLOAD_ID = 11;
    private static final int INDEX_SETTINGS_FRAGMENT = 12;
    private static final int INDEX_MEMBERSHIP_FRAGMENT = 13;
    private static final int INDEX_DASHBOARD_FRAGMENT = 14;
    public static final int INDEX_UPLOAD_PHOTO = 15;
    public static final int INDEX_UPLOAD_COVER_PHOTO = 16;
    public static final int INDEX_UPLOAD_PHOTO_GALLERY = 17;
    public static final int INDEX_UPLOAD_horoscope = 18;
    public static DrawerLayout drawer;
    RecyclerView rvValues;
   private NavigationView navigationView;
    Preferences preferences;
    private LinearLayoutManager listLayoutManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
    int rightindex;
    ArrayList<String> state_list = new ArrayList<String>();
    ArrayList<String> dist_list = new ArrayList<String>();
    ArrayList<String> city_list = new ArrayList<String>();
    private static final int PERMISSION_REQUESTS = 1;
    RightSideMenuAdapter motherTongueAdapter, profileCreatedByAdapter, complexionsAdapter,
            bodyTypeAdapter, disabilityAdapter, degreeAdapter, jobsAdapter, fatherOccupationAdapter, motherOccupationAdapter,
            workingSectorAdapter, monthlyIncomeAdapter, religionsAdapter,
            casteAdapter, subCasteAdapter, foodHabitsAdapter, smokingHabitsAdapter,
            drinkingHabitsAdapter, familyTypeAdapter, familStatusAdapter,MaritalStatusAdapter,DosamAdapter,paadhamAdapter,NatioonalityAdapter,
    countryAdapter,stateAdapter,districtAdapter,cityAdapter;

    RightSideMenuAdapter[] rightSideMenuAdapters;
    private User user;
    private UserDetailsViewModel userDetailsViewModel;

    public static enum ADAPTER_INDEX {
        MOTHER_TONGUE, PROFILE_CREATED_FOR_COMPLEXION, BODY_TYPE,
        DISABILITY, DEGREE, JOBS, FATHER_OCCUPATION,
        MOTHER_OCCUPATION, WORKING_SECTOR, MONTHLY_INCOME, RELIGIONS,
        CASTE, SUB_CASTE, FOOD_HABITS, SMOKING_HABITS,
        DRINKING_HABITS, FAMILY_TYPE, FAMILY_STATUS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity(this);
        userid=getIntent().getStringExtra("userid");
        if (allPermissionsGranted()) {
            onPermissionGranted();
        } else {
            getRuntimePermissions();
        }
        initViews();
        initAdapters();
        getUserDetails();
        subscribe();
    }
    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        Log.i("", "Permission granted!");
        if (allPermissionsGranted()) {
            onPermissionGranted();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void onPermissionGranted() {
        preferences = new Preferences(this);
        //webServiceManager.getStates(this);
    }
    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i("", "Permission granted: " + permission);
            return true;
        }
        Log.i("", "Permission NOT granted: " + permission);
        return false;
    }
    private void subscribe() {
        final Observer<User> getUserReceiver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User usr) {
                user = usr;
                setFragment(INDEX_PERSONAL_DETAILS, null);
            }
        };
        userDetailsViewModel.getUser().observeForever(getUserReceiver);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CODE_AUTHENTICATION_VERIFICATION) {
            Toast.makeText(this, "Success: Verified user's identity", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failure: Unable to verify user's identity", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDetails() {
        String user_id = "1";
        userDetailsViewModel.getUserDetails(user_id);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("stateanddist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void loadStateandDist() {
        state_list =new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("states");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String state = jo_inside.getString("state");
                state_list.add(state);
                stateAdapter= new RightSideMenuAdapter(this, this, state_list,rightindex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    public  void loaddist(String state){
//        dist_list=new ArrayList<>();
//        try {
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//            JSONArray m_jArry = obj.getJSONArray("states");
//            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
//            for (int i = 0; i < m_jArry.length(); i++) {
//                JSONObject jo_inside = m_jArry.getJSONObject(i);
//                String state1 = jo_inside.getString("state");
//                if (state1.equals(state)){
//                    JSONArray distarray = jo_inside.getJSONArray("districts");
//                    for (int k=0;k<distarray.length();k++ ) {
//                    dist_list.add(distarray.getString(k));
//                    districtAdapter= new RightSideMenuAdapter(this, this, dist_list,rightindex);
//                    }
//                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    public  void loadcity(String dist){
        city_list=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadcityjson());
            JSONArray m_jArry = obj.getJSONArray(dist);

            for (int k=0;k<m_jArry.length();k++ ) {
                city_list.add(m_jArry.getString(k));
                Log.e("cityarray",m_jArry.getString(k));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadcityjson() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void initAdapters() {
        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        motherTongueAdapter = new RightSideMenuAdapter(this, this, motherTongues,rightindex);
        ArrayList<String> profileBys = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_created_by)));
        profileCreatedByAdapter = new RightSideMenuAdapter(this, this, profileBys,rightindex);
        ArrayList<String> complexions = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.complexions)));
        complexionsAdapter = new RightSideMenuAdapter(this, this, complexions,rightindex);
        ArrayList<String> bodyTypes = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.body_types)));
        bodyTypeAdapter = new RightSideMenuAdapter(this, this, bodyTypes,rightindex);
        ArrayList<String> disabilities = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.disability)));
        disabilityAdapter = new RightSideMenuAdapter(this, this, disabilities,rightindex);
        ArrayList<String> degrees = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.degrees)));
        degreeAdapter = new RightSideMenuAdapter(this, this, degrees,rightindex);
        ArrayList<String> jobs = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.jobs)));
        jobsAdapter = new RightSideMenuAdapter(this, this, jobs,rightindex);
        fatherOccupationAdapter = new RightSideMenuAdapter(this, this, jobs,rightindex);
        motherOccupationAdapter = new RightSideMenuAdapter(this, this, jobs,rightindex);
        ArrayList<String> working_sectors = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.working_sectors)));
        workingSectorAdapter = new RightSideMenuAdapter(this, this, working_sectors,rightindex);
        ArrayList<String> religions = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        religionsAdapter = new RightSideMenuAdapter(this, this, religions,rightindex);
        ArrayList<String> castes = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.castes)));
        casteAdapter = new RightSideMenuAdapter(this, this, castes,rightindex);
        ArrayList<String> subCastes = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.castes)));
        subCasteAdapter = new RightSideMenuAdapter(this, this, subCastes,rightindex);
        ArrayList<String> foodHabits = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.eating_habits)));
        foodHabitsAdapter = new RightSideMenuAdapter(this, this, foodHabits,rightindex);
        ArrayList<String> smokingHabits = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.smoking_habits)));
        smokingHabitsAdapter = new RightSideMenuAdapter(this, this, smokingHabits,rightindex);
        ArrayList<String> drinkingHabits = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drinking_habits)));
        drinkingHabitsAdapter = new RightSideMenuAdapter(this, this, drinkingHabits,rightindex);
        ArrayList<String> familyTypes = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_types)));
        familyTypeAdapter = new RightSideMenuAdapter(this, this, familyTypes,rightindex);
        ArrayList<String> familyStatuses = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_statuses)));
        familStatusAdapter = new RightSideMenuAdapter(this, this, familyStatuses,rightindex);
        ArrayList<String> maritalstatus = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        MaritalStatusAdapter= new RightSideMenuAdapter(this, this, maritalstatus,rightindex);
        ArrayList<String> dosam = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dhosam)));
        DosamAdapter= new RightSideMenuAdapter(this, this, dosam,rightindex);
        ArrayList<String> paatham = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.paatham)));
        paadhamAdapter= new RightSideMenuAdapter(this, this, paatham,rightindex);
        ArrayList<String> nationality = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality)));
        NatioonalityAdapter= new RightSideMenuAdapter(this, this, nationality,rightindex);
        ArrayList<String> country = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        countryAdapter= new RightSideMenuAdapter(this, this, country,rightindex);
        loadStateandDist();
        cityAdapter= new RightSideMenuAdapter(this, this, city_list,rightindex);
        rightSideMenuAdapters = new RightSideMenuAdapter[]{motherTongueAdapter, profileCreatedByAdapter, complexionsAdapter,
                bodyTypeAdapter, disabilityAdapter, degreeAdapter, jobsAdapter, fatherOccupationAdapter, motherOccupationAdapter,
                workingSectorAdapter, monthlyIncomeAdapter, religionsAdapter,
                casteAdapter, subCasteAdapter, foodHabitsAdapter, smokingHabitsAdapter,
                drinkingHabitsAdapter, familyTypeAdapter, familStatusAdapter,MaritalStatusAdapter,DosamAdapter,paadhamAdapter,NatioonalityAdapter,
                countryAdapter,stateAdapter,cityAdapter};
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

         navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        rvValues = navigationView.findViewById(R.id.rvValues);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.menu_divider));
        rvValues.addItemDecoration(itemDecorator);
        rvValues.setHasFixedSize(true);
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(this);
        rvValues.setLayoutManager(rvLayoutManager);
        rvValues.setItemAnimator(new DefaultItemAnimator());
    }

    public void setFragment(int index, Bundle bundle) {
        Fragment newFragment = null;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        String fragmentTag = "";

        switch (index) {
            case INDEX_SETTINGS_FRAGMENT:
//                getSupportActionBar().setTitle("My Profile");
                fragmentTag = "SettingsFragment";
                newFragment = SettingsFragment.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            case INDEX_MEMBERSHIP_FRAGMENT:
                getSupportActionBar().setTitle("Buy Plan");
                fragmentTag = "MembershipFragment";
                newFragment = MembershipFragment.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            case INDEX_DASHBOARD_FRAGMENT:
                getSupportActionBar().setTitle("Home");
                fragmentTag = "DashboardFragment";
                newFragment = DashboardFragment.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            case INDEX_BASIC_DETAILS:
//                getSupportActionBar().setTitle("Basic Details");
                fragmentTag = "EditBasicDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.add(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditBasicDetails.newInstance();
                    fragmentTransaction.add(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_PERSONAL_DETAILS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditPersonalDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.add(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPersonalDetails.newInstance();
                    fragmentTransaction.add(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_EDUCATION_DETAILS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditEducation";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditEducation.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_RELIGION_DETAILS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditReligionDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditReligionDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_HABITUAL_DETAILS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditHabitsDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditHabitsDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_FAMILY_DETAILS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditFamilyDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditFamilyDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;
            case INDEX_ABOUTFAMILY:
                getSupportActionBar().setTitle("ABOUT FAMILY");
                fragmentTag = "EditAboutFamily";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditAboutFamily.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_ABOUT_MYSELF:
                getSupportActionBar().setTitle("ABOUT MYSELF");
                fragmentTag = "EditAboutMyselfDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditAboutMyselfDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;


            case INDEX_RESINDING_ADDRESS:
                getSupportActionBar().setTitle("REGISTER");
                fragmentTag = "EditResidencyAddress";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditResidencyAddress.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_ABOUT_MY_PARTNER:
                getSupportActionBar().setTitle("PARTNER DETAILS");
                fragmentTag = "EditPartnerDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPartnerDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_PARTNER_PREFERENCE:
                getSupportActionBar().setTitle("Partner Preferences");
                fragmentTag = "EditPartnerPreferenceDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPartnerPreferenceDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_UPLOAD_ID:
                getSupportActionBar().setTitle("UPLOAD");
                fragmentTag = "UploadID";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = UploadId.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;
            case INDEX_UPLOAD_PHOTO:
//                getSupportActionBar().setTitle("UPLOAD PROFILE PCTURE");
                fragmentTag = "UploadPhoto";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = UploadPhoto.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;
            case INDEX_UPLOAD_COVER_PHOTO:
//                getSupportActionBar().setTitle("UPLOAD COVER PHOTO");
                fragmentTag = "UploadCoverphoto";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = UploadCoverrphoto.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;
            case INDEX_UPLOAD_PHOTO_GALLERY:
//                getSupportActionBar().setTitle("UPLOAD PHOTO GALLERY");
                fragmentTag = "UploadPhotogallery";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = UploadPhotogallery.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_UPLOAD_horoscope:
//                getSupportActionBar().setTitle("UPLOAD PHOTO HOROSCOPE");
                fragmentTag = "UploadHoroscope";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = UploadHoroscope.newInstance();
                    fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).addToBackStack(fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;



            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flFragmentContainer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof MembershipFragment) {
            List<Fragment> fragments = ((MembershipFragment) currentFragment).getAdapter().getmFragmentList();
            for (Fragment fragment : fragments) {
                if (fragment.isAdded() && fragment.getChildFragmentManager().findFragmentById(R.id.flContainer) != null) {
                    PlanDetailFragment planDetailFragment = (PlanDetailFragment) fragment;
                    fragment.getChildFragmentManager().popBackStack();
                }
            }
        } else {
            super.onBackPressed();
        }*/
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                setFragment(INDEX_DASHBOARD_FRAGMENT, null);
                break;
            case R.id.nav_edit_profile:
                setFragment(INDEX_SETTINGS_FRAGMENT, null);
                break;
            case R.id.nav_membership_plan:
                setFragment(INDEX_MEMBERSHIP_FRAGMENT, null);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showSpinner(int index) {
        Log.e("right",String.valueOf(rightindex));
        drawer.openDrawer(Gravity.END);
        rightindex = index;
        initAdapters();
        rvValues.setAdapter(rightSideMenuAdapters[index]);

    }

    public User getUser() {
        return user;
    }
    public void drawerclose() {
        user.setMother_tongue(selectmother_tongu);
        drawer.closeDrawer(Gravity.END);

    }
}
