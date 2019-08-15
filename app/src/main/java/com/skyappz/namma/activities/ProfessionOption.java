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

public class ProfessionOption extends AppCompatActivity implements View.OnClickListener {
LinearLayout admin,agri,bank,beauty,denence,it_engineer,media,hospital,navy,management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_option);
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
        admin=(LinearLayout)findViewById(R.id.admin);
        admin.setOnClickListener(this);
        agri=(LinearLayout)findViewById(R.id.agri);
        agri.setOnClickListener(this);
        bank=(LinearLayout)findViewById(R.id.bank);
        bank.setOnClickListener(this);
        beauty=(LinearLayout)findViewById(R.id.beauty);
        beauty.setOnClickListener(this);
        denence=(LinearLayout)findViewById(R.id.denence);
        denence.setOnClickListener(this);
        it_engineer=(LinearLayout)findViewById(R.id.it_engineer);
        it_engineer.setOnClickListener(this);
        media=(LinearLayout)findViewById(R.id.media);
        media.setOnClickListener(this);
        hospital=(LinearLayout)findViewById(R.id.hospital);
        hospital.setOnClickListener(this);
        navy=(LinearLayout)findViewById(R.id.navy);
        navy.setOnClickListener(this);
        management=(LinearLayout)findViewById(R.id.management);
        management.setOnClickListener(this);
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
            case R.id.admin:
                Intent edu=new Intent(getApplicationContext(), Optionresult.class);
                edu.putExtra("type","profession");
                edu.putExtra("profession","Admin");
                startActivity(edu);
                break;
            case R.id.agri:
                Intent medi=new Intent(getApplicationContext(), Optionresult.class);
                medi.putExtra("type","profession");
                medi.putExtra("profession","Agriculture");
                startActivity(medi);
                break;
            case R.id.bank:
                Intent docc=new Intent(getApplicationContext(), Optionresult.class);
                docc.putExtra("type","profession");
                docc.putExtra("profession","Banking and finance");
                startActivity(docc);
                break;
            case R.id.beauty:
                Intent law=new Intent(getApplicationContext(), Optionresult.class);
                law.putExtra("type","profession");
                law.putExtra("profession","Beautician");
                startActivity(law);
                break;
            case R.id.denence:
                Intent fina=new Intent(getApplicationContext(), Optionresult.class);
                fina.putExtra("type","profession");
                fina.putExtra("profession","Defense");
                startActivity(fina);
                break;
            case R.id.it_engineer:
                Intent hr=new Intent(getApplicationContext(), Optionresult.class);
                hr.putExtra("type","profession");
                hr.putExtra("profession","IT and Engineering");
                startActivity(hr);
                break;
            case R.id.media:
                Intent legal=new Intent(getApplicationContext(), Optionresult.class);
                legal.putExtra("type","profession");
                legal.putExtra("profession","Media professional");
                startActivity(legal);
                break;
            case R.id.hospital:
                Intent mana=new Intent(getApplicationContext(), Optionresult.class);
                mana.putExtra("type","profession");
                mana.putExtra("profession","Hospitality");
                startActivity(mana);
                break;
            case R.id.navy:
                Intent polie=new Intent(getApplicationContext(), Optionresult.class);
                polie.putExtra("type","profession");
                polie.putExtra("profession","navy");
                startActivity(polie);
                break;
            case R.id.management:
                Intent diploma=new Intent(getApplicationContext(), Optionresult.class);
                diploma.putExtra("type","profession");
                diploma.putExtra("profession","management");
                startActivity(diploma);
                break;
        }

    }
}
