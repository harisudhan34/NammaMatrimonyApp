package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditEducationFragmentBinding;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.skyappz.namma.activities.HomeActivity.INDEX_FAMILY_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RESINDING_ADDRESS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditEducation extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_degree,select_job,select_workingsector;
    private EditEducationFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    List<String> degrees, jobs, workingSectors;
    private String errorMsg;
    private Activity mActivity;
    ArrayList state_list,city_list;
    AppCompatAutoCompleteTextView autoDegree,autoMyOcc,birth_auto_state,birth_auto_city;
    AppCompatSpinner spWorkingSector,sp_physicalstatus,Birth_SPcountry;
    AppCompatEditText etofficedetails,collegename;
    AppCompatButton update;
    String s_sate,s_city,s_country,s_birthtime;
    AppCompatTextView skip,birthtime;
    ProgressDialog dialog;
    AppCompatEditText disability_commend;
    ArrayList complexionList,bodyTypeList,disabilityList,paadhamList,heightlist,weightlist;
    AppCompatSpinner sp_complexion,sp_bodytype,sp_disability,sp_paadham,height_ed,weight_ed,spMonthlyIncome;
    String s_degree,s_myocc,s_workingsector,s_monthlyIncome,s_oficedetails,s_physical_status,s_collegename;
    String s_complexion,s_btype,s_disability,s_paadham,s_weight,s_height,s_disability_commend;
    public static EditEducation newInstance() {
        return new EditEducation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_education_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
//        binding.setFragment(this);
//        binding.setHomeActivity((HomeActivity) getActivity());
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
//        duplicate = ((HomeActivity) getActivity()).getUser();
//        user = new User().duplicate(duplicate);
//        getData();
//        binding.setUser(user);
        return view;
    }

    private void getData() {
        degrees = Arrays.asList(getActivity().getResources().getStringArray(R.array.degrees));
        jobs = Arrays.asList(getActivity().getResources().getStringArray(R.array.jobs));
        workingSectors = Arrays.asList(getActivity().getResources().getStringArray(R.array.working_sectors));
        user.setEducationPosition(degrees.indexOf(user.getEducation()));
        user.setOccupationPosition(jobs.indexOf(user.getOccupation()));
        user.setWorkingSectorPosition(workingSectors.indexOf(user.getWorking_sector()));
    }

    private void initViews(View view) {
        height_ed=(AppCompatSpinner)view.findViewById(R.id.tetHeight);
        weight_ed=(AppCompatSpinner)view.findViewById(R.id.tetWeight);
        sp_complexion=(AppCompatSpinner)view.findViewById(R.id.sp_complexion);
        sp_bodytype=(AppCompatSpinner)view.findViewById(R.id.sp_bodytype);
        sp_disability=(AppCompatSpinner)view.findViewById(R.id.sp_disability);
        disability_commend=(AppCompatEditText)view.findViewById(R.id.disability_commend);
        sp_paadham=(AppCompatSpinner)view.findViewById(R.id.paaham_sp);
        autoDegree=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoDegree);
        autoMyOcc=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoMyOcc);
        spWorkingSector=(AppCompatSpinner)view.findViewById(R.id.spWorkingSector);
        sp_physicalstatus=(AppCompatSpinner)view.findViewById(R.id.sp_physicalstatus);
        spMonthlyIncome=(AppCompatSpinner)view.findViewById(R.id.spMonthlyIncome);
        etofficedetails=(AppCompatEditText)view.findViewById(R.id.etofficedetails);
        collegename=(AppCompatEditText)view.findViewById(R.id.collegename);
        birthtime=(AppCompatTextView)view.findViewById(R.id.birthtime);
        birth_auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.birth_auto_state);
        birth_auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.birth_auto_city);
        Birth_SPcountry=(AppCompatSpinner)view.findViewById(R.id.Birth_SPcountry);

        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        update.setOnClickListener(this);
        skip.setOnClickListener(this);

        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        ArrayAdapter countryadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        countryadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        Birth_SPcountry.setAdapter(countryadapter);
        Birth_SPcountry.setOnItemSelectedListener(this);

        loadStateandDist();
        birth_auto_state.setOnItemClickListener(stateListner);
        birth_auto_city.setOnItemClickListener(citylistner);

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
        ArrayAdapter workigAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,workingsectorList);
        workigAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spWorkingSector.setAdapter(workigAdapter);
        spWorkingSector.setOnItemSelectedListener(this);

        ArrayList physicalstatus = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.physical_status)));
        ArrayAdapter physiacaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,physicalstatus);
        physiacaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_physicalstatus.setAdapter(physiacaladapter);
        sp_physicalstatus.setOnItemSelectedListener(this);


        ArrayList income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.monthly_income)));
        ArrayAdapter incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spMonthlyIncome.setAdapter(incomeAdapter);
        spMonthlyIncome.setOnItemSelectedListener(this);

        heightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        ArrayAdapter heightadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,heightlist);
        heightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        height_ed.setAdapter(heightadapter);
        height_ed.setOnItemSelectedListener(this);

        weightlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.weight)));
        ArrayAdapter weightlistadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,weightlist);
        weightlistadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        weight_ed.setAdapter(weightlistadapter);
        weight_ed.setOnItemSelectedListener(this);

        complexionList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.complexions)));
        ArrayAdapter complexionadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,complexionList);
        complexionadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_complexion.setAdapter(complexionadapter);
        sp_complexion.setOnItemSelectedListener(this);

        bodyTypeList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.body_types)));
        ArrayAdapter btypeadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,bodyTypeList);
        btypeadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_bodytype.setAdapter(btypeadapter);
        sp_bodytype.setOnItemSelectedListener(this);

        disabilityList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.disability)));
        ArrayAdapter diabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,disabilityList);
        diabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_disability.setAdapter(diabilityadapter);
        sp_disability.setOnItemSelectedListener(this);

        paadhamList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.paatham)));
        ArrayAdapter paadhaadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,paadhamList);
        paadhaadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_paadham.setAdapter(paadhaadapter);
        sp_paadham.setOnItemSelectedListener(this);

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
                birth_auto_state.setAdapter(adapter);
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
                birth_auto_city.setAdapter(adapter);
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void update() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait.");
        dialog.setCancelable(false);
        dialog.show();
        s_sate=birth_auto_state.getText().toString();
        s_city=birth_auto_city.getText().toString();
        s_disability_commend =disability_commend.getText().toString();
        s_degree=autoDegree.getText().toString();
        s_oficedetails=etofficedetails.getText().toString();
        s_myocc=autoMyOcc.getText().toString();
        s_collegename=collegename.getText().toString();
        Log.e("degreee",s_degree);
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
        if (s_height.equalsIgnoreCase("Select a Height")) {
            dialog.dismiss();
            errorMsg = "Height is empty!";
            return false;
        }
        if (s_weight.equalsIgnoreCase("Select a Weight")) {
            dialog.dismiss();
            errorMsg = "Weight is empty!";
            return false;
        }
        if (s_complexion.equals("Select your Complexions")) {
            dialog.dismiss();
            errorMsg = "Complexions is empty!";
            return false;
        }
        if (s_btype.equals("Select your Body Type")) {
            dialog.dismiss();
            errorMsg = "Body type  is empty!";
            return false;
        }
        if (s_disability.equals("Any Disability")) {
            dialog.dismiss();
            errorMsg = "Disability is empty!";
            return false;
        }
//        if (s_physical_status.equals("Select")) {
//            errorMsg = "physical status is empty!";
//            return false;
//        }
        if (s_physical_status.equalsIgnoreCase("Select physical_status")){
            dialog.dismiss();
            errorMsg = "physical status is empty!";
            return false;
        }
        if (s_paadham.equals("Select a Padhams")) {
            dialog.dismiss();
            errorMsg = "Padham is empty!";
            return false;
        }
        if (s_paadham.equals("Select a Padhams")) {
            dialog.dismiss();
            errorMsg = "Padham is empty!";
            return false;
        }
        if (s_paadham.equals("Select a Padhams")) {
            dialog.dismiss();
            errorMsg = "Padham is empty!";
            return false;
        }
        if (s_birthtime.equalsIgnoreCase("")){
            dialog.dismiss();
            errorMsg = "Birth Time   is empty!";
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

        if (s_degree.equalsIgnoreCase("Select your Degree") || Utils.isEmpty(s_degree) ) {
            dialog.dismiss();
            errorMsg = "Degree is empty!";
            return false;
        }
        if ((s_collegename.equalsIgnoreCase(""))){
            dialog.dismiss();
            errorMsg="Enter College name";
            return false;
        }
        if (s_myocc.equalsIgnoreCase("Select your Job") || Utils.isEmpty(s_myocc)) {
            dialog.dismiss();
            errorMsg = "Job is empty!";
            return false;
        }
        if (s_workingsector.equalsIgnoreCase("Select your Working Sector")) {
            dialog.dismiss();
            errorMsg = "Working Sector is empty!";
            return false;
        } if (Utils.isEmpty(s_oficedetails)) {
            dialog.dismiss();
            errorMsg = "Office Detail is empty!";
            return false;
        }

        if (Utils.isEmpty(s_monthlyIncome)) {
            dialog.dismiss();
            errorMsg = "Income is empty!";
            return false;
        }
        return true;
    }



    private  void checkparams(){
        Log.e("paadham",s_paadham);
        Log.e("physical",s_physical_status);
//  if (!user.getEducation().equalsIgnoreCase(duplicate.getEducation())) {
//            params.put("education", user.getEducation());

//        }
//        if (!user.getOccupation().equalsIgnoreCase(duplicate.getOccupation())) {
//            params.put("occupation", user.getOccupation());
//        }
//        if (!user.getWorking_sector().equalsIgnoreCase(duplicate.getWorking_sector())) {
//            params.put("working_sector", user.getWorking_sector());
//        }
//        if (!user.getMax_income().equalsIgnoreCase(duplicate.getMax_income())) {
//            params.put("monthly_income", user.getMax_income());
//        }
//        params.put("user_id", "1");
//        userDetailsViewModel.updateUser(params, this);
        params.put("user_id",userid);
        params.put("education", s_degree);
        params.put("occupation", s_myocc);
        params.put("working_sector", s_workingsector);
        params.put("max_income", s_monthlyIncome);
        params.put("complexion", s_complexion);
        params.put("body_type", s_btype);
        params.put("disability", s_disability);
        params.put("physical_status", s_physical_status);
        params.put("height", s_height);
        params.put("birth_country", s_country);
        params.put("birth_state", s_sate);
        params.put("birth_time", s_birthtime);
        params.put("birth_city", s_city);
        params.put("weight", s_weight);
        params.put("paadham", s_paadham);
        params.put("college", s_collegename);
        params.put("office_details", s_oficedetails);
        userDetailsViewModel.updateUser(params, this);
    }


    public void onEducationSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setEducation((String) parent.getAdapter().getItem(position));
        }
    }

    public void onOccupationSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setOccupation((String) parent.getAdapter().getItem(position));
        }
    }

    public void onWorkingSectorSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setWorking_sector((String) parent.getAdapter().getItem(position));
        }
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_RESINDING_ADDRESS, null);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        dialog.dismiss();
        ((HomeActivity) mActivity).setFragment(INDEX_FAMILY_DETAILS, null);
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
            case R.id.sp_complexion:
                s_complexion=sp_complexion.getSelectedItem().toString();
                break;
            case R.id.sp_bodytype:
                s_btype=sp_bodytype.getSelectedItem().toString();
                break;
            case R.id.sp_disability:
                s_disability=sp_disability.getSelectedItem().toString();
                if (s_disability .equalsIgnoreCase("Yes")){
                    disability_commend.setVisibility(View.VISIBLE);
                }else {
                    disability_commend.setVisibility(View.GONE);
                }
                break;
            case R.id.paaham_sp:
                s_paadham=sp_paadham.getSelectedItem().toString();
                Log.e("sp_paadham",s_paadham);
                break;
            case R.id.tetHeight:
                s_height=height_ed.getSelectedItem().toString();
                break;
            case R.id.tetWeight:
                s_weight=weight_ed.getSelectedItem().toString();
                break;
            case R.id.spWorkingSector:
                s_workingsector =spWorkingSector.getSelectedItem().toString();
                break;
            case R.id.spMonthlyIncome:
                s_monthlyIncome =spMonthlyIncome.getSelectedItem().toString();
                break;
            case R.id.sp_physicalstatus:
                s_physical_status =sp_physicalstatus.getSelectedItem().toString();
                break;
            case  R.id.Birth_SPcountry:
                s_country =Birth_SPcountry.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
