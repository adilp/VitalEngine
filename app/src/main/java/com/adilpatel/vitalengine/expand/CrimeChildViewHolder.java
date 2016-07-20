package com.adilpatel.vitalengine.expand;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.R;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Adil on 7/20/16.
 */
public class CrimeChildViewHolder extends ChildViewHolder {

    public ImageView picChild;
    public TextView personChild;
    public TextView specialtyChild;
    public TextView locationChild;


    public CrimeChildViewHolder(View itemView) {
        super(itemView);


        this.picChild = (ImageView) itemView.findViewById(R.id.teamImageChild);
        this.personChild = (TextView) itemView.findViewById(R.id.teamNameChild);
        this.specialtyChild = (TextView) itemView.findViewById(R.id.teamSpecialtyChild);
        this.locationChild = (TextView) itemView.findViewById(R.id.teamLocationChild);
    }
}
