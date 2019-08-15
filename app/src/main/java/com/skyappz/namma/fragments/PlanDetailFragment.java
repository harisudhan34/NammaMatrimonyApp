package com.skyappz.namma.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.ResponseEntities.BaseResponse;
import com.skyappz.namma.membership.MembershipViewModel;
import com.skyappz.namma.model.BuyPlan;
import com.skyappz.namma.model.Plan;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceManager;


public class PlanDetailFragment extends Fragment {


    private Context context;
    private View view;
    Plan plan;
    AppCompatButton btnBuy, btnCancel;
    WebServiceManager webServiceManager;
    private MembershipViewModel mViewModel;
    BuyPlan buyPlan;

    public static PlanDetailFragment newInstance(Plan plan) {
        PlanDetailFragment fragment = new PlanDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("plan", plan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plan = (Plan) getArguments().getSerializable("plan");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan_detail, container, false);
        initViews();
        mViewModel = ViewModelProviders.of(this).get(MembershipViewModel.class);
        mViewModel.setActivity(getActivity());
        subscribe();
        return view;
    }

    private void subscribe() {
        final Observer<BaseResponse> buyPlanObserver = new Observer<BaseResponse>() {
            @Override
            public void onChanged(@Nullable final BaseResponse buyPlanResponse) {
                Utils.showToast(getActivity(), buyPlanResponse.getMsg());
            }
        };
        mViewModel.getBuyPlan().observeForever(buyPlanObserver);
    }

    private void initViews() {
        btnBuy = view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPlan = new BuyPlan("1", "1", "grggrg", "online payment", "2019-04-16");
                mViewModel.buyPlan(buyPlan);
            }
        });
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
