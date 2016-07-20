package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adilpatel.vitalengine.ListViewRowHolder;
import com.adilpatel.vitalengine.Models.MyTeamModel;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 7/13/16.
 */
public class CustomRecyclerAdapterCreateTeam extends RecyclerView.Adapter<ListViewRowHolder>{


    private List<MyTeamModel> teamDataList;
    private Context context;

    public CustomRecyclerAdapterCreateTeam(Context context, List<MyTeamModel> teamDataList){
        this.teamDataList = teamDataList;
        this.context = context;
    }


    @Override
    public ListViewRowHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_create_team, null);

        ListViewRowHolder holder = new ListViewRowHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewRowHolder holder, int position) {

        MyTeamModel messageData = teamDataList.get(position);

        holder.person.setText(messageData.getName());
        holder.pic.setImageResource(messageData.getImages());
        holder.specialty.setText(messageData.getSpecialty());
        holder.location.setText(messageData.getLocation());

    }

    @Override
    public int getItemCount() {
        return teamDataList.size();
    }

    public void setFilter(List<MyTeamModel> teamModels) {
        teamDataList = new ArrayList<>();
        teamDataList.addAll(teamModels);
        notifyDataSetChanged();
    }
}
