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

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_FAMILY_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RESINDING_ADDRESS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditPersonalDetails extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_degree,select_job,select_workingsector;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    List<String> degrees, jobs, workingSectors;
    private String errorMsg;
    private Activity mActivity;
      AppCompatSpinner sp_physicalstatus;
    AppCompatButton update;
    GifImageView progress;
    AppCompatTextView skip;
    ProgressDialog dialog;
    AppCompatEditText disability_commend;
    ArrayList complexionList,bodyTypeList,disabilityList,heightlist,weightlist;
    AppCompatSpinner sp_complexion,sp_bodytype,sp_disability,height_ed,weight_ed;
    String s_physical_status;
    String s_complexion,s_btype,s_disability,s_weight,s_height,s_disability_commend;
    public static EditPersonalDetails newInstance() {
        return new EditPersonalDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_personal_fragment, container, false);
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
        progress=(GifImageView)view.findViewById(R.id.progress);
        progress=(GifImageView)view.findViewById(R.id.progress);
        height_ed=(AppCompatSpinner)view.findViewById(R.id.tetHeight);
        weight_ed=(AppCompatSpinner)view.findViewById(R.id.tetWeight);
        sp_complexion=(AppCompatSpinner)view.findViewById(R.id.sp_complexion);
        sp_bodytype=(AppCompatSpinner)view.findViewById(R.id.sp_bodytype);
        sp_disability=(AppCompatSpinner)view.findViewById(R.id.sp_disability);
        disability_commend=(AppCompatEditText)view.findViewById(R.id.disability_commend);
        sp_physicalstatus=(AppCompatSpinner)view.findViewById(R.id.sp_physicalstatus);


        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        update.setOnClickListener(this);
        skip.setOnClickListener(this);


        ArrayList physicalstatus = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.physical_status)));
        ArrayAdapter physiacaladapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,physicalstatus);
        physiacaladapter.setDropDownViewResource(R.layout.spinner_drop_item);
        sp_physicalstatus.setAdapter(physiacaladapter);
        sp_physicalstatus.setOnItemSelectedListener(this);


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



    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void update() {
       progress.setVisibility(View.VISIBLE);

        s_disability_commend =disability_commend.getText().toString();

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
            progress.setVisibility(View.GONE);
            errorMsg = "Height is empty!";
            return false;
        }
        if (s_weight.equalsIgnoreCase("Select a Weight")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Weight is empty!";
            return false;
        }
        if (s_complexion.equals("Select your Complexions")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Complexions is empty!";
            return false;
        }
        if (s_btype.equals("Select your Body Type")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Body type  is empty!";
            return false;
        }
        if (s_disability.equals("Any Disability")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Disability is empty!";
            return false;
        }
//        if (s_physical_status.equals("Select")) {
//            errorMsg = "physical status is empty!";
//            return false;
//        }
        if (s_physical_status.equalsIgnoreCase("Select physical_status")){
            progress.setVisibility(View.GONE);
            errorMsg = "physical status is empty!";
            return false;
        }





        return true;
    }



    private  void checkparams(){

        params.put("user_id",userid);

        params.put("complexion", s_complexion.toLowerCase());
        params.put("body_type", s_btype.toLowerCase());
        params.put("disability", s_disability.toLowerCase());
        params.put("physical_status", s_physical_status.toLowerCase());
        params.put("height", s_height.toLowerCase());
        params.put("weight", s_weight.toLowerCase());

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
        progress.setVisibility(View.GONE);
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

            case R.id.tetHeight:
                s_height=height_ed.getSelectedItem().toString();
                break;
            case R.id.tetWeight:
                s_weight=weight_ed.getSelectedItem().toString();
                String[] separated = s_weight.split(" ");
                s_weight = separated[0];
                break;

            case R.id.sp_physicalstatus:
                s_physical_status =sp_physicalstatus.getSelectedItem().toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
