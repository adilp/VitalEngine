package com.adilpatel.vitalengine.ListAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 7/13/16.
 */
public class CustomAdapterCreateTeam extends BaseAdapter{

    //ArrayList<MessageData> arrMessageData;

    ArrayList<MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterCreateTeam(Activity CreateMyTeamActivity, ArrayList<MessageData> arrMessageData){


        this.arrMessageData = arrMessageData;

        context = CreateMyTeamActivity;


        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView allName;
        ImageView allImage;
        TextView allMessage;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView allTitle;
        TextView allSubject;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.create_team_list_item,null);

        ViewHolder holder=new ViewHolder();



        holder.allImage = (ImageView) view.findViewById(R.id.teamImage);

        holder.allImage.setImageResource(arrMessageData.get(position).getImage());



        return convertView;
    }




}
