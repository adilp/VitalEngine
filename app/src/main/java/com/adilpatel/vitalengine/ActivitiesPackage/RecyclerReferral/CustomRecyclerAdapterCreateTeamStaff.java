package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 9/16/16.
 */
public class CustomRecyclerAdapterCreateTeamStaff extends RecyclerView.Adapter<CustomRecyclerAdapterCreateTeamStaff.ViewHolder>{


    private List<StaffObject> messageDataList;
    private Context context;
    private int myDocId;


    // Pass in the contact array into the constructor
    public CustomRecyclerAdapterCreateTeamStaff(Context mainActivity, ArrayList<StaffObject> messageDataList, int myDocId) {
        this.messageDataList = messageDataList;
        this.myDocId = myDocId;

        context = mainActivity;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public CustomRecyclerAdapterCreateTeamStaff.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_create_team, parent, false);



        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context,messageDataList, myDocId);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CustomRecyclerAdapterCreateTeamStaff.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        StaffObject contact = messageDataList.get(position);

        // Set item views based on your views and data model
        TextView person = viewHolder.person;
        person.setText(contact.getStaffname());
        ImageView image = viewHolder.pic;
        image.setImageResource(contact.getStaffPic());



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return messageDataList.size();
    }

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView pic;
        public TextView person;

        ArrayList<StaffObject> message = new ArrayList<StaffObject>();
        Context ctx;
        int myDocId;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context ctx, List<StaffObject> message, int myDocId) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.ctx = ctx;
            itemView.setOnClickListener(this);
            this.myDocId = myDocId;

            this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
            this.person = (TextView) itemView.findViewById(R.id.teamName);
            this.message = (ArrayList<StaffObject>) message;

        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            StaffObject obj = message.get(position);


            Toast.makeText(ctx, "CLICKED " + obj.getStaffname(), Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(ctx, RecyclerCreateReferringTeamActivity.class);
            //myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
            myIntent.putExtra("myStaffId", obj.getStaffId());
            myIntent.putExtra("myDocId", myDocId);

            ctx.startActivity(myIntent);
        }
    }
}

