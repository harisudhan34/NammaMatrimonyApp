package com.skyappz.namma.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.skyappz.namma.R;
import com.skyappz.namma.activities.SingleProfileView;
import com.skyappz.namma.model.User;

import java.util.List;


public class ViewAllMatches extends RecyclerView.Adapter<ViewAllMatches.MyViewHolder> {

    private Context mContext;
    private List<User> rowitem;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView userid, username,height,religioln,caste,city,state;
        public AppCompatImageView profile;
        LinearLayout card;

        public MyViewHolder(View view) {
            super(view);
            userid = (AppCompatTextView) view.findViewById(R.id.userid);
            username = (AppCompatTextView) view.findViewById(R.id.name);
            height = (AppCompatTextView) view.findViewById(R.id.height);
            religioln = (AppCompatTextView) view.findViewById(R.id.religion);
            caste = (AppCompatTextView) view.findViewById(R.id.caste);
            city = (AppCompatTextView) view.findViewById(R.id.city);
            state = (AppCompatTextView) view.findViewById(R.id.state);
            profile=(AppCompatImageView)view.findViewById(R.id.profile_pic);
            card=(LinearLayout)view.findViewById(R.id.card);
        }
    }


    public ViewAllMatches(Context mContext, List<User> rowitem) {
        this.mContext = mContext;
        this.rowitem = rowitem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_matches, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.userid.setText("NM-"+rowitem.get(position).getUser_id());
        holder.username.setText(rowitem.get(position).getName());
        holder.height.setText(rowitem.get(position).getHeight());
        holder.religioln.setText(rowitem.get(position).getReligion());
        holder.caste.setText(rowitem.get(position).getCaste());
        holder.city.setText(rowitem.get(position).getHome_city());
        holder.state.setText(rowitem.get(position).getState());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, SingleProfileView.class);
                i.putExtra("user_id",rowitem.get(position).getUser_id());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        UrlImageViewHelper.setUrlDrawable(holder.profile, "https://nammamatrimony.in/uploads/profile_image/" + rowitem.get(position).getProfile_image(), R.drawable.loading);


    }



    @Override
    public int getItemCount() {
        return rowitem.size();
    }
}
