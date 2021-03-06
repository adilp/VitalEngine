package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 6/14/16.
 */
public class CustomAdapterAll extends BaseAdapter{

    ArrayList<MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterAll(Context mainActivity, ArrayList<MessageData> arrMessageData){


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
        TextView allName;
        ImageView allImage;
        TextView allMessage;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView allTitle;
        TextView allSubject;
        TextView patient;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.list_item_all, null);


        holder.allName = (TextView) rootView.findViewById(R.id.allName);
        holder.allImage =(ImageView) rootView.findViewById(R.id.allImage);
        holder.allMessage = (TextView) rootView.findViewById(R.id.allMessage);
        holder.allTitle = (TextView) rootView.findViewById(R.id.allTitle);
        holder.allSubject = (TextView) rootView.findViewById(R.id.allSubject);
        holder.patient = (TextView) rootView.findViewById(R.id.allTitle);
        //holder.unreadCount = (TextView) rootView.findViewById(R.id.unreadCount);
        //holder.unreadCountBackground = (FrameLayout)rootView.findViewById(R.id.unreadCountBackground);


        holder.allMessage.setText("" + arrMessageData.get(position).getMessage());
        holder.allName.setText("" + arrMessageData.get(position).getName());
        holder.allImage.setImageBitmap(arrMessageData.get(position).getImage());
        holder.allTitle.setText("" + arrMessageData.get(position).getSubject());
        holder.patient.setText("" + arrMessageData.get(position).getPatient());

        if (arrMessageData.get(position).getPatient() != "") {
            holder.allSubject.setText("Patient: ");

        }
        else {
            holder.allSubject.setVisibility(View.GONE);
        }

        if (arrMessageData.get(position).getType() == "message"){
            rootView.setBackgroundColor(Color.parseColor("#EAEAFF"));
        }

        if (arrMessageData.get(position).getType() == "conversation"){
            rootView.setBackgroundColor(Color.parseColor("#EAFFEA"));
        }


//        if (arrMessageData.get(position).isRead() == true){
//            holder.tv1.setTypeface(Typeface.DEFAULT_BOLD);
//
//
//        }
//        if (arrMessageData.get(position).isRead() == false) {
//            holder.unreadCount.setText("");
//            //holder.unreadCountBackground.setBackgroundColor(Color.WHITE);
//            holder.unreadCount.setTypeface(Typeface.DEFAULT_BOLD);
//        }



//        rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Clicked item is : " + names[position], Toast.LENGTH_LONG).show();
//            }
//        });
        return rootView;
    }
}
