package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.graphics.Typeface;
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
 * Created by Adil on 5/29/16.
 */
public class CustomAdapterConversations extends BaseAdapter {

    ArrayList<MessageData> arrMessageData;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapterConversations(Context mainActivity, ArrayList<MessageData> arrMessageData){


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
        TextView conversationName;
        ImageView conversationImage;
        TextView conversationMessage;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView convoSubject;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        View rootView = inflater.inflate(R.layout.list_item_conversations, null);


        holder.conversationName = (TextView) rootView.findViewById(R.id.conversationName);
        holder.conversationImage =(ImageView) rootView.findViewById(R.id.conversationImage);
        holder.conversationMessage= (TextView) rootView.findViewById(R.id.conversationMessage);
        //holder.convoSubject = (TextView) rootView.findViewById(R.id.subjectTitle);
        //holder.convoSubject = (TextView) rootView.findViewById(R.id.ConversationSubjectTitle);
        //holder.unreadCount = (TextView) rootView.findViewById(R.id.unreadCount);
        //holder.unreadCountBackground = (FrameLayout)rootView.findViewById(R.id.unreadCountBackground);


        holder.conversationMessage.setText("" + arrMessageData.get(position).getMessage());
        holder.conversationName.setText("" + arrMessageData.get(position).getName());
        holder.conversationImage.setImageResource(arrMessageData.get(position).getImage());
        //holder.convoSubject.setText("" + arrMessageData.get(position).getSubject());
       // holder.convoSubject.setText("" + arrMessageData.get(position).getSubject());

        if (arrMessageData.get(position).isRead() == true){
            holder.conversationMessage.setTypeface(Typeface.DEFAULT_BOLD);


        }
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
