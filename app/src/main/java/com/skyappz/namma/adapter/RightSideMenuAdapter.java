package com.skyappz.namma.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyappz.namma.R;
import com.skyappz.namma.activities.HomeActivity;

import java.util.ArrayList;

import static com.skyappz.namma.activities.HomeActivity.drawer;
import static com.skyappz.namma.editprofile.EditBasicDetails.select_dosam;
import static com.skyappz.namma.editprofile.EditBasicDetails.select_padham;
import static com.skyappz.namma.editprofile.EditBasicDetails.selectMaritalstatus;
import static com.skyappz.namma.editprofile.EditPersonalDetails.select_job;
import static com.skyappz.namma.editprofile.EditPersonalDetails.select_workingsector;
import static com.skyappz.namma.editprofile.EditFamilyDetails.select_family_status;
import static com.skyappz.namma.editprofile.EditFamilyDetails.select_father_occ;
import static com.skyappz.namma.editprofile.EditFamilyDetails.select_mother_occ;
import static com.skyappz.namma.editprofile.EditHabitsDetails.select_drinkinghapit;
import static com.skyappz.namma.editprofile.EditHabitsDetails.select_smookinghapit;
import static com.skyappz.namma.editprofile.EditBasicDetails.select_bodytype;
import static com.skyappz.namma.editprofile.EditBasicDetails.select_complexion;
import static com.skyappz.namma.editprofile.EditBasicDetails.select_disability;
import static com.skyappz.namma.editprofile.EditPersonalDetails.select_degree;
import static com.skyappz.namma.editprofile.EditReligionDetails.select_caste;
import static com.skyappz.namma.editprofile.EditReligionDetails.select_region;
import static com.skyappz.namma.editprofile.EditReligionDetails.select_subcaste;
import static com.skyappz.namma.editprofile.EditHabitsDetails.select_foodhapit;
import static com.skyappz.namma.editprofile.EditFamilyDetails.select_familytype;
import static com.skyappz.namma.editprofile.EditResidencyAddress.select_city;
import static com.skyappz.namma.editprofile.EditResidencyAddress.select_country;
import static com.skyappz.namma.editprofile.EditResidencyAddress.select_nationality;
import static com.skyappz.namma.editprofile.EditResidencyAddress.select_state;

public class RightSideMenuAdapter extends RecyclerView.Adapter<RightSideMenuAdapter.ItemHolder> {

    private Fragment fragment;
    private ArrayList<String> items;
    private Activity context;
    private int rightindex;


    public RightSideMenuAdapter(HomeActivity fragment, Activity context, ArrayList<String> items,int rightindex) {
        this.items = items;
        this.context = context;
        this.rightindex=rightindex;
    }
    public RightSideMenuAdapter( Activity context, ArrayList<String> items) {
        this.items = items;
        this.context = context;
        this.rightindex=rightindex;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, null);
        ItemHolder rcv = new ItemHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        String item = items.get(position);
        holder.tvValue.setText(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView tvValue;

        private ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvValue = itemView.findViewById(R.id.tvValue);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String clickvalue= String.valueOf(items.get(position));
            Log.e("indexx",String.valueOf(rightindex));
            switch (rightindex){
//                case 0:
//                selectmother_tongu = clickvalue;
//                break;
//                case 1:
//                    select_profilecreatby = clickvalue;
//                    break;
                case 2:
                    select_complexion =clickvalue;
                    break;
                case 3:
                    select_bodytype = clickvalue;
                    break;
                case 4:
                    select_disability =clickvalue;
                    break;
                case 5:
                    select_degree =clickvalue;
                    break;
                case 6:
                    select_job = clickvalue;
                    break;
                case 7:
                    select_father_occ = clickvalue;
                    break;
                case 8:
                    select_mother_occ = clickvalue;
                    break;
                case  9:
                    select_workingsector = clickvalue;
                     break;
                case 10:
                    select_subcaste = clickvalue;
                    break;
                case 11:
                    select_region =clickvalue;
                    break;
                case 12:
                    select_caste =clickvalue;
                    break;
                case 13:
                    select_subcaste =clickvalue;
                    break;
                case 14:
                    select_foodhapit = clickvalue;
                    break;
                case 15:
                    select_smookinghapit = clickvalue;
                    break;
                case 16:
                    select_drinkinghapit = clickvalue;
                    break;
                case 17:
                    select_familytype = clickvalue;
                    break;
                case 18:
                    select_family_status =clickvalue;
                    break;
                case 19:
                    selectMaritalstatus =clickvalue;
                    break;
                case 20:
                    select_dosam =clickvalue;
                    break;
                case 21:
                    select_padham =clickvalue;
                    break;
                case 22:
                    select_nationality =clickvalue;
                    break;
                case 23:
                    select_country =clickvalue;
                    Log.e("pos",select_country);
                    break;
                case 24:
                    select_state =clickvalue;
                    ((HomeActivity) context).loadcity(select_state);
                    break;
                case 25:
                    select_city =clickvalue;
                    Log.e("click",select_city);
                    break;
                default: {

                }
            }
            drawer.closeDrawer(Gravity.END);

        }
    }
}
