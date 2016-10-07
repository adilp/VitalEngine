package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.R;

public class newReferenceActivity extends AppCompatActivity  {

    Button next;
    EditText name;
    EditText DOB;
    EditText forField;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reference);

        next = (Button) findViewById(R.id.btn_next);
        name = (EditText)findViewById(R.id.newPatName);
        DOB = (EditText)findViewById(R.id.newPatDOB);
        forField = (EditText)findViewById(R.id.newPatFor);






        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Patient patient = new Patient();
                patient.setName(name.getText().toString());
                patient.setDOB(DOB.getText().toString());
                patient.setFor(forField.getText().toString());

                Intent myIntent = new Intent(newReferenceActivity.this, RecyclerCreateMyTeamActivity.class);
                myIntent.putExtra("Patient",patient);
                newReferenceActivity.this.startActivity(myIntent);
            }

        });

    }


}
