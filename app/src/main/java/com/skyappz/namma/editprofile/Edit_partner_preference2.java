package com.skyappz.namma.editprofile;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.skyappz.namma.R;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_partner_preference2 extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    AppCompatAutoCompleteTextView spEducation,spProfession,spNationality,spPreferredstate,spPreferredCities;
    AppCompatSpinner spCountry;
    ArrayList<String> state_list =new ArrayList<>();
    ArrayList<String> city_list = new ArrayList<>();
    String s_degree,s_myocc,s_nationality,s_country,s_sate,s_city,errorMsg;
    AppCompatButton update;
    GifImageView progress ;
    User user;
    HashMap<String, String> params = new HashMap<>();
    private Activity mActivity;
    public Edit_partner_preference2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_blank, container, false);

        update=(AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);
        progress=(GifImageView)view.findViewById(R.id.progress);
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

        spNationality=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spNationality);
        ArrayList<String> nationalityList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nationality)));
        CustomListAdapter natioalityadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, nationalityList);
        spNationality.setAdapter(natioalityadapter);
        spNationality.setOnItemClickListener(nationlistylistner);

        spCountry=(AppCompatSpinner)view.findViewById(R.id.spCountry);
        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        ArrayAdapter disabilityadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        disabilityadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spCountry.setAdapter(disabilityadapter);
        spCountry.setOnItemSelectedListener(this);

        spPreferredstate=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredstate);
        loadStateandDist();
        spPreferredCities=(AppCompatAutoCompleteTextView)view.findViewById(R.id.spPreferredCities);
        spPreferredstate.setOnItemClickListener(stateListner);
        spPreferredCities.setOnItemClickListener(citylistner);

        return view;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.spCountry:
                s_country =spCountry.getSelectedItem().toString();
                break;
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

    private  void checkparams() {
        params.put("education", s_degree.toLowerCase());
        params.put("profession", s_myocc.toLowerCase());
        params.put("nationality", s_nationality.toLowerCase());
        params.put("country", s_country.toLowerCase());
        params.put("state", s_sate.toLowerCase());
        params.put("preferred_cities", s_city.toLowerCase());

    }
        private boolean isInputValidated(User user) {
        return true;
    }


}
