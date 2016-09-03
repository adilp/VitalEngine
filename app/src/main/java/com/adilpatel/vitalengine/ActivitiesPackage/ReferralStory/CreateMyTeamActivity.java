package com.adilpatel.vitalengine.ActivitiesPackage.ReferralStory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.Expand2.ExpandableListAdapter;
import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateMyTeamActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //Passing Data to next Activity
    private ArrayList<StaffObject> selectedItems = new ArrayList<>();
    private DoctorObject selectedDoc = new DoctorObject();
    private Patient patient;

    List<StaffObject> staffGroup;
    List<StaffObject> selectedStaff;

    //Used for Filtering
    private List<DoctorObject> teamDataList = new ArrayList<>();

    /*
    * RegListView expand variables
    * */

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;

    //Mapping for Doctors and their staff
    private List<DoctorObject> listDataDoc;
    private HashMap<DoctorObject, List<StaffObject>> listDataChildStaff;


    private Button nextButton;
    Context context;
    CheckBox chc;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_team);


/*
    Getting patient info from last screen
 */
        Intent b = getIntent();

        patient = b.getExtras().getParcelable("Patient");

        //Display a toast for testing purposes
        Toast.makeText(getApplicationContext(),
                patient.getName(),
                Toast.LENGTH_SHORT).show();

/*
 End Getting patient info from last screen
 */
        context = this;


        /* Regular Expand listview

         */

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.newTeamListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this,  listDataDoc, listDataChildStaff);

        // setting list adapter
        expListView.setAdapter(listAdapter);

       // expListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

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

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataDoc.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();


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


                DoctorObject currentDoc = listDataDoc.get(groupPosition);

                StaffObject currentStaff = listDataChildStaff.get(listDataDoc.get(groupPosition)).get(childPosition);
                chc = (CheckBox)findViewById(R.id.checkBoxChild);



                if (selectedStaff.contains(currentStaff)){

                    selectedStaff.remove(currentStaff);
                    chc.setChecked(false);
                }

                else {
                    selectedStaff.add(currentStaff);
                    chc.setChecked(true);
                }



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



        nextButton = (Button) findViewById(R.id.nextbuttonTeam);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(CreateMyTeamActivity.this, CreateReferralTeamActivity.class);
                myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
                myIntent.putExtra("doc", selectedDoc);
                myIntent.putExtra("patient", patient);
                CreateMyTeamActivity.this.startActivity(myIntent);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_team, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final MenuItem next = menu.findItem(R.id.next);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        TextView text = (TextView) MenuItemCompat.getActionView(next);

//        text.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent myIntent = new Intent(CreateMyTeamActivity.this, CreateReferralTeamActivity.class);
//                myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
//                myIntent.putExtra("doc", selectedDoc);
//                myIntent.putExtra("patient", patient);
//                CreateMyTeamActivity.this.startActivity(myIntent);
//
//
//            }
//        });

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        //adapter.setFilter(teamDataList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return super.onCreateOptionsMenu(menu);


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<DoctorObject> filteredModelList = filter(listDataDoc, newText);
        //adapter.setFilter(filteredModelList);
        return true;
    }

    private List<DoctorObject> filter(List<DoctorObject> models, String query) {
        query = query.toLowerCase();

        final List<DoctorObject> filteredModelList = new ArrayList<>();
        for (DoctorObject model : models) {
            final String text = model.getDocname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    /*
     * Preparing the list data regular expanded listview
     */
    private void prepareListData() {
        listDataDoc = new ArrayList<>();
        staffGroup = new ArrayList<>();


        listDataChildStaff = new HashMap<>();



        DoctorObject adil = new DoctorObject("Adil Patel");
        adil.setDocPic(R.drawable.msgthree);
        adil.setDocLocation("Chicago");
        adil.setDocspecialty("Cardiology");

        listDataDoc.add(adil);


        selectedStaff = new ArrayList<>();

        StaffObject cindy = new StaffObject("Cindy");
        cindy.setStaffPic(R.drawable.msgone);
        cindy.setStaffLocation("Chicago");
        cindy.setStaffSpecialty("Nurse");
        cindy.setStaffname("Cindy G");

        StaffObject bettina = new StaffObject("Bettina");
        bettina.setStaffname("Bettina");
        bettina.setStaffPic(R.drawable.msgtwo);
        bettina.setStaffSpecialty("PA");
        bettina.setStaffLocation("Chicago");

        staffGroup.add(cindy);
        staffGroup.add(bettina);


        selectedStaff.add(cindy);
        selectedStaff.add(bettina);

        listDataChildStaff.put(listDataDoc.get(0),staffGroup);
        selectedDoc = listDataDoc.get(0);


    }



//attribute staff object

}
