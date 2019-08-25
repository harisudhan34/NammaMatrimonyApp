package com.skyappz.namma.profileupdate;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFamily extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, WebServiceListener {

    AppCompatSpinner spFamilyType,spFamilyStatus;
    AppCompatEditText etNoOfSiblings,et_family_location;
            AppCompatAutoCompleteTextView spFatherOccupation,spMotherOccupation;
            String s_ffamily_type,s_familystatus,s_siblings,s_f_location,s_father_occ,s_mother_occc;
    ArrayList ftype,fstatus;
    AppCompatButton update;
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
    GifImageView progress;
    private UserDetailsViewModel userDetailsViewModel;
    HashMap<String, String> params = new HashMap<>();
    ArrayAdapter familyType,familystatus;
    public EditFamily() {
        // Required empty public constructor
    }

    public static EditFamily newInstance() {
        return new EditFamily();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_edit_family, container, false);
        spFamilyType=(AppCompatSpinner)v.findViewById(R.id.spFamilyType);
        spFamilyStatus=(AppCompatSpinner)v.findViewById(R.id.spFamilyStatus);

        etNoOfSiblings=(AppCompatEditText)v.findViewById(R.id.etNoOfSiblings);
        et_family_location=(AppCompatEditText)v.findViewById(R.id.et_family_location);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());

        spFatherOccupation=(AppCompatAutoCompleteTextView)v.findViewById(R.id.spFatherOccupation);
        spMotherOccupation=(AppCompatAutoCompleteTextView)v.findViewById(R.id.spMotherOccupation);

        update=(AppCompatButton) v.findViewById(R.id.update);
        update.setOnClickListener(this);

        progress=(GifImageView) v.findViewById(R.id.progress);

        ArrayList<String> father_occ = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.jobs)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, father_occ);
        spFatherOccupation.setAdapter(adapter);
        spFatherOccupation.setOnItemClickListener(fatheroccListner);
        ArrayList<String> mother_oc = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.mother_oupation)));
        CustomListAdapter motheradapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, mother_oc);
        spMotherOccupation.setAdapter(motheradapter);
        spMotherOccupation.setOnItemClickListener(motheroccListner);

        ftype = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_types)));
         familyType=new ArrayAdapter(getActivity(),R.layout.spinner_item,ftype);
        familyType.setDropDownViewResource(R.layout.spinner_drop_item);
        spFamilyType.setAdapter(familyType);
        spFamilyType.setOnItemSelectedListener(this);

        fstatus= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_statuses)));
         familystatus=new ArrayAdapter(getActivity(),R.layout.spinner_item,fstatus);
        familystatus.setDropDownViewResource(R.layout.spinner_drop_item);
        spFamilyStatus.setAdapter(familystatus);
        spFamilyStatus.setOnItemSelectedListener(this);
        get_user();
        return v;
    }

    private AdapterView.OnItemClickListener fatheroccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_father_occ = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };

    private AdapterView.OnItemClickListener motheroccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_mother_occc = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };
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


                                int selectionPosition= familyType.getPosition(user.getString("family_type"));
                                spFamilyType.setSelection(selectionPosition);

                                int selectionPosition9= familystatus.getPosition(user.getString("family_status"));
                                spFamilyStatus.setSelection(selectionPosition9);

                                spMotherOccupation.setText(user.getString("mother_occupation"));
                                spFatherOccupation.setText(user.getString("mother_occupation"));

                                etNoOfSiblings.setText(user.getString("no_of_siblings"));
                                et_family_location.setText(user.getString("family_location"));

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spFamilyType:
                s_ffamily_type=spFamilyType.getSelectedItem().toString();
                break;
            case R.id.spFamilyStatus:
                s_familystatus=spFamilyStatus.getSelectedItem().toString();
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
                s_father_occ=spFatherOccupation.getText().toString();
                s_mother_occc=spMotherOccupation.getText().toString();
                s_siblings=etNoOfSiblings.getText().toString();
                s_f_location=et_family_location.getText().toString();
               if (s_ffamily_type.equalsIgnoreCase("Select your Family Type")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Select Family Type");
            }else if (s_familystatus.equalsIgnoreCase("Select your Family Status")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Select Family status");
               }else if (s_father_occ.equalsIgnoreCase("")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Enter Father Occupation");
               }else  if (s_mother_occc.equalsIgnoreCase("")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Enter Mother Ocupation");
               }else if (s_siblings.equalsIgnoreCase("")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Enter No of siblibgs");
               }else  if (s_f_location.equalsIgnoreCase("")){
                   progress.setVisibility(View.GONE);
                   Utils.showAlert(getActivity(),"Enter Family Location");
               }else {
                   update_values();

               }

                break;
        }

    }

    private void update_values(){
        params.put("user_id",AppController.get_userid(getActivity()));
        params.put("family_status", s_familystatus).toLowerCase();
        params.put("family_type", s_ffamily_type.toLowerCase());
        params.put("father_occupation", s_father_occ.toLowerCase());
        params.put("mother_occupation", s_mother_occc.toLowerCase());
        params.put("no_of_siblings", s_siblings.toLowerCase());
        params.put("family_location", s_f_location.toLowerCase());

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
