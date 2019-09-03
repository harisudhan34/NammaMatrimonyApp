package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.utils.MultiSelectionSpinner;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_ABOUT_MYSELF;
import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditReligiousDetails extends Fragment implements WebServiceListener, View.OnClickListener,AdapterView.OnItemSelectedListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private UserDetailsViewModel userDetailsViewModel;

    private Activity mActivity;
    RadioGroup radiodhosam;
    String s_having_dhosam,s_natchathram,s_raasi,s_dosam,s_paadham;
    LinearLayout dhosam_details_layout;
    ArrayList dosamList;
    private String errorMsg;
    AppCompatButton update;
    AppCompatSpinner spRaasi,spNatchathiram,sp_paadham,Birth_SPcountry;
    AppCompatTextView birthtime;
    RadioButton radio_yes,radio_no;
    GifImageView progress;
    ArrayList raasi_list,start_list,paadhamList;
    String s_sate,s_city,s_country,s_birthtime,s_ccity_id,s_state_id;
    ArrayList<String> state_list = new ArrayList<>();
    ArrayList<String> state_list_id =new ArrayList<>();
    ArrayList<String> city_list = new ArrayList<>();
    ArrayList<String> city_list_id = new ArrayList<>();
    AppCompatAutoCompleteTextView birth_auto_state,birth_auto_city;

    HashMap<String, String> params = new HashMap<>();
    public static EditReligiousDetails newInstance() {
        return new EditReligiousDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_religious_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        return view;
    }



    private void initViews(View view) {
        birthtime=(AppCompatTextView)view.findViewById(R.id.birthtime);
        birth_auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.birth_auto_state);
        birth_auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.birth_auto_city);
        Birth_SPcountry=(AppCompatSpinner)view.findViewById(R.id.Birth_SPcountry);

        radiodhosam=(RadioGroup)view.findViewById(R.id.radiodhosam);
        dhosam_details_layout=(LinearLayout)view.findViewById(R.id.dhosam_details_layout);
        MultiSelectionSpinner sp_dosam=(MultiSelectionSpinner)view.findViewById(R.id.dhosam_new);

        radio_yes=(RadioButton)view.findViewById(R.id.radio_yes);
        radio_no=(RadioButton)view.findViewById(R.id.radio_no);
        progress=(GifImageView)view.findViewById(R.id.progress);
        update=(AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);

        sp_paadham=(AppCompatSpinner)view.findViewById(R.id.paaham_sp);
        paadhamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.paatham)));
        ArrayAdapter paadhaadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,paadhamList);
        paadhaadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_paadham.setAdapter(paadhaadapter);
        sp_paadham.setOnItemSelectedListener(this);


        spRaasi=(AppCompatSpinner)view.findViewById(R.id.spRaasi);
        raasi_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.raasis)));
        ArrayAdapter rassiadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,raasi_list);
        rassiadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spRaasi.setAdapter(rassiadapter);
        spRaasi.setOnItemSelectedListener(this);

        spNatchathiram=(AppCompatSpinner)view.findViewById(R.id.spNatchathiram);
        start_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.NATCHATRAM)));
        ArrayAdapter nachathramadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,start_list);
        nachathramadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spNatchathiram.setAdapter(nachathramadapter);
        spNatchathiram.setOnItemSelectedListener(this);


        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        ArrayAdapter countryadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        countryadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        Birth_SPcountry.setAdapter(countryadapter);
        Birth_SPcountry.setOnItemSelectedListener(this);

        getstate();
        CustomListAdapter stateadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, state_list);
        birth_auto_state.setAdapter(stateadapter);
        birth_auto_state.setOnItemClickListener(stateListner);
        birth_auto_city.setOnItemClickListener(citylistner);

        birthtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM;
                        if (selectedHour >=0 && selectedHour < 12){
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        s_birthtime=selectedHour + ":" + selectedMinute+" "+AM_PM;
                        birthtime.setText( selectedHour + ":" + selectedMinute+" "+AM_PM);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        radiodhosam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                s_having_dhosam = rb.getText().toString();
                if (rb.getText().equals("Yes")){
                    dhosam_details_layout.setVisibility(View.VISIBLE);

                }else {
                    dhosam_details_layout.setVisibility(View.GONE);

                }

            }
        });


        dosamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dhosam)));
        ArrayAdapter dosamadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,dosamList);
        dosamadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_dosam.setOnItemSelectedListener(this);
        sp_dosam.setItems(dosamList);
        sp_dosam.hasNoneOption(true);
        sp_dosam.setSelection(new int[]{0});
        sp_dosam.setListener(this);

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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:{
                update();
                break;
            }

        }

    }

    public void update() {
        progress.setVisibility(View.VISIBLE);
        s_sate=birth_auto_state.getText().toString();
        s_city=birth_auto_city.getText().toString();
        if (Utils.isConnected(mActivity)) {
            if (isInputValidated()) {
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


    private boolean isInputValidated() {

        if (Utils.isEmpty(s_having_dhosam)){
            progress.setVisibility(View.GONE);
            errorMsg = "Choose Dhosam!";
            return false;
        }
        if (s_having_dhosam.equals("Yes")){
            progress.setVisibility(View.GONE);
            if (s_dosam.equals("Select a Dhosam")) {
                errorMsg = "Dosam is empty!";
                return false;
            }
        }else {
            s_dosam="";
        }
        if (s_paadham.equals("Select a Padhams")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Padham is empty!";
            return false;
        }
        if (s_birthtime.equalsIgnoreCase("")){
            progress.setVisibility(View.GONE);
            errorMsg = "Birth Time   is empty!";
            return false;
        }
        if (s_country.equalsIgnoreCase("Country")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Country  is empty!";
            return false;
        }
        if (Utils.isEmpty(s_sate)){
            progress.setVisibility(View.GONE);
            errorMsg = "state  is empty!";
            return false;
        }

        if (Utils.isEmpty(s_city)) {
            progress.setVisibility(View.GONE);
            errorMsg = "city  is empty!";
            return false;
        }
        return true;
    }

    private  void checkparams(){
        params.put("user_id",userid);
        params.put("having_dosham", s_having_dhosam.toLowerCase());
        params.put("dosham_details",s_dosam.toLowerCase());
        params.put("star",s_natchathram.toLowerCase());
        params.put("raasi",s_raasi.toLowerCase());
        params.put("paadham", s_paadham.toLowerCase());
        params.put("birth_country", s_country.toLowerCase());
        params.put("birth_state", s_state_id);
        params.put("birth_time", s_birthtime.toLowerCase());
        params.put("birth_city", s_ccity_id);
        userDetailsViewModel.updateUser(params, this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.spNatchathiram:
                s_natchathram =spNatchathiram.getSelectedItem().toString();
                break;
            case R.id.spRaasi:
                s_raasi =spRaasi.getSelectedItem().toString();
                break;

            case R.id.paaham_sp:
                s_paadham=sp_paadham.getSelectedItem().toString();
                Log.e("sp_paadham",s_paadham);
                break;
            case  R.id.Birth_SPcountry:
                s_country =Birth_SPcountry.getSelectedItem().toString();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        progress.setVisibility(View.GONE);
        ((HomeActivity) mActivity).setFragment(INDEX_ABOUT_MYSELF, null);
    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {
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
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        s_dosam="";
        for (int i=0; i<strings.size();i++){
            s_dosam += strings.get(i)+",";
        }

    }


    public void getstate() {
        HttpsTrustManager.allowAllSSL();
        birth_auto_city.setText("");
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
//                                        city_lay.setVisibility(View.VISIBLE);
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            city_list.add(sublist.getString("city"));
                                            city_list_id.add(sublist.getString("id"));
                                            Log.e("givecity",sublist.getString("city"));
                                            CustomListAdapter cityadapter = new CustomListAdapter(getActivity(),
                                                    R.layout.right_menu_item, city_list);
                                            birth_auto_city.setAdapter(cityadapter);
                                            birth_auto_city.setOnItemClickListener(citylistner);
                                        }


                                        Log.e("eqeqe",String.valueOf(city_list.size()));


                                    }else {
//                                        city_lay.setVisibility(View.GONE);
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

}
