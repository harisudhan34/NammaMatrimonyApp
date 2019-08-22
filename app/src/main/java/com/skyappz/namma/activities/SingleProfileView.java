package com.skyappz.namma.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleProfileView extends AppCompatActivity implements WebServiceListener {
AppCompatTextView v_name,v_dob,v_gender,v_languvage,v_profilecreate,v_age,v_height,v_weight,v_complexion,v_bodytype,v_paadham,v_dhosam,v_disability,v_maritalstatus,
        v_religion,v_caste,v_ubcaste,v_degree,v_workingsector,v_occupation,v_monthlyincome,v_familytype,v_familystatus,v_fatheroccupation,v_motheroccupation,
        v_numberofsibiling,v_foodhabit,v_somokinghabit,DRINKING_HABIT,v_nationality,v_country,v_state,v_city,v_abtmyself,v_abtfamily,user_name;
    AppCompatImageView profile;
    private static final String URL_SINGLE_PROFILE = "https://nammamatrimony.in/api/getuser.php?user_id=";
String userid;
AppCompatTextView intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_profile_view);
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
         user_name=(AppCompatTextView)findViewById(R.id.username);
        userid=getIntent().getStringExtra("user_id");
        v_name = (AppCompatTextView)findViewById(R.id.v_name);
        v_dob = (AppCompatTextView)findViewById(R.id.v_dob);
        v_gender = (AppCompatTextView)findViewById(R.id.v_gender);
        v_languvage = (AppCompatTextView)findViewById(R.id.v_languvage);
        v_profilecreate = (AppCompatTextView)findViewById(R.id.v_profilecreate);
        v_height = (AppCompatTextView)findViewById(R.id.v_height);
        v_weight = (AppCompatTextView)findViewById(R.id.v_weight);
        v_complexion = (AppCompatTextView)findViewById(R.id.v_complexion);
        v_bodytype = (AppCompatTextView)findViewById(R.id.v_bodytype);
        v_paadham = (AppCompatTextView)findViewById(R.id.v_paadham);
        v_dhosam = (AppCompatTextView)findViewById(R.id.v_dhosam);
        v_disability = (AppCompatTextView)findViewById(R.id.v_disability);
        v_maritalstatus = (AppCompatTextView)findViewById(R.id.v_maritalstatus);
        v_religion = (AppCompatTextView)findViewById(R.id.v_religion);
        v_caste = (AppCompatTextView)findViewById(R.id.v_caste);
        v_ubcaste = (AppCompatTextView)findViewById(R.id.v_ubcaste);
        v_degree = (AppCompatTextView)findViewById(R.id.v_degree);
        v_workingsector = (AppCompatTextView)findViewById(R.id.v_workingsector);
        v_occupation = (AppCompatTextView)findViewById(R.id.v_occupation);
        v_monthlyincome = (AppCompatTextView)findViewById(R.id.v_monthlyincome);
        v_familytype = (AppCompatTextView)findViewById(R.id.v_familytype);
        v_familystatus = (AppCompatTextView)findViewById(R.id.v_familystatus);
        v_fatheroccupation = (AppCompatTextView)findViewById(R.id.v_fatheroccupation);
        v_motheroccupation = (AppCompatTextView)findViewById(R.id.v_motheroccupation);
        v_numberofsibiling = (AppCompatTextView)findViewById(R.id.v_numberofsibiling);
        v_foodhabit = (AppCompatTextView)findViewById(R.id.v_foodhabit);
        v_somokinghabit = (AppCompatTextView)findViewById(R.id.v_somokinghabit);
        DRINKING_HABIT = (AppCompatTextView)findViewById(R.id.DRINKING_HABIT);
        v_nationality = (AppCompatTextView)findViewById(R.id.v_nationality);
        v_country = (AppCompatTextView)findViewById(R.id.v_country);
        v_state = (AppCompatTextView)findViewById(R.id.v_state);
        v_city = (AppCompatTextView)findViewById(R.id.v_city);
        v_abtmyself = (AppCompatTextView)findViewById(R.id.v_abtmyself);
        v_abtfamily = (AppCompatTextView)findViewById(R.id.v_abtfamily);
        profile=(AppCompatImageView)findViewById(R.id.profile);

        v_age=(AppCompatTextView)findViewById(R.id.v_age);
        intro=(AppCompatTextView)findViewById(R.id.intro);
        get_todaymatches();
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


    public void get_todaymatches() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getmatches";
        String url = URL_SINGLE_PROFILE+ userid;
        Log.e("url",url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Single-profile",response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("true")){
                            JSONObject user=response.getJSONObject("user");
                             user_name.setText(user.getString("name"));
                             v_name.setText(user.getString("name"));
                            v_gender.setText(user.getString("gender"));
                            v_dob.setText(user.getString("dob"));
                            v_age.setText(user.getString("age"));
                            v_religion.setText(user.getString("religion"));
                            v_languvage.setText(user.getString("mother_tongue"));
                            v_nationality.setText(user.getString("nationality"));
                            v_country.setText(user.getString("country"));
                            v_state.setText(user.getString("state"));
                            v_city.setText(user.getString("home_city"));
                            v_degree.setText(user.getString("education"));
                            v_occupation.setText(user.getString("occupation"));
                            v_caste.setText(user.getString("caste"));
                            v_ubcaste.setText(user.getString("sub_caste"));
                            v_ubcaste.setText(user.getString("sub_caste"));
                            v_fatheroccupation.setText(user.getString("father_occupation"));
                            v_motheroccupation.setText(user.getString("mother_occupation"));
                            v_numberofsibiling.setText(user.getString("no_of_siblings"));
                            v_familystatus.setText(user.getString("family_status"));
                            v_familystatus.setText(user.getString("family_type"));
                            v_paadham.setText(user.getString("paadham"));
                            v_dhosam.setText(user.getString("having_dosham"));
                            v_height.setText(user.getString("height"));
                            v_weight.setText(user.getString("weight"));
                            v_bodytype.setText(user.getString("body_type"));
                            v_complexion.setText(user.getString("complexion"));
                            v_somokinghabit.setText(user.getString("smoking_habits"));
                            DRINKING_HABIT.setText(user.getString("drinking_habits"));
                            v_foodhabit.setText(user.getString("eating_habits"));
                            v_abtmyself.setText(user.getString("about_myself"));
                            v_maritalstatus.setText(user.getString("marital_status"));
                            v_profilecreate.setText(user.getString("profile_created_for"));
                            v_disability.setText(user.getString("disability"));
                            v_foodhabit.setText(user.getString("food_habits"));
                            v_workingsector.setText(user.getString("working_sector"));
                            v_abtfamily.setText(user.getString("about_family"));
                            v_monthlyincome.setText(user.getString("max_income"));
                                intro.setText("Hi I am "+user.getString("name")+" i am a "+user.getString("education")+" Graduate");
                                String s_gender= user.getString("gender");
                                if (s_gender.equalsIgnoreCase("Male")){
                                    UrlImageViewHelper.setUrlDrawable(profile, "https://nammamatrimony.in/uploads/profile_image/" + user.getString("profile_image"), R.drawable.female_noimage);

                                }else {
                                    UrlImageViewHelper.setUrlDrawable(profile, "https://nammamatrimony.in/uploads/profile_image/" + user.getString("profile_image"), R.drawable.male_noimage);

                                }

                            }


                        } catch (JSONException e) {



                        } catch (JsonParseException e) {

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        if (Utils.isConnected(this)) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {

    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {

    }

    @Override
    public void isNetworkAvailable(boolean flag) {

    }

    @Override
    public void onProgressStart() {

    }

    @Override
    public void onProgressEnd() {

    }
}
