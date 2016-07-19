package com.adilpatel.vitalengine.ActivitiesPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.adilpatel.vitalengine.ListAdapters.CustomRecyclerAdapterCreateTeam;
import com.adilpatel.vitalengine.MessageData;
import com.adilpatel.vitalengine.R;

import java.util.ArrayList;
import java.util.List;

public class CreateMyTeamActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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




        MessageData msg1 = new MessageData();
        msg1.setName("Anant Kharod (1245319599)");
        msg1.setImage(R.drawable.msgone);
        msg1.setSubject("Cardiology");
        msg1.setMessage("Chicago, IL");

        MessageData msg2 = new MessageData();
        msg2.setName("Adil Patel (1316019912)");
        msg2.setSubject("Dermatology");
        msg2.setMessage("Birmingham, AL");
        msg2.setImage(R.drawable.msgthree);


        messageDataList.add(msg1);
        messageDataList.add(msg2);

        Log.i(TAG, "Message: " + msg1);

        //arrMessageData.add(msg1);

        adapter = new CustomRecyclerAdapterCreateTeam(this,messageDataList);
        mRecyclerView.setAdapter(adapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_team, menu);

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(messageDataList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return super.onCreateOptionsMenu(menu);



//        SearchManager searchManager = (SearchManager)
//                getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchMenuItem = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) searchMenuItem.getActionView();

//        searchView.setSearchableInfo(searchManager.
//                getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
        //searchView.setOnQueryTextListener(this);

        //return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<MessageData> filteredModelList = filter(messageDataList, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    private List<MessageData> filter(List<MessageData> models, String query) {
        query = query.toLowerCase();

        final List<MessageData> filteredModelList = new ArrayList<>();
        for (MessageData model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}
