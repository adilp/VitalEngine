package com.adilpatel.vitalengine.ActivitiesPackage.Conversation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.Models.TeamObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;

public class MessageConfirmActivity extends AppCompatActivity {

    private RecyclerView ReferralTeamRecyclerlist;
    private RecyclerView myTeamRecyclerList;

    private ArrayList<StaffObject> myTeamList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_confirm);

        TeamObject me = new TeamObject();
        me.setStaffName("Adil");
        me.setStaffPic(R.drawable.msgthree);

        Intent b = getIntent();
        //ArrayList<TeamObject> list = b.getParcelableArrayListExtra("referralTeamStaff");
        TeamObject messageRecipient = b.getParcelableExtra("messagePerson");


//        Patient receivedPatient = b.getParcelableExtra("patient");
//        ArrayList<StaffObject> myTeamList = b.getParcelableArrayListExtra("MyTeamStaff");
//        DoctorObject referralTeamDoc = b.getParcelableExtra("referralTeamDoc");

//        myTeamList = new ArrayList<>();
//
//
//        myTeamRecyclerList = (RecyclerView)findViewById(R.id.recyclerViewMyTeam);
//        myTeamList.add(0,myDoc);
//        //list = StaffObject.createContactsList(20);
//        // Create adapter passing in the sample user data
//        RecyclerViewMessage adapter1 = new RecyclerViewMessage(this, myTeamList);
//        // Attach the adapter to the recyclerview to populate items
//        myTeamRecyclerList.setAdapter(adapter1);
//        // Set layout manager to position the items
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        myTeamRecyclerList.setLayoutManager(layoutManager);
    }
}
