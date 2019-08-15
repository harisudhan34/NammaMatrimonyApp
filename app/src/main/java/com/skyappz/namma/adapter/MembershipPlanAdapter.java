package com.skyappz.namma.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;


import com.skyappz.namma.R;
import com.skyappz.namma.model.Plan;
import com.skyappz.namma.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MembershipPlanAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final Context context;
    private final List<Plan> plans;

    public MembershipPlanAdapter(FragmentManager manager, Context cOntext, List<Plan> plans) {
        super(manager);
        this.context = cOntext;
        this.plans = plans;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_plan_tab, null);
        AppCompatTextView tvTitle = v.findViewById(R.id.title);
        tvTitle.setText(Utils.capitalizeWord(plans.get(position).getCategory()));
        return v;
    }

    public List<Fragment> getmFragmentList() {
        return mFragmentList;
    }
}