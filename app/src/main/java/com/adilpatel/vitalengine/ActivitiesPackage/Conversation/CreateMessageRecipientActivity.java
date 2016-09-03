package com.adilpatel.vitalengine.ActivitiesPackage.Conversation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Models.TeamObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

public class CreateMessageRecipientActivity extends AppCompatActivity {

    private List<TeamObject> teamObjects;
    private List<TeamObject> selectedObjects;

    private TeamObject currentPerson;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message_recipient);




        final ListView yourListView = (ListView) findViewById(R.id.createMessageRecipientListview);

        prepareListData();

// get data from the table by the ListAdapter
        CreateMessageRecipientListAdapter customAdapter = new CreateMessageRecipientListAdapter(this, teamObjects);

        yourListView .setAdapter(customAdapter);
        yourListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        selectedObjects = new ArrayList<>();

        yourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //TeamObject currentDoc = teamObjects.get(position);

                currentPerson = teamObjects.get(position);

                 Toast.makeText(getApplicationContext(),
                 "Group Clicked " + teamObjects.get(position).getStaffName(),
                 Toast.LENGTH_SHORT).show();


//                if (selectedObjects.contains(currentDoc)){
//
//                    selectedObjects.remove(currentDoc);
//                }
//
//                else {
//                    selectedObjects.add(currentDoc);
//                }


            }
        });

        nextButton = (Button)findViewById(R.id.nextbuttonTeam);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(CreateMessageRecipientActivity.this, MessageConfirmActivity.class);
                //myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
                //myIntent.putExtra("doc", selectedDoc);
                myIntent.putExtra("messagePerson", currentPerson);
                CreateMessageRecipientActivity.this.startActivity(myIntent);


            }
        });

    }





    private void prepareListData() {
        teamObjects = new ArrayList<>();



        TeamObject adil = new TeamObject();
        adil.setStaffPic(R.drawable.msgthree);
        adil.setStaffName("Adil");
        adil.setStaffLocation("Chicago");
        adil.setStaffSpecialty("Cardiology");

        TeamObject adil2 = new TeamObject();
        adil2.setStaffPic(R.drawable.msgone);
        adil2.setStaffName("Adil Patel");
        adil2.setStaffLocation("Chicago");
        adil2.setStaffSpecialty("Nurse");

        TeamObject adil3 = new TeamObject();
        adil3.setStaffPic(R.drawable.msgtwo);
        adil3.setStaffName("Adil A Patel");
        adil3.setStaffLocation("Chicago");
        adil3.setStaffSpecialty("PA");

        teamObjects.add(adil);
        teamObjects.add(adil2);
        teamObjects.add(adil3);





    }
}
