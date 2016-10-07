package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

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

public class CreateMyTeamStaffActivity extends AppCompatActivity {

    List <StaffObject> messageDataList = new ArrayList<StaffObject>();
    private List<StaffObject> selectedMyStaff = new ArrayList<>();

    private CustomRecyclerAdapterCreateTeamStaff adapter;

    RecyclerView mRecycerView;
    Context context;

    int ref;

    Button next;

    Patient patient;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_my_team_staff);

        Intent b = getIntent();

        ref = b.getExtras().getInt("docId");
        patient = b.getExtras().getParcelable("Patient");


        Toast.makeText(this, "DocId " + ref , Toast.LENGTH_SHORT).show();

        context = this;

        mRecycerView = (RecyclerView) findViewById(R.id.recyclerViewMyTeamStaff);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycerView.setLayoutManager(linearLayoutManager);

        next = (Button) findViewById(R.id.ComfirmButton);

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

                Log.d("Selected Size", selectedMyStaff.size() + " ? ");
                Intent myIntent = new Intent(context, RecyclerCreateReferringTeamActivity.class);
            myIntent.putParcelableArrayListExtra("Staff", (ArrayList<? extends Parcelable>) selectedMyStaff);
            myIntent.putExtra("myDocId", ref);
                myIntent.putExtra("Patient",patient);
            context.startActivity(myIntent);
            }
        });


        callapi(ref);
    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new CustomRecyclerAdapterCreateTeamStaff(CreateMyTeamStaffActivity.this, messageDataList, ref);
                    mRecycerView.setAdapter(adapter);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(CreateMyTeamStaffActivity.this, LinearLayoutManager.VERTICAL, false);
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



