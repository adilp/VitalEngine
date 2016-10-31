package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerConversation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral.CustomRecyclerAdapterCreateReferringTeamStaff;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
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

public class RecyclerAddOtherTeamStaff extends AppCompatActivity {

    List<StaffObject> messageDataList = new ArrayList<StaffObject>();
    private List<StaffObject> selectedMyStaff;

    private CustomRecyclerAdapterCreateReferringTeamStaff adapter;

    RecyclerView mRecycerView;
    Context context;

    List<StaffObject> myStaff;

    Button next;
    Patient patient;
    Button add;


    int myDoc;

    int referringDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_create_referring_team_staff);

        Intent b = getIntent();

        myStaff = new ArrayList<>();

        myDoc = b.getExtras().getInt("myDoc");
        myStaff = b.getExtras().getParcelableArrayList("Staff");
        referringDoc = b.getExtras().getInt("referringDoc");
        patient = b.getExtras().getParcelable("Patient");

        selectedMyStaff = new ArrayList<>();

        //Toast.makeText(this, "DocId " + ref, Toast.LENGTH_SHORT).show();

        context = this;

        mRecycerView = (RecyclerView) findViewById(R.id.recyclerViewReferringTeamStaff);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycerView.setLayoutManager(linearLayoutManager);

//        add = (Button)findViewById(R.id.AddButton);
//
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(context, "ADDDDDDDD", Toast.LENGTH_SHORT).show();
////                Intent myIntent = new Intent(context, RecyclerAddOtherTeam.class);
////                myIntent.putParcelableArrayListExtra("myStaff", (ArrayList<? extends Parcelable>) myStaff);
////                myIntent.putExtra("myDoc", myDoc);
////                myIntent.putExtra("referringDoc", referringDoc);
////                myIntent.putParcelableArrayListExtra("refStaff", (ArrayList<? extends Parcelable>) selectedMyStaff);
////                myIntent.putExtra("Patient",patient);
////                context.startActivity(myIntent);
//            }
//        });


        next = (Button)findViewById(R.id.ComfirmButtonReferring);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0 ; i<messageDataList.size(); i++){
                    StaffObject temp = messageDataList.get(i);

                    if (temp.isChecked() == true){
                        selectedMyStaff.add(temp);

                    }

                    else if (temp.isChecked() == false){
                        selectedMyStaff.remove(temp);
                    }

                }


                Toast.makeText(context, "NEXT", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(context, RecyclerConfirmConvActivity.class);
                myIntent.putParcelableArrayListExtra("myStaff", (ArrayList<? extends Parcelable>) myStaff);
                myIntent.putExtra("myDoc", myDoc);
                myIntent.putExtra("referringDoc", referringDoc);
                myIntent.putParcelableArrayListExtra("refStaff", (ArrayList<? extends Parcelable>) selectedMyStaff);
                myIntent.putExtra("Patient",patient);
                //myIntent.putExtra("refStaff", (Parcelable) selectedMyStaff);
                context.startActivity(myIntent);
            }
        });





        callapi(referringDoc);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new CustomRecyclerAdapterCreateReferringTeamStaff(RecyclerAddOtherTeamStaff.this, (ArrayList<StaffObject>) messageDataList);
                    mRecycerView.setAdapter(adapter);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecyclerAddOtherTeamStaff.this, LinearLayoutManager.VERTICAL, false);
                    mRecycerView.setLayoutManager(layoutManager);
                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };
    private void callapi(int ref){

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
                "getAssistants?isNonVitalUser=0&physicianUserId="+ ref + "&userId="+ userId;


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
                    messageDataList = new ArrayList<StaffObject>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);


                        String firstname = (String) object.get("fullName");
                        StaffObject msg3 = new StaffObject(firstname);
                        msg3.setStaffPic(R.drawable.msgone);
                        msg3.setStaffSpecialty((String) object.get("specialityName"));
                        msg3.setStaffId((Integer) object.get("Id"));



                        messageDataList.add(msg3);


                        handler.sendEmptyMessage(1);

                    }

                } catch (Exception e) {
                    Log.e("RecyclerVolley2", e.toString());
                }

            }


        });
    }

}
