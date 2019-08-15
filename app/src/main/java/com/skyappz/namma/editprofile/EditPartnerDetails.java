package com.skyappz.namma.editprofile;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.GetUserDetailsResponse;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.databinding.EditPartnerDetailsFragmentBinding;
import com.skyappz.namma.model.User;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import java.util.HashMap;

import static com.skyappz.namma.activities.HomeActivity.INDEX_ABOUTFAMILY;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class EditPartnerDetails extends Fragment implements WebServiceListener {

    private EditPartnerDetailsFragmentBinding binding;
    private UserDetailsViewModel userDetailsViewModel;
    User user, duplicate;
    HashMap<String, String> params = new HashMap<>();
    private String errorMsg;
    private Activity mActivity;
    ProgressDialog dialog;
    public static EditPartnerDetails newInstance() {
        return new EditPartnerDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_partner_details_fragment, container, false);
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
        binding.setFragment(this);
        userDetailsViewModel.setActivity((HomeActivity) getActivity());
        //userDetailsViewModel.getUserDetails("1");
        duplicate = ((HomeActivity) getActivity()).getUser();
        user = new User().duplicate(duplicate);
        binding.setUser(user);
        subscribe();
        return binding.getRoot();
    }

    private void subscribe() {
        final Observer<User> getUserReceiver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable final User usr) {
                user = usr;
                binding.setUser(user);
            }
        };

        userDetailsViewModel.getUser().observeForever(getUserReceiver);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    public void update(View view) {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait.");
        dialog.setCancelable(false);
        dialog.show();
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

        if (Utils.isEmpty(user.getAbout_partner())) {
            dialog.dismiss();
            errorMsg = "About partner  is empty!";
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
        params.put("about_partner", user.getAbout_partner());
        userDetailsViewModel.updateUser(params, this);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void skip(View view){
        ((HomeActivity) mActivity).setFragment(INDEX_ABOUTFAMILY, null);
    }
    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {
        dialog.dismiss();
        Utils.showToast(getActivity(), ((GetUserDetailsResponse) response).getMsg());
        ((HomeActivity) mActivity).setFragment(INDEX_ABOUTFAMILY, null);
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

    public void customdia(View view){
        ViewDialog alert = new ViewDialog();
        alert.showDialog(getActivity());
    }
    public class ViewDialog  implements View.OnClickListener {
        String iamalso_string="",hoppies_string="",finalstring;
        AppCompatEditText alert_etmyself;
        AppCompatButton save_and_continue;
        Dialog dialog;
        AppCompatTextView caring,causal,frindly,gentle,stright_forword,family_orianted,confitent,respecting_elders,responsible,helpful,talended,diligient,volunteering,
                Charity,Cooking,
                Nature,Photography,Dancing,Painting,Music,Pazzles,Handgraft,art,movies,Internet_suffering,Traveling,Gardening;
        public void showDialog(Activity activity){
            dialog = new Dialog(activity,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.edit_abtpartner_customalert);
            AppCompatTextView close=(AppCompatTextView)dialog.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            alert_etmyself=(AppCompatEditText)dialog.findViewById(R.id.alert_etmyself);

            save_and_continue=(AppCompatButton)dialog.findViewById(R.id.save_and_continue);
            save_and_continue.setOnClickListener(this);
            caring=(AppCompatTextView)dialog.findViewById(R.id.caring);
            causal=(AppCompatTextView)dialog.findViewById(R.id.causal);
            frindly=(AppCompatTextView)dialog.findViewById(R.id.frindly);
            gentle=(AppCompatTextView)dialog.findViewById(R.id.gentle);
            stright_forword=(AppCompatTextView)dialog.findViewById(R.id.stright_forword);
            family_orianted=(AppCompatTextView)dialog.findViewById(R.id.family_orianted);
            confitent=(AppCompatTextView)dialog.findViewById(R.id.confitent);
            volunteering=(AppCompatTextView)dialog.findViewById(R.id.volunteering);
            respecting_elders=(AppCompatTextView)dialog.findViewById(R.id.respecting_elders);
            responsible=(AppCompatTextView)dialog.findViewById(R.id.responsible);
            helpful=(AppCompatTextView)dialog.findViewById(R.id.helpful);
            talended=(AppCompatTextView)dialog.findViewById(R.id.talended);
            diligient=(AppCompatTextView)dialog.findViewById(R.id.diligient);
            volunteering=(AppCompatTextView)dialog.findViewById(R.id.volunteering);
            caring.setOnClickListener(this);
            causal.setOnClickListener(this);
            frindly.setOnClickListener(this);
            gentle.setOnClickListener(this);
            stright_forword.setOnClickListener(this);
            family_orianted.setOnClickListener(this);
            confitent.setOnClickListener(this);
            respecting_elders.setOnClickListener(this);
            responsible.setOnClickListener(this);
            helpful.setOnClickListener(this);
            talended.setOnClickListener(this);
            diligient.setOnClickListener(this);
            volunteering.setOnClickListener(this);
            Cooking=(AppCompatTextView)dialog.findViewById(R.id.Cooking);
            Nature=(AppCompatTextView)dialog.findViewById(R.id.Nature);
            Photography=(AppCompatTextView)dialog.findViewById(R.id.Photography);
            Dancing=(AppCompatTextView)dialog.findViewById(R.id.Dancing);
            Painting=(AppCompatTextView)dialog.findViewById(R.id.Painting);
            Music=(AppCompatTextView)dialog.findViewById(R.id.Music);
            Pazzles=(AppCompatTextView)dialog.findViewById(R.id.Pazzles);
            Handgraft=(AppCompatTextView)dialog.findViewById(R.id.Handgraft);
            art=(AppCompatTextView)dialog.findViewById(R.id.art);
            movies=(AppCompatTextView)dialog.findViewById(R.id.movies);
            Internet_suffering=(AppCompatTextView)dialog.findViewById(R.id.Internet_suffering);
            Traveling=(AppCompatTextView)dialog.findViewById(R.id.Traveling);
            Gardening=(AppCompatTextView)dialog.findViewById(R.id.Gardening);
            Cooking.setOnClickListener(this);
            Nature.setOnClickListener(this);
            Photography.setOnClickListener(this);
            Dancing.setOnClickListener(this);
            Painting.setOnClickListener(this);
            Music.setOnClickListener(this);
            Pazzles.setOnClickListener(this);
            Handgraft.setOnClickListener(this);
            art.setOnClickListener(this);
            movies.setOnClickListener(this);
            Internet_suffering.setOnClickListener(this);
            Traveling.setOnClickListener(this);
            Gardening.setOnClickListener(this);

            dialog.show();

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save_and_continue:
                    if (finalstring.equals("")){
                        Utils.showToast(mActivity, "about myself is empty");
                    }else {
                        dialog.dismiss();
                        user.setAbout_partner(finalstring);
                        binding.setUser(user);

                    }
                    break;
                case R.id.caring:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Carrying,")){
                        iamalso_string += " Carrying,";
                        caring.setBackgroundResource(R.drawable.pink_rounded);
                        caring.setTextColor(getResources().getColor(R.color.white));
                        caring.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Carrying,","");
                        caring.setBackgroundResource(R.drawable.pink_border_rounded);
                        caring.setTextColor(getResources().getColor(R.color.black));
                        caring.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }

                    break;
                case R.id.causal:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Casual,")){
                        iamalso_string += " Casual,";
                        causal.setBackgroundResource(R.drawable.pink_rounded);
                        causal.setTextColor(getResources().getColor(R.color.white));
                        causal.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Casual,","");
                        causal.setBackgroundResource(R.drawable.pink_border_rounded);
                        causal.setTextColor(getResources().getColor(R.color.black));
                        causal.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.frindly:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Friendly,")){
                        iamalso_string += " Friendly,";
                        frindly.setBackgroundResource(R.drawable.pink_rounded);
                        frindly.setTextColor(getResources().getColor(R.color.white));
                        frindly.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Friendly,","");
                        frindly.setBackgroundResource(R.drawable.pink_border_rounded);
                        frindly.setTextColor(getResources().getColor(R.color.black));
                        frindly.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.gentle:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Gentle,")){
                        iamalso_string += " Gentle,";
                        gentle.setBackgroundResource(R.drawable.pink_rounded);
                        gentle.setTextColor(getResources().getColor(R.color.white));
                        gentle.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Gentle,","");
                        gentle.setBackgroundResource(R.drawable.pink_border_rounded);
                        gentle.setTextColor(getResources().getColor(R.color.black));
                        gentle.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }

                    break;
                case R.id.stright_forword:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Stright-Forward,")){
                        iamalso_string += "Stright-Forward,";
                        stright_forword.setBackgroundResource(R.drawable.pink_rounded);
                        stright_forword.setTextColor(getResources().getColor(R.color.white));
                        stright_forword.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Stright-Forward,","");
                        stright_forword.setBackgroundResource(R.drawable.pink_border_rounded);
                        stright_forword.setTextColor(getResources().getColor(R.color.black));
                        stright_forword.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.family_orianted:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Family oriented,")){
                        iamalso_string += " Family oriented,";
                        family_orianted.setBackgroundResource(R.drawable.pink_rounded);
                        family_orianted.setTextColor(getResources().getColor(R.color.white));
                        family_orianted.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Family oriented,","");
                        family_orianted.setBackgroundResource(R.drawable.pink_border_rounded);
                        family_orianted.setTextColor(getResources().getColor(R.color.black));
                        family_orianted.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.confitent:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Confident,")){
                        iamalso_string += " Confident,";
                        confitent.setBackgroundResource(R.drawable.pink_rounded);
                        confitent.setTextColor(getResources().getColor(R.color.white));
                        confitent.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" confitent,","");
                        confitent.setBackgroundResource(R.drawable.pink_border_rounded);
                        confitent.setTextColor(getResources().getColor(R.color.black));
                        confitent.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.volunteering:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Volunteering,")){
                        iamalso_string += " Volunteering,";
                        volunteering.setBackgroundResource(R.drawable.pink_rounded);
                        volunteering.setTextColor(getResources().getColor(R.color.white));
                        volunteering.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Volunteering,","");
                        volunteering.setBackgroundResource(R.drawable.pink_border_rounded);
                        volunteering.setTextColor(getResources().getColor(R.color.black));
                        volunteering.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.helpful:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Helpful,")){
                        iamalso_string += " Helpful,";
                        helpful.setBackgroundResource(R.drawable.pink_rounded);
                        helpful.setTextColor(getResources().getColor(R.color.white));
                        helpful.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Helpful,","");
                        helpful.setBackgroundResource(R.drawable.pink_border_rounded);
                        helpful.setTextColor(getResources().getColor(R.color.black));
                        helpful.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }

                    break;
                case R.id.talended:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Talented,")){
                        iamalso_string += " Talented,";
                        talended.setBackgroundResource(R.drawable.pink_rounded);
                        talended.setTextColor(getResources().getColor(R.color.white));
                        talended.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Talented,","");
                        talended.setBackgroundResource(R.drawable.pink_border_rounded);
                        talended.setTextColor(getResources().getColor(R.color.black));
                        talended.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;

                case R.id.diligient:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" Diligent,")){
                        iamalso_string += " Diligent,";
                        diligient.setBackgroundResource(R.drawable.pink_rounded);
                        diligient.setTextColor(getResources().getColor(R.color.white));
                        diligient.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" Diligent,","");
                        diligient.setBackgroundResource(R.drawable.pink_border_rounded);
                        diligient.setTextColor(getResources().getColor(R.color.black));
                        diligient.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.respecting_elders:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" respecting elders,")){
                        iamalso_string += " respecting elders,";
                        respecting_elders.setBackgroundResource(R.drawable.pink_rounded);
                        respecting_elders.setTextColor(getResources().getColor(R.color.white));
                        respecting_elders.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" respecting elders,","");
                        respecting_elders.setBackgroundResource(R.drawable.pink_border_rounded);
                        respecting_elders.setTextColor(getResources().getColor(R.color.black));
                        respecting_elders.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }

                    break;
                case R.id.responsible:
                    if (iamalso_string.equals("")){
                        iamalso_string += "My partner should be";
                    }
                    if (!iamalso_string.contains(" responsible,")){
                        iamalso_string += " responsible,";
                        responsible.setBackgroundResource(R.drawable.pink_rounded);
                        responsible.setTextColor(getResources().getColor(R.color.white));
                        responsible.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        iamalso_string =  iamalso_string.replace(" responsible,","");
                        responsible.setBackgroundResource(R.drawable.pink_border_rounded);
                        responsible.setTextColor(getResources().getColor(R.color.black));
                        responsible.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.Cooking:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Cooking,")){
                        hoppies_string += " Cooking,";
                        Cooking.setBackgroundResource(R.drawable.pink_rounded);
                        Cooking.setTextColor(getResources().getColor(R.color.white));
                        Cooking.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Cooking,","");
                        Charity.setBackgroundResource(R.drawable.pink_border_rounded);
                        Charity.setTextColor(getResources().getColor(R.color.black));
                        Charity.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Nature:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Nature,")){
                        hoppies_string += " Nature,";
                        Nature.setBackgroundResource(R.drawable.pink_rounded);
                        Nature.setTextColor(getResources().getColor(R.color.white));
                        Nature.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Nature,","");
                        Nature.setBackgroundResource(R.drawable.pink_border_rounded);
                        Nature.setTextColor(getResources().getColor(R.color.black));
                        Nature.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Photography:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Photography,")){
                        hoppies_string += " Photography,";
                        Photography.setBackgroundResource(R.drawable.pink_rounded);
                        Photography.setTextColor(getResources().getColor(R.color.white));
                        Photography.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Photography,","");
                        Photography.setBackgroundResource(R.drawable.pink_border_rounded);
                        Photography.setTextColor(getResources().getColor(R.color.black));
                        Photography.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Dancing:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Dancing,")){
                        hoppies_string += " Dancing,";
                        Dancing.setBackgroundResource(R.drawable.pink_rounded);
                        Dancing.setTextColor(getResources().getColor(R.color.white));
                        Dancing.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Dancing,","");
                        Dancing.setBackgroundResource(R.drawable.pink_border_rounded);
                        Dancing.setTextColor(getResources().getColor(R.color.black));
                        Dancing.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }
                    break;
                case R.id.Painting:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Painting,")){
                        hoppies_string += " Painting,";
                        Painting.setBackgroundResource(R.drawable.pink_rounded);
                        Painting.setTextColor(getResources().getColor(R.color.white));
                        Painting.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Painting,","");
                        Painting.setBackgroundResource(R.drawable.pink_border_rounded);
                        Painting.setTextColor(getResources().getColor(R.color.black));
                        Painting.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }break;
                case R.id.Music:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Music,")){
                        hoppies_string += " Music,";
                        Music.setBackgroundResource(R.drawable.pink_rounded);
                        Music.setTextColor(getResources().getColor(R.color.white));
                        Music.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Music,","");
                        Music.setBackgroundResource(R.drawable.pink_border_rounded);
                        Music.setTextColor(getResources().getColor(R.color.black));
                        Music.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Pazzles:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Pazzles,")){
                        hoppies_string += " Pazzles,";
                        Pazzles.setBackgroundResource(R.drawable.pink_rounded);
                        Pazzles.setTextColor(getResources().getColor(R.color.white));
                        Pazzles.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Pazzles,","");
                        Pazzles.setBackgroundResource(R.drawable.pink_border_rounded);
                        Pazzles.setTextColor(getResources().getColor(R.color.black));
                        Pazzles.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Handgraft:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Handgraft,")){
                        hoppies_string += " Handgraft,";
                        Handgraft.setBackgroundResource(R.drawable.pink_rounded);
                        Handgraft.setTextColor(getResources().getColor(R.color.white));
                        Handgraft.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Handgraft,","");
                        Handgraft.setBackgroundResource(R.drawable.pink_border_rounded);
                        Handgraft.setTextColor(getResources().getColor(R.color.black));
                        Handgraft.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.art:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Art,")){
                        hoppies_string += " Art,";
                        art.setBackgroundResource(R.drawable.pink_rounded);
                        art.setTextColor(getResources().getColor(R.color.white));
                        art.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Art,","");
                        art.setBackgroundResource(R.drawable.pink_border_rounded);
                        art.setTextColor(getResources().getColor(R.color.black));
                        art.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.movies:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Movies,")){
                        hoppies_string += " Movies,";
                        movies.setBackgroundResource(R.drawable.pink_rounded);
                        movies.setTextColor(getResources().getColor(R.color.white));
                        movies.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Movies,","");
                        movies.setBackgroundResource(R.drawable.pink_border_rounded);
                        movies.setTextColor(getResources().getColor(R.color.black));
                        movies.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Internet_suffering:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Internet sufering,")){
                        hoppies_string += " Internet sufering,";
                        Internet_suffering.setBackgroundResource(R.drawable.pink_rounded);
                        Internet_suffering.setTextColor(getResources().getColor(R.color.white));
                        Internet_suffering.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Internet sufering,,","");
                        Internet_suffering.setBackgroundResource(R.drawable.pink_border_rounded);
                        Internet_suffering.setTextColor(getResources().getColor(R.color.black));
                        Internet_suffering.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Traveling:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Traveling,")){
                        hoppies_string += " Traveling,";
                        Traveling.setBackgroundResource(R.drawable.pink_rounded);
                        Traveling.setTextColor(getResources().getColor(R.color.white));
                        Traveling.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Traveling,","");
                        Traveling.setBackgroundResource(R.drawable.pink_border_rounded);
                        Traveling.setTextColor(getResources().getColor(R.color.black));
                        Traveling.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                case R.id.Gardening:
                    if (hoppies_string.equals("")){
                        hoppies_string +="\nmy hoppies are";
                    }
                    if (!hoppies_string.contains(" Gardening,")){
                        hoppies_string += " Gardening,";
                        Gardening.setBackgroundResource(R.drawable.pink_rounded);
                        Gardening.setTextColor(getResources().getColor(R.color.white));
                        Gardening.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);
                    }else {
                        hoppies_string =  hoppies_string.replace(" Gardening,","");
                        Gardening.setBackgroundResource(R.drawable.pink_border_rounded);
                        Gardening.setTextColor(getResources().getColor(R.color.black));
                        Gardening.setPadding(10,5,10,5);
                        finalstring =iamalso_string + hoppies_string;
                        alert_etmyself.setText(finalstring);

                    }
                    break;
                default:{

                }
            }
        }
    }

}
