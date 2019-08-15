package com.skyappz.namma.editprofile;

import android.app.Activity;
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

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditResidencyAddressFragmentBinding;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_ABOUT_MYSELF;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditResidencyAddress extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_nationality,select_country,select_state,select_city;
    private EditResidencyAddressFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    AppCompatAutoCompleteTextView autoNationality, auto_state,auto_city;
            AppCompatSpinner SPcountry;
    HashMap<String, String> params = new HashMap<>();
    private String errorMsg;
    AppCompatButton update;
    AppCompatTextView skip;
    ArrayList state_list,city_list;
    String s_sate,s_city;
    private Activity mActivity;
    String s_nationality,s_country;
    public static EditResidencyAddress newInstance() {
        return new EditResidencyAddress();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_residency_address_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
//        binding.setFragment(this);
//        binding.setHomeActivity((HomeActivity)getActivity());
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
//        duplicate = ((HomeActivity) getActivity()).getUser();
//        user = new User().duplicate(duplicate);
//        binding.setUser(user);
        return view;
    }
    private void initViews(View view) {
        autoNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.autoNationality);
        auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_state);
        auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_city);
        SPcountry=(AppCompatSpinner)view.findViewById(R.id.SPcountry);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    public void update() {
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

        if (Utils.isEmpty(s_nationality) || s_nationality.equalsIgnoreCase("Select Nationality")) {
            errorMsg = "Nationality  is empty!";
            return false;
        }
        if (s_country.equalsIgnoreCase("Country")) {
            errorMsg = "Country  is empty!";
            return false;
        }
        if (Utils.isEmpty(s_sate)){
            errorMsg = "state  is empty!";
            return false;
        }

        if (Utils.isEmpty(s_city)) {
            errorMsg = "city  is empty!";
            return false;
        }
        return true;
    }



    private  void checkparams(){
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
        params.put("nationality", s_nationality);
        params.put("country", s_country);
        params.put("state", s_sate);
        params.put("home_city", s_city);
        userDetailsViewModel.updateUser(params, this);
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_ABOUT_MYSELF, null);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
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
        s_country =SPcountry.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
