package com.adilpatel.vitalengine.ActivitiesPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.adilpatel.vitalengine.ListAdapters.CustomRecyclerAdapterCreateTeam;
import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

public class CreateMyTeamActivity extends AppCompatActivity {

    List<MessageData> messageDataList = new ArrayList<MessageData>();

    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapterCreateTeam adapter;


    ListView usersListView;
    Context context;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_team);

        context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


//        usersListView = (ListView) findViewById(R.id.newTeamListView);

        //ArrayList<MessageData> arrMessageData = new ArrayList<MessageData>();

        MessageData msg1 = new MessageData();
        msg1.setName("Anant Kharod");
        msg1.setImage(R.drawable.msgone);

        MessageData msg2 = new MessageData();
        msg2.setName("Adil");
        msg2.setImage(R.drawable.msgthree);

        messageDataList.add(msg1);
        messageDataList.add(msg2);

        Log.i(TAG, "Message: " + msg1);

        //arrMessageData.add(msg1);

            CustomRecyclerAdapterCreateTeam adapter = new CustomRecyclerAdapterCreateTeam(this,messageDataList);
        mRecyclerView.setAdapter(adapter);

//        CustomAdapterCreateTeam adapter = new CustomAdapterCreateTeam( this, arrMessageData);
//        usersListView.setAdapter(adapter);


    }


}
