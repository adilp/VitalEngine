package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
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
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView referralSubject;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.list_item_referral, null);


        holder.referralName = (TextView) rootView.findViewById(R.id.referralName);
        holder.referralImage =(ImageView) rootView.findViewById(R.id.referralImage);
        holder.referralMessage= (TextView) rootView.findViewById(R.id.referralMessage);
        //holder.convoSubject = (TextView) rootView.findViewById(R.id.ConversationSubjectTitle);
        //holder.unreadCount = (TextView) rootView.findViewById(R.id.unreadCount);
        //holder.unreadCountBackground = (FrameLayout)rootView.findViewById(R.id.unreadCountBackground);


        holder.referralMessage.setText("" + arrMessageData.get(position).getMessage());
        holder.referralName.setText("" + arrMessageData.get(position).getName());
        holder.referralImage.setImageResource(arrMessageData.get(position).getImage());
        //holder.convoSubject.setText("" + arrMessageData.get(position).getSubject());

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
