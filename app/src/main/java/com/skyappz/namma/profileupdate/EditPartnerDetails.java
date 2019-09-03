package com.skyappz.namma.profileupdate;


import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
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
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.editprofile.UserDetailsViewModel;
import com.skyappz.namma.model.PartnerPreference;
import com.skyappz.namma.model.User;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDIT_PARTNER_2;
import static com.skyappz.namma.activities.HomeActivity.userid;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPartnerDetails extends Fragment implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, View.OnClickListener, WebServiceListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener  {

    private UserDetailsViewModel userDetailsViewModel;
    User user;
    RadioGroup gender,radiodhosam;
    RadioButton radioMale,radioFemale,radio_yes,radio_no;
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> castesid =new ArrayList<>();
    ArrayAdapter minheightadapter,ageadapeer,maxheightadapter,minweightlistadapter,maxnweightlistadapter,incomeAdapter,maritaladapter,
            adapter1,spDisabilityadapter,nachathramadapter,paadhaadapter;
    ArrayList<String> subcastes =new ArrayList<>();
    ArrayList<String> subcastesid =new ArrayList<>();
    PartnerPreference partnerPreference;
    ArrayList maritalStatusList,paadhamList,dosamList,raasi_list,min_height_list,max_height_list,min_weightlist,max_weightlist,natchartamlist;
    ProgressDialog dialog;
    AppCompatSpinner etMinAge,etMaxAge,etMinIncome,etMaxincome, spMaritalStatus,spRaasi,spNatchathiram,spPaadham,spDisability,etMinHeight,etMaxHeight,etMinweight,etMaxWeight;
    AppCompatAutoCompleteTextView auto_religion,spCaste,spSubCaste,spMotherTongue;
    String s_gender,s_mothertounge,s_caste,s_casteid,s_subcaste,s_subcaste_id,s_maritalstatus,s_paatham,s_dhosam,s_disability,
            s_minage,s_maxage="",s_minheight,s_maxheight,s_minweight,s_max_weight,s_natchathram,s_min_income,s_max_inxome,s_raasi,s_having_dhosam;
    AppCompatButton update;
    AppCompatTextView skip;
    private Activity mActivity;
    private String errorMsg;
    MultiSelectionSpinner spDosham;
    Handler h;
    private static final String URL_GET_PARTNER = "https://nammamatrimony.in/api/getpartner.php?user_id=";
    GifImageView progress;
    Integer mi_he,max_he,mi_weight,max_weight,mi_income,max_income;
    String s_religion;
    LinearLayout dhosam_details_layout;
    HashMap<String, String> params = new HashMap<>();
    public EditPartnerDetails() {
        // Required empty public constructor
    }

    public static EditPartnerDetails newInstance() {
        return new EditPartnerDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_edit_partner_details, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());
        //userDetailsViewModel.getUserDetails("1");
        subscribe();
        initViews(v);


        return v;
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

                                int selectionPosition= ageadapeer.getPosition(user.getString("min_age"));
                                etMinAge.setSelection(selectionPosition);
                                int selectionPosition1= ageadapeer.getPosition(user.getString("max_age"));
                                etMaxAge.setSelection(selectionPosition1);

                                int selectionPosition2= minheightadapter.getPosition(user.getString("min_height"));
                                etMinHeight.setSelection(selectionPosition2);
                                int selectionPosition3= maxheightadapter.getPosition(user.getString("max_height"));
                                etMaxHeight.setSelection(selectionPosition3);

                                int selectionPosition4= minweightlistadapter.getPosition(user.getString("min_weight"));
                                etMinweight.setSelection(selectionPosition4);
                                int selectionPosition5= maxnweightlistadapter.getPosition(user.getString("max_weight"));
                                etMaxWeight.setSelection(selectionPosition5);

                                int selectionPosition6= incomeAdapter.getPosition(user.getString("min_income"));
                                etMinIncome.setSelection(selectionPosition6);
                                int selectionPosition7= incomeAdapter.getPosition(user.getString("max_income"));
                                etMaxincome.setSelection(selectionPosition7);

                                int selectionPosition8= maritaladapter.getPosition(user.getString("marital_status"));
                                spMaritalStatus.setSelection(selectionPosition8);

                                int selectionPosition9= adapter1.getPosition(user.getString("marital_status"));
                                spMotherTongue.setSelection(selectionPosition9);

                                int selectionPosition10= spDisabilityadapter.getPosition(user.getString("physical_status"));
                                spDisability.setSelection(selectionPosition10);

                                spCaste.setText(user.getString("caste"));
                                spSubCaste.setText(user.getString("sub_caste"));

                                int selectionPosition11= nachathramadapter.getPosition(user.getString("natchathiram"));
                                spNatchathiram.setSelection(selectionPosition11);

                                int selectionPosition12= paadhaadapter.getPosition(user.getString("paadham"));
                                spPaadham.setSelection(selectionPosition12);

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

    public void initViews(View view){
        h = new Handler();
        progress=(GifImageView)view.findViewById(R.id.progress);
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

        auto_religion=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_religion);
        ArrayList<String> religojn = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, religojn);
        auto_religion.setAdapter(adapter);
        auto_religion.setOnItemClickListener(religionclick);
        radiodhosam=(RadioGroup)view.findViewById(R.id.radiodhosam);
        radio_yes=(RadioButton)view.findViewById(R.id.radio_yes);
        radio_no=(RadioButton)view.findViewById(R.id.radio_no);
        dhosam_details_layout=(LinearLayout)view.findViewById(R.id.dhosam_details_layout);
        spCaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spCaste);
        spSubCaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spSubCaste);
        getAllCaste();

        ArrayList income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.min_income)));
        incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinIncome.setAdapter(incomeAdapter);
        etMinIncome.setOnItemSelectedListener(this);

        ArrayList max_income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.max_income)));
        ArrayAdapter max_incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,max_income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMaxincome.setAdapter(max_incomeAdapter);
        etMaxincome.setOnItemSelectedListener(this);

        ArrayList age = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.age)));
        ageadapeer=new ArrayAdapter(getActivity(),R.layout.spinner_item,age);
        ageadapeer.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinAge.setAdapter(ageadapeer);
        etMinAge.setOnItemSelectedListener(this);
        etMaxAge.setAdapter(ageadapeer);
        etMaxAge.setOnItemSelectedListener(this);

        min_height_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        minheightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,min_height_list);
        minheightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinHeight.setAdapter(minheightadapter);
        etMinHeight.setOnItemSelectedListener(this);

        max_height_list = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        maxheightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,max_height_list);
        maxheightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMaxHeight.setAdapter(maxheightadapter);
        etMaxHeight.setOnItemSelectedListener(this);

        min_weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
        minweightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,min_weightlist);
        minweightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMinweight.setAdapter(minweightlistadapter);
        etMinweight.setOnItemSelectedListener(this);

        max_weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
        maxnweightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,max_weightlist);
        maxnweightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etMaxWeight.setAdapter(maxnweightlistadapter);
        etMaxWeight.setOnItemSelectedListener(this);

        spMaritalStatus=(AppCompatSpinner)view.findViewById(R.id.spMaritalStatus);
        maritalStatusList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        maritaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,maritalStatusList);
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
        nachathramadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,natchartamlist);
        nachathramadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spNatchathiram.setAdapter(nachathramadapter);
        spNatchathiram.setOnItemSelectedListener(this);



        spPaadham=(AppCompatSpinner)view.findViewById(R.id.spPaadham);
        paadhamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.paatham)));
        paadhaadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,paadhamList);
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



        spMotherTongue=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spMotherTongue);
        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        adapter1 = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, motherTongues);
        spMotherTongue.setAdapter(adapter1);
        spMotherTongue.setOnItemClickListener(mothertoungelistner);


        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        spCaste.setAdapter(casetadapter);
        spCaste.setOnItemClickListener(caseteListner);



        spSubCaste.setOnItemClickListener(subcastelistner);



        spDisability=(AppCompatSpinner)view.findViewById(R.id.spDisability);
        ArrayList disablitylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.physical_status)));
        spDisabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,disablitylist);
        spDisabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spDisability.setAdapter(spDisabilityadapter);
        spDisability.setOnItemSelectedListener(this);





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

        get_user();
    }
    private AdapterView.OnItemClickListener religionclick =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_religion = String.valueOf(adapterView.getItemAtPosition(i));
                    Log.e("religion",s_religion);
                }
            };



    public void getAllCaste() {
        HttpsTrustManager.allowAllSSL();
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
                                castesid.add(list.getString("id"));
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

    public void getsubcaste(final String select_caste) {
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
                                if (select_caste.equalsIgnoreCase(list.getString("caste"))){
                                    JSONArray subcasetarray=list.getJSONArray("subcastes");
                                    if (subcasetarray.length()> 0){
                                        for (int j=0;j<subcasetarray.length();j++){
                                            JSONObject sublist = subcasetarray.getJSONObject(j);
                                            subcastes.add(sublist.getString("subcaste"));
                                            subcastesid.add(sublist.getString("id"));
                                        }
                                        CustomListAdapter subcasetadapter = new CustomListAdapter(getActivity(),
                                                R.layout.right_menu_item, subcastes);
                                        spSubCaste.setAdapter(subcasetadapter);
                                        spSubCaste.setOnItemClickListener(subcastelistner);

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
                    int castepos = castes.indexOf(s_caste);
                    String castid=  castesid.get(castepos);
                    Log.e("casteid",castid);
                    s_casteid= castid;
                    getsubcaste(s_caste);
                }
            };
    private AdapterView.OnItemClickListener subcastelistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_subcaste = String.valueOf(adapterView.getItemAtPosition(i));
                    int castepos = subcastes.indexOf(s_subcaste);
                    String castid=  subcastesid.get(castepos);
                    Log.e("casteid",castid);
                    s_subcaste_id = castid;
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
                String[] separated = s_minweight.split(" ");
                s_minweight = separated[0];
                break;
            case R.id.etMaxWeight:
                max_weight=etMaxWeight.getSelectedItemPosition()+1;
                if (max_weight < mi_weight){
                    Utils.showAlert(getActivity(),"maximum Weight should be gretter then to minimum weight");
                    etMaxWeight.setSelection(0);
                    etMinweight.setSelection(0);
                }else {
                    s_max_weight=etMaxWeight.getSelectedItem().toString();
                    String[] separated1 = s_max_weight.split(" ");
                    s_max_weight = separated1[0];
                }
                break;
            case R.id.spNatchathiram:
                s_natchathram =spNatchathiram.getSelectedItem().toString();
                break;
            case R.id.etMinincome:
                mi_income=etMinIncome.getSelectedItemPosition()+1;
                s_min_income =etMinIncome.getSelectedItem().toString();
                if (!s_min_income.equalsIgnoreCase("Select Min Income")){
                    switch (s_min_income){
                        case "Less than Rs 50,000" :
                            s_min_income = "49000";
                            break;
                        case "Rs.50 Thousand" :
                            s_min_income = "50000";
                            break;
                        case "Rs.1 Lakh" :
                            s_min_income = "100000";
                            break;
                        case "Rs.2 Lakh" :
                            s_min_income = "200000";
                            break;
                        case "Rs.3 Lakh" :
                            s_min_income = "300000";
                            break;
                        case "Rs.4 Lakh" :
                            s_min_income = "600000";
                            break;
                        case "Rs.5 Lakh" :
                            s_min_income = "500000";
                            break;
                        case "Rs.6 Lakh" :
                            s_min_income = "600000";
                            break;
                        case "Rs.7 Lakh" :
                            s_min_income = "700000";
                            break;
                        case "Rs.8 Lakh" :
                            s_min_income = "800000";
                            break;
                        case "Rs.9 Lakh" :
                            s_min_income = "900000";
                            break;
                        case "Rs.10 Lakh" :
                            s_min_income = "1000000";
                            break;
                        case "Rs.12 Lakh" :
                            s_min_income = "1200000";
                            break;
                        case "Rs.14 Lakh" :
                            s_min_income = "1400000";
                            break;
                        case "Rs.16 Lakh" :
                            s_min_income = "1600000";
                            break;
                        case "Rs.18 Lakh" :
                            s_min_income = "1800000";
                            break;
                        case "Rs.20 Lakh" :
                            s_min_income = "2000000";
                            break;
                        case "Rs.25 Lakh" :
                            s_min_income = "2500000";
                            break;
                        case "Rs.30 Lakh" :
                            s_min_income = "3000000";
                            break;
                        case "Rs.40 Lakh" :
                            s_min_income = "4000000";
                            break;
                        case "Rs.50 Lakh" :
                            s_min_income = "5000000";
                            break;
                        case "Rs.60 Lakh" :
                            s_min_income = "6000000";
                            break;
                        case "Rs.70 Lakh" :
                            s_min_income = "7000000";
                            break;
                        case "Rs.80 Lakh" :
                            s_min_income = "8000000";
                            break;
                        case "Rs.90 Lakh" :
                            s_min_income = "9000000";
                            break;

                    }
                }

                break;
            case R.id.etMaxincome:
                max_income=etMaxincome.getSelectedItemPosition()+1;
                if (mi_income > max_income){
                    Utils.showAlert(getActivity(),"maximum income should be gretter then to minimum inome");
                    etMaxincome.setSelection(0);
                    etMinIncome.setSelection(0);
                }else {
                    s_max_inxome =etMaxincome.getSelectedItem().toString();
                    if (!s_max_inxome.equalsIgnoreCase("Select Min Income")){
                        switch (s_max_inxome){
                            case "Less than Rs 50,000" :
                                s_max_inxome = "49000";
                                break;
                            case "Rs.50 Thousand" :
                                s_max_inxome = "50000";
                                break;
                            case "Rs.1 Lakh" :
                                s_max_inxome = "100000";
                                break;
                            case "Rs.2 Lakh" :
                                s_max_inxome = "200000";
                                break;
                            case "Rs.3 Lakh" :
                                s_max_inxome = "300000";
                                break;
                            case "Rs.4 Lakh" :
                                s_max_inxome = "600000";
                                break;
                            case "Rs.5 Lakh" :
                                s_max_inxome = "500000";
                                break;
                            case "Rs.6 Lakh" :
                                s_max_inxome = "600000";
                                break;
                            case "Rs.7 Lakh" :
                                s_max_inxome = "700000";
                                break;
                            case "Rs.8 Lakh" :
                                s_max_inxome = "800000";
                                break;
                            case "Rs.9 Lakh" :
                                s_max_inxome = "900000";
                                break;
                            case "Rs.10 Lakh" :
                                s_max_inxome = "1000000";
                                break;
                            case "Rs.12 Lakh" :
                                s_max_inxome = "1200000";
                                break;
                            case "Rs.14 Lakh" :
                                s_max_inxome = "1400000";
                                break;
                            case "Rs.16 Lakh" :
                                s_min_income = "1600000";
                                break;
                            case "Rs.18 Lakh" :
                                s_max_inxome = "1800000";
                                break;
                            case "Rs.20 Lakh" :
                                s_max_inxome = "2000000";
                                break;
                            case "Rs.25 Lakh" :
                                s_max_inxome = "2500000";
                                break;
                            case "Rs.30 Lakh" :
                                s_max_inxome = "3000000";
                                break;
                            case "Rs.40 Lakh" :
                                s_max_inxome = "4000000";
                                break;
                            case "Rs.50 Lakh" :
                                s_max_inxome = "5000000";
                                break;
                            case "Rs.60 Lakh" :
                                s_max_inxome = "6000000";
                                break;
                            case "Rs.70 Lakh" :
                                s_max_inxome = "7000000";
                                break;
                            case "Rs.80 Lakh" :
                                s_max_inxome = "8000000";
                                break;
                            case "Rs.90 Lakh" :
                                s_max_inxome = "9000000";
                                break;

                        }
                    }


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
        progress.setVisibility(View.VISIBLE);
        if (radioMale.isChecked()) {
            s_gender = radioMale.getText().toString();
        }else {
            s_gender = radioFemale.getText().toString();
        }

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
                progress.setVisibility(View.GONE);
                errorMsg = "Dosam is empty!";
                return false;
            }
        }else {
            s_dhosam="";
        }

        return true;
    }
    private  void checkparams(){

        params.put("user_id",userid);
        params.put("min_age", s_minage.toLowerCase());
        params.put("min_income", s_min_income.toLowerCase());
        params.put("max_income", s_max_inxome.toLowerCase());
        params.put("max_age", s_maxage.toLowerCase());
        params.put("min_height", s_minheight.toLowerCase());
        params.put("max_height", s_maxheight.toLowerCase());
        params.put("min_weight", s_minweight.toLowerCase());
        params.put("max_weight", s_max_weight.toLowerCase());
        params.put("caste", s_casteid);
        if (s_subcaste != null){
            params.put("sub_caste", s_subcaste_id);
        }

        params.put("marital_status", s_maritalstatus.toLowerCase());
        params.put("mother_tongue", s_mothertounge.toLowerCase());
        params.put("paadham", s_paatham.toLowerCase());
        if (s_dhosam != null){
            params.put("dosham", s_dhosam.toLowerCase());
        }

        params.put("having_dosham", s_having_dhosam.toLowerCase());

        params.put("natchathiram",s_natchathram.toLowerCase());
        params.put("physical_status", s_disability.toLowerCase());
        params.put("gender",s_gender.toLowerCase());
        params.put("raasi",s_raasi.toLowerCase());


        userDetailsViewModel.setpartner(params, this);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((Setpartner) response).getMsg());
        progress.setVisibility(View.GONE);
        ((HomeActivity) mActivity).setFragment(INDEX_EDIT_PARTNER_2, null);
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
        s_dhosam="";
        for (int i=0; i<strings.size();i++){
            s_dhosam += strings.get(i)+",";
        }
    }


}
