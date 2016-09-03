package com.adilpatel.vitalengine.ActivitiesPackage.Conversation;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.Models.TeamObject;

import java.util.List;

/**
 * Created by Adil on 8/1/16.
 */
public class CreateMessageRecipientListAdapter extends BaseAdapter {


    private Context _context;
    private List<TeamObject> _listDataHeader; // header titles



    public CreateMessageRecipientListAdapter(Context context, List<TeamObject> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;

    }

    @Override
    public int getCount() {
        return _listDataHeader.size();
    }

    @Override
    public Object getItem(int position) {
        return _listDataHeader.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_create_team, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.teamName);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(_listDataHeader.get(position).getStaffName());

        ImageView lblListImg = (ImageView) convertView.findViewById(R.id.teamImage);
        lblListImg.setImageResource(_listDataHeader.get(position).getStaffPic());

        TextView lblLocation = (TextView)convertView.findViewById(R.id.teamLocation);
        lblLocation.setText(_listDataHeader.get(position).getStaffLocation());

        TextView lblSpecialty = (TextView)convertView.findViewById(R.id.teamSpecialty);
        lblSpecialty.setText(_listDataHeader.get(position).getStaffSpecialty());


        return convertView;

    }

}

