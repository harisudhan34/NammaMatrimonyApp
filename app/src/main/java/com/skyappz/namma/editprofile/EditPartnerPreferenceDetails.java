package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.CompoundButton;
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
import com.skyappz.namma.ResponseEntities.Setpartner;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditPartnerPreferenceFragmentBinding;
import com.skyappz.namma.model.PartnerPreference;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_UPLOAD_ID;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditPartnerPreferenceDetails extends Fragment implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, View.OnClickListener, WebServiceListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private EditPartnerPreferenceFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user;
    RadioGroup gender,radiodhosam;
    RadioButton radioMale,radioFemale,radio_yes,radio_no;
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> subcastes =new ArrayList<>();
    PartnerPreference partnerPreference;
    ArrayList maritalStatusList,paadhamList,dosamList,state_list,city_list,raasi_list,start_list,min_height_list,max_height_list,min_weightlist,max_weightlist,natchartamlist;
    ProgressDialog dialog;
    AppCompatSpinner etMinAge,etMaxAge,etMinIncome,etMaxincome, spMaritalStatus,spRaasi,spNatchathiram,spPaadham,spCountry,spDisability,etMinHeight,etMaxHeight,etMinweight,etMaxWeight;
    AppCompatAutoCompleteTextView spCaste,spSubCaste,spEducation,spProfession,spNationality,spPreferredstate,spPreferredCities,spMotherTongue;
    String s_gender,s_mothertounge,s_caste,s_subcaste,s_degree,s_myocc,s_nationality,s_sate,s_city,s_maritalstatus,s_paatham,s_dhosam,s_country,s_disability,
    s_minage,s_maxage="",s_minheight,s_maxheight,s_minweight,s_max_weight,s_natchathram,s_min_income,s_max_inxome,s_raasi,s_having_dhosam;
    AppCompatButton update;
    AppCompatTextView skip;
    private Activity mActivity;
    private String errorMsg;
    MultiSelectionSpinner spDosham;
    Handler h;
    Integer mi_he,max_he,mi_weight,max_weight,mi_income,max_income;

    LinearLayout dhosam_details_layout;
    HashMap<String, String> params = new HashMap<>();
    public static EditPartnerPreferenceDetails newInstance() {
        return new EditPartnerPreferenceDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_partner_preference_fragment, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        //userDetailsViewModel.getUserDetails("1");
        subscribe();
        initViews(view);

        return  view;
    }


    public void initViews(View view){
        h = new Handler();
        update=(AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);
        etMinAge=(AppCompatSpinner)view.findViewById(R.id.etMinAge);
        etMaxAge=(AppCompatSpinner)view.findViewById(R.id.etMaxAge);

        etMinIncome=(AppCompatSpinner)view.findViewById(R.id.etMinincome);
        etMaxincome=(AppCompatSpinner)view.findViewById(R.id.etMaxincome);

        etMinHeight=(AppCompatSpinner)view.findViewById(R.id.etMinHeight);
        etMaxHeight=(AppCompatSpinner)view.findViewById(R.id.etMaxHeight);
        etMinweight=(AppCompatSpinner)view.findViewById(R.id.etMinweight);
        etMaxWeight=(AppCompatSpinner)view.findViewById(R.id.etMaxWeight);

        radioMale=(RadioButton)view.findViewById(R.id.radioMale);
        radioFemale=(RadioButton)view.findViewById(R.id.radioFemale);
        gender = (RadioGroup)view.findViewById(R.id.radioSex);

        radiodhosam=(RadioGroup)view.findViewById(R.id.radiodhosam);
        radio_yes=(RadioButton)view.findViewById(R.id.radio_yes);
        radio_no=(RadioButton)view.findViewById(R.id.radio_no);
        dhosam_details_layout=(LinearLayout)view.findViewById(R.id.dhosam_details_layout);
        spCaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spCaste);
        spSubCaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spSubCaste);
        getAllCaste();

        ArrayList income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.monthly_income)));
        ArrayAdapter incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinIncome.setAdapter(incomeAdapter);
        etMinIncome.setOnItemSelectedListener(this);
        etMaxincome.setAdapter(incomeAdapter);
        etMaxincome.setOnItemSelectedListener(this);

        ArrayList age = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.age)));
        ArrayAdapter ageadapeer=new ArrayAdapter(getActivity(),R.layout.spinner_item,age);
        ageadapeer.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinAge.setAdapter(ageadapeer);
        etMinAge.setOnItemSelectedListener(this);
        etMaxAge.setAdapter(ageadapeer);
        etMaxAge.setOnItemSelectedListener(this);

        min_height_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        ArrayAdapter minheightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,min_height_list);
        minheightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinHeight.setAdapter(minheightadapter);
        etMinHeight.setOnItemSelectedListener(this);

        max_height_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        ArrayAdapter maxheightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,max_height_list);
        maxheightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMaxHeight.setAdapter(maxheightadapter);
        etMaxHeight.setOnItemSelectedListener(this);

        min_weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
        ArrayAdapter minweightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,min_weightlist);
        minweightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinweight.setAdapter(minweightlistadapter);
        etMinweight.setOnItemSelectedListener(this);

        max_weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
        ArrayAdapter maxnweightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,max_weightlist);
        maxnweightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMaxWeight.setAdapter(maxnweightlistadapter);
        etMaxWeight.setOnItemSelectedListener(this);

        spMaritalStatus=(AppCompatSpinner)view.findViewById(R.id.spMaritalStatus);
        maritalStatusList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        ArrayAdapter maritaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,maritalStatusList);
        maritaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spMaritalStatus.setAdapter(maritaladapter);
        spMaritalStatus.setOnItemSelectedListener(this);


        spRaasi=(AppCompatSpinner)view.findViewById(R.id.spRaasi);
        raasi_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.raasis)));
        ArrayAdapter rassiadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,raasi_list);
        rassiadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spRaasi.setAdapter(rassiadapter);
        spRaasi.setOnItemSelectedListener(this);


        spNatchathiram=(AppCompatSpinner)view.findViewById(R.id.spNatchathiram);
        natchartamlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.NATCHATRAM)));
        ArrayAdapter nachathramadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,natchartamlist);
        nachathramadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spNatchathiram.setAdapter(nachathramadapter);
        spNatchathiram.setOnItemSelectedListener(this);



        spPaadham=(AppCompatSpinner)view.findViewById(R.id.spPaadham);
        paadhamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.paatham)));
        ArrayAdapter paadhaadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,paadhamList);
        paadhaadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spPaadham.setAdapter(paadhaadapter);
        spPaadham.setOnItemSelectedListener(this);

        spDosham=(MultiSelectionSpinner)view.findViewById(R.id.spDosham);
        dosamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.dhosam)));
        ArrayAdapter dosamadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,dosamList);
        dosamadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spDosham.setOnItemSelectedListener(this);
        spDosham.setItems(dosamList);
        spDosham.hasNoneOption(true);
        spDosham.setSelection(new int[]{0});
        spDosham.setListener(this);

        spCountry=(AppCompatSpinner)view.findViewById(R.id.spCountry);
        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        ArrayAdapter disabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        disabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spCountry.setAdapter(disabilityadapter);
        spCountry.setOnItemSelectedListener(this);

        spMotherTongue=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spMotherTongue);
        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, motherTongues);
        spMotherTongue.setAdapter(adapter);
        spMotherTongue.setOnItemClickListener(mothertoungelistner);


        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        spCaste.setAdapter(casetadapter);
        spCaste.setOnItemClickListener(caseteListner);



        spSubCaste.setOnItemClickListener(subcastelistner);

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

        spDisability=(AppCompatSpinner)view.findViewById(R.id.spDisability);
        ArrayList disablitylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.physical_status)));
        ArrayAdapter spDisabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,disablitylist);
        spDisabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spDisability.setAdapter(spDisabilityadapter);
        spDisability.setOnItemSelectedListener(this);

        spNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spNationality);
        ArrayList<String> nationalityList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality)));
        CustomListAdapter natioalityadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, nationalityList);
        spNationality.setAdapter(natioalityadapter);
        spNationality.setOnItemClickListener(nationlistylistner);

        spPreferredstate=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredstate);
        loadStateandDist();
        spPreferredCities=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredCities);
        spPreferredstate.setOnItemClickListener(stateListner);
        spPreferredCities.setOnItemClickListener(citylistner);

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


    }


    public void getAllCaste() {
        spSubCaste.setText("");
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
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            subcastes.add(sublist.getString("subcaste"));
                                        }
                                        CustomListAdapter subcasetadapter = new CustomListAdapter(getActivity(),
                                                R.layout.right_menu_item, subcastes);
                                        spSubCaste.setAdapter(subcasetadapter);

                                    }else {
                                        Utils.showToast(mActivity,"No sub caste");
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
                spPreferredstate.setAdapter(adapter);
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
                spPreferredCities.setAdapter(adapter);
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

    private AdapterView.OnItemClickListener mothertoungelistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    s_mothertounge=String.valueOf(adapterView.getItemAtPosition(i));

                }
            };
    private AdapterView.OnItemClickListener caseteListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_caste = String.valueOf(adapterView.getItemAtPosition(i));
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


    private void subscribe() {
       /* final Observer<User> getUserReceiver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User usr) {
                user = usr;
                binding.setUser(user);
            }
        };*/

        //userDetailsViewModel.getUsers().observeForever(getUserReceiver);
    }

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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {

            case R.id.rbMale:
                user.setGender("male");
                break;

            case R.id.rbFemale:
                user.setGender("female");
                break;

            default:
                break;
        }
    }

    public void onCasteSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setCaste((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onSubCasteSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setSub_caste((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onMotherTongueSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setMother_tongue((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onRaasiSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setRaasi((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onStarSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setNatchathiram((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onPaadhamSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setPaadham((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onDoshamSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setDosham((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onEducationSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setEducation((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onProfessionSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setProfession((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onDisabilitySelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setAny_disablility((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onNationalitySelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setNationality((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onMaritalStatusSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setMarital_status((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onCountrySelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setCountry((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }

    public void onPreferredCitiesSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            partnerPreference.setPreferred_cities((String) parent.getAdapter().getItem(position));
            userDetailsViewModel.setUser(user);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()){
                case R.id.spMaritalStatus:
                    s_maritalstatus =spMaritalStatus.getSelectedItem().toString();
                    break;
                case R.id.spPaadham:
                    s_paatham =spPaadham.getSelectedItem().toString();
                    Log.e("paadham",s_paatham);
                    break;
                case R.id.spDosham:
                    s_dhosam =spDosham.getSelectedItem().toString();
                    break;
                case R.id.spCountry:
                    s_country =spCountry.getSelectedItem().toString();
                    break;
                case R.id.spDisability:
                    s_disability =spDisability.getSelectedItem().toString();
                    break;
                case R.id.etMinHeight:
                    mi_he=etMinHeight.getSelectedItemPosition()+1;
                    s_minheight=etMinHeight.getSelectedItem().toString();
                    break;
                case R.id.etMaxHeight:
                    max_he=etMaxHeight.getSelectedItemPosition()+1;
                    if (max_he < mi_he){
                        Utils.showAlert(getActivity(),"maximum height should be gretter then to minimum height");
                        etMaxHeight.setSelection(0);
                        etMinHeight.setSelection(0);
                    }else {
                        s_maxheight=etMaxHeight.getSelectedItem().toString();

                    }

                    break;
                case R.id.etMinweight:
                    mi_weight=etMinweight.getSelectedItemPosition()+1;
                    s_minweight=etMinweight.getSelectedItem().toString();
                    break;
                case R.id.etMaxWeight:
                    max_weight=etMaxWeight.getSelectedItemPosition()+1;
                    if (max_weight < mi_weight){
                        Utils.showAlert(getActivity(),"maximum Weight should be gretter then to minimum weight");
                        etMaxWeight.setSelection(0);
                        etMinweight.setSelection(0);
                    }else {
                        s_max_weight=etMaxWeight.getSelectedItem().toString();

                    }
                    break;
                case R.id.spNatchathiram:
                    s_natchathram =spNatchathiram.getSelectedItem().toString();
                    break;
                case R.id.etMinincome:
                    mi_income=etMinIncome.getSelectedItemPosition()+1;
                    s_min_income =etMinIncome.getSelectedItem().toString();
                    break;
                case R.id.etMaxincome:
                    max_income=etMaxincome.getSelectedItemPosition()+1;
                    if (mi_income > max_income){
                        Utils.showAlert(getActivity(),"maximum income should be gretter then to minimum inome");
                        etMaxincome.setSelection(0);
                        etMinIncome.setSelection(0);
                    }else {
                        s_max_inxome =etMaxincome.getSelectedItem().toString();

                    }
                    break;
                case R.id.etMinAge:
                    s_minage =etMinAge.getSelectedItem().toString();

//                        new Thread(new Runnable() {
//                            public void run() {
//                                // DO NOT ATTEMPT TO DIRECTLY UPDATE THE UI HERE, IT WON'T WORK!
//                                // YOU MUST POST THE WORK TO THE UI THREAD'S HANDLER
//                                h.postDelayed(new Runnable() {
//                                    public void run() {
//                                        // Open the Spinner...
//                                        etMaxAge.performClick();
//                                    }
//                                }, 1);
//                            }
//                        }).start();
                    break;
                case R.id.etMaxAge:
                    s_maxage =etMaxAge.getSelectedItem().toString();
                    if (!s_maxage.equalsIgnoreCase("Select Age")){
                        if (Integer.parseInt(s_maxage) < Integer.parseInt(s_minage)){

                            Utils.showAlert(getActivity(),"maximum age should be gretter then to minimum age");
                            etMinAge.setSelection(0);
                            etMaxAge.setSelection(0);
                        }else {
                            s_maxage =etMaxAge.getSelectedItem().toString();
                        }
                    }

                    break;
                case R.id.spRaasi:
                    s_raasi=spRaasi.getSelectedItem().toString();
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
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait.");
        dialog.setCancelable(false);
        dialog.show();
        if (radioMale.isChecked()) {
            s_gender = radioMale.getText().toString();
        }else {
            s_gender = radioFemale.getText().toString();
        }

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


    private boolean isInputValidated(User user) {

        if (s_having_dhosam.equals("Yes")){
            if (s_dhosam.equals("Select a Dhosam")) {
                dialog.dismiss();
                errorMsg = "Dosam is empty!";
                return false;
            }
        }else {
            s_dhosam="";
        }
//        if (Utils.isEmpty(bridename)) {
//            errorMsg = "Name is empty!";
//            return false;
//        }
//        if (Utils.isEmpty(dob)) {
//            errorMsg = "Date of Birth is empty!";
//            return false;
//        }
//        if (Utils.isEmpty(s_mothertounge)) {
//            errorMsg = "Mother tongue is empty!";
//            return false;
//        }
//        if (Utils.isEmpty(s_profileby)) {
//            errorMsg = "profile created by is empty!";
//            return false;
//        }

        return true;
    }
    private  void checkparams(){

//        if (!user.getName().equalsIgnoreCase(duplicate.getName())) {
//            params.put("name", user.getName());
//        }
//        if (!user.getDob().equalsIgnoreCase(duplicate.getDob())) {
//            params.put("dob", user.getDob());
//        }
//        if (!user.getMother_tongue().equalsIgnoreCase(duplicate.getMother_tongue())) {
//            params.put("mother_tongue", user.getMother_tongue());
//        }
//        if (!user.getProfile_created_for().equalsIgnoreCase(duplicate.getProfile_created_for())) {
//            params.put("profile_created_for", user.getProfile_created_for());
//        }
        params.put("user_id",userid);
        params.put("min_age", s_minage);
        params.put("min_income", s_min_income);
        params.put("max_income", s_max_inxome);
        params.put("max_age", s_maxage);
        params.put("min_height", s_minheight);
        params.put("max_height", s_maxheight);
        params.put("min_weight", s_minweight);
        params.put("max_weight", s_max_weight);
        params.put("caste", s_caste);
        params.put("sub_caste", s_subcaste);
        params.put("marital_status", s_maritalstatus);
        params.put("mother_tongue", s_mothertounge);
        params.put("paadham", s_paatham);
        params.put("dosham", s_dhosam);
        params.put("having_dosham", s_having_dhosam);
        params.put("education", s_degree);
        params.put("profession", s_myocc);
        params.put("nationality", s_nationality);
        params.put("country", s_country);
        params.put("state", s_sate);
        params.put("natchathiram",s_natchathram);
        params.put("preferred_cities", s_city);
        params.put("physical_status", s_disability);
        params.put("gender",s_gender);
        params.put("raasi",s_raasi);


        userDetailsViewModel.setpartner(params, this);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((Setpartner) response).getMsg());
        dialog.dismiss();
        ((HomeActivity) mActivity).setFragment(INDEX_UPLOAD_ID, null);
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
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        for (int i=0; i<strings.size();i++){
            s_dhosam += strings.get(i)+",";
        }
    }
}
