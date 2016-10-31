package com.adilpatel.vitalengine.homeScreenRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.ActivitiesPackage.ChatStory.ChatlistActivity;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 9/14/16.
 */
public class conversationRecyclerViewAdapter extends RecyclerView.Adapter<conversationRecyclerViewAdapter.ViewHolder>{


    ArrayList<MessageData> arrMessageData;
    Context context;


    // Pass in the contact array into the constructor
    public conversationRecyclerViewAdapter(Context mainActivity, ArrayList<MessageData> arrMessageData) {
        this.arrMessageData = arrMessageData;

        context = mainActivity;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public conversationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_conversations, parent, false);



        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context,arrMessageData);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(conversationRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        MessageData contact = arrMessageData.get(position);

        // Set item views based on your views and data model
        TextView name = viewHolder.conversationName;
        name.setText(contact.getName());
        TextView message = viewHolder.conversationMessage;
        message.setText(contact.getMessage());
        ImageView image = viewHolder.conversationImage;
        image.setImageBitmap(contact.getImage());
//        TextView patient = viewHolder.referralPatient;
//        patient.setText(contact.getSubject());
//        TextView time = viewHolder.referralTimeStamp;
//        time.setText(contact.getTime());


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return arrMessageData.size();
    }



    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView conversationName;
        ImageView conversationImage;
        TextView conversationMessage;
//        TextView referralPatient;
//        TextView referralTimeStamp;

        ArrayList<MessageData> message = new ArrayList<MessageData>();
        Context ctx;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context ctx, ArrayList<MessageData> message) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.message = message;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            conversationName = (TextView) itemView.findViewById(R.id.conversationName);
            conversationImage =(ImageView) itemView.findViewById(R.id.conversationImage);
            conversationMessage= (TextView) itemView.findViewById(R.id.conversationMessage);

            //referralTimeStamp = (TextView) itemView.findViewById(R.id.con);

//            docImage = (ImageView) itemView.findViewById(R.id.referralComfirmDocPic);
//            docName = (TextView) itemView.findViewById(R.id.referralComfirmDocName);
        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            MessageData obj = message.get(position);

            int test = obj.getId();




            Toast.makeText(ctx, "CLICKED " + obj.getId(), Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(ctx, ChatlistActivity.class);
            //myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
            myIntent.putExtra("refId", obj.getId());
            myIntent.putExtra("typeOfActivity", "conversation");
            //myIntent.putExtra("messagePerson", currentPerson);
            ctx.startActivity(myIntent);
        }
    }
}