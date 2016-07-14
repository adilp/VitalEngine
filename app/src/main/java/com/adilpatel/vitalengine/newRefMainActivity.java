package com.adilpatel.vitalengine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class newRefMainActivity extends AppCompatActivity implements newRefMainActivityFragment.nextScreenCallback {

    //FragmentManager manager = getFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ref_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                pushFragments(new CreateMyTeamFrag(),
                        true, // true --> add Fragment in backstack else false
                        "YourFragment");
            }
        });


    }

    public void addFrag(){

        CreateMyTeamFrag f1 = new CreateMyTeamFrag();
       // FragmentTransaction transaction = manager.beginTransaction();
       // transaction.replace(R.id.container1, f1, "A");
        // transaction.commit();


    }

    public void pushFragments(Fragment fragment, boolean shouldAdd, String tag) {
        FragmentManager manager = getFragmentManager();//Use this line for FragmentManager , if you are in Activity
        //FragmentManager manager = getActivity().getSupportFragmentManager();//Use this line for FragmentManager , if you are in Fragment
        FragmentTransaction ft = manager.beginTransaction();

        //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);//Uncomment this line if you don't want to maintain Fragment Backsack
        ft.replace(R.id.fakeContainer, fragment, tag);
        if (shouldAdd) {
            ft.addToBackStack(tag); //add fragment in backstack
        }
        ft.commit();
    }

    @Override
    public void nextButtonClicked(int flag) {

    }
}
