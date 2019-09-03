package com.skyappz.namma.profileupdate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.skyappz.namma.R;

public class ProfileUpdate extends AppCompatActivity {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    int index;
    public static final int INDEX_ABOUT_MYSELF = 0;
    public static final int INDEX_BASIC_DETAILS =1;
    public static final int INDEX_RELIGIOUS =2;
    public static final int INDEX_PERSONALINFO =3;
    public static final int INDEX_LOCATION =4;
    public static final int INDEX_Family =5;
    public static final int INDEX_PARTNER_BASIC =6;
    public static final int INDEX_PARTNER_EDU =7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        index=Integer.parseInt(getIntent().getStringExtra("fragtype"));
        setFragment(index);
    }

    public void setFragment(int index) {
        Fragment newFragment = null;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        String fragmentTag = "";

        switch (index) {
            case INDEX_ABOUT_MYSELF:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "EditAbtMySelf";
                newFragment = EditAbtMySelf.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;
            case INDEX_BASIC_DETAILS:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "EditBasicProfile";
                newFragment = EditBasicProfile.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;
            case INDEX_RELIGIOUS:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "EditReligious";
                newFragment = EditReligious.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;
            case INDEX_PERSONALINFO:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "Personalinfo";
                newFragment = Personalinfo.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;
            case INDEX_LOCATION:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "EditLoation";
                newFragment = EditLocation.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;
            case INDEX_Family:
                getSupportActionBar().setTitle("EDIT PROFILE");
                fragmentTag = "EditLoation";
                newFragment = EditFamily.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            case INDEX_PARTNER_BASIC:
                getSupportActionBar().setTitle("EDIT PARTNER");
                fragmentTag = "EditPartnerDetails";
                newFragment = EditPartnerDetails.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            case INDEX_PARTNER_EDU:
                getSupportActionBar().setTitle("EDIT PARTNER");
                fragmentTag = "Edit_partner2";
                newFragment = Edit_partner2.newInstance();
                fragmentTransaction.replace(R.id.flFragmentContainer, newFragment, fragmentTag).commit();
                break;

            default:
                break;
        }
    }

}
