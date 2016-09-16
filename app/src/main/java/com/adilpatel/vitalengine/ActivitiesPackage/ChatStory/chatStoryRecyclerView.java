package com.adilpatel.vitalengine.ActivitiesPackage.ChatStory;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;

import java.util.HashMap;

/**
 * Created by Adil on 9/12/16.
 */
public class chatStoryRecyclerView extends RecyclerView.Adapter<chatStoryRecyclerView.ViewHolder>{


    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;


    private LayoutInflater layoutInflater;
    private HashMap<Integer, chatMessageModel> messages;


    // Pass in the contact array into the constructor
    public chatStoryRecyclerView(Activity activity, HashMap<Integer, chatMessageModel> messages ) {
        layoutInflater = activity.getLayoutInflater();
        this.messages = messages;
    }


    @Override
    public chatStoryRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0){
            View contactView = inflater.inflate(R.layout.message_right, parent, false);

            ViewHolder viewHolder = new ViewHolder(contactView);

            return viewHolder;
        }
        else if (viewType == 1) {
            View contactView = inflater.inflate(R.layout.message_left, parent, false);

            ViewHolder viewHolder = new ViewHolder(contactView);

            return viewHolder;

        }

        else {
            View contactView = inflater.inflate(R.layout.message_left, parent, false);

            ViewHolder viewHolder = new ViewHolder(contactView);

            return viewHolder;
        }
        // Inflate the custom layout


        // Return a new holder instance

    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(chatStoryRecyclerView.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        chatMessageModel contact = messages.get(position);

        // Set item views based on your views and data model
        TextView message = viewHolder.txtMessage;
        message.setText(contact.getMessage());
        TextView time = viewHolder.time;
        time.setText(contact.getTime());
        TextView sender = viewHolder.sender;
        sender.setText(contact.getSender());
//        TextView patient = viewHolder.referralPatient;
//        patient.setText(contact.getSubject());
//        TextView time = viewHolder.referralTimeStamp;
//        time.setText(contact.getTime());



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return messages.size();
    }



    @Override
    public int getItemViewType(int i) {
        return messages.get(i).getDirection();
    }

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView txtMessage;
        TextView time;
        TextView sender;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            time =(TextView) itemView.findViewById(R.id.txtDate);
            sender= (TextView) itemView.findViewById(R.id.txtSender);
//            referralPatient = (TextView) itemView.findViewById(R.id.subjectTitle);
//            referralTimeStamp = (TextView) itemView.findViewById(R.id.referralTimestamp);

//            docImage = (ImageView) itemView.findViewById(R.id.referralComfirmDocPic);
//            docName = (TextView) itemView.findViewById(R.id.referralComfirmDocName);
        }
    }
}


