package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCreateReferringTeamStaffActivity extends AppCompatActivity {

    List<StaffObject> messageDataList = new ArrayList<StaffObject>();

    private CustomRecyclerAdapterCreateReferringTeamStaff adapter;

    RecyclerView mRecycerView;
    Context context;

    int myDoc;
    int myStaff;
    int referringDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_create_referring_team_staff);

        Intent b = getIntent();

        myDoc = b.getExtras().getInt("myDoc");
        myStaff = b.getExtras().getInt("myStaff");
        referringDoc = b.getExtras().getInt("referringDoc");


        //Toast.makeText(this, "DocId " + ref, Toast.LENGTH_SHORT).show();

        context = this;

        mRecycerView = (RecyclerView) findViewById(R.id.recyclerViewReferringTeamStaff);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycerView.setLayoutManager(linearLayoutManager);

        StaffObject msg1 = new StaffObject("adil");

        StaffObject msg2 = new StaffObject("ozair");

        messageDataList.add(msg1);
        messageDataList.add(msg2);

        adapter = new CustomRecyclerAdapterCreateReferringTeamStaff(this, (ArrayList<StaffObject>) messageDataList, myDoc,myStaff,referringDoc);
        mRecycerView.setAdapter(adapter);
    }
}
