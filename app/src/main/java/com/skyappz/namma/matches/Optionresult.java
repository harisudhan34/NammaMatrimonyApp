package com.skyappz.namma.matches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.activities.SearchActivity;
import com.skyappz.namma.adapter.ViewAllMatches;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Optionresult extends AppCompatActivity implements WebServiceListener {
    RecyclerView all_todaymatches;
    ArrayList<User> todayMatches = new ArrayList<>();
    ViewAllMatches matchesadapter;
    AppCompatTextView nodata;
    RelativeLayout sort_layout;
    String s_education,type,s_profession_type;
    private static final String URL_GET_TODAY_MATCHES = "https://nammamatrimony.in/api/discover_matches.php?user_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionresult);
        type=(getIntent().getStringExtra("type"));
        if (type.equalsIgnoreCase("education")){
            s_education=getIntent().getStringExtra("education");
            get_education_matches("education_matches");
        }
        if (type.equalsIgnoreCase("profession")){
            s_education=getIntent().getStringExtra("profession");
            get_education_matches("profession_matches");
        }
        all_todaymatches =(RecyclerView)findViewById(R.id.all_todaymatches);
        nodata=(AppCompatTextView)findViewById(R.id.nodata);
        sort_layout=(RelativeLayout)findViewById(R.id.sort_layout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        all_todaymatches.setLayoutManager(mLayoutManager);
        all_todaymatches.setNestedScrollingEnabled(false);
    }

    public void get_education_matches(String type) {
        String tag_json_obj = "getmatches";
        String url = URL_GET_TODAY_MATCHES+ AppController.get_userid(getApplicationContext()) +"&type="+type+"&parameter=age,gender,height,weight,caste,marital_status,mother_tongue,raasi,having_dosham,physical_status,paadham,dosham_details&education="+s_education ;
        Log.e("url",url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("matches",response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("true")){
                                JSONArray m_jArry = response.getJSONArray("Today Matches");
                                for (int i=0; i<m_jArry.length();i++){
                                    JSONObject listobj =m_jArry.getJSONObject(i);
                                    User user =new User();
                                    user.setUser_id(listobj.getString("user_id"));
                                    user.setProfile_image(listobj.getString("profile_image"));
                                    user.setName(listobj.getString("name"));
                                    user.setHeight(listobj.getString("height"));
                                    user.setReligion(listobj.getString("religion"));
                                    user.setCaste(listobj.getString("caste"));
                                    user.setHome_city(listobj.getString("home_city"));
                                    user.setState(listobj.getString("state"));
                                    todayMatches.add(user);

                                }
                                Log.e("toadaymafragment",String.valueOf(todayMatches.size()));
                                if (todayMatches.size() > 0 ) {
                                    matchesadapter = new ViewAllMatches(getApplicationContext(), todayMatches);
                                    all_todaymatches.setAdapter(matchesadapter);

                                }

                            }else {
                                nodata.setVisibility(View.VISIBLE);
                                all_todaymatches.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"no matches",Toast.LENGTH_SHORT).show();
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
