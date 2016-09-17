package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

public class CreateMyTeamStaffActivity extends AppCompatActivity {

    List <StaffObject> messageDataList = new ArrayList<StaffObject>();

    private CustomRecyclerAdapterCreateTeamStaff adapter;

    RecyclerView mRecycerView;
    Context context;

    int ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_team_staff);

        Intent b = getIntent();

        ref = b.getExtras().getInt("docId");


        Toast.makeText(this, "DocId " + ref , Toast.LENGTH_SHORT).show();

        context = this;

        mRecycerView = (RecyclerView) findViewById(R.id.recyclerViewMyTeamStaff);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycerView.setLayoutManager(linearLayoutManager);

        StaffObject msg1 = new StaffObject("adil");
        msg1.setStaffId(1);

        StaffObject msg2 = new StaffObject("ozair");
        msg1.setStaffId(2);

        messageDataList.add(msg1);
        messageDataList.add(msg2);

        adapter = new CustomRecyclerAdapterCreateTeamStaff(this, (ArrayList<StaffObject>) messageDataList, ref);
        mRecycerView.setAdapter(adapter);

    }
}
