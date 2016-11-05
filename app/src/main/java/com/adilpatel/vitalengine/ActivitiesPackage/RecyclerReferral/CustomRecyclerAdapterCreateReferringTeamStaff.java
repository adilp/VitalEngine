package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 9/16/16.
 */
public class CustomRecyclerAdapterCreateReferringTeamStaff extends RecyclerView.Adapter<CustomRecyclerAdapterCreateReferringTeamStaff.ViewHolder>{


private List<StaffObject> messageDataList;
private Context context;




// Pass in the contact array into the constructor
public CustomRecyclerAdapterCreateReferringTeamStaff(Context mainActivity, ArrayList<StaffObject> messageDataList) {
        this.messageDataList = messageDataList;
        context = mainActivity;

        }

// Easy access to the context object in the recyclerview
private Context getContext() {
        return context;
        }

@Override
public CustomRecyclerAdapterCreateReferringTeamStaff.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_create_team, parent, false);



        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context,messageDataList);
        return viewHolder;
        }


// Involves populating data into the item through holder
@Override
public void onBindViewHolder(CustomRecyclerAdapterCreateReferringTeamStaff.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final StaffObject contact = messageDataList.get(position);

        // Set item views based on your views and data model
        TextView person = viewHolder.person;
        person.setText(contact.getStaffname());
        ImageView image = viewHolder.pic;
    SharedPreferences settings = PreferenceManager
            .getDefaultSharedPreferences(context);
    String auth_token_string = settings.getString("token", ""/*default value*/);
    String auth_token_type = settings.getString("tokenType", "");
    String userId = settings.getString("userId", "");

    Log.i("prefs", auth_token_type);

    GlideUrl url = new GlideUrl("https://staging.vitalengine.com/portal-api/img/user/false/" + contact.getStaffId(), new LazyHeaders.Builder()
            .addHeader("Authorization", auth_token_type + " "+ auth_token_string)
            .build());

    Glide.with(context)
            .load(url)
            .centerCrop()
            .into(image);

    TextView specialty = viewHolder.specialty;
    specialty.setText(contact.getStaffSpecialty());

    TextView location = viewHolder.location;
    location.setText(contact.getStaffLocation());

    CheckBox selection = viewHolder.selected;

    selection.setChecked(contact.isChecked());

    viewHolder.selected.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox cb = (CheckBox) v;
            //int clickedPos = ((Integer) cb.getTag()).intValue();

            contact.setChecked(cb.isChecked());



        }
    });




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
    public CheckBox selected;
    public TextView specialty;
    public TextView location;

    ArrayList<StaffObject> message = new ArrayList<StaffObject>();
    Context ctx;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView, Context ctx, List<StaffObject> message) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        this.ctx = ctx;
        itemView.setOnClickListener(this);


        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
        this.message = (ArrayList<StaffObject>) message;
        this.selected = (CheckBox) itemView.findViewById(R.id.visibleButton);
        this.specialty = (TextView) itemView.findViewById(R.id.teamSpecialty);
        this.location = (TextView) itemView.findViewById(R.id.teamLocation);

    }


    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();
        StaffObject obj = message.get(position);


        Toast.makeText(ctx, "CLICKED " + obj.getStaffname(), Toast.LENGTH_SHORT).show();
//
//        Intent myIntent = new Intent(ctx, RecyclerCreateReferringTeamActivity.class);
//       myIntent.putParcelableArrayListExtra("myStaff", (ArrayList<? extends Parcelable>) myStaff);
//        myIntent.putExtra("myDoc", myDoc);
//        myIntent.putExtra("referringDoc", referringDoc);
//        //myIntent.putExtra("referringStaff", obj.getStaffname());
//        ctx.startActivity(myIntent);
    }
}}

