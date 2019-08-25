package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.databinding.EditHabitsDetailsFragmentBinding;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.skyappz.namma.activities.HomeActivity.INDEX_ABOUT_MYSELF;
import static com.skyappz.namma.activities.HomeActivity.INDEX_EDUCATION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RELIGION_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.INDEX_RELIGIO_DETAILS;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditHabitsDetails extends Fragment implements WebServiceListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String select_foodhapit,select_smookinghapit,select_drinkinghapit;
    private EditHabitsDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    List<String> foodHabits, smokingHabits, drinkingHabits;
    private Activity mActivity;
    private String errorMsg;
    String s_food,s_smook,s_drink;
    AppCompatButton update;
    AppCompatTextView skip;
    ProgressDialog dialog;
    GifImageView progress;
    AppCompatSpinner spFoodHabit,spSmookingHabit,spDrinkHabit;
    public static EditHabitsDetails newInstance() {
        return new EditHabitsDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.edit_habits_details_fragment, container, false);
        initViews(view);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
//        duplicate = ((HomeActivity) getActivity()).getUser();
//        user = new User().duplicate(duplicate);
//        getData();
//        binding.setUser(user);
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    private void getData() {
        smokingHabits = Arrays.asList(getActivity().getResources().getStringArray(R.array.smoking_habits));
        drinkingHabits = Arrays.asList(getActivity().getResources().getStringArray(R.array.drinking_habits));
        foodHabits = Arrays.asList(getActivity().getResources().getStringArray(R.array.eating_habits));
        user.setSmokingHabitsPosition(smokingHabits.indexOf(user.getSmoking_habits()));
        user.setFoodHabitsPosition(foodHabits.indexOf(user.getEating_habits()));
        user.setDrinkingHabitsPosition(drinkingHabits.indexOf(user.getDrinking_habits()));
    }

    private void initViews(View view) {
        progress=(GifImageView)view.findViewById(R.id.progress);
        spFoodHabit=(AppCompatSpinner)view.findViewById(R.id.spFoodHabit);
        spSmookingHabit=(AppCompatSpinner)view.findViewById(R.id.spSmookingHabit);
        spDrinkHabit=(AppCompatSpinner)view.findViewById(R.id.spDrinkHabit);
        update=(AppCompatButton)view.findViewById(R.id.update);
        skip=(AppCompatTextView)view.findViewById(R.id.skip);

        ArrayList foodlist = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.eating_habits)));
        ArrayAdapter foodadapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,foodlist);
        foodadapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spFoodHabit.setAdapter(foodadapter);
        spFoodHabit.setOnItemSelectedListener(this);

        ArrayList smookinglist= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.smoking_habits)));
        ArrayAdapter smokingAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,smookinglist);
        smokingAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spSmookingHabit.setAdapter(smokingAdapter);
        spSmookingHabit.setOnItemSelectedListener(this);

        ArrayList drinkList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.drinking_habits)));
        ArrayAdapter drinkAdapter=new ArrayAdapter(getActivity(),R.layout.spinner_item,drinkList);
        drinkAdapter.setDropDownViewResource(R.layout.spinner_drop_item);
        spDrinkHabit.setAdapter(drinkAdapter);
        spDrinkHabit.setOnItemSelectedListener(this);

        update.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onFoodHabitSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setSmoking_habits((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onSmokingHabitSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setSmoking_habits((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }

    public void onDrinkingHabitSelected(AdapterView<?> parent, View view, int position, long id) {
        if (user != null) {
            user.setDrinking_habits((String) parent.getAdapter().getItem(position));
            //userDetailsViewModel.setUser(user);
        }
    }


    public void update() {
       progress.setVisibility(View.VISIBLE);
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

        if (s_food.equalsIgnoreCase("Select your Food Habits")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Food habit is empty!";
            return false;
        }
        if (s_smook.equalsIgnoreCase("Select your Smoking Habits")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Smooke habit is empty!";
            return false;
        }
        if (s_drink.equalsIgnoreCase("Select your Drinking Habits")) {
            progress.setVisibility(View.GONE);
            errorMsg = "Drink habit is empty!";
            return false;
        }
        return true;
    }

    private  void checkparams(){
//  if (!user.getSmoking_habits().equalsIgnoreCase(duplicate.getSmoking_habits())) {
//            params.put("smoking_habits", user.getSmoking_habits());
//        }
//        if (!user.getDrinking_habits().equalsIgnoreCase(duplicate.getDrinking_habits())) {
//            params.put("drinking_habits", user.getDrinking_habits());
//        }
//        if (!user.getEating_habits().equalsIgnoreCase(duplicate.getEating_habits())) {
//            params.put("eating_habits", user.getEating_habits());
//        }
//        params.put("user_id", "1");
//        userDetailsViewModel.updateUser(params, this);
        params.put("user_id",userid);
        params.put("smoking_habits", s_smook.toLowerCase());
        params.put("drinking_habits", s_drink.toLowerCase());
        params.put("eating_habits", s_food.toLowerCase());
        userDetailsViewModel.updateUser(params, this);
    }



    public void skip(){
        ((HomeActivity) mActivity).setFragment(INDEX_EDUCATION_DETAILS, null);
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        progress.setVisibility(View.GONE);
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        ((HomeActivity) mActivity).setFragment(INDEX_RELIGIO_DETAILS, null);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spFoodHabit:
                s_food=spFoodHabit.getSelectedItem().toString();
                break;
            case R.id.spSmookingHabit:
                s_smook=spSmookingHabit.getSelectedItem().toString();
                break;
            case R.id.spDrinkHabit:
                s_drink=spDrinkHabit.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
