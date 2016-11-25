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
 * Created by Adil on 5/17/16.
 */
public class CustomAdapterMessages extends BaseAdapter {


    ArrayList<MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterMessages(Context mainActivity, ArrayList<MessageData> arrMessageData){


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
        TextView tv;
        ImageView iv;
        TextView tv1;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView subject;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.list_item_message, null);


        holder.tv = (TextView) rootView.findViewById(R.id.myTV);
        holder.iv=(ImageView) rootView.findViewById(R.id.myIV);
        holder.tv1= (TextView) rootView.findViewById(R.id.myTV1);
        holder.unreadCount = (TextView) rootView.findViewById(R.id.unreadCount);
        //holder.subject = (TextView)rootView.findViewById(R.id.subjectTitle);
        //holder.unreadCountBackground = (FrameLayout)rootView.findViewById(R.id.unreadCountBackground);


        holder.tv1.setText("" + arrMessageData.get(position).getMessage());
        holder.tv.setText(""+ arrMessageData.get(position).getName());
        holder.iv.setImageBitmap(arrMessageData.get(position).getImage());
        //holder.subject.setText("" + arrMessageData.get(position).getSubject());

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
