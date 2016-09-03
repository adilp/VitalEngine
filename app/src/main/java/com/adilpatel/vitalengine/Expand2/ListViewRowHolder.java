package com.adilpatel.vitalengine.Expand2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;

/**
 * Created by Adil on 7/13/16.
 */
public class ListViewRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView pic;
    public TextView person;
    public TextView specialty;
    public TextView location;
    public ImageButton mParentDropDownArrow;

    public ListViewRowHolder(View itemView) {
        super(itemView);

        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
        this.specialty = (TextView) itemView.findViewById(R.id.teamSpecialty);
        this.location = (TextView) itemView.findViewById(R.id.teamLocation);
        //this.mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);


        itemView.setOnClickListener(this);
    }

    public void bind(MessageData messageModel) {
        this.pic.setImageResource(messageModel.getImage());
        this.person.setText(messageModel.getName());

    }

    public void onClick(View v) {

        Toast.makeText(v.getContext(),"Clicked",Toast.LENGTH_SHORT).show();

    }


}
