package com.skyappz.namma.dashboard;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.ResponseEntities.UserListEntity;
import com.skyappz.namma.activities.DiscoverMatches;
import com.skyappz.namma.activities.EducationOption;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.activities.ProfessionOption;
import com.skyappz.namma.activities.SingleProfileView;
import com.skyappz.namma.adapter.UserPagerAdapter;
import com.skyappz.namma.matches.TodayMatchesViewModel;
import com.skyappz.namma.matches.ViewAllMatchess;
import com.skyappz.namma.model.OTPRequest;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.VolleyMultipartRequest;
import com.skyappz.namma.webservice.WebServiceListener;
import com.skyappz.namma.webservice.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardFragment extends Fragment implements WebServiceListener, View.OnClickListener {
    private WebServiceManager webServiceManager;
    public Activity mActivity;
    private TodayMatchesViewModel mViewModel;
    AppCompatTextView no_data_today,nodata_perimium,nodata_recommend,username,userid,recommended_seemore,premium_seemore,today_seemore,discover_seemore;
    AppCompatImageView coverimage;
    CircleImageView profile_image;
//    ViewPager vpTodayMatches, vpPremium, vpRecommendations;
    private View view;
    private static final String URL_GET_TODAY_MATCHES = "https://nammamatrimony.in/api/todaymatches.php?user_id=";
    private static final String URL_GET_DUMMY_MATCHES = "https://nammamatrimony.in/api/datematches.php?";
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
   View today_matchesview,recommendmatchesview,premiummatchesview;
    ArrayList<User> todayMatches = new ArrayList<>();
    ArrayList<User> perimium = new ArrayList<>();
    ArrayList<User> recommended = new ArrayList<>();
    UserPagerAdapter todayMatchesAdapter, premiumMatchesAdapter, recommendationsAdapter;
    LinearLayout vpTodayMatches,vpRecommend_matches,vpPreimiusmatches;
    String s_username,s_userid,s_profileimage,s_coverimage,s_gender="";
    LinearLayout education_layout,profession_layout,star_layout;
    String todate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    final LocalDate date = LocalDate.now();
    final LocalDate dateMinus7Days = date.minusDays(5);
    //Format and display date
    final String fromdate = dateMinus7Days.format(DateTimeFormatter.ISO_LOCAL_DATE);

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
//        vpTodayMatches = view.findViewById(R.id.vpTodayMatches);
//        vpPremium = view.findViewById(R.id.vpPremium);
//        vpRecommendations = view.findViewById(R.id.vpRecommendations);
//        todayMatches.add(new User());
//        todayMatches.add(new User());
//        todayMatches.add(new User());
//        todayMatches.add(new User());
//        todayMatches.add(new User());
//        todayMatches.add(new User());
//        todayMatchesAdapter = new UserPagerAdapter(getActivity(), todayMatches);
//        vpTodayMatches.setAdapter(todayMatchesAdapter);
//        premiumMatchesAdapter = new UserPagerAdapter(getActivity(), todayMatches);
//        vpPremium.setAdapter(premiumMatchesAdapter);
//        recommendationsAdapter = new UserPagerAdapter(getActivity(), todayMatches);
//        vpRecommendations.setAdapter(recommendationsAdapter);

        vpTodayMatches = (LinearLayout)view.findViewById(R.id.vpTodayMatches);
        vpRecommend_matches = (LinearLayout)view.findViewById(R.id.vpRecommend_matches);
        String [] types = getResources().getStringArray(R.array.types);
        vpPreimiusmatches = (LinearLayout)view.findViewById(R.id.vpPreimiusmatches);
        no_data_today =(AppCompatTextView)view.findViewById(R.id.no_matches_today);
        nodata_perimium =(AppCompatTextView)view.findViewById(R.id.no_matches_premium);
        nodata_recommend =(AppCompatTextView)view.findViewById(R.id.no_matches_reecommend);
        username =(AppCompatTextView)view.findViewById(R.id.username);
        userid =(AppCompatTextView)view.findViewById(R.id.userid);
        today_seemore =(AppCompatTextView)view.findViewById(R.id.today_seemore);
        today_seemore.setOnClickListener(this);
        premium_seemore =(AppCompatTextView)view.findViewById(R.id.premium_seemore);
        premium_seemore.setOnClickListener(this);
        recommended_seemore =(AppCompatTextView)view.findViewById(R.id.recommended_seemore);
        recommended_seemore.setOnClickListener(this);
        discover_seemore=(AppCompatTextView)view.findViewById(R.id.discover_seemore);
        discover_seemore.setOnClickListener(this);
        coverimage=(AppCompatImageView)view.findViewById(R.id.coverimage);
        profile_image=(CircleImageView)view.findViewById(R.id.profile_image);
        education_layout=(LinearLayout)view.findViewById(R.id.education_layout);
        education_layout.setOnClickListener(this);
        profession_layout=(LinearLayout)view.findViewById(R.id.profession_layout);
        profession_layout.setOnClickListener(this);
        star_layout=(LinearLayout)view.findViewById(R.id.star_layout);
        star_layout.setOnClickListener(this);
        get_user();
        get_perimium();
        get_todaymatches();
        get_recommended();
//        loadcity();
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
//    public void get_todayMatches(){
//        webServiceManager = new WebServiceManager(mActivity);
//        webServiceManager.getTodayMatches(type,userid, this);
//    }



    public void get_user() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "get_user";
        String url = URL_GET_USER+ AppController.get_userid(getActivity()) ;
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
                               s_username=user.getString("name");

                               s_userid=user.getString("user_id");
                               s_profileimage=user.getString("profile_image");
                               s_coverimage=user.getString("cover_image");
                               s_gender=user.getString("gender");
                               username.setText(s_username + " ( "+"NM-"+s_userid +" )");
                               userid.setText("NM-"+s_userid);
                               if (s_gender.equalsIgnoreCase("male")){
                                   UrlImageViewHelper.setUrlDrawable(profile_image, "https://nammamatrimony.in/uploads/profile_image/" + s_profileimage, R.drawable.dashboardicon);

                               }else {
                                   UrlImageViewHelper.setUrlDrawable(profile_image, "https://nammamatrimony.in/uploads/profile_image/" + s_profileimage, R.drawable.dashboardicon);

                               }
//                                UrlImageViewHelper.setUrlDrawable(coverimage, "https://nammamatrimony.in/uploads/cover_image/" + s_coverimage, R.drawable.loading);

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


    public void get_todaymatches() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getmatches";
        String url = URL_GET_TODAY_MATCHES+ AppController.get_userid(getActivity()) +"&type=today_matches&parameter=gender,age,height,weight,caste,sub_caste,marital_status,mother_tongue,raasi,star,having_dosham,dosham_details,occupation,physical_status,nationality,country,home_city,paadham,education,state" ;
        Log.e("url",url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("today_matches",response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("true")){
                                JSONArray m_jArry = response.getJSONArray("Today Matches");
                                for (int i=0; i< m_jArry.length();i++){

                                    JSONObject listobj =m_jArry.getJSONObject(i);
                                    User user =new User();
                                    user.setProfile_image(listobj.getString("profile_image"));
                                    user.setUser_id(listobj.getString("user_id"));

                                    user.setName(listobj.getString("name"));
                                    user.setAge(listobj.getString("age"));
                                    user.setReligion(listobj.getString("religion"));
                                    user.setHome_city(listobj.getString("home_city"));
                                    user.setHeight(listobj.getString("height"));
                                    todayMatches.add(user);
                                    Log.e("test",String.valueOf(todayMatches.size()));

                                }
                                Log.e("toadayma",String.valueOf(todayMatches.size()));
                                if (todayMatches.size() > 5) {
                                    totaymatches_set(todayMatches);
                                }else {
                                    dummydata2();

                                }
                            }else {
//                                vpTodayMatches.setVisibility(View.GONE);
//                                no_data_today.setVisibility(View.VISIBLE);
//                                dummy_data();

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
                                    user.setAge(listobj.getString("age"));
                                    todayMatches.add(user);
                                    Log.e("toadayma",String.valueOf(todayMatches.size()));

                                }
                                Log.e("toadayma",String.valueOf(todayMatches.size()));
                                if (todayMatches.size() > 0) {
                                    totaymatches_set(todayMatches);
                                }
                            }else {
                                vpTodayMatches.setVisibility(View.GONE);
                                no_data_today.setVisibility(View.VISIBLE);
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


    public void get_recommended() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getmatches";
        String url = URL_GET_TODAY_MATCHES+ AppController.get_userid(getActivity()) +"&type=recommendation_matches&parameter=age,gender,height,weight,caste,sub_caste,marital_status,mother_tongue,raasi,star,having_dosham,occupation,physical_status,nationality,country,home_city,monthly_income,paadham,education" ;
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
                                for (int i=0; i<20;i++){
                                    JSONObject listobj =m_jArry.getJSONObject(i);
                                    User user =new User();
                                    user.setUser_id(listobj.getString("user_id"));
                                    user.setProfile_image(listobj.getString("profile_image"));
                                    user.setName(listobj.getString("name"));
                                    user.setReligion(listobj.getString("religion"));
                                    user.setHome_city(listobj.getString("home_city"));
                                    user.setHeight(listobj.getString("height"));
                                    user.setAge(listobj.getString("age"));
                                    recommended.add(user);

                                }

                                if (recommended.size() > 0) {
                                    recommendmatches_set(recommended);
                                }

                            }else {
                                vpRecommend_matches.setVisibility(View.GONE);
                                nodata_recommend.setVisibility(View.VISIBLE);
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

    public void get_perimium() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getmatches";
        String url = URL_GET_TODAY_MATCHES+ AppController.get_userid(getActivity()) +"&type=premium_matches&parameter=age,gender,height,weight,caste,sub_caste,marital_status,mother_tongue,raasi,star,having_dosham,occupation,physical_status,nationality,country,home_city,monthly_income,paadham,education" ;
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
                                    user.setReligion(listobj.getString("religion"));
                                    user.setHome_city(listobj.getString("home_city"));
                                    user.setHeight(listobj.getString("height"));
                                    user.setAge(listobj.getString("age"));
                                    perimium.add(user);

                                }

                                if (perimium.size() > 0) {
                                    premiummatches_set(perimium);
                                }

                        }else {
                            vpPreimiusmatches.setVisibility(View.GONE);
                            nodata_perimium.setVisibility(View.VISIBLE);
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



//    public String loadcityjson() {
//        String json = null;
//        try {
//            InputStream is = getActivity().getAssets().open("todaymatches.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//    public  void loadcity(){
//        todayMatches=new ArrayList<>();
//        try {
//            JSONObject obj = new JSONObject(loadcityjson());
//            JSONArray m_jArry = obj.getJSONArray("Today Matches");
//            for (int i=0; i<m_jArry.length();i++){
//                JSONObject listobj =m_jArry.getJSONObject(i);
//                User user =new User();
//                user.setProfile_image(listobj.getString("profile_image"));
//                user.setName(listobj.getString("name"));
//                user.setReligion(listobj.getString("religion"));
//                user.setHome_city(listobj.getString("home_city"));
//                todayMatches.add(user);
//            }
//            if (todayMatches.size() > 0) {
//                totaymatches_set(todayMatches);
//                recommendmatches_set(todayMatches);
//                premiummatches_set(todayMatches);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void totaymatches_set(final ArrayList<User> data) {
//        int amount = 0;
//        if (data.size() <= 5) {
//            amount = data.size();
//        } else amount = 5;
        for (int i = 0; i < data.size(); i++) {

            today_matchesview = getLayoutInflater().inflate(R.layout.match_item, vpTodayMatches, false);
            today_matchesview.setId(i);
            vpTodayMatches.addView(today_matchesview);
            TextView name = (TextView) today_matchesview.findViewById(R.id.name);
            TextView caste = (TextView) today_matchesview.findViewById(R.id.caste);
            TextView city = (TextView) today_matchesview.findViewById(R.id.city);
            AppCompatImageView profile_pic =(AppCompatImageView)today_matchesview.findViewById(R.id.profile_image);
            LinearLayout  card=(LinearLayout) today_matchesview.findViewById(R.id.profile);
            final String aa=data.get(i).getUser_id();

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(), SingleProfileView.class);
                    i.putExtra("user_id",aa);
                    startActivity(i);
                }
            });
            name.setText(data.get(i).getName()+" ,"+data.get(i).getAge());
            caste.setText(data.get(i).getReligion());
            if (s_gender.equalsIgnoreCase("male")){
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.female_noimage);

            }else {
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.male_noimage);

            }
            city.setText(data.get(i).getHeight());

        }
    }

    public void recommendmatches_set(final ArrayList<User> data) {
//        int amount = 0;
//        if (data.size() <= 5) {
//            amount = data.size();
//        } else amount = 5;
        for (int i = 0; i < data.size(); i++) {

            recommendmatchesview = getLayoutInflater().inflate(R.layout.match_item3, vpRecommend_matches, false);
            recommendmatchesview.setId(i);
            vpRecommend_matches.addView(recommendmatchesview);
            TextView name = (TextView) recommendmatchesview.findViewById(R.id.name);
            TextView caste = (TextView) recommendmatchesview.findViewById(R.id.caste);
            TextView city = (TextView) recommendmatchesview.findViewById(R.id.city);
            AppCompatImageView profile_pic =(AppCompatImageView)recommendmatchesview.findViewById(R.id.profile_image);
            LinearLayout  card=(LinearLayout) recommendmatchesview.findViewById(R.id.profile);
            final String aa=data.get(i).getUser_id();
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(), SingleProfileView.class);
                    i.putExtra("user_id",aa);
                    startActivity(i);
                }
            });

            name.setText(data.get(i).getName()+" ,"+data.get(i).getAge());
            caste.setText(data.get(i).getReligion());
            if (s_gender.equalsIgnoreCase("male")){
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.female_noimage);

            }else {
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.male_noimage);

            }            city.setText(data.get(i).getHeight());

        }
    }
    public void premiummatches_set(final ArrayList<User> data) {
//        int amount = 0;
//        if (data.size() <= 5) {
//            amount = data.size();
//        } else amount = 5;
        for (int i = 0; i < data.size(); i++) {

            premiummatchesview = getLayoutInflater().inflate(R.layout.match_item2, vpPreimiusmatches, false);
            premiummatchesview.setId(i);
            vpPreimiusmatches.addView(premiummatchesview);
            TextView name = (TextView) premiummatchesview.findViewById(R.id.name);
            TextView caste = (TextView) premiummatchesview.findViewById(R.id.caste);
            TextView city = (TextView) premiummatchesview.findViewById(R.id.city);
            AppCompatImageView profile_pic =(AppCompatImageView)premiummatchesview.findViewById(R.id.profile_image);
            RelativeLayout card=(RelativeLayout) premiummatchesview.findViewById(R.id.profile);
            final String aa=data.get(i).getUser_id();
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(), SingleProfileView.class);
                    i.putExtra("user_id",aa);
                    startActivity(i);

                }
            });

            name.setText(data.get(i).getName()+" ,"+data.get(i).getAge());
            caste.setText(data.get(i).getReligion());
            if (s_gender.equalsIgnoreCase("male")){
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.female_noimage);

            }else {
                UrlImageViewHelper.setUrlDrawable(profile_pic, "https://nammamatrimony.in/uploads/profile_image/" + data.get(i).getProfile_image(), R.drawable.male_noimage);

            }            city.setText(data.get(i).getHeight());

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(TodayMatchesViewModel.class);
//        mViewModel.setActivity(getActivity());
//        mViewModel.getTodayMatches("1");
//        subscribe();
    }

    private void subscribe() {
        final Observer<ArrayList<User>> getUserReceiver = new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<User> users) {
                todayMatches.clear();
                todayMatches.addAll(users);
                todayMatchesAdapter.notifyDataSetChanged();
                premiumMatchesAdapter.notifyDataSetChanged();
            }
        };
        mViewModel.getUsers().observeForever(getUserReceiver);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        if (requestCode == 1010){
            Utils.showToast(getActivity(), ((UserListEntity) response).getMsg());
            Log.e("today",((UserListEntity) response).getMsg());
//            totaymatches_set(((UserListEntity) response).getMsg())
        }else if (requestCode == 1013){
            Utils.showToast(getActivity(), ((UserListEntity) response).getMsg());
        }else if (requestCode == 1014){
            Utils.showToast(getActivity(), ((UserListEntity) response).getMsg());
        }

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
            case R.id.today_seemore:{
                Intent i=new Intent(getActivity(), ViewAllMatchess.class);
                i.putExtra("match_type","today");
                startActivity(i);
                break;
            }
            case R.id.premium_seemore:{
                Intent i=new Intent(getActivity(), ViewAllMatchess.class);
                i.putExtra("match_type","premium");
                startActivity(i);
                break;
            }
            case R.id.recommended_seemore:{
                Intent i=new Intent(getActivity(), ViewAllMatchess.class);
                i.putExtra("match_type","recommended");
                startActivity(i);
                break;
            }

            case R.id.discover_seemore:{
                Intent i=new Intent(getActivity(), EducationOption.class);
                startActivity(i);
                break;
            }
            case R.id.education_layout:{
                Intent i=new Intent(getActivity(), EducationOption.class);
                startActivity(i);
                break;
            }
            case R.id.profession_layout:{
                Intent i=new Intent(getActivity(), ProfessionOption.class);
                startActivity(i);
                break;
            }
            case R.id.star_layout:{
                Intent i=new Intent(getActivity(), EducationOption.class);
                startActivity(i);
                break;
            }
        }
    }
}
