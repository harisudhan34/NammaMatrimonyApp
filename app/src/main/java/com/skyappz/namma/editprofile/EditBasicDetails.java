package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_PERSONAL_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RELIGION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditBasicDetails extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_complexion,select_bodytype,select_disability,selectMaritalstatus,select_dosam,select_padham;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    List<String> complexions, disablility, bodyTypes;
    private Activity mActivity;
    public static EditBasicDetails newInstance() {
        return new EditBasicDetails();
    }
    private String errorMsg;
    AppCompatButton update;
    AppCompatTextView skip;
    AppCompatAutoCompleteTextView autoNationality, auto_state,auto_city;
    AppCompatSpinner SPcountry;
    String s_caste,s_subcaste,s_other_community,s_childdren="",s_casteid;
    AppCompatSpinner sp_maritalstatus,sp_numofchiildren;
    ArrayList maritalStatusList,dosamList,numofchild_list;
    String child_count;
    String s_maritalstatus;
    AppCompatAutoCompleteTextView auto_caaste,auto_subcaste;
    LinearLayout numof_children_layot;
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> caste_id =new ArrayList<>();
    ArrayList<String> subcastes =new ArrayList<>();
    ArrayList<String> subcasteid =new ArrayList<>();
    RadioGroup radio_othecommuity,radio_children;
    RadioButton radio_yes,radio_no,community_yes,community_no;
    ArrayList<String> state_list =new ArrayList<>();
    ArrayList<String> state_list_id =new ArrayList<>();
    ArrayList<String> city_list =new ArrayList<>();
    ArrayList<String> city_list_id =new ArrayList<>();
    String s_sate,s_city,s_state_id,s_ccity_id;
    ProgressDialog dialog;
    LinearLayout subcaste_layout;
    String s_nationality,s_country;
    GifImageView progress;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_basic_details_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    private void initViews(View view) {
        sp_maritalstatus=(AppCompatSpinner)view.findViewById(R.id.sp_maritalstatus);
        sp_numofchiildren=(AppCompatSpinner)view.findViewById(R.id.sp_numofchiildren);
        auto_caaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_caste);
        auto_subcaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_subcaste);
        progress=(GifImageView)view.findViewById(R.id.progress);


        radio_othecommuity=(RadioGroup)view.findViewById(R.id.radio_othecommuity);
        radio_children=(RadioGroup)view.findViewById(R.id.childrengroup);
        community_yes=(RadioButton)view.findViewById(R.id.community_yes);
        community_no=(RadioButton)view.findViewById(R.id.community_no);
        autoNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoNationality);
        auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_state);
        auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_city);
        SPcountry=(AppCompatSpinner)view.findViewById(R.id.SPcountry);

        numof_children_layot=(LinearLayout)view.findViewById(R.id.numof_children_layot);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        subcaste_layout=(LinearLayout)view.findViewById(R.id.subcaste_layout);
        SPcountry.setOnItemSelectedListener(this);



        radio_othecommuity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                s_other_community = rb.getText().toString();

            }
        });
        radio_children.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                s_childdren = rb.getText().toString();

            }
        });


        getAllCaste();
        getstate();
        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        auto_caaste.setAdapter(casetadapter);
        auto_caaste.setOnItemClickListener(caseteListner);



//        auto_subcaste.setOnItemClickListener(subcastelistner);

        update.setOnClickListener(this);
        skip.setOnClickListener(this);

        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        ArrayAdapter countryadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        countryadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        SPcountry.setAdapter(countryadapter);
        SPcountry.setOnItemSelectedListener(this);
        ArrayList<String> nationalityList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, nationalityList);
        autoNationality.setAdapter(adapter);
//        loadStateandDist();

        CustomListAdapter stateadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, state_list);
        auto_state.setAdapter(stateadapter);
        auto_state.setOnItemClickListener(stateListner);






        maritalStatusList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        ArrayAdapter maritaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,maritalStatusList);
        maritaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_maritalstatus.setAdapter(maritaladapter);
        sp_maritalstatus.setOnItemSelectedListener(this);

        numofchild_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.num_of_child)));
        ArrayAdapter childadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,numofchild_list);
        childadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_numofchiildren.setAdapter(childadapter);
        sp_numofchiildren.setOnItemSelectedListener(this);





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
    private AdapterView.OnItemClickListener caseteListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_caste = String.valueOf(adapterView.getItemAtPosition(i));
                    s_casteid= s_caste;
                    int castepos = castes.indexOf(s_caste);
                    String castid=  caste_id.get(castepos);
                    Log.e("casteid",castid);
                    s_caste = castid;
                    getsubcaste();
                }
            };
    private AdapterView.OnItemClickListener subcastelistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_subcaste = String.valueOf(adapterView.getItemAtPosition(i));
                    int castepos = subcastes.indexOf(s_subcaste);
                    String castid=  subcasteid.get(castepos);
                    Log.e("casteid",castid);
                    s_subcaste = castid;
                }
            };



    public void update() {
        progress.setVisibility(View.VISIBLE);
        s_nationality=autoNationality.getText().toString();
        s_sate=auto_state.getText().toString();
        s_city=auto_city.getText().toString();

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
    private boolean isInputValidated(User user) {
        if (s_maritalstatus.equals("Select a Marital Status")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Marital status is empty!";
            return false;
        }
        if (!s_maritalstatus.equalsIgnoreCase("Unmarried")){
            progress.setVisibility(View.GONE);
            if (Utils.isEmpty(child_count)){
                errorMsg = "Child count is empty!";
                return false;
            }
            if (child_count.equalsIgnoreCase("")){
                errorMsg = "Choose num of children";
            }
            if (s_childdren == null){
                errorMsg = "Choose children living with me";
                return false;
            }
        }
        if (Utils.isEmpty(s_caste)){
            progress.setVisibility(View.GONE);
            errorMsg = "Caste is empty!";
            return false;
        }

        if (Utils.isEmpty(s_nationality) || s_nationality.equalsIgnoreCase("Select Nationality")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Nationality  is empty!";
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
        params.put("marital_status", s_maritalstatus.toLowerCase());
        params.put("caste", s_caste);
        params.put("sub_caste", s_subcaste);
        params.put("login_way","mobile");
        params.put("nationality",s_nationality.toLowerCase());
        params.put("country",s_country.toLowerCase());
        params.put("state",s_state_id);
        params.put("home_city",s_ccity_id);
        params.put("age",AppController.get_age(getActivity()));
        params.put("no_of_children",child_count.toLowerCase());
        params.put("living_with_me",s_childdren.toLowerCase());
        params.put("willing_other_community",s_other_community.toLowerCase());
        params.put("dob",AppController.get_dob(getActivity()).toLowerCase());
        params.put("gender",AppController.get_gender(getActivity()).toLowerCase());
        params.put("mother_tongue", AppController.get_mothertounge(getActivity()).toLowerCase());
        params.put("religion", AppController.get_religion(getActivity()).toLowerCase());
        params.put("profile_created_for",AppController.get_signupprofilevetae(getActivity()).toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_RELIGION_DETAILS, null);
    }
    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        progress.setVisibility(View.GONE);
        ((HomeActivity) mActivity).setFragment(INDEX_PERSONAL_DETAILS, null);
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
            case R.id.update:{
               update();
                break;
            }
            case R.id.skip:{
                skip();
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.sp_maritalstatus:
                s_maritalstatus=sp_maritalstatus.getSelectedItem().toString();
                if (!s_maritalstatus.equals("Unmarried")){
                    numof_children_layot.setVisibility(View.VISIBLE);
                }else {
                    numof_children_layot.setVisibility(View.GONE);
                }
                break;

            case  R.id.SPcountry:
                s_country =SPcountry.getSelectedItem().toString();
                break;

            case R.id.sp_numofchiildren:
                child_count =sp_numofchiildren.getSelectedItem().toString();
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getAllCaste() {
        HttpsTrustManager.allowAllSSL();
        auto_subcaste.setText("");
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
                                caste_id.add(list.getString("id"));
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
                        Log.e("subcaste",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("castes");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                if (s_casteid.equalsIgnoreCase(list.getString("caste"))){
                                    JSONArray subcasetarray=list.getJSONArray("subcastes");
                                    if (subcasetarray.length()> 0){
                                        subcaste_layout.setVisibility(View.VISIBLE);
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            subcastes.add(sublist.getString("subcaste"));
                                            subcasteid.add(sublist.getString("id"));
                                        }

                                        CustomListAdapter subcasetadapter = new CustomListAdapter(getActivity(),
                                                R.layout.right_menu_item, subcastes);
                                        auto_subcaste.setAdapter(subcasetadapter);
                                        auto_subcaste.setOnItemClickListener(subcastelistner);

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

    public void getstate() {
        HttpsTrustManager.allowAllSSL();
        auto_subcaste.setText("");
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
                                    Log.e("eqeqe","state true");
                                    JSONArray subcasetarray=list.getJSONArray("cities");
                                    if (subcasetarray.length()> 0){
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            city_list.add(sublist.getString("city"));
                                            city_list_id.add(sublist.getString("id"));
                                        }


                                        Log.e("eqeqe",String.valueOf(city_list.size()));
                                        CustomListAdapter cityadapter = new CustomListAdapter(getActivity(),
                                                R.layout.right_menu_item, city_list);
                                        auto_city.setAdapter(cityadapter);
                                        auto_city.setOnItemClickListener(citylistner);
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
