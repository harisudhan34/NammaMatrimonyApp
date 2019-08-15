package com.skyappz.namma.membership;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.skyappz.namma.R;
import com.skyappz.namma.adapter.PlanListAdapter;
import com.skyappz.namma.fragments.PlanDetailFragment;
import com.skyappz.namma.model.Plan;

import java.io.Serializable;
import java.util.List;

public class PlanListFragment extends Fragment {

    List<Plan> plans;
    private View view;
    RecyclerView rvPlans;
    private PlanListAdapter planListAdapter;
    FrameLayout flContainer;
    private Context context;

    public PlanListFragment() {
    }

    public static PlanListFragment newInstance(List<Plan> plans) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle args = new Bundle();
        args.putSerializable("plans", (Serializable) plans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //plans = (List<Plan>) getArguments().getSerializable("plans");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan_list, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        flContainer = view.findViewById(R.id.flContainer);
        rvPlans = view.findViewById(R.id.rvPlans);
        rvPlans.setHasFixedSize(true);
        LinearLayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        rvPlans.setLayoutManager(rvLayoutManager);
        rvPlans.setItemAnimator(new DefaultItemAnimator());
        planListAdapter = new PlanListAdapter(getActivity(), plans, this);
        rvPlans.setAdapter(planListAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onPlanSelected(Plan plan) {
        showPlanDetailScreen(plan);
    }

    private void showPlanDetailScreen(Plan plan) {
        PlanDetailFragment planDetailFragment = PlanDetailFragment.newInstance(plan);
        getChildFragmentManager().beginTransaction().add(R.id.flContainer, planDetailFragment).addToBackStack("PlanDetailFragment").commit();
    }
}
