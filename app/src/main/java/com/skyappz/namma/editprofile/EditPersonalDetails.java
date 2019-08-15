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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditPersonalDetailsFragmentBinding;
import com.skyappz.namma.model.User;
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
import java.util.HashMap;
import java.util.List;

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RELIGION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditPersonalDetails extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    public static String select_complexion,select_bodytype,select_disability,selectMaritalstatus,select_dosam,select_padham;
    private EditPersonalDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    AppCompatSpinner spRaasi,spNatchathiram;
    HashMap<String, String> params = new HashMap<>();
    List<String> complexions, disablility, bodyTypes;
    private Activity mActivity;
    public static EditPersonalDetails newInstance() {
        return new EditPersonalDetails();
    }
    private String errorMsg;
    AppCompatButton update;
    AppCompatTextView skip;
    AppCompatAutoCompleteTextView autoNationality, auto_state,auto_city;
    AppCompatSpinner SPcountry;
    String s_caste,s_subcaste,s_having_dhosam,s_other_community,s_childdren,s_casteid;
    AppCompatSpinner sp_maritalstatus,sp_numofchiildren;
    ArrayList maritalStatusList,dosamList,numofchild_list;
    String s_natchathram,s_raasi,child_count;
    String s_maritalstatus,s_dosam;
    AppCompatAutoCompleteTextView auto_caaste,auto_subcaste;
    LinearLayout dhosam_details_layout,numof_children_layot;
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> caste_id =new ArrayList<>();
    ArrayList<String> subcastes =new ArrayList<>();
    ArrayList<String> state =new ArrayList<>();
    ArrayList<String> state_id =new ArrayList<>();
    RadioGroup radiodhosam,radio_othecommuity,radio_children;
    RadioButton radio_yes,radio_no,community_yes,community_no;
    ArrayList state_list,city_list;
    String s_sate,s_city;
    ProgressDialog dialog;
    LinearLayout subcaste_layout;
    String s_nationality,s_country;
    ArrayList raasi_list,start_list;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_personal_details_fragment, container, false);
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
        MultiSelectionSpinner sp_dosam=(MultiSelectionSpinner)view.findViewById(R.id.dhosam_new);
        sp_maritalstatus=(AppCompatSpinner)view.findViewById(R.id.sp_maritalstatus);
        sp_numofchiildren=(AppCompatSpinner)view.findViewById(R.id.sp_numofchiildren);
        auto_caaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_caste);
        auto_subcaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_subcaste);
        radiodhosam=(RadioGroup)view.findViewById(R.id.radiodhosam);
        radio_yes=(RadioButton)view.findViewById(R.id.radio_yes);
        radio_no=(RadioButton)view.findViewById(R.id.radio_no);

        radio_othecommuity=(RadioGroup)view.findViewById(R.id.radio_othecommuity);
        radio_children=(RadioGroup)view.findViewById(R.id.childrengroup);
        community_yes=(RadioButton)view.findViewById(R.id.community_yes);
        community_no=(RadioButton)view.findViewById(R.id.community_no);
        autoNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoNationality);
        auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_state);
        auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_city);
        SPcountry=(AppCompatSpinner)view.findViewById(R.id.SPcountry);
        dhosam_details_layout=(LinearLayout)view.findViewById(R.id.dhosam_details_layout);
        numof_children_layot=(LinearLayout)view.findViewById(R.id.numof_children_layot);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        subcaste_layout=(LinearLayout)view.findViewById(R.id.subcaste_layout);
        SPcountry.setOnItemSelectedListener(this);

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
        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        auto_caaste.setAdapter(casetadapter);
        auto_caaste.setOnItemClickListener(caseteListner);



        auto_subcaste.setOnItemClickListener(subcastelistner);

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
        loadStateandDist();
        auto_state.setOnItemClickListener(stateListner);
        auto_city.setOnItemClickListener(citylistner);




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

        dosamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dhosam)));
        ArrayAdapter dosamadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,dosamList);
        dosamadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_dosam.setOnItemSelectedListener(this);
        sp_dosam.setItems(dosamList);
        sp_dosam.hasNoneOption(true);
        sp_dosam.setSelection(new int[]{0});
        sp_dosam.setListener(this);



    }
    public void loadStateandDist() {
        state_list =new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("states");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String state = jo_inside.getString("state");
                state_list.add(state);
                CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                        R.layout.right_menu_item, state_list);
                auto_state.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("stateanddist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public  void loadcity(String dist){
        city_list=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadcityjson());
            JSONArray m_jArry = obj.getJSONArray(dist);

            for (int k=0;k<m_jArry.length();k++ ) {
                city_list.add(m_jArry.getString(k));
                Log.e("cityarray",m_jArry.getString(k));
                CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                        R.layout.right_menu_item, city_list);
                auto_city.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadcityjson() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private AdapterView.OnItemClickListener citylistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_city = String.valueOf(adapterView.getItemAtPosition(i));

                }
            };

    private AdapterView.OnItemClickListener stateListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_sate = String.valueOf(adapterView.getItemAtPosition(i));
                    loadcity(s_sate);

                }
            };
    private AdapterView.OnItemClickListener caseteListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_caste = String.valueOf(adapterView.getItemAtPosition(i));
//                    int selectedposition=i+1;
////                    s_casteid=..get(i);
//                    Log.e("castepos.",caste_id.get(selectedposition));
                    getsubcaste();
                }
            };
    private AdapterView.OnItemClickListener subcastelistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_subcaste = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };



    public void update() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait.");
        dialog.setCancelable(false);
        dialog.show();
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
            dialog.dismiss();
            errorMsg = "Marital status is empty!";
            return false;
        }
        if (!s_maritalstatus.equalsIgnoreCase("Unmarried")){
            dialog.dismiss();
            if (Utils.isEmpty(child_count)){
                errorMsg = "Child count is empty!";
                return false;
            }
            if (child_count.equalsIgnoreCase("")){
                errorMsg = "Choose num of children";
            }
            if (Utils.isEmpty(s_childdren)){
                errorMsg = "Choose children living with me";
                return false;
            }
        }
        if (Utils.isEmpty(s_caste)){
            dialog.dismiss();
            errorMsg = "Caste is empty!";
            return false;
        }
       if (Utils.isEmpty(s_having_dhosam)){
           dialog.dismiss();
           errorMsg = "Choose Dhosam!";
           return false;
       }
       if (s_having_dhosam.equals("Yes")){
           dialog.dismiss();
           if (s_dosam.equals("Select a Dhosam")) {
               errorMsg = "Dosam is empty!";
               return false;
           }
       }else {
           s_dosam="";
       }
        if (Utils.isEmpty(s_nationality) || s_nationality.equalsIgnoreCase("Select Nationality")) {
            dialog.dismiss();
            errorMsg = "Nationality  is empty!";
            return false;
        }
        if (s_country.equalsIgnoreCase("Country")) {
            dialog.dismiss();
            errorMsg = "Country  is empty!";
            return false;
        }
        if (Utils.isEmpty(s_sate)){
            dialog.dismiss();
            errorMsg = "state  is empty!";
            return false;
        }

        if (Utils.isEmpty(s_city)) {
            dialog.dismiss();
            errorMsg = "city  is empty!";
            return false;
        }

        return true;
    }


    private  void checkparams(){
        params.put("user_id",userid);
        params.put("marital_status", s_maritalstatus);
        params.put("caste", s_caste);
        params.put("sub_caste", s_subcaste);
        params.put("having_dosham", s_having_dhosam);
        params.put("dosham_details",s_dosam );
        params.put("nationality",s_nationality );
        params.put("country",s_country );
        params.put("state",s_sate );
        params.put("home_city",s_city );
        params.put("age","22");
        params.put("no_of_children",child_count);
        params.put("living_with_me",s_childdren);
        params.put("star",s_natchathram);
        params.put("willing_other_community",s_other_community);
        params.put("raasi",s_raasi);
        params.put("dob",AppController.get_dob(getActivity()));
        params.put("gender",AppController.get_gender(getActivity()));
        params.put("mother_tongue", AppController.get_mothertounge(getActivity()));
        params.put("religion", AppController.get_religion(getActivity()));
        params.put("profile_created_for",AppController.get_signupprofilevetae(getActivity()));
        userDetailsViewModel.updateUser(params, this);
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_RELIGION_DETAILS, null);
    }
    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        dialog.dismiss();
        ((HomeActivity) mActivity).setFragment(INDEX_EDUCATION_DETAILS, null);
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

            case R.id.spNatchathiram:
                s_natchathram =spNatchathiram.getSelectedItem().toString();
                break;
            case R.id.spRaasi:
                s_raasi =spRaasi.getSelectedItem().toString();
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
                                        Utils.showToast(mActivity,"No sub caste");
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

    public void getstate() {
        auto_subcaste.setText("");
        String tag_json_obj = "getAllCaste";
        String url = "https://nammamatrimony.in/api/getstate.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("state",response.toString());
                        try {
                            JSONArray castearray=response.getJSONArray("states");
                            for (int i=0; i<castearray.length();i++){
                                JSONObject list= castearray.getJSONObject(i);
                                HashMap<String,String> castelist =new HashMap<>();
                                state.add(list.getString("state"));

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
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        for (int i=0; i<strings.size();i++){
            s_dosam += strings.get(i)+",";
        }

    }
}
