package com.adilpatel.vitalengine.homeScreenRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 9/12/16.
 */
public class allRecyclerViewAdapter extends RecyclerView.Adapter<allRecyclerViewAdapter.ViewHolder> {



    ArrayList<MessageData> arrMessageData;
    Context context;


    // Pass in the contact array into the constructor
    public allRecyclerViewAdapter(Context mainActivity, ArrayList<MessageData> arrMessageData) {
        this.arrMessageData = arrMessageData;

        context = mainActivity;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public allRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_all, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(allRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        MessageData contact = arrMessageData.get(position);

        // Set item views based on your views and data model
        TextView name = viewHolder.allName;
        name.setText(contact.getName());
        TextView message = viewHolder.allMessage;
        message.setText(contact.getMessage());
        ImageView image = viewHolder.allImage;
        image.setImageBitmap(contact.getImage());
        TextView patient = viewHolder.patient;
        patient.setText(contact.getPatient());
        TextView time = viewHolder.allTitle;
        time.setText(contact.getSubject());

        TextView subject = viewHolder.allSubject;


        if (contact.getPatient() != "") {
            subject.setText("Patient: ");

        }
        else {
            subject.setVisibility(View.GONE);
        }

//        if (arrMessageData.get(position).getType() == "message"){
//            setBackgroundColor(Color.parseColor("#EAEAFF"));
//        }
//
//        if (arrMessageData.get(position).getType() == "conversation"){
//            rootView.setBackgroundColor(Color.parseColor("#EAFFEA"));
//        }




    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return arrMessageData.size();
    }

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView allName;
        ImageView allImage;
        TextView allMessage;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView allTitle;
        TextView allSubject;
        TextView patient;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            allName = (TextView) itemView.findViewById(R.id.allName);
            allImage =(ImageView) itemView.findViewById(R.id.allImage);
            allMessage = (TextView) itemView.findViewById(R.id.allMessage);
            allTitle = (TextView) itemView.findViewById(R.id.allTitle);
            allSubject = (TextView) itemView.findViewById(R.id.allSubject);
            patient = (TextView) itemView.findViewById(R.id.allTitle);

//            docImage = (ImageView) itemView.findViewById(R.id.referralComfirmDocPic);
//            docName = (TextView) itemView.findViewById(R.id.referralComfirmDocName);
        }
    }
}


