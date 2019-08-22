package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.CustomListAdapter;
import com.skyappz.namma.databinding.EditRegionalDetailsFragmentBinding;
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

import static com.skyappz.namma.activities.HomeActivity.INDEX_FAMILY_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditReligionDetails extends Fragment implements WebServiceListener , View.OnClickListener {
    public static String select_region,select_caste,select_subcaste;
    private EditRegionalDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    List<String> resubCastes, religions;
    ArrayList<String> castes =new ArrayList<>();
    ArrayList<String> caste_id =new ArrayList<>();
    ArrayList<String> subcastes =new ArrayList<>();
    private Activity mActivity;
    private String errorMsg;
    AppCompatButton update;
    AppCompatTextView skip;
    String s_nationality,s_country;
    String religion,caste,subcaste;
    AppCompatAutoCompleteTextView auto_religion,auto_caaste,auto_subcaste;
    public static EditReligionDetails newInstance() {
        return new EditReligionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_regional_details_fragment, container, false);
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
    private void initViews(View view) {
        auto_religion=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_religion);
        auto_caaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_caste);
        auto_subcaste=(AppCompatAutoCompleteTextView)view.findViewById(R.id.auto_subcaste);

        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);


        getAllCaste();

        ArrayList<String> motherTongues = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.religions)));
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, motherTongues);
        auto_religion.setAdapter(adapter);
        auto_religion.setOnItemClickListener(religionclick);

        CustomListAdapter casetadapter = new CustomListAdapter(getActivity(),
                R.layout.right_menu_item, castes);
        auto_caaste.setAdapter(casetadapter);
        auto_caaste.setOnItemClickListener(caseteListner);



        auto_subcaste.setOnItemClickListener(subcastelistner);
        update.setOnClickListener(this);
        skip.setOnClickListener(this);

    }

    private AdapterView.OnItemClickListener religionclick =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    religion = String.valueOf(adapterView.getItemAtPosition(i));
                    Log.e("pocastes",caste_id.get(i));
                     }
            };
    private AdapterView.OnItemClickListener caseteListner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    caste = String.valueOf(adapterView.getItemAtPosition(i));
                    getsubcaste();
                }
            };
    private AdapterView.OnItemClickListener subcastelistner =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    subcaste = String.valueOf(adapterView.getItemAtPosition(i));
                }
            };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
//    private void getData() {
//        religions = Arrays.asList(getActivity().getResources().getStringArray(R.array.religions));
//        castes = Arrays.asList(getActivity().getResources().getStringArray(R.array.castes));
//        user.setReligionPosition(religions.indexOf(user.getReligion()));
//        user.setCastePosition(castes.indexOf(user.getCaste()));
//        user.setSubCastePosition(castes.indexOf(user.getSub_caste()));
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onReligionSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setReligion((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onCasteSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setCaste((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onSubCasteSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setSub_caste((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }


    public void getAllCaste() {
        auto_subcaste.setText("");
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
                                caste_id.add(list.getString("id"));
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

    public void getsubcaste() {
        String tag_json_obj = "getAllSubCaste";
        String url = "https://nammamatrimony.in/api/getsubcaste.php";
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
                               if (caste.equalsIgnoreCase(list.getString("caste"))){
                                   JSONArray subcasetarray=list.getJSONArray("subcastes");
                                   if (subcasetarray.length()> 0){
                                       for (int j=0;j<subcasetarray.length();j++){
                                           JSONObject sublist = subcasetarray.getJSONObject(j);
                                           subcastes.add(sublist.getString("subcaste"));
                                       }
                                       CustomListAdapter subcasetadapter = new CustomListAdapter(getActivity(),
                                               R.layout.right_menu_item, subcastes);
                                       auto_subcaste.setAdapter(subcasetadapter);
                                   }else {
                                       Utils.showToast(mActivity,"No sub caste");
                                       subcaste="";
                                   }
                                }
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



    public void update() {
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

        if (religion.equalsIgnoreCase("Select a Religion") || religion.isEmpty()) {
            errorMsg = "Religion is empty!";
            return false;
        }
        if (caste.equalsIgnoreCase("Select your Caste") || caste.isEmpty()) {
            errorMsg = "Caste is empty!";
            return false;
        }
        if (subcaste.equalsIgnoreCase("Select your Caste")) {
            errorMsg = "Subcaste is empty!";
            return false;
        }
        return true;
    }

    private  void checkparams(){
//    if (!user.getReligion().equalsIgnoreCase(duplicate.getReligion())) {
//            params.put("religion", user.getReligion());
//        }
//        if (!user.getCaste().equalsIgnoreCase(duplicate.getCaste())) {
//            params.put("caste", user.getCaste());
//        }
//        if (!user.getSub_caste().equalsIgnoreCase(duplicate.getSub_caste())) {
//            params.put("sub_caste", user.getSub_caste());
//        }
//        params.put("user_id", "1");
        params.put("user_id",userid);
        params.put("religion", religion);
        params.put("caste", caste);
        params.put("sub_caste", subcaste);
        userDetailsViewModel.updateUser(params, this);
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_FAMILY_DETAILS, null);
    }
    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
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
}
