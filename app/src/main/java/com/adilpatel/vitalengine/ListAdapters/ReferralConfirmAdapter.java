package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.List;

/**
 * Created by Adil on 7/28/16.
 */
public class ReferralConfirmAdapter extends BaseAdapter {
    private Context _context;
    //DoctorObject doctorObject;
    private List<StaffObject> staffObjects;




    public ReferralConfirmAdapter(Context context,  List<StaffObject> staffObjects) {
        this._context = context;
        //this.doctorObject = doctorObject;
        this.staffObjects = staffObjects;
    }



    @Override
    public int getCount() {
        return staffObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return staffObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        StaffObject model = (StaffObject) getItem(position);
        String childText = model.getStaffname();
        int childPic = model.getStaffPic();
        String childLocation = model.getStaffLocation();
        String childSpecialty = model.getStaffSpecialty();


        // Get the data item for this position
        //User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(this._context).inflate(R.layout.simple_list_item_1, parent, false);
        }
        // Lookup view for data population
        ImageView referralComfirmDocPic = (ImageView) convertView.findViewById(R.id.referralComfirmDocPic);
        TextView referralComfirmDocName = (TextView) convertView.findViewById(R.id.referralComfirmDocName);
        // Populate the data into the template view  the data object
        referralComfirmDocPic.setImageResource(childPic);
        referralComfirmDocName.setText(childText);
        // Return the completed view to render on screen
        return convertView;
    }

}
