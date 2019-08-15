package com.skyappz.namma.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.skyappz.namma.R;
import com.skyappz.namma.fragments.ProfileDetailFragment;
import com.skyappz.namma.fragments.SignUpFragment;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;

import java.util.HashMap;

public class ProfileUpdationActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = "ProfileUpdationActivity";

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    public static final int INDEX_UPDATE_PROFILE_FRAGMENT = 1;
    HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();
    private int lastIndex;

    boolean isLoginned = false;
    Preferences preferences;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_updation);
        preferences = new Preferences(this);
        initViews();
        isLoginned = preferences.isLoginned();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.addOnBackStackChangedListener(this);
        setFragment(INDEX_UPDATE_PROFILE_FRAGMENT, null);
    }


    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("tool title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {

        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof SignUpFragment) {
            setFragment(INDEX_UPDATE_PROFILE_FRAGMENT, null);
        } else {
            finish();
        }
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


    public void setFragment(int index, Object object) {
        if (lastIndex != index) {
            lastIndex = index;
            Fragment newFragment = null;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            String fragmentTag = "";
            switch (index) {

                case INDEX_UPDATE_PROFILE_FRAGMENT:
                    getSupportActionBar().setTitle("Update Profile");
                    fragmentTag = "ProfileDetailFragment";
                    if (fragments.containsKey(fragmentTag)) {
                        newFragment = fragments.get(fragmentTag);
                        //Fragment currentFragment = getCurrentFragment();
                        //fragmentTransaction.hide(currentFragment);
                        fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag);
                        fragmentTransaction.commit();
                    } else {
                        newFragment = ProfileDetailFragment.newInstance();
                        fragmentTransaction.replace(R.id.flFramentContainer, newFragment, fragmentTag).commit();
                        fragments.put(fragmentTag, newFragment);
                    }
                    break;

                default:
                    break;
            }
        } else {
            Utils.showToast(this, "same index");
        }
    }

    private Fragment getCurrentFragment() {
        return fragmentManager.findFragmentById(R.id.flFramentContainer);
    }

    private void logout() {
        User user = new User();
        preferences.updateUser(user);
        preferences.setLoginned(false);
        setFragment(INDEX_UPDATE_PROFILE_FRAGMENT, null);
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onClick(View v) {

    }

    public void changeMenuOnLogin(boolean isLoginned) {

    }
}
