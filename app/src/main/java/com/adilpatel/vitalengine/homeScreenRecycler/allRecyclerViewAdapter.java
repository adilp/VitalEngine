package com.adilpatel.vitalengine.homeScreenRecycler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.ActivitiesPackage.ChatStory.ChatlistActivity;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

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
        ViewHolder viewHolder = new ViewHolder(contactView, context, arrMessageData);
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
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);

        GlideUrl url = new GlideUrl("https://staging.vitalengine.com/portal-api/" + contact.getPhotoURL(), new LazyHeaders.Builder()
                .addHeader("Authorization", auth_token_type + " "+ auth_token_string)
                .build());

        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(image);
        TextView patient = viewHolder.patient;
        patient.setText(contact.getPatient());
        TextView title = viewHolder.allTitle;
        title.setText(contact.getSubject());




        TextView subject = viewHolder.allSubject;

//        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date parsed = null; // => Date is in UTC now
//        try {
//            parsed = sourceFormat.parse(contact.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        TimeZone tz = TimeZone.getTimeZone("America/Chicago");
//        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        destFormat.setTimeZone(tz);
//
//        String result = destFormat.format(parsed);


        TextView time = viewHolder.time;



        time.setText(contact.getTime());
//        if (formattedDate.contains(contact.getLastDate())){
//
//            time.setText(contact.getTime());
//        }
//
//        else {
//            time.setText(formattedDate);
//        }

//        if (contact.getLastDate().contains())


        if (contact.getType().contains("referral")) {
            subject.setText("Patient: ");

            title.setText(contact.getPatient());
            name.setText(contact.getName() + " -> " + contact.getToUser());

        }

        if (contact.getType().contains("conversation")){
            subject.setText("Subject: ");
        }
        if (contact.getType().contains("message")) {
            subject.setVisibility(View.GONE);
            title.setVisibility(View.GONE);

        }

       if  (contact.isRead() == 0){
           message.setTypeface(null, Typeface.BOLD);
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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView allName;
        ImageView allImage;
        TextView allMessage;
        TextView unreadCount;
        FrameLayout unreadCountBackground;
        TextView time;
        TextView allTitle;
        TextView allSubject;
        TextView patient;

        ArrayList<MessageData> message = new ArrayList<MessageData>();
        Context ctx;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context ctx, ArrayList<MessageData> message) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            allName = (TextView) itemView.findViewById(R.id.allName);
            allImage =(ImageView) itemView.findViewById(R.id.allImage);
            allMessage = (TextView) itemView.findViewById(R.id.allMessage);
            allTitle = (TextView) itemView.findViewById(R.id.allTitle);
            allSubject = (TextView) itemView.findViewById(R.id.allSubject);
            patient = (TextView) itemView.findViewById(R.id.allTitle);
            time = (TextView) itemView.findViewById(R.id.allTimestamp);
            itemView.setOnClickListener(this);
            this.message = message;
            this.ctx = ctx;

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
            myIntent.putExtra("typeOfActivity", obj.getType());
            //myIntent.putExtra("messagePerson", currentPerson);
            ctx.startActivity(myIntent);
        }
    }
}


