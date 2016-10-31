package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.R;

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
        holder.pic.setImageBitmap(messageData.getDocPic());

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