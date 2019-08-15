package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditBasicDetailsFragmentBinding;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.MyDatePickerDialog;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.skyappz.namma.activities.HomeActivity.INDEX_PERSONAL_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditBasicDetails extends Fragment implements WebServiceListener, View.OnClickListener,AdapterView.OnItemSelectedListener {
    public  static  String selectmother_tongu,select_profilecreatby;
    private EditBasicDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate,user1;
    HashMap<String, String> params = new HashMap<>();
    List<String> motherTongues, profilesFor;
    private Activity mActivity;
    private String errorMsg;
    TextInputEditText nameEd;

    Calendar calendar = Calendar.getInstance();
    int c_year = calendar.get(Calendar.YEAR);
    Preferences preferences;
    TextInputEditText tetDOB,tetName;
    String bridename,dob,s_mothertounge,s_profileby,age,gnder;
    AppCompatAutoCompleteTextView mothertongue_auto;
    AppCompatSpinner profile_created_sp;
    ArrayList<String> profileBys;
    AppCompatImageView ivPickDate;
    AppCompatButton update;
    AppCompatTextView skip;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    public static EditBasicDetails newInstance() {
        return new EditBasicDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_basic_details_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
//        binding.setHomeActivity((HomeActivity)getActivity());
//        binding.setFragment(this);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        preferences =new Preferences(mActivity);
//        duplicate = ((HomeActivity) getActivity()).getUser();
//        user = new User().duplicate(duplicate);
//        getData();
        radioGroup = (RadioGroup)view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId)
          {
              radioButton = (RadioButton)view.findViewById(checkedId);
              gnder=radioButton.getText().toString();
              Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
          }
      }
        );
//        binding.setUser(user);
//        return binding.getRoot();
        return view;
    }



    private void initViews(View view) {

        mothertongue_auto=(AppCompatAutoCompleteTextView)view.findViewById(R.id.mothertongue_auto);
        tetDOB=(TextInputEditText)view.findViewById(R.id.tetDOB);
        tetName=(TextInputEditText)view.findViewById(R.id.tetName);
        tetName.setText(AppController.get_signupname(getActivity()));
        ivPickDate=(AppCompatImageView)view.findViewById(R.id.ivPickDate);
        profile_created_sp=(AppCompatSpinner)view.findViewById(R.id.profile_created_sp);

        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, motherTongues);
        mothertongue_auto.setAdapter(adapter);
         profileBys = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.profile_created_by)));
        ArrayAdapter profilesp=new ArrayAdapter(getActivity(),R.layout.spinner_item,profileBys);
        profilesp.setDropDownViewResource(R.layout.spinner_drop_item);
        profile_created_sp.setAdapter(profilesp);
        profile_created_sp.setSelection(AppController.get_signupprofilevetaepos(getActivity()));
        mothertongue_auto.setOnItemClickListener(onItemClickListener);
        ivPickDate.setOnClickListener(this);
        update.setOnClickListener(this);
        profile_created_sp.setOnItemSelectedListener(this);
        skip.setOnClickListener(this);

    }
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    s_mothertounge=String.valueOf(adapterView.getItemAtPosition(i));

                }
            };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    private void getData() {
        motherTongues = Arrays.asList(getActivity().getResources().getStringArray(R.array.languages));
        profilesFor = Arrays.asList(getActivity().getResources().getStringArray(R.array.profile_created_by));
        user.setMotherTonguePosition(motherTongues.indexOf(user.getMother_tongue()));
        user.setProfileForPosition(profilesFor.indexOf(user.getProfile_created_for()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showDatePicker() {
        MyDatePickerDialog myDatePickerDialog = new MyDatePickerDialog();
        myDatePickerDialog.showDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker mDatePicker, int year, int month, int dayOfMonth) {
                String strDate = Utils.parseDate(dayOfMonth, month, year);
                tetDOB.setText(strDate);
                age = String.valueOf(c_year - year);

                //////userDetailsViewModel.setUser(user);
            }
        });
    }

    public void onLanaguageSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setMother_tongue((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onProfileSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setProfile_created_for((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_PERSONAL_DETAILS, null);
    }
    public void update() {
        bridename=tetName.getText().toString();
        dob=tetDOB.getText().toString();

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

        if (Utils.isEmpty(bridename)) {
            errorMsg = "Name is empty!";
            return false;
        }
        if (Utils.isEmpty(dob)) {
            errorMsg = "Date of Birth is empty!";
            return false;
        }
        if (Utils.isEmpty(s_mothertounge)) {
            errorMsg = "Mother tongue is empty!";
            return false;
        }
        if (Utils.isEmpty(s_profileby)) {
            errorMsg = "profile created by is empty!";
            return false;
        }

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
        params.put("name", bridename);
        params.put("dob", dob);
        params.put("age",age);
        params.put("gender",gnder);
        params.put("mother_tongue", s_mothertounge);
        params.put("profile_created_for", s_profileby);
        params.put("profile_created_for", s_profileby);

        userDetailsViewModel.updateUser(params, this);
    }


    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        ((HomeActivity) mActivity).setFragment(INDEX_PERSONAL_DETAILS, null);
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
//    public void drawerclose() {
//        user.setMother_tongue(selectmother_tongu);
//        binding.setUser(user);
//        drawer.closeDrawer(Gravity.END);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:{
                update();
                break;
            }
            case R.id.ivPickDate:{
                showDatePicker();
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
         s_profileby =profile_created_sp.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
