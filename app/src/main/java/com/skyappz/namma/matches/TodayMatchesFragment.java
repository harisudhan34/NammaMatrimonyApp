package com.skyappz.namma.matches;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.activities.SearchActivity;
import com.skyappz.namma.adapter.ViewAllMatches;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TodayMatchesFragment extends Fragment implements WebServiceListener {

    private TodayMatchesViewModel mViewModel;
    RecyclerView all_todaymatches;
    ArrayList<User> todayMatches = new ArrayList<>();
    ViewAllMatches matchesadapter;
    AppCompatTextView  nodata;
    RelativeLayout sort_layout;
    String todate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    final LocalDate date = LocalDate.now();
    final LocalDate dateMinus7Days = date.minusDays(5);
    //Format and display date
    final String fromdate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE);
    private static final String URL_GET_DUMMY_MATCHES = "https://nammamatrimony.in/api/datematches.php?";
    private static final String URL_GET_TODAY_MATCHES = "https://nammamatrimony.in/api/todaymatches.php?user_id=";
    public static TodayMatchesFragment newInstance() {
        return new TodayMatchesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.today_matches_fragment, container, false);
        all_todaymatches =(RecyclerView)v.findViewById(R.id.all_todaymatches);
        nodata=(AppCompatTextView)v.findViewById(R.id.nodata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        all_todaymatches.setLayoutManager(mLayoutManager);
        all_todaymatches.setNestedScrollingEnabled(false);
        sort_layout=(RelativeLayout)v.findViewById(R.id.sort_layout);
        sort_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search=new Intent(getActivity(), SearchActivity.class);
                startActivity(search);
            }
        });
        get_todaymatches();
//        new get_all_matches().execute();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodayMatchesViewModel.class);
        // TODO: Use the ViewModel
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

    public class get_all_matches extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//                      if (!progressBar.isShown()) {
//                progressBar.setVisibility(View.VISIBLE);
////                txtAlert.setVisibility(8);
//            }

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            // parse json data from server in background
            // SplashActivity.parseJSON_Deals();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // otherwise, show alert text
//            if (progressBar.isShown())
//                progressBar.setVisibility(View.GONE);


        }
    }
    public void get_todaymatches() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getmatches";
        String url = URL_GET_TODAY_MATCHES+ AppController.get_userid(getActivity()) +"&type=today_matches&parameter=age,gender,height,weight,caste,sub_caste,marital_status,mother_tongue,raasi,star,having_dosham,occupation,physical_status,nationality,country,home_city,monthly_income,paadham,education" ;
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
                                if (todayMatches.size() > 5 ) {
                                    matchesadapter = new ViewAllMatches(getContext(), todayMatches);
                                    all_todaymatches.setAdapter(matchesadapter);

                                }else {
                                    dummydata2();
                                }

                            }else {
                                dummydata2();
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

        if (Utils.isConnected((getActivity()))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }

    public void dummydata2(){
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "dummy";
        String url = URL_GET_DUMMY_MATCHES;
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String myjsonString = response;
                            Log.e("dummy1",myjsonString);
                            JSONObject jsonObject = new JSONObject(myjsonString);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")){
                                JSONArray m_jArry = jsonObject.getJSONArray("matches");
                                for (int i=0; i<m_jArry.length();i++){
                                    JSONObject listobj =m_jArry.getJSONObject(i);
                                    User user =new User();
                                    user.setProfile_image(listobj.getString("profile_image"));
                                    user.setUser_id(listobj.getString("user_id"));
                                    user.setName(listobj.getString("name"));
                                    user.setReligion(listobj.getString("religion"));
                                    user.setHome_city(listobj.getString("home_city"));
                                    user.setHeight(listobj.getString("height"));
                                    todayMatches.add(user);

                                }
                                Log.e("toadayma",String.valueOf(todayMatches.size()));
                                if (todayMatches.size() > 0) {
                                    matchesadapter = new ViewAllMatches(getContext(), todayMatches);
                                    all_todaymatches.setAdapter(matchesadapter);
                                }
                            }else {

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", AppController.get_userid(getActivity()));
                params.put("fromdate", fromdate);
                params.put("todate", todate);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (Utils.isConnected((getActivity()))) {
            AppController.getInstance().addToRequestQueue(jsonObjRequest, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }    }



}
