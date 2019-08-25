package com.skyappz.namma.profileupdate;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.EditProfileActivity;
import com.skyappz.namma.activities.HttpsTrustManager;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.editprofile.UserDetailsViewModel;
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

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditLocation extends Fragment implements  View.OnClickListener, WebServiceListener,AdapterView.OnItemSelectedListener {
    HashMap<String, String> params = new HashMap<>();
    AppCompatSpinner SPcountry;
    AppCompatAutoCompleteTextView  auto_state,auto_city;
    ArrayAdapter countryadapter;
    AppCompatButton update;
    GifImageView progress;
    String s_sate,s_city,s_country;
    private static final String URL_GET_USER = "https://nammamatrimony.in/api/getuser.php?user_id=";
    ArrayList state_list,city_list;
    private UserDetailsViewModel userDetailsViewModel;
    public EditLocation() {
        // Required empty public constructor
    }
    public static EditLocation newInstance() {
        return new EditLocation();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_location, container, false);
        SPcountry=(AppCompatSpinner)view.findViewById(R.id.SPcountry);
        auto_state=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_state);
        auto_city=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_city);
        update=(AppCompatButton) view.findViewById(R.id.update);
        update.setOnClickListener(this);
        progress=(GifImageView)view.findViewById(R.id.progress);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((ProfileUpdate) getActivity());

        ArrayList countrylist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country)));
        countryadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,countrylist);
        countryadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        SPcountry.setAdapter(countryadapter);
        SPcountry.setOnItemSelectedListener(this);

        loadStateandDist();
        auto_state.setOnItemClickListener(stateListner);
        auto_city.setOnItemClickListener(citylistner);

        get_user();
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case  R.id.SPcountry:
                s_country =SPcountry.getSelectedItem().toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void get_user() {
        Log.e("getuser","getuser");
        HttpsTrustManager.allowAllSSL();
        String tag_json_obj = "get_user";
        String url = URL_GET_USER+ AppController.get_userid(getActivity()) ;
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

                                int selectionPosition= countryadapter.getPosition(user.getString("country"));
                                SPcountry.setSelection(selectionPosition);
                                auto_state.setText(user.getString("state"));
                                auto_city.setText(user.getString("home_city"));

                                progress.setVisibility(View.GONE);

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
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.update:
                progress.setVisibility(View.VISIBLE);
                s_sate=auto_state.getText().toString();
                s_city=auto_city.getText().toString();
                if (s_country.equalsIgnoreCase("Country")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Select Country");
                }else  if (s_sate.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Enter State");
                }else if (s_city.equalsIgnoreCase("")){
                    progress.setVisibility(View.GONE);
                    Utils.showAlert(getActivity(),"Enter City");
                }else {
                    updatevalue();
                }
                break;
        }
    }
    private  void  updatevalue(){
        params.put("user_id", AppController.get_userid(getActivity()));
        params.put("country", s_country.toLowerCase());
        params.put("state", s_sate.toLowerCase());
        params.put("home_city", s_city.toLowerCase());

        userDetailsViewModel.updateUser(params, this);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        Intent i= new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
        getActivity().finish();
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
