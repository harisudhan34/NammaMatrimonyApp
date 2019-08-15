package com.skyappz.namma.matches;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.skyappz.namma.R;

public class ViewAllMatchess extends AppCompatActivity implements View.OnClickListener {
AppCompatTextView btn_today,btn_premium,btn_recommend,btn_nearbyme;
String match_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_matchess);
        match_type=getIntent().getStringExtra("match_type");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Matches");
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_today =(AppCompatTextView)findViewById(R.id.btn_today);
        btn_today.setOnClickListener(this);
        btn_premium =(AppCompatTextView)findViewById(R.id.btn_premium);
        btn_premium.setOnClickListener(this);
        btn_recommend =(AppCompatTextView)findViewById(R.id.btn_recommend);
        btn_recommend.setOnClickListener(this);
        btn_nearbyme =(AppCompatTextView)findViewById(R  .id.btn_nearbyme);
        btn_nearbyme.setOnClickListener(this);

        if (match_type.equalsIgnoreCase("today")){
            btn_today.setBackgroundResource(R.drawable.matches_header_selected);
            btn_today.setTextColor(getResources().getColor(R.color.white));
            btn_premium.setBackgroundResource(R.drawable.matches_header);
            btn_premium.setTextColor(getResources().getColor(R.color.black));
            btn_recommend.setBackgroundResource(R.drawable.matches_header);
            btn_recommend.setTextColor(getResources().getColor(R.color.black));
            btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
            btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
            Farg(new TodayMatchesFragment());
        }else if (match_type.equalsIgnoreCase("premium")){
            btn_today.setBackgroundResource(R.drawable.matches_header);
            btn_today.setTextColor(getResources().getColor(R.color.black));
            btn_premium.setBackgroundResource(R.drawable.matches_header_selected);
            btn_premium.setTextColor(getResources().getColor(R.color.white));
            btn_recommend.setBackgroundResource(R.drawable.matches_header);
            btn_recommend.setTextColor(getResources().getColor(R.color.black));
            btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
            btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
            Farg(new PremiumMatches());
        }else {
            btn_today.setBackgroundResource(R.drawable.matches_header);
            btn_today.setTextColor(getResources().getColor(R.color.black));
            btn_premium.setBackgroundResource(R.drawable.matches_header);
            btn_premium.setTextColor(getResources().getColor(R.color.black));
            btn_recommend.setBackgroundResource(R.drawable.matches_header_selected);
            btn_recommend.setTextColor(getResources().getColor(R.color.white));
            btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
            btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
            Farg(new RecommendaationMatches());
        }

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



    private void Farg(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.frame_inside_matches, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_today:
                btn_today.setBackgroundResource(R.drawable.matches_header_selected);
                btn_today.setTextColor(getResources().getColor(R.color.white));
                btn_premium.setBackgroundResource(R.drawable.matches_header);
                btn_premium.setTextColor(getResources().getColor(R.color.black));
                btn_recommend.setBackgroundResource(R.drawable.matches_header);
                btn_recommend.setTextColor(getResources().getColor(R.color.black));
                btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
                btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
                Farg(new TodayMatchesFragment());
            break;

            case R.id.btn_premium:
                btn_today.setBackgroundResource(R.drawable.matches_header);
                btn_today.setTextColor(getResources().getColor(R.color.black));
                btn_premium.setBackgroundResource(R.drawable.matches_header_selected);
                btn_premium.setTextColor(getResources().getColor(R.color.white));
                btn_recommend.setBackgroundResource(R.drawable.matches_header);
                btn_recommend.setTextColor(getResources().getColor(R.color.black));
                btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
                btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
                Farg(new PremiumMatches());
            break;


            case R.id.btn_recommend:
                btn_today.setBackgroundResource(R.drawable.matches_header);
                btn_today.setTextColor(getResources().getColor(R.color.black));
                btn_premium.setBackgroundResource(R.drawable.matches_header);
                btn_premium.setTextColor(getResources().getColor(R.color.black));
                btn_recommend.setBackgroundResource(R.drawable.matches_header_selected);
                btn_recommend.setTextColor(getResources().getColor(R.color.white));
                btn_nearbyme.setBackgroundResource(R.drawable.matches_header);
                btn_nearbyme.setTextColor(getResources().getColor(R.color.black));
                Farg(new RecommendaationMatches());
            break;


            case R.id.btn_nearbyme:

            break;


        }
    }
}
