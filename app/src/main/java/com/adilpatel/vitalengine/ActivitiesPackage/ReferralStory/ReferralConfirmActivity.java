package com.adilpatel.vitalengine.ActivitiesPackage.ReferralStory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.ListAdapters.ReferralConfirmAdapter;
import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.Expand2.RecyclerViewAdapter;

import java.util.ArrayList;

public class ReferralConfirmActivity extends AppCompatActivity {


    ArrayList<StaffObject> currentStaff;


    //ReferralConfirmAdapter adapter;

    TextView patientName;
    TextView patientDob;
    TextView patientFor;
    ImageView patientImage;

    private ReferralConfirmAdapter listAdapter;
    //private TwoWayView lvTest;
    //private ListView lvTest;
    private RecyclerView ReferralTeamRecyclerlist;
    private RecyclerView myTeamRecyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_confirm);



        /*
        Receiving data from past activity
         */
        Intent b = getIntent();
        ArrayList<StaffObject> list = b.getParcelableArrayListExtra("referralTeamStaff");
        DoctorObject myTeamDoc = b.getParcelableExtra("MyTeamDoc");
        Patient receivedPatient = b.getParcelableExtra("patient");
        ArrayList<StaffObject> myTeamList = b.getParcelableArrayListExtra("MyTeamStaff");
        DoctorObject referralTeamDoc = b.getParcelableExtra("referralTeamDoc");


        Log.i("TAG", "list SIZE :" + list.size());

        //Referral Team doctor
        StaffObject referralDoc = new StaffObject(referralTeamDoc.getDocname());
        referralDoc.setStaffLocation(referralTeamDoc.getDocLocation());
        referralDoc.setStaffPic(referralTeamDoc.getDocPic());
        referralDoc.setStaffSpecialty(referralTeamDoc.getDocspecialty());

        //My Team doctor
        StaffObject myDoc = new StaffObject(myTeamDoc.getDocname());
        myDoc.setStaffLocation(myTeamDoc.getDocLocation());
        myDoc.setStaffPic(myTeamDoc.getDocPic());
        myDoc.setStaffSpecialty(myTeamDoc.getDocspecialty());

        /*
        end
         */



        String lists ="";
        for (int i=0; i < myTeamList.size(); i++){
            lists  =  myTeamList.get(i).getStaffname();
        }

        Toast.makeText(getApplicationContext(),
                myTeamDoc.getDocname(),
                Toast.LENGTH_SHORT).show();

                //lvTest = (TwoWayView) findViewById(R.id.MyTeamItems);
//
                //lvTest = (ListView)findViewById(R.id.MyTeamItems);
                listAdapter = new ReferralConfirmAdapter(this, list);

        //lvTest.setAdapter(listAdapter);




        ArrayList<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");

        ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, items);



        /*
        Patient stuff
         */

        patientName = (TextView)findViewById(R.id.referralConfirmPatientName);
        patientDob = (TextView)findViewById(R.id.referralConfirmDOB);
        patientFor = (TextView)findViewById(R.id.referralComfirmFor);
        patientImage = (ImageView)findViewById(R.id.referralConfirmPatientImage);

        patientName.setText(receivedPatient.getName());
        patientDob.setText(receivedPatient.getDOB());
        patientFor.setText(receivedPatient.getFor());
        patientImage.setImageResource(R.drawable.group);


        /*
        RecyclerView My Team
         */

        myTeamRecyclerList = (RecyclerView)findViewById(R.id.recyclerViewMyTeam);
        myTeamList.add(0,myDoc);
        //list = StaffObject.createContactsList(20);
        // Create adapter passing in the sample user data
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this, myTeamList);
        // Attach the adapter to the recyclerview to populate items
        myTeamRecyclerList.setAdapter(adapter1);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myTeamRecyclerList.setLayoutManager(layoutManager);

        /*
        End
         */



        /*
        RecyclerView Referral Team
         */

        ReferralTeamRecyclerlist = (RecyclerView)findViewById(R.id.recyclerViewReferralTeam);
        list.add(0,referralDoc);
        //list = StaffObject.createContactsList(20);
        // Create adapter passing in the sample user data
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        // Attach the adapter to the recyclerview to populate items
        ReferralTeamRecyclerlist.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ReferralTeamRecyclerlist.setLayoutManager(layoutManager1);
        // That's all!


        /*
        End
         */



    }
}
