package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Preferences;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import java.util.HashMap;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_PARTNER_PREFERENCE;
import static com.skyappz.namma.activities.HomeActivity.userid;


public class EditAboutFamily extends Fragment implements View.OnClickListener, WebServiceListener {
    User user, duplicate,user1;
    HashMap<String, String> params = new HashMap<>();
    private Activity mActivity;
    private String errorMsg;
    Preferences preferences;
    AppCompatEditText etAboutFamily;
    AppCompatTextView help,skip;
    AppCompatButton update;
    String s_abtfamily;
    ProgressDialog dialog;
    GifImageView progress;
    private UserDetailsViewModel userDetailsViewModel;

    public static EditAboutFamily newInstance() {
        return new EditAboutFamily();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_about_family, container, false);
        intialviews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        return  view;
    }

    public void intialviews(View view){
        progress=(GifImageView)view.findViewById(R.id.progress);
        etAboutFamily =(AppCompatEditText)view.findViewById(R.id.etAboutFamily);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);
        update=(AppCompatButton)view.findViewById(R.id.update);
        update.setOnClickListener(this);
        skip.setOnClickListener(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skip:
                skip();
                break;
            case R.id.update:
                update();
                break;
        }
    }

    public void update() {
        progress.setVisibility(View.VISIBLE);
        s_abtfamily=etAboutFamily.getText().toString();
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

        if (Utils.isEmpty(s_abtfamily)) {
            progress.setVisibility(View.GONE);
            errorMsg = "About my  is empty!";
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
        params.put("about_family", s_abtfamily.toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }
    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_PARTNER_PREFERENCE, null);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        ((HomeActivity) mActivity).setFragment(INDEX_PARTNER_PREFERENCE, null);
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
