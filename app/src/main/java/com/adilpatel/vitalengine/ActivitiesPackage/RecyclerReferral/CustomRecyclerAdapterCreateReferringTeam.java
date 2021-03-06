package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 9/16/16.
 */
public class CustomRecyclerAdapterCreateReferringTeam extends RecyclerView.Adapter<CustomRecyclerAdapterCreateReferringTeam.ViewHolder>{


    private List<DoctorObject> messageDataList;
    private Context context;
    private int myDoc;
    private List<StaffObject> myStaff;
    private Patient patient;

    // Pass in the contact array into the constructor
    public CustomRecyclerAdapterCreateReferringTeam(Context mainActivity, ArrayList<DoctorObject> messageDataList, int myDoc, ArrayList<StaffObject> myStaff, Patient patient) {
        this.messageDataList = messageDataList;
        this.myDoc = myDoc;
        this.myStaff = myStaff;
        this.patient= patient;

        context = mainActivity;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public CustomRecyclerAdapterCreateReferringTeam.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_create_team, parent, false);



        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context,messageDataList, myDoc, myStaff, patient);
        return viewHolder;
    }


    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CustomRecyclerAdapterCreateReferringTeam.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        DoctorObject contact = messageDataList.get(position);

        // Set item views based on your views and data model
        TextView person = viewHolder.person;
        person.setText(contact.getDocname());
        ImageView image = viewHolder.pic;
        image.setImageResource(contact.getDocPic());

        viewHolder.selected.setVisibility(View.INVISIBLE);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return messageDataList.size();
    }

    public void setFilter(List<DoctorObject> messageModels) {
        messageDataList = new ArrayList<>();
        messageDataList.addAll(messageModels);
        notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView pic;
        public TextView person;
        public int myDoc;
        public List<StaffObject> myStaff;
        CheckBox selected;
        Patient patient;

        ArrayList<DoctorObject> message = new ArrayList<DoctorObject>();
        Context ctx;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context ctx, List<DoctorObject> message, int myDoc, List<StaffObject> myStaff, Patient patient) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.ctx = ctx;
            itemView.setOnClickListener(this);
            this.myDoc = myDoc;
            this.myStaff = myStaff;
            this.patient = patient;
            this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
            this.person = (TextView) itemView.findViewById(R.id.teamName);
            this.message = (ArrayList<DoctorObject>) message;
            this.selected = (CheckBox) itemView.findViewById(R.id.visibleButton);


        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            DoctorObject obj = message.get(position);


            Toast.makeText(ctx, "CLICKED " + obj.getDocname(), Toast.LENGTH_SHORT).show();
//
            Intent myIntent = new Intent(ctx, RecyclerCreateReferringTeamStaffActivity.class);
            myIntent.putParcelableArrayListExtra("Staff", (ArrayList<? extends Parcelable>) myStaff);
            myIntent.putExtra("myDoc", myDoc);
            myIntent.putExtra("Patient",patient);
            myIntent.putExtra("referringDoc", obj.getDocId());
            ctx.startActivity(myIntent);
        }
    }
}