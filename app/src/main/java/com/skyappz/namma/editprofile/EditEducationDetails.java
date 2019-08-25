package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.utils.MultiSelectionSpinner;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_FAMILY_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditEducationDetails extends Fragment implements WebServiceListener, View.OnClickListener,AdapterView.OnItemSelectedListener {

    private UserDetailsViewModel userDetailsViewModel;

    private Activity mActivity;
    GifImageView progress;
    AppCompatEditText etofficedetails,collegename;
    HashMap<String, String> params = new HashMap<>();
    AppCompatButton update;
    AppCompatSpinner spWorkingSector,spMonthlyIncome;
    AppCompatAutoCompleteTextView autoDegree,autoMyOcc;
    String s_degree,s_myocc,errorMsg,s_workingsector,s_monthlyIncome,s_oficedetails,s_collegename,s_min_inccome;
    public static EditEducationDetails newInstance() {
        return new EditEducationDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_education_details_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        return view;
    }



    private void initViews(View view) {

        spWorkingSector=(AppCompatSpinner)view.findViewById(R.id.spWorkingSector);
        spMonthlyIncome=(AppCompatSpinner)view.findViewById(R.id.spMonthlyIncome);
        etofficedetails=(AppCompatEditText)view.findViewById(R.id.etofficedetails);
        collegename=(AppCompatEditText)view.findViewById(R.id.collegename);
        autoDegree=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoDegree);
        autoMyOcc=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoMyOcc);
        update = (AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);
        progress=(GifImageView)view.findViewById(R.id.progress);
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

        ArrayList income = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.monthly_income)));
        ArrayAdapter incomeAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,income);
        incomeAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spMonthlyIncome.setAdapter(incomeAdapter);
        spMonthlyIncome.setOnItemSelectedListener(this);


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
        s_degree=autoDegree.getText().toString();
        s_oficedetails=etofficedetails.getText().toString();
        s_myocc=autoMyOcc.getText().toString();
        s_collegename=collegename.getText().toString();
        Log.e("degreee",s_degree);
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

        if (s_degree.equalsIgnoreCase("Select your Degree") || Utils.isEmpty(s_degree) ) {
            progress.setVisibility(View.GONE);
            errorMsg = "Degree is empty!";
            return false;
        }
        if ((s_collegename.equalsIgnoreCase(""))){
            progress.setVisibility(View.GONE);
            errorMsg="Enter College name";
            return false;
        }
        if (s_myocc.equalsIgnoreCase("Select your Job") || Utils.isEmpty(s_myocc)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Job is empty!";
            return false;
        }
        if (s_workingsector.equalsIgnoreCase("Select your Working Sector")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Working Sector is empty!";
            return false;
        } if (Utils.isEmpty(s_oficedetails)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Office Detail is empty!";
            return false;
        }

        if (Utils.isEmpty(s_monthlyIncome)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Income is empty!";
            return false;
        }
        return true;
    }

    private  void checkparams(){
        params.put("user_id",userid);
        params.put("education", s_degree.toLowerCase());
        params.put("occupation", s_myocc.toLowerCase());
        params.put("working_sector", s_workingsector.toLowerCase());
        params.put("max_income", s_monthlyIncome);
        params.put("min_income", s_min_inccome);
        params.put("college", s_collegename.toLowerCase());
        params.put("office_details", s_oficedetails.toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.spWorkingSector:
                s_workingsector =spWorkingSector.getSelectedItem().toString();
                break;
            case R.id.spMonthlyIncome:
                s_monthlyIncome =spMonthlyIncome.getSelectedItem().toString();
                String selectedpos = String.valueOf(spMonthlyIncome.getItemIdAtPosition(position));
                if (!s_monthlyIncome.equalsIgnoreCase("Select Anual Income")){
                    String[] incomesplit = s_monthlyIncome.split(" ");
                    s_monthlyIncome = incomesplit[2];
                    s_min_inccome = incomesplit[0];

                    switch (s_monthlyIncome){
                        case "1" :
                            s_monthlyIncome = "100000";
                            break;
                        case "2" :
                            s_monthlyIncome = "200000";
                            break;
                        case "3" :
                            s_monthlyIncome = "300000";
                            break;
                        case "4" :
                            s_monthlyIncome = "400000";
                            break;
                        case "5" :
                            s_monthlyIncome = "500000";
                            break;
                        case "6" :
                            s_monthlyIncome = "600000";
                            break;
                        case "7" :
                            s_monthlyIncome = "700000";
                            break;
                        case "8" :
                            s_monthlyIncome = "800000";
                            break;
                        case "9" :
                            s_monthlyIncome = "900000";
                            break;
                        case "10" :
                            s_monthlyIncome = "1000000";
                            break;
                        case "12" :
                            s_monthlyIncome = "1200000";
                            break;
                        case "14" :
                            s_monthlyIncome = "1400000";
                            break;
                        case "16" :
                            s_monthlyIncome = "1600000";
                            break;
                        case "18" :
                            s_monthlyIncome = "1800000";
                            break;
                        case "20" :
                            s_monthlyIncome = "2000000";
                            break;
                        case "25" :
                            s_monthlyIncome = "2500000";
                            break;
                        case "30" :
                            s_monthlyIncome = "3000000";
                            break;
                        case "40" :
                            s_monthlyIncome = "4000000";
                            break;
                        case "50" :
                            s_monthlyIncome = "5000000";
                            break;
                        case "60" :
                            s_monthlyIncome = "6000000";
                            break;
                        case "70" :
                            s_monthlyIncome = "7000000";
                            break;
                        case "80" :
                            s_monthlyIncome = "8000000";
                            break;
                        case "90" :
                            s_monthlyIncome = "9000000";
                            break;

                    }

                    switch (s_min_inccome){
                        case "0" :
                            s_min_inccome = "0";
                            break;
                        case "1" :
                            s_min_inccome = "100000";
                            break;
                        case "2" :
                            s_min_inccome = "200000";
                            break;
                        case "3" :
                            s_min_inccome = "300000";
                            break;
                        case "4" :
                            s_min_inccome = "400000";
                            break;
                        case "5" :
                            s_min_inccome = "500000";
                            break;
                        case "6" :
                            s_monthlyIncome = "600000";
                            break;
                        case "7" :
                            s_min_inccome = "700000";
                            break;
                        case "8" :
                            s_min_inccome = "800000";
                            break;
                        case "9" :
                            s_min_inccome = "900000";
                            break;
                        case "10" :
                            s_min_inccome = "1000000";
                            break;
                        case "12" :
                            s_monthlyIncome = "1200000";
                            break;
                        case "14" :
                            s_min_inccome = "1400000";
                            break;
                        case "16" :
                            s_min_inccome = "1600000";
                            break;
                        case "18" :
                            s_monthlyIncome = "1800000";
                            break;
                        case "20" :
                            s_min_inccome = "2000000";
                            break;
                        case "25" :
                            s_min_inccome = "2500000";
                            break;
                        case "30" :
                            s_monthlyIncome = "3000000";
                            break;
                        case "40" :
                            s_min_inccome = "4000000";
                            break;
                        case "50" :
                            s_monthlyIncome = "5000000";
                            break;
                        case "60" :
                            s_min_inccome = "6000000";
                            break;
                        case "70" :
                            s_min_inccome = "7000000";
                            break;
                        case "80" :
                            s_monthlyIncome = "8000000";
                            break;

                    }


                }


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
        ((HomeActivity) mActivity).setFragment(INDEX_FAMILY_DETAILS, null);
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
