package com.adilpatel.vitalengine.Expand2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

/**
 * Created by Adil on 7/29/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<StaffObject> mStaff;
    // Store the context for easy access
    private Context mContext;


    // Pass in the contact array into the constructor
    public RecyclerViewAdapter(Context context, List<StaffObject> mStaff) {
        this.mStaff = mStaff;
        mContext = context;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        StaffObject contact = mStaff.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.docName;
        textView.setText(contact.getStaffname());
        ImageView button = viewHolder.docImage;
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);

        GlideUrl url = new GlideUrl("https://staging.vitalengine.com/portal-api/img/user/false/" + contact.getStaffId(), new LazyHeaders.Builder()
                .addHeader("Authorization", auth_token_type + " "+ auth_token_string)
                .build());

        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .into(button);
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
