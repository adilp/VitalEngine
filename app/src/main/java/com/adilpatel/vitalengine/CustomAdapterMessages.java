package com.adilpatel.vitalengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Adil on 5/17/16.
 */
public class CustomAdapterMessages extends BaseAdapter {

    int [] images;    String [] names; String [] messages;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterMessages(Context mainActivity, int[] images, String [] names, String [] messages) {
        this.images = images;        this.names = names;
        this.messages = messages;

        context = mainActivity;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return names.length;
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
        holder.tv = (TextView) rootView.findViewById(R.id.myTV);
        holder.iv=(ImageView) rootView.findViewById(R.id.myIV);
        holder.tv1= (TextView) rootView.findViewById(R.id.myTV1);
        holder.tv.setText(names[position]);
        holder.tv1.setText(messages[position]);
        holder.iv.setImageResource(images[position]);




        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked item is : " + names[position], Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }
}
