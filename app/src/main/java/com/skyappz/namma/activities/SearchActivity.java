package com.skyappz.namma.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, WebServiceListener {
AppCompatSpinner search_age,search_height,search_marital_status,search_religion,search_mother_tongue,search_caste;
    ArrayList<String> castes =new ArrayList<>();
    String s_age,s_height,s_marital_ststus,s_religiion,s_languvage,s_caste;
    AppCompatButton apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_age=(AppCompatSpinner)findViewById(R.id.search_age);
        search_height=(AppCompatSpinner)findViewById(R.id.search_height);
        search_marital_status=(AppCompatSpinner)findViewById(R.id.search_marital_status);
        search_religion=(AppCompatSpinner)findViewById(R.id.search_religion);
        search_mother_tongue=(AppCompatSpinner)findViewById(R.id.search_mother_tongue);
        search_caste=(AppCompatSpinner)findViewById(R.id.search_caste);

        ArrayList age = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.search_age)));
        ArrayAdapter ageadapeer=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,age);
        ageadapeer.setDropDownViewResource(R.layout.spinner_drop_item);
        search_age.setAdapter(ageadapeer);
        search_age.setOnItemSelectedListener(this);

        ArrayList height = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.height)));
        ArrayAdapter heightadapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,height);
        heightadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        search_height.setAdapter(heightadapter);
        search_height.setOnItemSelectedListener(this);

        ArrayList marital_status = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.marital_statuses)));
        ArrayAdapter maritalstatusadapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,marital_status);
        maritalstatusadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        search_marital_status.setAdapter(maritalstatusadapter);
        search_marital_status.setOnItemSelectedListener(this);

        final ArrayList religion = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        ArrayAdapter religionadapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,religion);
        religionadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        search_religion.setAdapter(religionadapter);
        search_religion.setOnItemSelectedListener(this);

        ArrayList mothertongue = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        ArrayAdapter mothertongueadapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,mothertongue);
        mothertongueadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        search_mother_tongue.setAdapter(mothertongueadapter);
        search_mother_tongue.setOnItemSelectedListener(this);
        getAllCaste();

        ArrayAdapter casteAdapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_item,castes);
        casteAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        search_caste.setAdapter(casteAdapter);
        search_caste.setOnItemSelectedListener(this);

        apply=(AppCompatButton)findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_age.equalsIgnoreCase("Select")){
                    s_age="";
                }
                if (s_height.equalsIgnoreCase("Select a Height")){
                    s_height="";
                }
                if (s_marital_ststus.equalsIgnoreCase("Select a Marital Status")){
                    s_marital_ststus="";
                }
                if (s_religiion.equalsIgnoreCase("Select a Religion")){
                    s_religiion="";
                }
                if (s_languvage.equalsIgnoreCase("Select your Mother Tongue")){
                    s_languvage="";
                }

                Intent i=new Intent(getApplicationContext(),Searchresult.class);
                i.putExtra("age",s_age);
                i.putExtra("height",s_height);
                i.putExtra("marital",s_marital_ststus);
                i.putExtra("religion",s_religiion);
                i.putExtra("tongue",s_languvage);
                i.putExtra("caste",s_caste);
                startActivity(i);
            }
        });


    }

    public void getAllCaste() {
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

        if (Utils.isConnected((this))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.search_age:
                s_age =search_age.getSelectedItem().toString();
                break;
            case R.id.search_height:
                s_height =search_height.getSelectedItem().toString();
                break;
            case R.id.search_marital_status:
                s_marital_ststus =search_marital_status.getSelectedItem().toString();
                break;
            case R.id.search_religion:
                s_religiion =search_religion.getSelectedItem().toString();
                break;
            case R.id.search_mother_tongue:
                s_languvage =search_mother_tongue.getSelectedItem().toString();
                break;
            case R.id.search_caste:
                s_caste=search_caste.getSelectedItem().toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {

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

}
