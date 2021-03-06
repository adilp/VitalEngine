package com.adilpatel.vitalengine.Expand2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.List;

/**
 * Created by Adil on 8/3/16.
 */


    public class RecyclerViewMessage extends RecyclerView.Adapter<RecyclerViewMessage.ViewHolder> {

        private List<StaffObject> mStaff;
        // Store the context for easy access
        private Context mContext;


        // Pass in the contact array into the constructor
        public RecyclerViewMessage(Context context, List<StaffObject> contacts) {
            mStaff = contacts;
            mContext = context;

        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.simple_list_item_1, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            // Get the data model based on position
            StaffObject contact = mStaff.get(position);

            // Set item views based on your views and data model
            TextView textView = viewHolder.docName;
            textView.setText(contact.getStaffname());
            ImageView button = viewHolder.docImage;
            button.setImageResource(contact.getStaffPic());
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return mStaff.size();
        }

        // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public ImageView docImage;
            public TextView docName;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                docImage = (ImageView) itemView.findViewById(R.id.referralComfirmDocPic);
                docName = (TextView) itemView.findViewById(R.id.referralComfirmDocName);
            }
        }
    }

