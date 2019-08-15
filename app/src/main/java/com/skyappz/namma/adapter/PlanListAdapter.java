package com.skyappz.namma.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.membership.PlanListFragment;
import com.skyappz.namma.model.Plan;

import java.util.List;


public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.PlanHolder> {

    private List<Plan> plans;
    private Activity context;
    private Fragment fragment;

    public PlanListAdapter(Activity context, List<Plan> postItems, Fragment fragment) {
        this.plans = postItems;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public PlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, null);
        PlanHolder leaveHolder = new PlanHolder(layoutView);
        return leaveHolder;
    }

    @Override
    public void onBindViewHolder(PlanHolder holder, final int position) {
        Plan plan = plans.get(position);
        holder.tvPlanName.setText(plan.getPlan_name());
    }

    @Override
    public int getItemCount() {
        return this.plans.size();
    }


    public class PlanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView tvPlanName, tvPrice, tvDuration;

        private PlanHolder(View itemView) {
            super(itemView);
            tvPlanName = itemView.findViewById(R.id.tvPlanName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ((PlanListFragment) fragment).onPlanSelected(plans.get(position));
        }
    }

}
