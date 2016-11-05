package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

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

import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 9/16/16.
 */
public class CustomRecyclerAdapterCreateTeam extends RecyclerView.Adapter<ListViewRowHolder> {

    private List<DoctorObject> messageDataList;
    private Context context;
    private Patient patient;

    public CustomRecyclerAdapterCreateTeam(Context context, List<DoctorObject> messageDataList, Patient patient) {
        this.messageDataList = messageDataList;
        this.context = context;
        this.patient = patient;
    }


    @Override
    public ListViewRowHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_create_team, null);

        ListViewRowHolder holder = new ListViewRowHolder(v, context, (ArrayList<DoctorObject>) messageDataList, patient);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewRowHolder holder, int position) {

        DoctorObject messageData = messageDataList.get(position);

        holder.person.setText(messageData.getDocname());
        //holder.pic.setImageResource(messageData.getDocPic());
        ImageView image = holder.pic;
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);

        GlideUrl url = new GlideUrl("https://staging.vitalengine.com/portal-api/img/user/false/" + messageData.getDocId(), new LazyHeaders.Builder()
                .addHeader("Authorization", auth_token_type + " "+ auth_token_string)
                .build());

        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(image);

        TextView specialty = holder.specialty;
        specialty.setText(messageData.getDocspecialty());

        TextView location = holder.location;
        location.setText(messageData.getDocLocation());

        holder.selected.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return messageDataList.size();
    }

    public void setFilter(List<DoctorObject> messageModels) {
        messageDataList = new ArrayList<>();
        messageDataList.addAll(messageModels);
        notifyDataSetChanged();
    }
}