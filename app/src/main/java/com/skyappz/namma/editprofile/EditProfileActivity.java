package com.skyappz.namma.editprofile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.skyappz.namma.R;
import com.skyappz.namma.fragments.SettingsFragment;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "EditProfileActivity";
    private static final int RC_SIGN_IN = 9001;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    public static final int INDEX_BASIC_DETAILS = 0;
    public static final int INDEX_PERSONAL_DETAILS = 1;
    public static final int INDEX_EDUCATION_DETAILS = 2;
    public static final int INDEX_RELIGION_DETAILS = 3;
    public static final int INDEX_HABITUAL_DETAILS = 4;
    public static final int INDEX_FAMILY_DETAILS = 5;
    public static final int INDEX_ABOUT_MY_DETAILS = 6;
    public static final int INDEX_ABOUT_MYSELF = 7;
    public static final int INDEX_ABOUT_MY_PARTNER = 8;
    public static final int INDEX_RESINDING_ADDRESS = 9;
    public static final int INDEX_PARTNER_PREFERENCE = 10;
    public static final int INDEX_CONTACT_DETAILS = 11;
    private static final int INDEX_SETTINGS_FRAGMENT = 12;

    HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
    private int menuIndex;

    Preferences preferences;
    private Fragment currentFragment;
    User user;
    private UserDetailsViewModel userDetailsViewModel;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        index = getIntent().getIntExtra("position", -1);
        initViews();
        initSetup();
        getUserDetails();
        subscribe();
    }

    private void subscribe() {
        final Observer<User> getUserReceiver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User usr) {
                user = usr;
                setFragment(index, null);
            }
        };
        userDetailsViewModel.getUser().observeForever(getUserReceiver);
    }

    private void getUserDetails() {
        String user_id = "1";
        userDetailsViewModel.getUserDetails(user_id);
    }

    private void initSetup() {
        preferences = new Preferences(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.addOnBackStackChangedListener(this);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity(this);
    }


    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
        menuIndex = index;
        Fragment newFragment = null;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        String fragmentTag = "";
        switch (index) {

            case INDEX_BASIC_DETAILS:
                getSupportActionBar().setTitle("Basic Details");
                fragmentTag = "EditReligiousDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditReligiousDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_PERSONAL_DETAILS:
                getSupportActionBar().setTitle("Personal Details");
                fragmentTag = "EditBasicDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditBasicDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_EDUCATION_DETAILS:
                getSupportActionBar().setTitle("Education Details");
                fragmentTag = "EditPersonalDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPersonalDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_RELIGION_DETAILS:
                getSupportActionBar().setTitle("Religion Details");
                fragmentTag = "EditReligionDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditReligionDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_HABITUAL_DETAILS:
                getSupportActionBar().setTitle("Habits");
                fragmentTag = "EditHabitsDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditHabitsDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_FAMILY_DETAILS:
                getSupportActionBar().setTitle("Family Details");
                fragmentTag = "EditHabitsDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditFamilyDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_ABOUT_MYSELF:
                getSupportActionBar().setTitle("About Myself");
                fragmentTag = "EditAboutMyselfDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditAboutMyselfDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;


            case INDEX_RESINDING_ADDRESS:
                getSupportActionBar().setTitle("Residency Address");
                fragmentTag = "EditResidencyAddress";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditResidencyAddress.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_ABOUT_MY_PARTNER:
                getSupportActionBar().setTitle("Partner Details");
                fragmentTag = "EditPartnerDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPartnerDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_PARTNER_PREFERENCE:
                getSupportActionBar().setTitle("Partner Preferences");
                fragmentTag = "EditPartnerPreferenceDetails";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = EditPartnerPreferenceDetails.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;

            case INDEX_SETTINGS_FRAGMENT:
                getSupportActionBar().setTitle(getString(R.string.settings));
                fragmentTag = "SettingsFragment";
                if (fragments.containsKey(fragmentTag)) {
                    newFragment = fragments.get(fragmentTag);
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                    fragmentTransaction.commit();
                } else {
                    newFragment = SettingsFragment.newInstance();
                    fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                    fragments.put(fragmentTag, newFragment);
                }
                break;


            default:
                break;
        }
    }

    private Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.flFramentContainer);
    }

    private void logout() {
        User user = new User();
        preferences.updateUser(user);
        preferences.setLoginned(false);
        setFragment(INDEX_BASIC_DETAILS, null);
    }

    @Override
    public void onBackStackChanged() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
