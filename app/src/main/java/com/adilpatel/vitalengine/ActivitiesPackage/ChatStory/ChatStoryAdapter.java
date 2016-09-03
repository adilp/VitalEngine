package com.adilpatel.vitalengine.ActivitiesPackage.ChatStory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;

import java.util.HashMap;

/**
 * Created by Adil on 8/16/16.
 */
public class ChatStoryAdapter extends BaseAdapter{

    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;


    private LayoutInflater layoutInflater;
    private HashMap<Integer, chatMessageModel> messages;

    public ChatStoryAdapter(Activity activity, HashMap<Integer, chatMessageModel> messages ) {
        layoutInflater = activity.getLayoutInflater();
        this.messages = messages;
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i) {
        return messages.get(i).getDirection();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        int direction = getItemViewType(i);

        //show message on left or right, depending on if
        //it's incoming or outgoing
        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
            }
            convertView = layoutInflater.inflate(res, viewGroup, false);
        }

        String message = messages.get(i).getMessage();

        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        txtMessage.setText(message.toString());

        TextView time = (TextView)convertView.findViewById(R.id.txtDate);
        time.setText(messages.get(i).getTime());

        TextView sender = (TextView)convertView.findViewById(R.id.txtSender);
        sender.setText(messages.get(i).getSender());



        return convertView;
    }
}
