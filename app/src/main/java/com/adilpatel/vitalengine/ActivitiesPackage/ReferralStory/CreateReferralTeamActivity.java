package com.adilpatel.vitalengine.ActivitiesPackage.ReferralStory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Expand2.ExpandableListAdapter;
import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.Models.TeamObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateReferralTeamActivity extends AppCompatActivity {


    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    private List<DoctorObject> listDataDoc;
    private HashMap<DoctorObject, List<StaffObject>> listDataChildStaff;

    //private ArrayList<StaffObject> selectedItems = new ArrayList<>();
    private DoctorObject selectedDoc = new DoctorObject();

    List<StaffObject> staffGroup;
    List<StaffObject> selectedStaff;

    private Button nextButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral_team);

        /*
        Getting Patient data, referal team doc and their staff.
         */
        Intent b = getIntent();
        final ArrayList<StaffObject> list = b.getParcelableArrayListExtra("NAME");
        final DoctorObject receivedDoc = b.getParcelableExtra("doc");
        final Patient receivedPatient = b.getParcelableExtra("patient");

        /*
        End of getting data from previous activities
         */

        /*
        Testing purposes showing texts
         */
        String lists ="";
        for (int i=0; i < list.size(); i++){
           lists  =  list.get(i).getStaffname();
        }

//        Log.i("TAG", "list SIZE :" + list.size());

//        TextView text = (TextView) findViewById(R.id.sampleText);
//        TextView text2 = (TextView) findViewById(R.id.sampleText2);
//        TextView text3 = (TextView) findViewById(R.id.sampleText3);


        //Testing to see if staff object came through
        StringBuilder builder = new StringBuilder();
        for (StaffObject details : list) {
            builder.append(details.getStaffname() + "\n");
        }

//        text.setText(builder.toString());
//        text2.setText(receivedDoc.getDocname());
//        text3.setText(receivedPatient.getName());


        //text.setText(lists);

        Toast.makeText(getApplicationContext(),
                lists,
                Toast.LENGTH_SHORT).show();

        /*
        End of testing purposes
         */



        context = this;

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.CreateReferralTeamListView);

        // preparing list data
        prepareListData();


        listAdapter = new ExpandableListAdapter(this,  listDataDoc, listDataChildStaff);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataDoc.get(groupPosition),
                // Toast.LENGTH_SHORT).show();

                DoctorObject currentDoc = listDataDoc.get(groupPosition);

                selectedDoc = currentDoc;
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
//               if(groupPosition != previousGroup)
                expListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataDoc.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                //int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));


                DoctorObject currentDoc = listDataDoc.get(groupPosition);

                StaffObject currentStaff = listDataChildStaff.get(listDataDoc.get(groupPosition)).get(childPosition);

//                if (selectedItems.contains(currentStaff)){
//                    v.setBackgroundColor(Color.BLUE);
//                }
//
//                else {
//                    v.setBackgroundColor(Color.WHITE);
//                }




                if (selectedStaff.contains(currentStaff)){

                    selectedStaff.remove(currentStaff);
                }

                else {
                    selectedStaff.add(currentStaff);
                }
                //selectedItems.add(currentStaff);

                selectedDoc = currentDoc;
                String toastStaff = currentStaff.getStaffname();

                Toast.makeText(getApplicationContext(),
                        toastStaff,
                        Toast.LENGTH_SHORT).show();



                return true;
            }
        });



        /* End of regular expand listview

         */


        nextButton = (Button) findViewById(R.id.nextbuttonReferralTeam);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(CreateReferralTeamActivity.this, ReferralConfirmActivity.class);
                myIntent.putParcelableArrayListExtra("referralTeamStaff", (ArrayList<? extends Parcelable>) selectedStaff);

                myIntent.putParcelableArrayListExtra("MyTeamStaff", list);
                myIntent.putExtra("referralTeamDoc", selectedDoc);

                myIntent.putExtra("MyTeamDoc", receivedDoc);
                myIntent.putExtra("patient", receivedPatient);
                CreateReferralTeamActivity.this.startActivity(myIntent);


            }
        });
    }


    /*
    * Preparing the list data regular expanded listview
    */
    private void prepareListData() {
        listDataDoc = new ArrayList<>();
        listDataChildStaff = new HashMap<>();

        ArrayList<TeamObject> all = new ArrayList<>();


        DoctorObject adil = new DoctorObject(" Patel");
        adil.setDocPic(R.drawable.msgthree);
        adil.setDocLocation("Chicago");
        adil.setDocspecialty("Cardiology");

        DoctorObject adil2 = new DoctorObject(" AP");
        adil2.setDocPic(R.drawable.msgone);
        adil2.setDocLocation("Chicago");
        adil2.setDocspecialty("Cardiology");

        listDataDoc.add(adil2);

        listDataDoc.add(adil);

        staffGroup = new ArrayList<>();
        List<StaffObject> staffGroup2 = new ArrayList<>();
        selectedStaff = new ArrayList<>();

        StaffObject cindy = new StaffObject("Phillip Johnson2");
        cindy.setStaffPic(R.drawable.msgone);
        cindy.setStaffLocation("Chicago");
        cindy.setStaffSpecialty("Nurse");
        cindy.setStaffname("Phillip Johnson2");

        StaffObject bettina = new StaffObject("Mustafa Ahmed");
        bettina.setStaffname("Mustafa Ahmed");
        bettina.setStaffPic(R.drawable.msgtwo);
        bettina.setStaffSpecialty("PA");
        bettina.setStaffLocation("Chicago");

        StaffObject cindy1 = new StaffObject("Phillip Johnson");
        cindy1.setStaffPic(R.drawable.msgone);
        cindy1.setStaffLocation("Chicago");
        cindy1.setStaffSpecialty("Nurse");
        cindy1.setStaffname("Phillip Johnson");

        StaffObject bettina1 = new StaffObject("Mustafa");
        bettina1.setStaffname("Mustafa");
        bettina1.setStaffPic(R.drawable.msgtwo);
        bettina1.setStaffSpecialty("PA");
        bettina1.setStaffLocation("Chicago");

        staffGroup.add(cindy);
        staffGroup.add(bettina);
        staffGroup.add(cindy1);
        staffGroup.add(bettina1);

        selectedStaff.add(cindy);
        selectedStaff.add(bettina);
        selectedStaff.add(cindy1);
        selectedStaff.add(bettina1);



        StaffObject cindy2 = new StaffObject("Cindy");
        cindy2.setStaffPic(R.drawable.msgone);
        cindy2.setStaffLocation("Chicago");
        cindy2.setStaffSpecialty("Nurse");
        cindy2.setStaffname("Cindy G");

        StaffObject bettina2 = new StaffObject("Bettina");
        bettina2.setStaffname("Bettina");
        bettina2.setStaffPic(R.drawable.msgtwo);
        bettina2.setStaffSpecialty("PA");
        bettina2.setStaffLocation("Chicago");

        staffGroup2.add(cindy2);
        staffGroup2.add(bettina2);


        listDataChildStaff.put(listDataDoc.get(0), staffGroup);
        listDataChildStaff.put(listDataDoc.get(1), staffGroup2);
        //selectedDoc = listDataDoc.get(0);


    }

    private void newArray(){

         //values = listDataChildStaff.values().toArray(new StaffObject[listDataChildStaff.size()]);



        //listDataChildStaff.values().toArray(new SomeObject[0]);
    }
}
