package com.skyappz.namma.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.skyappz.namma.R;
import com.skyappz.namma.matches.Optionresult;

public class EducationOption extends AppCompatActivity implements View.OnClickListener {
LinearLayout enginering,medical,doctor,law,finane,hr,legal,management,police,diploma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("");
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        enginering=(LinearLayout)findViewById(R.id.enginering);
        enginering.setOnClickListener(this);
        medical=(LinearLayout)findViewById(R.id.medical);
        medical.setOnClickListener(this);
        doctor=(LinearLayout)findViewById(R.id.doctor);
        doctor.setOnClickListener(this);
        law=(LinearLayout)findViewById(R.id.law);
        law.setOnClickListener(this);
        finane=(LinearLayout)findViewById(R.id.finane);
        finane.setOnClickListener(this);
        hr=(LinearLayout)findViewById(R.id.hr);
        hr.setOnClickListener(this);
        legal=(LinearLayout)findViewById(R.id.legal);
        legal.setOnClickListener(this);
        management=(LinearLayout)findViewById(R.id.management);
        management.setOnClickListener(this);
        police=(LinearLayout)findViewById(R.id.police);
        police.setOnClickListener(this);
        diploma=(LinearLayout)findViewById(R.id.diploma);
        diploma.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enginering:
                Intent edu=new Intent(getApplicationContext(), Optionresult.class);
                edu.putExtra("type","education");
                edu.putExtra("education","B.E");
                startActivity(edu);
                break;
            case R.id.medical:
                Intent medi=new Intent(getApplicationContext(), Optionresult.class);
                medi.putExtra("type","education");
                medi.putExtra("education","medical");
                startActivity(medi);
                break;
            case R.id.doctor:
                Intent docc=new Intent(getApplicationContext(), Optionresult.class);
                docc.putExtra("type","education");
                docc.putExtra("education","MBBS");
                startActivity(docc);
                break;
            case R.id.law:
                Intent law=new Intent(getApplicationContext(), Optionresult.class);
                law.putExtra("type","education");
                law.putExtra("education","law");
                startActivity(law);
                break;
            case R.id.finane:
                Intent fina=new Intent(getApplicationContext(), Optionresult.class);
                fina.putExtra("type","education");
                fina.putExtra("education","B.Com");
                startActivity(fina);
                break;
            case R.id.hr:
                Intent hr=new Intent(getApplicationContext(), Optionresult.class);
                hr.putExtra("type","education");
                hr.putExtra("education","hr");
                startActivity(hr);
                break;
            case R.id.legal:
                Intent legal=new Intent(getApplicationContext(), Optionresult.class);
                legal.putExtra("type","education");
                legal.putExtra("education","legal");
                startActivity(legal);
                break;
            case R.id.management:
                Intent mana=new Intent(getApplicationContext(), Optionresult.class);
                mana.putExtra("type","education");
                mana.putExtra("education","MCA");
                startActivity(mana);
                break;
            case R.id.police:
                Intent polie=new Intent(getApplicationContext(), Optionresult.class);
                polie.putExtra("type","education");
                polie.putExtra("education","police");
                startActivity(polie);
                break;
            case R.id.diploma:
                Intent diploma=new Intent(getApplicationContext(), Optionresult.class);
                diploma.putExtra("type","education");
                diploma.putExtra("education","Diploma");
                startActivity(diploma);
                break;
        }
    }
}
