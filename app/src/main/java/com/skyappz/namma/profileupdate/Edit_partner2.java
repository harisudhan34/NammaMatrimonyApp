package com.skyappz.namma.profileupdate;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.Setpartner;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.editprofile.UserDetailsViewModel;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_UPLOAD_ID;
import static com.skyappz.namma.activities.HomeActivity.userid;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_partner2 extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, WebServiceListener {

    AppCompatAutoCompleteTextView spEducation,spProfession,spNationality,spPreferredstate,spPreferredCities;
    AppCompatSpinner spCountry;
    ArrayList<String> state_list =new ArrayList<>();
    ArrayList<String> state_list_id =new ArrayList<>();
    ArrayList<String> city_list = new ArrayList<>();
    ArrayList<String> city_list_id = new ArrayList<>();
    ArrayAdapter natioalityadapter,disabilityadapter;
    String s_degree,s_myocc,s_nationality,s_country,s_sate,s_city,errorMsg,s_state_id,s_ccity_id;
    AppCompatButton update;
    GifImageView progress ;
    private static final String URL_GET_PARTNER = "https://nammamatrimony.in/api/getpartner.php?user_id=";
    User user;
    private UserDetailsViewModel userDetailsViewModel;
    HashMap<String, String> params = new HashMap<>();
    private Activity mActivity;
    public Edit_partner2() {
        // Required empty public constructor
    }

    public static Edit_partner2 newInstance() {
        return new Edit_partner2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_edit_partner2, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());
        update=(AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);
        progress=(GifImageView)view.findViewById(R.id.progress);
        spEducation=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spEducation);
        ArrayList<String> degreeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.degrees)));
        CustomListAdapter degreeadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, degreeList);
        spEducation.setAdapter(degreeadapter);
        spEducation.setOnItemClickListener(degreeListner);

        spProfession=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spProfession);
        ArrayList<String> occlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.jobs)));
        CustomListAdapter occAdaoter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, occlist);
        spProfession.setAdapter(occAdaoter);
        spProfession.setOnItemClickListener(myoccListner);

        spNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spNationality);
        ArrayList<String> nationalityList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality)));
         natioalityadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, nationalityList);
        spNationality.setAdapter(natioalityadapter);
        spNationality.setOnItemClickListener(nationlistylistner);

        spCountry=(AppCompatSpinner)view.findViewById(R.id.spCountry);
        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        disabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        disabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spCountry.setAdapter(disabilityadapter);
        spCountry.setOnItemSelectedListener(this);

        spPreferredstate=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredstate);
//        loadStateandDist();

        spPreferredCities=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredCities);
        getstate();
        CustomListAdapter stateadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, state_list);
        spPreferredstate.setAdapter(stateadapter);
        spPreferredstate.setOnItemClickListener(stateListner);
        spPreferredCities.setOnItemClickListener(citylistner);



        return view;
    }

    public void get_user() {
        Log.e("getuser","getuser");
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "get_user";
        String url = URL_GET_PARTNER+ AppController.get_userid(getActivity()) ;
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

                                spEducation.setText(user.getString("education"));
                                spProfession.setText(user.getString("profession"));
                                spPreferredstate.setText(user.getString("state"));
                                spPreferredCities.setText(user.getString("preferred_cities"));
                                int selectionPosition= natioalityadapter.getPosition(user.getString("nationality"));
                                spNationality.setSelection(selectionPosition);

                                int selectionPosition1= disabilityadapter.getPosition(user.getString("country"));
                                spCountry.setSelection(selectionPosition1);

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



    private AdapterView.OnItemClickListener citylistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_city = String.valueOf(adapterView.getItemAtPosition(i));
                    int castepos = city_list.indexOf(s_city);
                    String castid=  city_list_id.get(castepos);
                    Log.e("casteid",castid);
                    s_ccity_id = castid;
                }
            };

    private AdapterView.OnItemClickListener stateListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_sate = String.valueOf(adapterView.getItemAtPosition(i));
                    int statepos = state_list.indexOf(s_sate);
                    String stateid=  state_list_id.get(statepos);
                    Log.e("casteid",stateid);
                    s_state_id = stateid;
                    getcity(s_sate);


                }
            };

    private AdapterView.OnItemClickListener degreeListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_degree = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };

    private AdapterView.OnItemClickListener myoccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_myocc = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };


    private AdapterView.OnItemClickListener nationlistylistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_nationality = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.spCountry:
                s_country =spCountry.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:
                update();
        }
    }
    public void update() {
        progress.setVisibility(View.VISIBLE);

        s_city =spPreferredCities.getText().toString();
        if (Utils.isConnected(mActivity)) {
            if (isInputValidated(user)) {
                errorMsg = "";
                //sendOTP();
                checkparams();
                //showOTPRequestAlert();
            } else {
                Utils.showToast(mActivity, errorMsg);
            }
            //signUp(user);
        } else {
            Utils.showAlert(mActivity, mActivity.getResources().getString(R.string.no_internet));
        }

    }

    private  void checkparams() {
        params.put("user_id",userid);
        params.put("education", s_degree.toLowerCase());
        params.put("profession", s_myocc.toLowerCase());
        params.put("nationality", s_nationality.toLowerCase());
        params.put("country", s_country.toLowerCase());
        params.put("state", s_state_id);
        params.put("preferred_cities", s_ccity_id);
        userDetailsViewModel.setpartner(params, this);
    }
    private boolean isInputValidated(User user) {
        return true;
    }



    public void getstate() {
        HttpsTrustManager.allowAllSSL();
        spPreferredCities.setText("");
        String tag_json_obj = "getAllCaste";
        String url = "https://nammamatrimony.in/api/getstate.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("caste",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("states");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                state_list.add(list.getString("state"));
                                state_list_id.add(list.getString("id"));
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

    public void getcity(final  String state) {
        HttpsTrustManager.allowAllSSL();
        city_list = new ArrayList<>();
        city_list_id = new ArrayList<>();
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getAllSubCaste";
        String url = "https://nammamatrimony.in/api/getcity.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("subcaste",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("states");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                if (state.equalsIgnoreCase(list.getString("state"))){
                                    Log.e("statename",state);
                                    Log.e("statename",list.getString("state"));
                                    Log.e("eqeqe","state true");
                                    JSONArray subcasetarray=list.getJSONArray("cities");
                                    if (subcasetarray.length()> 0){
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            city_list.add(sublist.getString("city"));
                                            city_list_id.add(sublist.getString("id"));
                                            Log.e("givecity",sublist.getString("city"));
                                            CustomListAdapter cityadapter = new CustomListAdapter(getActivity(),
                                                    R.layout.right_menu_item, city_list);
                                            spPreferredCities.setAdapter(cityadapter);
                                            spPreferredCities.setOnItemClickListener(citylistner);
                                        }


                                        Log.e("eqeqe",String.valueOf(city_list.size()));


                                    }else {
                                    }
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

        if (Utils.isConnected((getActivity()))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }


    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((Setpartner) response).getMsg());
        progress.setVisibility(View.GONE);
        ((HomeActivity) mActivity).setFragment(INDEX_UPLOAD_ID, null);
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
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
