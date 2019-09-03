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
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

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
import com.skyappz.namma.databinding.EditFamilyDetailsFragmentBinding;
import com.skyappz.namma.model.User;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_HABITUAL_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditFamilyDetails extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_familytype,select_family_status,select_father_occ,select_mother_occ;
    private EditFamilyDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    AppCompatSpinner spFamilyType, spFamilyStatus,spFamilyValues,etbro_status,et_sis_status;
    AppCompatAutoCompleteTextView spMotherOccupation,spFatherOccupation;
    List<String> jobs, familyTypes, familyStatuses;
    private String errorMsg;
    private Activity mActivity;
    ProgressDialog dialog;
    AppCompatButton update;
    GifImageView progress;
    AppCompatTextView skip;
    int sibling_count;
    String s_change_nosibiling,s_change_no_bro,s_change_no_sis,s_change_bro_status,s_change_sis_status;
    AppCompatEditText etNoOfSiblings,etNoOfbrother,etNoOfsister,et_family_location;
    ArrayList ftype,fstatus,f_values,bro_status;
    AppCompatEditText etFatherName,etMothername;
    String s_familytype,s_familystatus,s_fatherocc,s_motherocc,s_noofsibiling,s_fathername,s_mothername,s_familyvalue,s_no_of_bro,s_no_sis,s_bro_status,s_sis_status,s_family_location;
    public static EditFamilyDetails newInstance() {
        return new EditFamilyDetails();
    }
    LinearLayout _nim_bro_lay,_num_sis_lay,_bro_status_lay,_sis_status_lay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_family_details_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        super.onAttach(activity);
        mActivity = activity;
    }
    private void initViews(View view) {
        progress=(GifImageView)view.findViewById(R.id.progress);
        spMotherOccupation=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spMotherOccupation);
        spFatherOccupation=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spFatherOccupation);
        etNoOfSiblings=(AppCompatEditText)view.findViewById(R.id.etNoOfSiblings);
        etNoOfbrother=(AppCompatEditText)view.findViewById(R.id.etNoOfbrother);
        etNoOfsister=(AppCompatEditText)view.findViewById(R.id.etNoOfsister);
        etbro_status=(AppCompatSpinner)view.findViewById(R.id.etbro_status);
        et_sis_status=(AppCompatSpinner)view.findViewById(R.id.et_sis_status);
        _nim_bro_lay=(LinearLayout)view.findViewById(R.id._nim_bro_lay);
        _num_sis_lay=(LinearLayout)view.findViewById(R.id._num_sis_lay);
        _bro_status_lay=(LinearLayout)view.findViewById(R.id._bro_status_lay);
        _sis_status_lay=(LinearLayout)view.findViewById(R.id._sis_status_lay);
        et_family_location=(AppCompatEditText)view.findViewById(R.id.et_family_location);
        spFamilyType=(AppCompatSpinner)view.findViewById(R.id.spFamilyType);
        spFamilyStatus=(AppCompatSpinner)view.findViewById(R.id.spFamilyStatus);
        spFamilyValues=(AppCompatSpinner)view.findViewById(R.id.spFamilyValues);
        etFatherName=(AppCompatEditText)view.findViewById(R.id.etFatherName);
        etMothername=(AppCompatEditText)view.findViewById(R.id.etMothername);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);

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
        ArrayAdapter familyType=new ArrayAdapter(getActivity(),R.layout.spinner_item,ftype);
        familyType.setDropDownViewResource(R.layout.spinner_drop_item);
        spFamilyType.setAdapter(familyType);
        spFamilyType.setOnItemSelectedListener(this);

        fstatus= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_statuses)));
        ArrayAdapter familystatus=new ArrayAdapter(getActivity(),R.layout.spinner_item,fstatus);
        familystatus.setDropDownViewResource(R.layout.spinner_drop_item);
        spFamilyStatus.setAdapter(familystatus);
        spFamilyStatus.setOnItemSelectedListener(this);

        f_values= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.family_values)));
        ArrayAdapter familyvalueadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,f_values);
        familyvalueadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spFamilyValues.setAdapter(familyvalueadapter);
        spFamilyValues.setOnItemSelectedListener(this);

        bro_status= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.select_status)));
        ArrayAdapter brostatusaapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,bro_status);
        brostatusaapter.setDropDownViewResource(R.layout.spinner_drop_item);
        etbro_status.setAdapter(brostatusaapter);
        et_sis_status.setAdapter(brostatusaapter);
        etbro_status.setOnItemSelectedListener(this);
        et_sis_status.setOnItemSelectedListener(this);

        etNoOfSiblings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s_change_nosibiling=s.toString();
                if (!s_change_nosibiling.equalsIgnoreCase("")){
                    if (Integer.parseInt(s_change_nosibiling) > 0){
                        _num_sis_lay.setVisibility(View.VISIBLE);
                        _nim_bro_lay.setVisibility(View.VISIBLE);
                    }else {
                        _num_sis_lay.setVisibility(View.GONE);
                        _nim_bro_lay.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNoOfbrother.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s_change_no_bro=s.toString();
                if (!s_change_no_bro.equalsIgnoreCase("")){
                    if (Integer.parseInt(s_change_no_bro) > 0){
                        _bro_status_lay.setVisibility(View.VISIBLE);
                    }else {
                        _bro_status_lay.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNoOfsister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s_change_no_sis=s.toString();
                if(!s_change_no_sis.equalsIgnoreCase("")){

                    if (Integer.parseInt(s_change_no_sis) > 0){
                        _sis_status_lay.setVisibility(View.VISIBLE);
                    }else {
                        _sis_status_lay.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        update.setOnClickListener(this);
        skip.setOnClickListener(this);

    }


    private AdapterView.OnItemClickListener fatheroccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_fatherocc = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };

    private AdapterView.OnItemClickListener motheroccListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    s_motherocc = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };

    private void getData() {
        familyTypes = Arrays.asList(getActivity().getResources().getStringArray(R.array.family_types));
        familyStatuses = Arrays.asList(getActivity().getResources().getStringArray(R.array.family_statuses));
        jobs = Arrays.asList(getActivity().getResources().getStringArray(R.array.jobs));
        user.setFamilyTypePosition(familyTypes.indexOf(user.getFamily_type()));
        user.setFamilyStatusPosition(familyStatuses.indexOf(user.getFamily_status()));
        user.setMotherOccupationPosition(jobs.indexOf(user.getMother_occupation()));
        user.setFatherOccupationPosition(jobs.indexOf(user.getFather_occupation()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onFamilyTypeSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setFamily_type((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onFamilyStatusSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setFamily_status((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onFatherOccupationSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setFather_occupation((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onMotherOccupationSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setMother_occupation((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }
    public void update() {
       progress.setVisibility(View.VISIBLE);
       if (s_no_of_bro  == null){
           s_no_of_bro = "0";
       }
       if (s_no_sis == null){
           s_no_sis = "0";
       }
        s_noofsibiling=etNoOfSiblings.getText().toString();
        s_no_of_bro=etNoOfbrother.getText().toString();
        s_no_sis=etNoOfsister.getText().toString();
        s_family_location=et_family_location.getText().toString();
        s_fathername=etFatherName.getText().toString();
        s_mothername=etMothername.getText().toString();
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
        if (s_familytype.equalsIgnoreCase("Select your Family Type")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Family type is empty!";
            return false;
        }
        if (s_familytype.equalsIgnoreCase("Select your Family Type")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Family type is empty!";
            return false;
        }
        if (s_familystatus.equalsIgnoreCase("Select your Family Status")) {
            progress.setVisibility(View.GONE);
            errorMsg = "family status is empty!";
            return false;
        }
        if (s_fathername.equalsIgnoreCase("")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Father Name is empty!";
            return false;
        }
        if (Utils.isEmpty(s_fatherocc)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Father occupation is empty!";
            return false;
        }
        if (s_mothername.equalsIgnoreCase("")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Mother Name is empty!";
            return false;
        }
        if (Utils.isEmpty(s_motherocc)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Mother occupation is empty!";
            return false;
        }
        if (Utils.isEmpty(s_noofsibiling)) {
            progress.setVisibility(View.GONE);
            errorMsg = "Num of siblings is empty!";
            return false;
        }
//       if (Integer.parseInt(s_noofsibiling)  == (Integer.parseInt(s_no_of_bro)+Integer.parseInt(s_no_sis)) ){
//           progress.setVisibility(View.GONE);
//           errorMsg = "Siblings does't match";
//           return false;
//       }
        return true;
    }



    private  void checkparams(){
//   if (!user.getFamily_type().equalsIgnoreCase(duplicate.getFamily_type())) {
//            params.put("family_type", user.getFamily_type());
//        }
//        if (!user.getFamily_status().equalsIgnoreCase(duplicate.getFamily_status())) {
//            params.put("family_status", user.getFamily_status());
//        }
//        if (!user.getFather_occupation().equalsIgnoreCase(duplicate.getFather_occupation())) {
//            params.put("father_occupation", user.getFather_occupation());
//        }
//        if (!user.getMother_occupation().equalsIgnoreCase(duplicate.getMother_occupation())) {
//            params.put("mother_occupation", user.getMother_occupation());
//        }
//        if (!user.getNo_of_siblings().equalsIgnoreCase(duplicate.getNo_of_siblings())) {
//            params.put("no_of_siblings", user.getNo_of_siblings());
//        }
//        if (!user.getAbout_myself().equalsIgnoreCase(duplicate.getAbout_myself())) {
//            params.put("about_myself", user.getAbout_myself());
//        }
//        params.put("user_id", "1");
//        userDetailsViewModel.updateUser(params, this);
        params.put("user_id",userid);
        params.put("father_name", s_fathername.toLowerCase());
        params.put("mother_name", s_mothername.toLowerCase());
        params.put("family_type", s_familytype.toLowerCase());
        params.put("family_status", s_familystatus.toLowerCase());
        params.put("father_occupation", s_fatherocc.toLowerCase());
        params.put("mother_occupation", s_motherocc.toLowerCase());
        params.put("no_of_siblings", s_noofsibiling.toLowerCase());
        params.put("no_of_brothers", s_no_of_bro.toLowerCase());
        params.put("no_of_sisters", s_no_sis.toLowerCase());
        if (s_bro_status != null){
            params.put("brother_status", s_bro_status.toLowerCase());
        }
        if (s_sis_status != null){
            params.put("sister_status", s_sis_status.toLowerCase());
        }


        params.put("family_location", s_family_location.toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }


    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_HABITUAL_DETAILS, null);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        ((HomeActivity) mActivity).setFragment(INDEX_HABITUAL_DETAILS, null);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long carid) {
        switch (parent.getId()){
            case R.id.spFamilyType:
                s_familytype=spFamilyType.getSelectedItem().toString();
                break;
            case R.id.spFamilyStatus:
                s_familystatus=spFamilyStatus.getSelectedItem().toString();
                break;
            case R.id.spFamilyValues:
                s_familyvalue=spFamilyValues.getSelectedItem().toString();
                break;

            case R.id.etbro_status:
                s_bro_status=etbro_status.getSelectedItem().toString();
                break;
            case R.id.et_sis_status:
                s_sis_status=et_sis_status.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
