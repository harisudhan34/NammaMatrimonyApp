package com.skyappz.namma.profileupdate;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.EditProfileActivity;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.editprofile.UserDetailsViewModel;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;


public class Personalinfo extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, WebServiceListener {
    AppCompatAutoCompleteTextView autoDegree,autoMyOcc;
    AppCompatEditText collegename,etofficedetails;
    AppCompatSpinner spWorkingSector,spMonthlyIncome;
    String s_degree,s_myocc,s_collegename,s_officedetails,s_working_sector,s_income;
    ArrayAdapter workigAdapter,incomeAdapter;
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
    HashMap<String, String> params = new HashMap<>();
    GifImageView progress;
    private UserDetailsViewModel userDetailsViewModel;
    AppCompatButton  update;
     public Personalinfo() {
        // Required empty public constructor
    }

    public static Personalinfo newInstance() {
        return new Personalinfo();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_personalinfo, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());
        progress=(GifImageView)v.findViewById(R.id.progress);
        autoDegree=(AppCompatAutoCompleteTextView)v.findViewById(R.id.autoDegree);
        autoMyOcc=(AppCompatAutoCompleteTextView)v.findViewById(R.id.autoMyOcc);
        collegename=(AppCompatEditText)v.findViewById(R.id.collegename);
        etofficedetails=(AppCompatEditText)v.findViewById(R.id.etofficedetails);
        spWorkingSector=(AppCompatSpinner)v.findViewById(R.id.spWorkingSector);
        spMonthlyIncome=(AppCompatSpinner)v.findViewById(R.id.spMonthlyIncome);
        update=(AppCompatButton)v.findViewById(R.id.update);
        update.setOnClickListener(this);

        ArrayList<String> degreeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.degrees)));
        CustomListAdapter degreeadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, degreeList);
        autoDegree.setAdapter(degreeadapter);
        autoDegree.setOnItemClickListener(degreeListner);

        ArrayList<String> occlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.jobs)));
        CustomListAdapter occAdaoter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, occlist);
        autoMyOcc.setAdapter(occAdaoter);
        autoMyOcc.setOnItemClickListener(myoccListner);

        ArrayList workingsectorList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.working_sectors)));
        workigAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,workingsectorList);
        workigAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spWorkingSector.setAdapter(workigAdapter);
        spWorkingSector.setOnItemSelectedListener(this);


        ArrayList income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.monthly_income)));
        incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spMonthlyIncome.setAdapter(incomeAdapter);
        spMonthlyIncome.setOnItemSelectedListener(this);

        get_user();
        return v;
    }

    private AdapterView.OnItemClickListener myoccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_myocc = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };



    private AdapterView.OnItemClickListener degreeListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_degree = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spWorkingSector:
                s_working_sector = spWorkingSector.getSelectedItem().toString();
                break;
            case R.id.spMonthlyIncome:
                s_income = spMonthlyIncome.getSelectedItem().toString();
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
                progress.setVisibility(View.VISIBLE);
            s_collegename=collegename.getText().toString();
            s_officedetails=etofficedetails.getText().toString();
            s_degree=autoDegree.getText().toString();
            s_myocc=autoMyOcc.getText().toString();
            if (s_degree.equalsIgnoreCase("")){
                progress.setVisibility(View.GONE);
                Utils.showAlert(getActivity(),"Enter Education");
            }else if (s_collegename.equalsIgnoreCase("")){
                progress.setVisibility(View.GONE);
                Utils.showAlert(getActivity(),"Enter College Details");
            }else if (s_myocc.equalsIgnoreCase("")){
                progress.setVisibility(View.GONE);
                Utils.showAlert(getActivity(),"Enter Ocupation Details");
            }else  if (s_working_sector.equalsIgnoreCase("Select your Working Sector")){
                progress.setVisibility(View.GONE);
                Utils.showAlert(getActivity(),"Select Working Setor");
            }else if (s_income.equalsIgnoreCase("Select Anual Income")){
                progress.setVisibility(View.GONE);
                Utils.showAlert(getActivity(),"Select Income");
            }else {
                updatevalue();
            }


            break;
        }
    }
    public void get_user() {
        Log.e("getuser","getuser");
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


                                int selectionPosition= workigAdapter.getPosition(user.getString("working_sector"));
                                spWorkingSector.setSelection(selectionPosition);

                                int selectionPosition9= incomeAdapter.getPosition(user.getString("max_income"));
                                spMonthlyIncome.setSelection(selectionPosition9);

                                autoDegree.setText(user.getString("education"));
                                autoMyOcc.setText(user.getString("occupation"));
                                collegename.setText(user.getString("college"));
                                etofficedetails.setText(user.getString("office_details"));

                                progress.setVisibility(View.GONE);

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


    private  void  updatevalue(){
        params.put("user_id", AppController.get_userid(getActivity()));
        params.put("education", s_degree.toLowerCase());
        params.put("occupation", s_myocc.toLowerCase());
        params.put("max_income", s_income.toLowerCase());
        params.put("working_sector", s_working_sector.toLowerCase());
        params.put("office_details", s_officedetails.toLowerCase());
        params.put("college", s_collegename.toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        Intent i= new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
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
