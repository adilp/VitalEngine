package com.adilpatel.vitalengine.Expand2;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.StaffObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Adil on 7/21/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<DoctorObject> _listDataHeader; // header titles
    private HashMap<DoctorObject, List<StaffObject>> _listDataChild;

    //Keep track of checks
    ArrayList<ArrayList<Integer>> check_states = new ArrayList<ArrayList<Integer>>();

    ArrayList<StaffObject> check = new ArrayList<>();


    public ExpandableListAdapter(Context context, List<DoctorObject> listDataHeader, HashMap<DoctorObject, List<StaffObject>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final StaffObject model = (StaffObject)getChild(groupPosition, childPosition);
        String childText = model.getStaffname();
        int childPic = model.getStaffPic();
        String childLocation = model.getStaffLocation();
        String childSpecialty = model.getStaffSpecialty();



        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_create_team_child, null);
        }

        final CheckBox chk = (CheckBox) convertView.findViewById(R.id.checkBoxChild);

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked == true){

                                                        check.add(model);

                                                        //Toast.makeText(_context, check.lastIndexOf(model), Toast.LENGTH_SHORT).show();

                                                    }

                                                    else {
                                                        if(check.contains(model)) {
                                                            check.remove(model);
                                                            //Toast.makeText(_context, check.lastIndexOf(model), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                            }
                                        }
        );

        //String test = String.valueOf(_listDataChild.containsKey("Adil"));

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.teamNameChild);
        ImageView imgListChild = (ImageView)convertView.findViewById(R.id.teamImageChild);
        TextView locationListChild = (TextView)convertView.findViewById(R.id.teamLocationChild);
        TextView specialtyListChild = (TextView)convertView.findViewById(R.id.teamSpecialtyChild);

        txtListChild.setText(childText);
        imgListChild.setImageResource(childPic);
        locationListChild.setText(childLocation);
        specialtyListChild.setText(childSpecialty);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {



        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_create_team, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.teamName);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(_listDataHeader.get(groupPosition).getDocname());

        ImageView lblListImg = (ImageView) convertView.findViewById(R.id.teamImage);
        //lblListImg.setImageResource(_listDataHeader.get(groupPosition).getDocPic());
        lblListImg.setImageBitmap(_listDataHeader.get(groupPosition).getDocPic());

        TextView lblLocation = (TextView)convertView.findViewById(R.id.teamLocation);
        lblLocation.setText(_listDataHeader.get(groupPosition).getDocLocation());

        TextView lblSpecialty = (TextView)convertView.findViewById(R.id.teamSpecialty);
        lblSpecialty.setText(_listDataHeader.get(groupPosition).getDocspecialty());


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }




}
