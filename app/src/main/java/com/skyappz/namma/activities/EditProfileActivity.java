package com.skyappz.namma.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.profileupdate.ProfileUpdate;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity  implements WebServiceListener, View.OnClickListener {
    AppCompatTextView text_about_myself,text_name,text_age,text_height,text_weight,test_marital_status,text_mother_tongue,text_physical_status,text_body_type
    ,text_complexion,text_profile_created_by,text_edting_habit,text_drinking_habit,text_smoking_habit,text_religion,text_caste,text_star,text_raasi
    ,text_dhosam,text_education,text_college,text_oupation,text_organization,text_working_sector,text_income,text_country,text_state,text_city,text_family_value
    ,text_family_type,text_family_status,text_father_occ,text_mother_occc,text_num_sibling,text_family_location,text_abt_family,text_p_age
            ,text_p_height,text_p_marital_status,text_p_physical_status,text_p_eating_habits,text_p_drinking,text_p_smoking,text_p_mother_toungue
            ,text_p_religion,text_p_caste,text_p_dhosam,text_p_education,text_p_occu,text_p_incom,text_p_country,text_p_state,text_p_about_patner;

    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
    private static final String URL_GET_PARTNER = "https://nammamatrimony.in/api/getpartner.php?user_id=";

    AppCompatImageView edit_aboutmyself,edit_basic,edit_religious,edit_personal_info,edit_location,edit_family_details;
    RelativeLayout edit_partner_religious,edit_partner_basic,edit_partner_professional,edit_partner_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        inti();
        get_user();
        get_partner();
    }
     private void inti(){

         text_about_myself=(AppCompatTextView)findViewById(R.id.text_about_myself);
         text_name=(AppCompatTextView)findViewById(R.id.text_name);
         text_age=(AppCompatTextView)findViewById(R.id.text_age);
         text_height=(AppCompatTextView)findViewById(R.id.text_height);
         text_weight=(AppCompatTextView)findViewById(R.id.text_weight);
         test_marital_status=(AppCompatTextView)findViewById(R.id.test_marital_status);
         text_mother_tongue=(AppCompatTextView)findViewById(R.id.text_mother_tongue);
         text_physical_status=(AppCompatTextView)findViewById(R.id.text_physical_status);
         text_body_type=(AppCompatTextView)findViewById(R.id.text_body_type);
         text_complexion=(AppCompatTextView)findViewById(R.id.text_complexion);
         text_profile_created_by=(AppCompatTextView)findViewById(R.id.text_profile_created_by);
         text_edting_habit=(AppCompatTextView)findViewById(R.id.text_edting_habit);
         text_drinking_habit=(AppCompatTextView)findViewById(R.id.text_drinking_habit);
         text_smoking_habit=(AppCompatTextView)findViewById(R.id.text_smoking_habit);
         text_religion=(AppCompatTextView)findViewById(R.id.text_religion);
         text_caste=(AppCompatTextView)findViewById(R.id.text_caste);
         text_star=(AppCompatTextView)findViewById(R.id.text_star);
         text_raasi=(AppCompatTextView)findViewById(R.id.text_raasi);
         text_dhosam=(AppCompatTextView)findViewById(R.id.text_dhosam);
         text_education=(AppCompatTextView)findViewById(R.id.text_education);
         text_college=(AppCompatTextView)findViewById(R.id.text_college);
         text_oupation=(AppCompatTextView)findViewById(R.id.text_oupation);
         text_organization=(AppCompatTextView)findViewById(R.id.text_organization);
         text_working_sector=(AppCompatTextView)findViewById(R.id.text_working_sector);
         text_income=(AppCompatTextView)findViewById(R.id.text_income);
         text_country=(AppCompatTextView)findViewById(R.id.text_country);
         text_family_type=(AppCompatTextView)findViewById(R.id.text_family_type);
         text_state=(AppCompatTextView)findViewById(R.id.text_state);
         text_family_status=(AppCompatTextView)findViewById(R.id.text_family_status);
         text_father_occ=(AppCompatTextView)findViewById(R.id.text_father_occ);
         text_mother_occc=(AppCompatTextView)findViewById(R.id.text_mother_occc);
         text_city=(AppCompatTextView)findViewById(R.id.text_city);
         text_num_sibling=(AppCompatTextView)findViewById(R.id.text_num_sibling);
         text_family_value=(AppCompatTextView)findViewById(R.id.text_family_value);
         text_family_location=(AppCompatTextView)findViewById(R.id.text_family_location);
         text_abt_family=(AppCompatTextView)findViewById(R.id.text_abt_family);

         text_p_age=(AppCompatTextView)findViewById(R.id.text_p_age);
         text_p_height=(AppCompatTextView)findViewById(R.id.text_p_height);
         text_p_marital_status=(AppCompatTextView)findViewById(R.id.text_p_marital_status);
         text_p_physical_status=(AppCompatTextView)findViewById(R.id.text_p_physical_status);
         text_p_eating_habits=(AppCompatTextView)findViewById(R.id.text_p_eating_habits);
         text_p_drinking=(AppCompatTextView)findViewById(R.id.text_p_drinking);
         text_p_smoking=(AppCompatTextView)findViewById(R.id.text_p_smoking);
         text_p_mother_toungue=(AppCompatTextView)findViewById(R.id.text_p_mother_toungue);
         text_p_religion=(AppCompatTextView)findViewById(R.id.text_p_religion);
         text_p_caste=(AppCompatTextView)findViewById(R.id.text_p_caste);
         text_p_dhosam=(AppCompatTextView)findViewById(R.id.text_p_dhosam);
         text_p_education=(AppCompatTextView)findViewById(R.id.text_p_education);
         text_p_occu=(AppCompatTextView)findViewById(R.id.text_p_occu);
         text_p_incom=(AppCompatTextView)findViewById(R.id.text_p_incom);
         text_p_country=(AppCompatTextView)findViewById(R.id.text_p_country);
         text_p_state=(AppCompatTextView)findViewById(R.id.text_p_state);
         text_p_about_patner=(AppCompatTextView)findViewById(R.id.text_p_about_patner);

         edit_aboutmyself=(AppCompatImageView)findViewById(R.id.edit_aboutmyself);
         edit_aboutmyself.setOnClickListener(this);

                 edit_basic=(AppCompatImageView)findViewById(R.id.edit_basic);
         edit_basic.setOnClickListener(this);

         edit_religious=(AppCompatImageView)findViewById(R.id.edit_religious);
         edit_religious.setOnClickListener(this);

         edit_personal_info=(AppCompatImageView)findViewById(R.id.edit_personal_info);
         edit_personal_info.setOnClickListener(this);

         edit_location=(AppCompatImageView)findViewById(R.id.edit_location);
         edit_location.setOnClickListener(this);

         edit_family_details=(AppCompatImageView)findViewById(R.id.edit_family_details);
         edit_family_details.setOnClickListener(this);

         edit_partner_religious = (RelativeLayout)findViewById(R.id.edit_partner_religious);
         edit_partner_religious.setOnClickListener(this);

         edit_partner_basic = (RelativeLayout)findViewById(R.id.edit_partner_basic);
         edit_partner_basic.setOnClickListener(this);

         edit_partner_professional = (RelativeLayout)findViewById(R.id.edit_partner_professional);
         edit_partner_professional.setOnClickListener(this);

         edit_partner_location = (RelativeLayout)findViewById(R.id.edit_partner_location);
         edit_partner_location.setOnClickListener(this);
     }

    public void get_partner() {
        Log.e("getuser","getuser");
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "get_user";
        String url = URL_GET_PARTNER+ AppController.get_userid(getApplicationContext()) ;
        Log.e("url",url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("user",response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("true")){
                                JSONObject user=response.getJSONObject("user");
                                text_p_age.setText(user.getString("min_age")+" - "+user.getString("max_age"));
                                text_p_height.setText(user.getString("min_height")+" - "+user.getString("max_height"));
                                text_p_marital_status.setText(user.getString("marital_status"));
                                test_marital_status.setText(user.getString("marital_status"));
                                text_p_physical_status.setText(user.getString("physical_status"));
                                text_p_mother_toungue.setText(user.getString("mother_tongue"));
                                text_p_caste.setText(user.getString("caste"));
                                text_p_dhosam.setText(user.getString("dosham"));
                                text_p_education.setText(user.getString("education"));
                                text_p_occu.setText(user.getString("profession"));
                                text_p_incom.setText(user.getString("min_income")+" - "+user.getString("max_income"));
                                text_p_country.setText(user.getString("country"));
                                text_p_state.setText(user.getString("state"));


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

        if (Utils.isConnected((this))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }

    public void get_user() {
        Log.e("getuser","getuser");
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "get_user";
        String url = URL_GET_USER+ AppController.get_userid(getApplicationContext()) ;
        Log.e("url",url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("user",response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("true")){
                                JSONObject user=response.getJSONObject("user");
                                text_name.setText(user.getString("name"));
                                text_age.setText(user.getString("age"));
                                text_height.setText(user.getString("height"));
                                text_weight.setText(user.getString("weight"));
                                test_marital_status.setText(user.getString("marital_status"));
                                text_mother_tongue.setText(user.getString("mother_tongue"));
                                text_physical_status.setText(user.getString("physical_status"));
                                text_body_type.setText(user.getString("body_type"));
                                text_complexion.setText(user.getString("complexion"));
                                text_profile_created_by.setText(user.getString("profile_created_for"));
                                text_edting_habit.setText(user.getString("eating_habits"));
                                text_drinking_habit.setText(user.getString("drinking_habits"));
                                text_smoking_habit.setText(user.getString("smoking_habits"));
                                text_religion.setText(user.getString("religion"));
                                text_caste.setText(user.getString("caste"));
                                text_star.setText(user.getString("star"));
                                text_raasi.setText(user.getString("raasi"));
                                text_dhosam.setText(user.getString("dosham_details"));
                                text_education.setText(user.getString("education"));
                                text_college.setText(user.getString("college"));
                                text_oupation.setText(user.getString("occupation"));
                                text_organization.setText(user.getString("office_details"));
                                text_working_sector.setText(user.getString("working_sector"));
                                text_income.setText(user.getString("max_income"));
                                text_country.setText(user.getString("country"));
                                text_state.setText(user.getString("state"));
                                text_city.setText(user.getString("home_city"));
                                text_family_type.setText(user.getString("family_type"));
                                text_family_status.setText(user.getString("family_status"));
                                text_father_occ.setText(user.getString("father_occupation"));
                                text_mother_occc.setText(user.getString("mother_occupation"));
                                text_num_sibling.setText(user.getString("no_of_siblings"));
                                text_family_location.setText(user.getString("family_location"));
                                text_abt_family.setText(user.getString("about_family"));
                                text_p_about_patner.setText(user.getString("about_partner"));
                                text_about_myself.setText(user.getString("about_myself"));

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

        if (Utils.isConnected((this))) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_aboutmyself:
                Intent i=new Intent(getApplicationContext(), ProfileUpdate.class);
                i.putExtra("fragtype","0");
                startActivity(i);
                finish();
                break;
            case R.id.edit_basic:
                Intent basci=new Intent(getApplicationContext(), ProfileUpdate.class);
                basci.putExtra("fragtype","1");
                startActivity(basci);
                finish();
                break;
            case R.id.edit_religious:
                Intent religious=new Intent(getApplicationContext(), ProfileUpdate.class);
                religious.putExtra("fragtype","2");
                startActivity(religious);
                finish();
                break;
            case R.id.edit_personal_info:
                Intent personal=new Intent(getApplicationContext(), ProfileUpdate.class);
                personal.putExtra("fragtype","3");
                startActivity(personal);
                finish();
                break;
            case R.id.edit_location:
                Intent location=new Intent(getApplicationContext(), ProfileUpdate.class);
                location.putExtra("fragtype","4");
                startActivity(location);
                finish();
                break;
            case R.id.edit_family_details:
                Intent family=new Intent(getApplicationContext(), ProfileUpdate.class);
                family.putExtra("fragtype","5");
                startActivity(family);
                finish();
                break;
            case R.id.edit_partner_basic:
                Intent partnerbasic=new Intent(getApplicationContext(), ProfileUpdate.class);
                partnerbasic.putExtra("fragtype","6");
                startActivity(partnerbasic);
                finish();
                break;
            case R.id.edit_partner_religious:
                Intent partnerreligious=new Intent(getApplicationContext(), ProfileUpdate.class);
                partnerreligious.putExtra("fragtype","6");
                startActivity(partnerreligious);
                finish();
                break;

            case R.id.edit_partner_professional:
                Intent partnerpro=new Intent(getApplicationContext(), ProfileUpdate.class);
                partnerpro.putExtra("fragtype","7");
                startActivity(partnerpro);
                finish();
                break;


            case R.id.edit_partner_location:
                Intent partnerlocation=new Intent(getApplicationContext(), ProfileUpdate.class);
                partnerlocation.putExtra("fragtype","7");
                startActivity(partnerlocation);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
