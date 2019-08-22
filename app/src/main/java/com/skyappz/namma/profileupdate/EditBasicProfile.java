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
import com.skyappz.namma.activities.HomeActivity;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditBasicProfile extends Fragment implements AdapterView.OnItemSelectedListener, WebServiceListener, View.OnClickListener {
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
AppCompatEditText tetName;
AppCompatSpinner tetHeight,tetWeight,sp_complexion,sp_bodytype,sp_maritalstatus,spProfilesFor,sp_physicalstatus,spFoodHabit,spSmookingHabit,spDrinkHabit;
    AppCompatAutoCompleteTextView  mothertongue_auto;
    ArrayList complexionList,bodyTypeList,heightlist,weightlist,maritalStatusList;
    ArrayAdapter heightadapter,weightlistadapter,complexionadapter,btypeadapter,maritaladapter,physiacaladapter,profilesAdapter
            ,foodadapter,smokingAdapter,drinkAdapter;
    AppCompatButton update;
    HashMap<String, String> params = new HashMap<>();
    String s_name,s_height,s_weight,s_marital,s_mother,s_physical,s_bodytype,s_complexion,s_profilereate,s_eating,s_drinking,s_smoking;
    private UserDetailsViewModel userDetailsViewModel;
    GifImageView progress;

    public EditBasicProfile() {
        // Required empty public constructor
    }
    public static EditBasicProfile newInstance() {
        return new EditBasicProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_edit_basic_profile, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());
        init(v);
        return v;
    }

   private void init(View v){
        progress=(GifImageView)v.findViewById(R.id.progress);
       tetName=(AppCompatEditText)v.findViewById(R.id.tetName);
       mothertongue_auto=(AppCompatAutoCompleteTextView)v.findViewById(R.id.mothertongue_auto);
       tetHeight=(AppCompatSpinner)v.findViewById(R.id.tetHeight);
       tetWeight=(AppCompatSpinner)v.findViewById(R.id.tetWeight);
       sp_complexion=(AppCompatSpinner)v.findViewById(R.id.sp_complexion);
       sp_bodytype=(AppCompatSpinner)v.findViewById(R.id.sp_bodytype);
       sp_maritalstatus=(AppCompatSpinner)v.findViewById(R.id.sp_maritalstatus);
       spProfilesFor=(AppCompatSpinner)v.findViewById(R.id.spProfilesFor);
       sp_physicalstatus=(AppCompatSpinner)v.findViewById(R.id.sp_physicalstatus);
       spFoodHabit=(AppCompatSpinner)v.findViewById(R.id.spFoodHabit);
       spSmookingHabit=(AppCompatSpinner)v.findViewById(R.id.spSmookingHabit);
       spDrinkHabit=(AppCompatSpinner)v.findViewById(R.id.spDrinkHabit);

       update=(AppCompatButton)v.findViewById(R.id.update);
       update.setOnClickListener(this);

       heightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        heightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,heightlist);
       heightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
       tetHeight.setAdapter(heightadapter);
       tetHeight.setOnItemSelectedListener(this);

       weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
       weightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,weightlist);
       weightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
       tetWeight.setAdapter(weightlistadapter);
       tetWeight.setOnItemSelectedListener(this);

       complexionList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.complexions)));
        complexionadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,complexionList);
       complexionadapter.setDropDownViewResource(R.layout.spinner_drop_item);
       sp_complexion.setAdapter(complexionadapter);
       sp_complexion.setOnItemSelectedListener(this);

       bodyTypeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.body_types)));
        btypeadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,bodyTypeList);
       btypeadapter.setDropDownViewResource(R.layout.spinner_drop_item);
       sp_bodytype.setAdapter(btypeadapter);
       sp_bodytype.setOnItemSelectedListener(this);

       maritalStatusList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        maritaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,maritalStatusList);
       maritaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
       sp_maritalstatus.setAdapter(maritaladapter);
       sp_maritalstatus.setOnItemSelectedListener(this);

       ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
       CustomListAdapter adapter1 = new CustomListAdapter(getActivity(),
               R.layout.right_menu_item, motherTongues);
       mothertongue_auto.setAdapter(adapter1);
       mothertongue_auto.setOnItemClickListener(onItemClickListener);

       ArrayList physicalstatus = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.physical_status)));
       physiacaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,physicalstatus);
       physiacaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
       sp_physicalstatus.setAdapter(physiacaladapter);
       sp_physicalstatus.setOnItemSelectedListener(this);

       ArrayList profiile = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_created_by)));
       profilesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, profiile);
       profilesAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
       spProfilesFor.setAdapter(profilesAdapter);
       spProfilesFor.setOnItemSelectedListener(this);

       ArrayList foodlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.eating_habits)));
        foodadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,foodlist);
       foodadapter.setDropDownViewResource(R.layout.spinner_drop_item);
       spFoodHabit.setAdapter(foodadapter);
       spFoodHabit.setOnItemSelectedListener(this);

       ArrayList smookinglist= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.smoking_habits)));
        smokingAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,smookinglist);
       smokingAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
       spSmookingHabit.setAdapter(smokingAdapter);
       spSmookingHabit.setOnItemSelectedListener(this);

       ArrayList drinkList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drinking_habits)));
        drinkAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,drinkList);
       drinkAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
       spDrinkHabit.setAdapter(drinkAdapter);
       spDrinkHabit.setOnItemSelectedListener(this);


       get_user();
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    s_mother=String.valueOf(adapterView.getItemAtPosition(i));

                }
            };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spProfilesFor:
                s_profilereate=spProfilesFor.getSelectedItem().toString();
                break;
            case R.id.sp_maritalstatus:
                s_marital=sp_maritalstatus.getSelectedItem().toString();
                break;
            case R.id.tetHeight:
                s_height=tetHeight.getSelectedItem().toString();
                break;
            case R.id.tetWeight:
                s_weight=tetWeight.getSelectedItem().toString();
                break;
            case R.id.sp_complexion:
                s_complexion=sp_complexion.getSelectedItem().toString();
                break;
            case R.id.sp_bodytype:
                s_bodytype=sp_bodytype.getSelectedItem().toString();
                break;
            case R.id.sp_physicalstatus:
                s_physical=sp_physicalstatus.getSelectedItem().toString();
                break;
            case R.id.spFoodHabit:
                s_eating=spFoodHabit.getSelectedItem().toString();
                break;
            case R.id.spSmookingHabit:
                s_smoking=spSmookingHabit.getSelectedItem().toString();
                break;
            case R.id.spDrinkHabit:
                s_drinking=spDrinkHabit.getSelectedItem().toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

                                tetName.setText(user.getString("name"));
                                s_mother=user.getString("mother_tongue");
                                mothertongue_auto.setText(s_mother);

                                int selectionPosition= heightadapter.getPosition(user.getString("height"));
                                tetHeight.setSelection(selectionPosition);

                                int selectionPosition1= weightlistadapter.getPosition(user.getString("weight"));
                                tetWeight.setSelection(selectionPosition1);

                                int selectionPosition2= complexionadapter.getPosition(user.getString("complexion"));
                                sp_complexion.setSelection(selectionPosition2);

                                int selectionPosition3= btypeadapter.getPosition(user.getString("body_type"));
                                sp_bodytype.setSelection(selectionPosition3);

                                int selectionPosition4= maritaladapter.getPosition(user.getString("marital_status"));
                                sp_maritalstatus.setSelection(selectionPosition4);

                                int selectionPosition5= physiacaladapter.getPosition(user.getString("physical_status"));
                                sp_physicalstatus.setSelection(selectionPosition5);

                                int selectionPosition6= profilesAdapter.getPosition(user.getString("profile_created_for"));
                                spProfilesFor.setSelection(selectionPosition6);

                                int selectionPosition7= foodadapter.getPosition(user.getString("eating_habits"));
                                spFoodHabit.setSelection(selectionPosition7);

                                int selectionPosition8= drinkAdapter.getPosition(user.getString("drinking_habits"));
                                spDrinkHabit.setSelection(selectionPosition8);

                                int selectionPosition9= smokingAdapter.getPosition(user.getString("smoking_habits"));
                                spSmookingHabit.setSelection(selectionPosition9);

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
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.update:
                progress.setVisibility(View.VISIBLE);
                s_name=tetName.getText().toString();
                s_mother=mothertongue_auto.getText().toString();
                if (s_name.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Enter User name");
                } else if (s_profilereate.equalsIgnoreCase("profile_created_by")) {
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Choose profile create for");
                }else  if (s_marital.equalsIgnoreCase("Select status")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select marital Status");
                }else  if (s_mother.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Mother Tongue is empty");
                }else if (s_height.equalsIgnoreCase("Select a Height")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Height");
                }else if (s_weight.equalsIgnoreCase("Select a Weight")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Weight");
                }else if (s_complexion.equalsIgnoreCase("Select your Complexions")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Complexions");
                }else if (s_bodytype.equalsIgnoreCase("Select your Body Type")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Body Type");
                }else if (s_physical.equalsIgnoreCase("Select physical_status")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select physical status");
                }else if (s_eating.equalsIgnoreCase("Select your Food Habits")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Food Habits");
                }else if (s_smoking.equalsIgnoreCase("Select your Smoking Habits")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Smoking Habits");
                }else if (s_drinking.equalsIgnoreCase("Select your Drinking Habits")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Drinking Habits");
                }else {
                    update_values();
                }

                break;
        }
    }
    private void update_values(){
        params.put("user_id",AppController.get_userid(getActivity()));
        params.put("name", s_name);
        params.put("profile_created_for", s_profilereate);
        params.put("marital_status", s_marital);
        params.put("mother_tongue", s_mother);
        params.put("height", s_height);
        params.put("weight", s_weight);
        params.put("complexion", s_complexion);
        params.put("body_type", s_bodytype);
        params.put("physical_status", s_physical);
        params.put("smoking_habits", s_smoking);
        params.put("drinking_habits", s_drinking);
        params.put("eating_habits", s_eating);

        userDetailsViewModel.updateUser(params, this);
    }
}
