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
import android.widget.Toast;

import com.adilpatel.vitalengine.ListAdapters.CustomRecyclerAdapterCreateTeam;
import com.adilpatel.vitalengine.Models.MyTeamModel;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.expand.Ingredient;
import com.adilpatel.vitalengine.expand.Recipe;
import com.adilpatel.vitalengine.expand.RecipeAdapter;
import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateMyTeamActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<MyTeamModel> teamDataList = new ArrayList<MyTeamModel>();

    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapterCreateTeam adapter;
    private RecipeAdapter mAdapter;



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

        Ingredient beef = new Ingredient("beef");
        Ingredient cheese = new Ingredient("cheese");
        Ingredient salsa = new Ingredient("salsa");
        Ingredient tortilla = new Ingredient("tortilla");
        Ingredient ketchup = new Ingredient("ketchup");
        Ingredient bun = new Ingredient("bun");

        Recipe taco = new Recipe("taco", Arrays.asList(beef, cheese, salsa, tortilla));
        Recipe quesadilla = new Recipe("quesadilla", Arrays.asList(cheese, tortilla));
        Recipe burger = new Recipe("burger", Arrays.asList(beef, cheese, ketchup, bun));
        final List<Recipe> recipes = Arrays.asList(taco, quesadilla, burger);

        mAdapter = new RecipeAdapter(this, recipes);





        MyTeamModel msg1 = new MyTeamModel();
        msg1.setName("Anant Kharod (1245319599)");
        msg1.setImages(R.drawable.msgone);
        msg1.setSpecialty("Cardiology");
        msg1.setLocation("Chicago, IL");

        MyTeamModel msg2 = new MyTeamModel();
        msg2.setName("Adil Patel (1316019912)");
        msg2.setSpecialty("Dermatology");
        msg2.setLocation("Birmingham, AL");
        msg2.setImages(R.drawable.msgthree);


        teamDataList.add(msg1);
        teamDataList.add(msg2);

        Log.i(TAG, "Message: " + msg1);

        //arrMessageData.add(msg1);

        //adapter = new CustomRecyclerAdapterCreateTeam(this,teamDataList);

        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                Recipe expandedRecipe = recipes.get(position);


                Toast.makeText(CreateMyTeamActivity.this,
                        "Expand",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onListItemCollapsed(int position) {
                Recipe collapsedRecipe = recipes.get(position);


                Toast.makeText(CreateMyTeamActivity.this,
                        "Collapse",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);




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
                        adapter.setFilter(teamDataList);
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
        final List<MyTeamModel> filteredModelList = filter(teamDataList, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    private List<MyTeamModel> filter(List<MyTeamModel> models, String query) {
        query = query.toLowerCase();

        final List<MyTeamModel> filteredModelList = new ArrayList<>();
        for (MyTeamModel model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}
