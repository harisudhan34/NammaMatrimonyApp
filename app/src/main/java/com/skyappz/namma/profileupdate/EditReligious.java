package com.skyappz.namma.profileupdate;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.LinearLayout;

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
import com.skyappz.namma.utils.MultiSelectionSpinner;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditReligious extends Fragment implements View.OnClickListener, WebServiceListener, AdapterView.OnItemSelectedListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    AppCompatAutoCompleteTextView religion_auto,caste_auto,auto_subcaste;
            AppCompatSpinner spstar,sprassi;
    MultiSelectionSpinner dhosam_new;
    AppCompatButton update;
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> subcastes =new ArrayList<>();
    String s_religion,s_caste,s_star,s_raasi,s_dhosam,s_subcaste;
    LinearLayout subcaste_layout;
    ArrayAdapter rassiadapter,nachathramadapter;
    ArrayList start_list,raasi_list,dosamList;
    GifImageView progress;
    HashMap<String, String> params = new HashMap<>();
    private UserDetailsViewModel userDetailsViewModel;
    public EditReligious() {
        // Required empty public constructor
    }

    public static EditReligious newInstance() {
        return new EditReligious();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_religious, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());
        progress=(GifImageView) view.findViewById(R.id.progress);
        getAllCaste();
        religion_auto=(AppCompatAutoCompleteTextView)view.findViewById(R.id.religion_auto);
        caste_auto=(AppCompatAutoCompleteTextView)view.findViewById(R.id.caste_auto);
        subcaste_layout=(LinearLayout)view.findViewById(R.id.subcaste_layout);
        auto_subcaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_subcaste);
        spstar=(AppCompatSpinner)view.findViewById(R.id.spstar);
        sprassi=(AppCompatSpinner)view.findViewById(R.id.sprassi);
        dhosam_new=(MultiSelectionSpinner)view.findViewById(R.id.dhosam_new);
        update=(AppCompatButton) view.findViewById(R.id.update);
        update.setOnClickListener(this);

        ArrayList<String> religojn = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, religojn);
        religion_auto.setAdapter(adapter);
        religion_auto.setOnItemClickListener(religionclick);


        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        caste_auto.setAdapter(casetadapter);
        caste_auto.setOnItemClickListener(caseteListner);

        start_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.NATCHATRAM)));
         nachathramadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,start_list);
        nachathramadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spstar.setAdapter(nachathramadapter);
        spstar.setOnItemSelectedListener(this);

        raasi_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.raasis)));
         rassiadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,raasi_list);
        rassiadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sprassi.setAdapter(rassiadapter);
        sprassi.setOnItemSelectedListener(this);

        dosamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dhosam)));
        ArrayAdapter dosamadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,dosamList);
        dosamadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        dhosam_new.setOnItemSelectedListener(this);
        dhosam_new.setItems(dosamList);
        dhosam_new.hasNoneOption(true);
        dhosam_new.setSelection(new int[]{0});
        dhosam_new.setListener(this);
        get_user();
        return view;
    }
    private AdapterView.OnItemClickListener caseteListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_caste = String.valueOf(adapterView.getItemAtPosition(i));
//                    int selectedposition=i+1;
////                    s_casteid=..get(i);
//                    Log.e("castepos.",caste_id.get(selectedposition));
                   // getsubcaste();
                }
            };

    public void getAllCaste() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getAllCaste";
        String url = "https://nammamatrimony.in/api/getcaste.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("caste",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("castes");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                HashMap<String,String> castelist =new HashMap<>();
                                castes.add(list.getString("caste"));
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


    public void getsubcaste() {
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "getAllSubCaste";
        String url = "https://nammamatrimony.in/api/getsubcaste.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("caste",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("castes");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                if (s_caste.equalsIgnoreCase(list.getString("caste"))){
                                    JSONArray subcasetarray=list.getJSONArray("subcastes");
                                    if (subcasetarray.length()> 0){
                                        subcaste_layout.setVisibility(View.VISIBLE);
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            subcastes.add(sublist.getString("subcaste"));
                                        }
                                        CustomListAdapter subcasetadapter = new CustomListAdapter(getActivity(),
                                                R.layout.right_menu_item, subcastes);
                                        auto_subcaste.setAdapter(subcasetadapter);
                                    }else {
                                        subcaste_layout.setVisibility(View.GONE);
                                        Utils.showToast(getActivity(),"No sub caste");
                                        s_subcaste="";
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


    private AdapterView.OnItemClickListener religionclick =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_religion = String.valueOf(adapterView.getItemAtPosition(i));
                    Log.e("religion",s_religion);
                }
            };

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sprassi:
                s_raasi = sprassi.getSelectedItem().toString();
                break;
            case R.id.spstar:
                s_star = spstar.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

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


                                int selectionPosition= rassiadapter.getPosition(user.getString("raasi"));
                                sprassi.setSelection(selectionPosition);

                                int selectionPosition9= nachathramadapter.getPosition(user.getString("star"));
                                spstar.setSelection(selectionPosition9);
                                caste_auto.setText(user.getString("caste"));
                                religion_auto.setText(user.getString("religion"));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:
                progress.setVisibility(View.VISIBLE);
                s_religion=religion_auto.getText().toString();
                s_caste=caste_auto.getText().toString();
                if (s_religion.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Choose Religion");
                }else if (s_caste.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Choose caste");
                }else if (s_raasi.equalsIgnoreCase("Select your Raasi")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select rassi");
                }else if (s_star.equalsIgnoreCase("Select Star")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Star");
                }else {
                    update_values();
                }
                break;
        }
    }
    private void update_values(){
        params.put("user_id",AppController.get_userid(getActivity()));
        params.put("religion", s_religion);
        params.put("caste", s_caste);
        params.put("raasi", s_raasi);
        params.put("star", s_star);

        userDetailsViewModel.updateUser(params, this);
    }

}
