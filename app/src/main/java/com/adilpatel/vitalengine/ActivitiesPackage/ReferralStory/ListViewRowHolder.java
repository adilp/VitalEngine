package com.adilpatel.vitalengine.ActivitiesPackage.ReferralStory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

/**
 * Created by Adil on 9/16/16.
 */
public class ListViewRowHolder extends RecyclerView.ViewHolder {

    public ImageView pic;
    public TextView person;

    public ListViewRowHolder(View itemView) {
        super(itemView);

        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
    }

    public void bind(MessageData messageModel) {
        this.pic.setImageResource(messageModel.getImage());
        this.person.setText(messageModel.getName());
    }
}
