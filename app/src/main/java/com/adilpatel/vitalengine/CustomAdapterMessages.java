package com.adilpatel.vitalengine;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adil on 5/17/16.
 */
public class CustomAdapterMessages extends BaseAdapter {

    //int [] images;    String [] names; String [] messages; boolean[] readUnread;
    ArrayList<OneFragment.MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterMessages(Context mainActivity, ArrayList<OneFragment.MessageData> arrMessageData){ //int[] images, String [] names, String [] messages, boolean[] readUnread) {
        //this.images = images;
        //this.names = names;
        //this.messages = messages;
        //this.readUnread = readUnread;

        this.arrMessageData = arrMessageData;

        context = mainActivity;
//

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
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.user_list_item , null);
//        holder.tv = (TextView) rootView.findViewById(R.id.myTV);
//        holder.iv=(ImageView) rootView.findViewById(R.id.myIV);
//        holder.tv1= (TextView) rootView.findViewById(R.id.myTV1);
//        holder.tv.setText(names[position]);
//        holder.tv1.setText(messages[position]);
//        holder.iv.setImageResource(images[position]);

        holder.tv = (TextView) rootView.findViewById(R.id.myTV);
        holder.iv=(ImageView) rootView.findViewById(R.id.myIV);
        holder.tv1= (TextView) rootView.findViewById(R.id.myTV1);

        holder.tv1.setText("" + arrMessageData.get(position).getMessage());
        holder.tv.setText(""+ arrMessageData.get(position).getName());
        holder.iv.setImageResource(arrMessageData.get(position).getImage());

        if (arrMessageData.get(position).isRead() == true){
            holder.tv1.setTypeface(Typeface.DEFAULT_BOLD);
        }



//        String message1 = arrMessageData.get(0).getMessage();
//        String name1 = arrMessageData.get(0).getName();
//        int pic1 = arrMessageData.get(0).getImage();
//        holder.tv1.setText(message1);
//        holder.tv.setText(name1);
//        holder.iv.setImageResource(pic1);







//        rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Clicked item is : " + names[position], Toast.LENGTH_LONG).show();
//            }
//        });
        return rootView;
    }
}
