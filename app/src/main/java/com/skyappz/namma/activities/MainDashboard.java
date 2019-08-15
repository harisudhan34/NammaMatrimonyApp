package com.skyappz.namma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.skyappz.namma.R;
import com.skyappz.namma.dashboard.DashboardFragment;
import com.skyappz.namma.matches.TodayMatchesFragment;

public class MainDashboard extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    LinearLayoutCompat bottom_home,bottom_search,bottom_inbox,bottom_mathes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottom_home=(LinearLayoutCompat)findViewById(R.id.bottom_home);
        bottom_home.setOnClickListener(this);
        bottom_search=(LinearLayoutCompat)findViewById(R.id.bottom_searh);
        bottom_search.setOnClickListener(this);
        bottom_mathes=(LinearLayoutCompat)findViewById(R.id.bottom_mathes);
        bottom_mathes.setOnClickListener(this);
        bottom_inbox=(LinearLayoutCompat)findViewById(R.id.bottom_imbox);
        bottom_inbox.setOnClickListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(MainDashboard.this, mDrawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.nav_header_home, null, false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }



//        navigationView.addHeaderView(listHeaderView);

        Farg(new DashboardFragment());

    }
    private void Farg(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.frame_inside, fragment).commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            case R.id.nav_edit_profile: {
                                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                                startActivity(i);
                                break;
                            }

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_home:{
                Farg(new DashboardFragment());
                break;
            }
            case R.id.bottom_searh:{{
               Intent i =new Intent(getApplicationContext(),SearchActivity.class);
               startActivity(i);
                break;
            }}
        }
    }
}
