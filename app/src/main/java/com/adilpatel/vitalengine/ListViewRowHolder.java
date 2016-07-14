package com.adilpatel.vitalengine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Adil on 7/13/16.
 */
public class ListViewRowHolder extends RecyclerView.ViewHolder {

    protected ImageView pic;
    protected TextView person;

    public ListViewRowHolder(View itemView) {
        super(itemView);

        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
    }
}
