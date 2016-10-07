package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 6/13/16.
 */
public class CustomAdapterReferral extends BaseAdapter {

    ArrayList<MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterReferral(Context mainActivity, ArrayList<MessageData> arrMessageData){


        this.arrMessageData = arrMessageData;

        context = mainActivity;


        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public int getCount() {
        return arrMessageData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView referralName;
        ImageView referralImage;
        TextView referralMessage;
        TextView referralPatient;
        TextView referralTimeStamp;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.list_item_referral, null);


        holder.referralName = (TextView) rootView.findViewById(R.id.referralName);
        holder.referralImage =(ImageView) rootView.findViewById(R.id.referralImage);
        holder.referralMessage= (TextView) rootView.findViewById(R.id.referralMessage);
        holder.referralPatient = (TextView) rootView.findViewById(R.id.subjectTitle);
        holder.referralTimeStamp = (TextView) rootView.findViewById(R.id.referralTimestamp);

        holder.referralMessage.setText("" + arrMessageData.get(position).getMessage());
        holder.referralName.setText("" + arrMessageData.get(position).getName());
        holder.referralImage.setImageBitmap(arrMessageData.get(position).getImage());
        holder.referralPatient.setText("" + arrMessageData.get(position).getSubject());
        holder.referralTimeStamp.setText("" + arrMessageData.get(position).getTime());

        return rootView;
    }
}
