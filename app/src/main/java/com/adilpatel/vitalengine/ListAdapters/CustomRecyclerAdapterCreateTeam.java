package com.adilpatel.vitalengine.ListAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adilpatel.vitalengine.ListViewRowHolder;
import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adil on 7/13/16.
 */
public class CustomRecyclerAdapterCreateTeam extends RecyclerView.Adapter<ListViewRowHolder>{


    private List<MessageData> messageDataList;
    private Context context;

    public CustomRecyclerAdapterCreateTeam(Context context, List<MessageData> messageDataList){
        this.messageDataList = messageDataList;
        this.context = context;
    }


    @Override
    public ListViewRowHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_team_list_item, null);

        ListViewRowHolder holder = new ListViewRowHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewRowHolder holder, int position) {

        MessageData messageData = messageDataList.get(position);

        holder.person.setText(messageData.getName());
        holder.pic.setImageResource(messageData.getImage());
        holder.specialty.setText(messageData.getSubject());
        holder.location.setText(messageData.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageDataList.size();
    }

    public void setFilter(List<MessageData> messageModels) {
        messageDataList = new ArrayList<>();
        messageDataList.addAll(messageModels);
        notifyDataSetChanged();
    }
}
