package com.adilpatel.vitalengine.expand;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by Adil on 7/20/16.
 */
public class CrimeParentViewHolder extends ParentViewHolder {

    public ImageView pic;
    public TextView person;
    public TextView specialty;
    public TextView location;
    public ImageButton mParentDropDownArrow;

    public CrimeParentViewHolder(View itemView) {
        super(itemView);

        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
        this.specialty = (TextView) itemView.findViewById(R.id.teamSpecialty);
        this.location = (TextView) itemView.findViewById(R.id.teamLocation);
        this.mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }
}
