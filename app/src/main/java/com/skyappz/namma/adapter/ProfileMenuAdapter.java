package com.skyappz.namma.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.fragments.SettingsFragment;
import com.skyappz.namma.model.NavigationMenuItem;

import java.util.ArrayList;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.NavigationMenuHolder> {

    private Fragment fragment;
    private ArrayList<NavigationMenuItem> navigationMenuItems;
    private Activity context;

    public ProfileMenuAdapter(Fragment fragment, Activity context, ArrayList<NavigationMenuItem> navigationMenuItems) {
        this.navigationMenuItems = navigationMenuItems;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public NavigationMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_menu_item, null);
        NavigationMenuHolder rcv = new NavigationMenuHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(NavigationMenuHolder holder, final int position) {
        NavigationMenuItem post = navigationMenuItems.get(position);
        holder.tvName.setText(post.getName());
        holder.ivIcon.setImageResource(post.getImage());
    }

    @Override
    public int getItemCount() {
        return navigationMenuItems.size();
    }

    public class NavigationMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView tvName;
        private AppCompatImageView ivIcon;

        private NavigationMenuHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = itemView.findViewById(R.id.tvName);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ((SettingsFragment) fragment).onMenuSelected(position);
            Log.e("selectment",String.valueOf(position));
        }
    }
}
