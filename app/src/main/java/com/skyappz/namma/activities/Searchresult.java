package com.skyappz.namma.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import org.json.JSONObject;

public class Searchresult extends AppCompatActivity implements WebServiceListener {

    String s_age,s_height,s_religiion,s_marital_ststus,s_languvage,s_caste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        s_age = getIntent().getStringExtra("age");
        s_height = getIntent().getStringExtra("height");
        s_marital_ststus = getIntent().getStringExtra("marital");
        s_religiion = getIntent().getStringExtra("religion");
        s_languvage = getIntent().getStringExtra("tongue");
        s_caste= getIntent().getStringExtra("caste");

        searh_result();

    }

    public void searh_result() {
        String tag_json_obj = "searcchresult";
        String url = "https://nammamatrimony.in/api/search.php?gender=male&max_age="+s_age+"&max_height="+s_height+"&marital_status="+s_marital_ststus+"&religion="+s_religiion+"&mother_tongue="+s_languvage+"&caste="+s_caste;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("search",response.toString());
//                        try {
//                            JSONArray castearray=response.getJSONArray("castes");
//                            for (int i=0; i<castearray.length();i++){
//                                JSONObject list= castearray.getJSONObject(i);
//                                HashMap<String,String> castelist =new HashMap<>();
//                                castes.add(list.getString("caste"));
//                            }
//
//                        } catch (JSONException e) {
//
//                        } catch (JsonParseException e) {

                      //  }
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
