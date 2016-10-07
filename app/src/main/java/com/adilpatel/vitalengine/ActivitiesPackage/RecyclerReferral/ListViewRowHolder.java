package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

/**
 * Created by Adil on 9/16/16.
 */
public class ListViewRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView pic;
    public TextView person;
    CheckBox selected;
    Patient patient;

    Context ctx;
    ArrayList<DoctorObject> message = new ArrayList<DoctorObject>();

    public ListViewRowHolder(View itemView, Context ctx, ArrayList<DoctorObject> message, Patient patient) {
        super(itemView);

        this.pic = (ImageView) itemView.findViewById(R.id.teamImage);
        this.person = (TextView) itemView.findViewById(R.id.teamName);
        this.message = message;
        this.ctx = ctx;
        this.selected = (CheckBox) itemView.findViewById(R.id.visibleButton);
        this.patient = patient;

        itemView.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();

        DoctorObject obj = message.get(position);


        Toast.makeText(ctx, "CLICKED " + obj.getDocname(), Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(ctx, CreateMyTeamStaffActivity.class);
        //myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
        myIntent.putExtra("docId", obj.getDocId());
        myIntent.putExtra("Patient",patient);
        //myIntent.putExtra("messagePerson", currentPerson);
        ctx.startActivity(myIntent);

    }
}
