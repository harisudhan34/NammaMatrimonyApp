package com.skyappz.namma.membership;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.adapter.MembershipPlanAdapter;
import com.skyappz.namma.model.Plan;

import java.util.List;

public class MembershipFragment extends Fragment {

    private MembershipViewModel mViewModel;
    private View view;
    private TabLayout tlPlans;
    private ViewPager viewPager;
    private MembershipPlanAdapter adapter;

    public static MembershipFragment newInstance() {
        return new MembershipFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.membership_fragment, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        tlPlans = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.pager);
        mViewModel = ViewModelProviders.of(this).get(MembershipViewModel.class);
        mViewModel.setActivity(getActivity());
        mViewModel.getAllPlans();
        final Observer<List<Plan>> getUserReceiver = new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable final List<Plan> plans) {
                addPlansInPager(plans);
            }
        };
        mViewModel.getPlan().observeForever(getUserReceiver);
    }

    private void addPlansInPager(List<Plan> plans) {
        setupViewPager(viewPager, plans);
        tlPlans.setupWithViewPager(viewPager);
        addCustomTabs(plans);
    }

    private void addCustomTabs(List<Plan> plans) {
        for (int i = 0; i < tlPlans.getTabCount(); i++) {
            TabLayout.Tab tab = tlPlans.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }

    private void setupViewPager(ViewPager viewPager, List<Plan> plans) {
        adapter = new MembershipPlanAdapter(getChildFragmentManager(), getActivity(), plans);
        for (Plan plan : plans) {
            adapter.addFrag(PlanListFragment.newInstance(plans), plan.getPlan_name());
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public MembershipPlanAdapter getAdapter() {
        return adapter;
    }
}
