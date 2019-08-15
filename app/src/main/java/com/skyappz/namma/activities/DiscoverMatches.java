package com.skyappz.namma.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skyappz.namma.R;
import com.skyappz.namma.matches.EducationMatches;
import com.skyappz.namma.matches.LocalityMatches;
import com.skyappz.namma.matches.ProfessionMatches;
import com.skyappz.namma.matches.StarMatches;

public class DiscoverMatches extends AppCompatActivity implements View.OnClickListener {
AppCompatTextView btn_education,btn_locality,btn_star,btn_profession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_matches);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Matches");
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_education =(AppCompatTextView)findViewById(R.id.btn_education);
        btn_education.setOnClickListener(this);
        btn_locality =(AppCompatTextView)findViewById(R.id.btn_locality);
        btn_locality.setOnClickListener(this);
        btn_star =(AppCompatTextView)findViewById(R.id.btn_star);
        btn_star.setOnClickListener(this);
        btn_profession =(AppCompatTextView)findViewById(R.id.btn_profession);
        btn_profession.setOnClickListener(this);
        Farg(new EducationMatches());
    }
    private void Farg(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.frame_inside_matches, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_education:
                btn_education.setBackgroundResource(R.drawable.matches_header_selected);
                btn_education.setTextColor(getResources().getColor(R.color.white));
                btn_locality.setBackgroundResource(R.drawable.matches_header);
                btn_locality.setTextColor(getResources().getColor(R.color.black));
                btn_star.setBackgroundResource(R.drawable.matches_header);
                btn_star.setTextColor(getResources().getColor(R.color.black));
                btn_profession.setBackgroundResource(R.drawable.matches_header);
                btn_profession.setTextColor(getResources().getColor(R.color.black));
                Farg(new EducationMatches());
                break;

            case R.id.btn_locality:
                btn_education.setBackgroundResource(R.drawable.matches_header);
                btn_education.setTextColor(getResources().getColor(R.color.black));
                btn_locality.setBackgroundResource(R.drawable.matches_header_selected);
                btn_locality.setTextColor(getResources().getColor(R.color.white));
                btn_star.setBackgroundResource(R.drawable.matches_header);
                btn_star.setTextColor(getResources().getColor(R.color.black));
                btn_profession.setBackgroundResource(R.drawable.matches_header);
                btn_profession.setTextColor(getResources().getColor(R.color.black));
                Farg(new LocalityMatches());
                break;


            case R.id.btn_star:
                btn_education.setBackgroundResource(R.drawable.matches_header);
                btn_education.setTextColor(getResources().getColor(R.color.black));
                btn_locality.setBackgroundResource(R.drawable.matches_header);
                btn_locality.setTextColor(getResources().getColor(R.color.black));
                btn_star.setBackgroundResource(R.drawable.matches_header_selected);
                btn_star.setTextColor(getResources().getColor(R.color.white));
                btn_profession.setBackgroundResource(R.drawable.matches_header);
                btn_profession.setTextColor(getResources().getColor(R.color.black));
                Farg(new StarMatches());
                break;


            case R.id.btn_profession:
                btn_education.setBackgroundResource(R.drawable.matches_header);
                btn_education.setTextColor(getResources().getColor(R.color.black));
                btn_locality.setBackgroundResource(R.drawable.matches_header);
                btn_locality.setTextColor(getResources().getColor(R.color.black));
                btn_star.setBackgroundResource(R.drawable.matches_header);
                btn_star.setTextColor(getResources().getColor(R.color.black));
                btn_profession.setBackgroundResource(R.drawable.matches_header_selected);
                btn_profession.setTextColor(getResources().getColor(R.color.white));
                Farg(new ProfessionMatches());
                break;
                }

    }
}
