package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.Models.DoctorObject;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RecyclerCreateMyTeamActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<DoctorObject> messageDataList;

    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapterCreateTeam adapter;


    //ListView usersListView;
    Context context;
    private Patient patient;

    private static final String TAG = "MyActivity";
    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_create_my_team);

        setTitle("Referring Doctor");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Intent b = getIntent();

        patient = b.getExtras().getParcelable("Patient");



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


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void clearData() {


        if (messageDataList !=null) {

            messageDataList.clear();

            adapter.notifyDataSetChanged();

        }
        else{

        }



    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()){
            clearData();
            return false;
        } else
        {
            callapi(newText);
//            final List<DoctorObject> filteredModelList = filter(messageDataList, newText);
//            adapter.setFilter(filteredModelList);
            return true;
        }

    }



//    private List<DoctorObject> filter(List<DoctorObject> models, String query) {
//        query = query.toLowerCase();
//
//        final List<DoctorObject> filteredModelList = new ArrayList<>();
//        for (DoctorObject model : models) {
//            final String text = model.getDocname().toLowerCase();
//            if (text.contains(query)) {
//                filteredModelList.add(model);
//            }
//        }
//        return filteredModelList;
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new CustomRecyclerAdapterCreateTeam(RecyclerCreateMyTeamActivity.this, messageDataList,patient);
                    mRecyclerView.setAdapter(adapter);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecyclerCreateMyTeamActivity.this, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(layoutManager);
                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };

    private void callapi(String newText){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();

        String credentials = "ezhu:Ccare@123";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Log.e("Test", auth);


        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);


        String url  = "https://staging.vitalengine.com/portal-api/api/user/referral/" +
                "getPhysicianSearchList?inVitalEngine=1&referralId=0&userId=" + userId + "&search=" + newText;


        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", auth_token_type + " "+ auth_token_string )
                .addHeader("user-tz", "-330")
                        //.addHeader("Content-Type", "application/json")
                .build();




        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.i(TAG, "call api error");
                Log.e("Volley", e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) {

                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    final String body = response.body().string();

                    JSONObject Jobject = new JSONObject(body);

                    //JSONObject sub = Jobject.getJSONObject("response");

                    JSONArray Jarray = Jobject.getJSONArray("response");
                    //Log.e("JSON", String.valueOf(sub));
                    Log.e("Array", String.valueOf(Jarray.length()));

                    //arrMessageData = new ArrayList<>();
                    messageDataList = new ArrayList<DoctorObject>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);


                        Log.e("Recycler", object.getString("user_id"));


                        DoctorObject msg3 = new DoctorObject();
                        String firstname = (String) object.get("display_name");
                        msg3.setDocname(firstname);
                        msg3.setDocLocation((String) object.get("city"));
                        msg3.setDocId((Integer) object.get("user_id"));

                        //getImage(String.valueOf(object.get("user_id")));
                        //msg3.setDocPic(image);
                        msg3.setDocspecialty((String) object.get("speciality_name"));




                        messageDataList.add(msg3);


                        handler.sendEmptyMessage(1);

                    }

                } catch (Exception e) {
                    Log.e("RecyclerVolley2", e.toString());
                }

            }


        });
    }

//    public void getImage(String id) throws IOException {
//
//        String credentials = "ezhu:Ccare@123";
//        String auth = "Basic "
//                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//
//        Log.e("Test", auth);
//
//
//        //SharedPreferences preferences = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
//
//        SharedPreferences settings = PreferenceManager
//                .getDefaultSharedPreferences(this);
//        String auth_token_string = settings.getString("token", ""/*default value*/);
//        String auth_token_type = settings.getString("tokenType", "");
//        String userId = settings.getString("userId", "");
//
//        Log.i("prefs", auth_token_type);
//
//
//
//
//
//
//        URL imgurl = new URL("https://staging.vitalengine.com/portal-api/" + id);
//        URLConnection conn = imgurl.openConnection();
//        conn.addRequestProperty("Authorization", auth_token_type + " "+ auth_token_string);
//        conn.connect();
//
//        InputStream in = conn.getInputStream();
//
//        Bitmap bmp = BitmapFactory.decodeStream(in);
//
//
//
//
//        image = bmp;
//
//
//    }

}


