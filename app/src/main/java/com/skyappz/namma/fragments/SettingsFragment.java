package com.skyappz.namma.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.activities.HomeActivity;
import com.skyappz.namma.adapter.ProfileMenuAdapter;
import com.skyappz.namma.model.NavigationMenuItem;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {

    private View rootView;
    private RecyclerView rvMenus;
    private LinearLayoutManager listLayoutManager;
    private ProfileMenuAdapter profileMenuAdapter;
    private Activity mActivity;
    ArrayList<NavigationMenuItem> navigationMenuItems = new ArrayList<>();
    String names[];
    int icons[];

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        getData();
        initViews(rootView);
        return rootView;
    }

    private void getData() {
        names = new String[]{
                getActivity().getResources().getString(R.string.basic_details),
                getActivity().getResources().getString(R.string.personal_appearance),
                getActivity().getResources().getString(R.string.religion),
                getActivity().getResources().getString(R.string.family_details),
                getActivity().getResources().getString(R.string.habitual),
                getActivity().getResources().getString(R.string.education),
                getActivity().getResources().getString(R.string.residing_address),
                getActivity().getResources().getString(R.string.about_myself),
                getActivity().getResources().getString(R.string.about_my_partner),
                getActivity().getResources().getString(R.string.about_my_family),
                getActivity().getResources().getString(R.string.partner_preference),
        };
        icons = new int[]{
                R.drawable.basic_detail, R.drawable.personal_appearance,R.drawable.religion, R.drawable.family_detals,
                 R.drawable.habitual, R.drawable.education,
                R.drawable.residing_address, R.drawable.about_myself, R.drawable.about_my_partner,
                R.drawable.about_my_family, R.drawable.partner_preference
        };

        for (int i = 0; i < names.length; i++) {
            navigationMenuItems.add(new NavigationMenuItem(icons[i], names[i]));
        }
    }

    private void initViews(View rootView) {
        rvMenus = rootView.findViewById(R.id.rvMenus);
        listLayoutManager = new LinearLayoutManager(getActivity());
        rvMenus.setLayoutManager(listLayoutManager);
        rvMenus.setItemAnimator(new DefaultItemAnimator());
        profileMenuAdapter = new ProfileMenuAdapter(this, getActivity(), navigationMenuItems);
        rvMenus.setAdapter(profileMenuAdapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void onMenuSelected(int position) {
        ((HomeActivity) mActivity).setFragment(position, null);
        Log.e("posirti",String.valueOf(position));
    }
}
