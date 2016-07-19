package com.adilpatel.vitalengine.ActivitiesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.adilpatel.vitalengine.R;

public class newReferenceActivity extends AppCompatActivity  {

    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reference);

        next = (Button) findViewById(R.id.btn_next);



        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(newReferenceActivity.this, CreateMyTeamActivity.class);
                newReferenceActivity.this.startActivity(myIntent);
            }

        });

        //pushFragments(new refPatientFrag(), true, "patientFrag");

        //button = (Button)findViewById(R.id.mButtons);
        //frame = (FrameLayout)findViewById(R.id.newnewcontainer);

//        frame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(newReferenceActivity.this, "New Referral Button clicked", Toast.LENGTH_SHORT).show();
//                pushFragments(new CreateMyTeamFrag(),
//                        true, // true --> add Fragment in backstack else false
//                        "YourFragment");
//            }
//        });
    }

//    public void pushFragments(Fragment fragment, boolean shouldAdd, String tag) {
//        FragmentManager manager = getFragmentManager();//Use this line for FragmentManager , if you are in Activity
//        //FragmentManager manager = getActivity().getSupportFragmentManager();//Use this line for FragmentManager , if you are in Fragment
//        FragmentTransaction ft = manager.beginTransaction();
//
//        //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//Uncomment this line if you don't want to maintain Fragment Backsack
//        ft.replace(R.id.newnewcontainer, fragment, tag);
//        if (shouldAdd) {
//            ft.addToBackStack(tag); //add fragment in backstack
//        }
//        ft.commit();
//    }


}
