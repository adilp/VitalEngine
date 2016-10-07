package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.ActivitiesPackage.LoginActivity;
import com.adilpatel.vitalengine.ActivitiesPackage.MainActivity;
import com.adilpatel.vitalengine.Expand2.RecyclerViewAdapter;
import com.adilpatel.vitalengine.ListAdapters.ReferralConfirmAdapter;
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Adil on 9/16/16.
 */
public class RecyclerReferralConfirmActivity extends AppCompatActivity {


    List<StaffObject> currentStaff; //= new ArrayList<>();


    TextView patientName;
    TextView patientDob;
    TextView patientFor;
    ImageView patientImage;
    EditText refMessage;
    Button refSendButton;


    private ReferralConfirmAdapter listAdapter;
    private RecyclerView ReferralTeamRecyclerlist;
    private RecyclerView myTeamRecyclerList;


    int mydoc ;
    int referringDoc ;
    List<StaffObject> referringStaff; //= new ArrayList<>();

    RecyclerViewAdapter adapter1;
    RecyclerViewAdapter adapter;
    Patient patient;
    String userId;

    JSONArray refParticipantsArray = new JSONArray();
    JSONArray recParticipantsArray = new JSONArray();

    JSONObject referralJson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_referral_confirm);



        /*
        Receiving data from past activity
         */
        Intent b = getIntent();


        mydoc = b.getExtras().getInt("myDoc");
        currentStaff = new ArrayList<>();
        currentStaff = b.getExtras().getParcelableArrayList("myStaff");
        referringDoc = b.getExtras().getInt("referringDoc");
        referringStaff = new ArrayList<>();
        referringStaff = b.getExtras().getParcelableArrayList("refStaff");
        patient = b.getExtras().getParcelable("Patient");

        getDocApi(referringDoc, true);
        getDocApi(mydoc, false);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
         userId = settings.getString("userId", "");

        /*
        end
         */


        /*
        Patient stuff
         */

        patientName = (TextView)findViewById(R.id.referralConfirmPatientName);
        patientDob = (TextView)findViewById(R.id.referralConfirmDOB);
        patientFor = (TextView)findViewById(R.id.referralComfirmFor);
        patientImage = (ImageView)findViewById(R.id.referralConfirmPatientImage);
        refMessage = (EditText)findViewById(R.id.refMessageBodyField);
        refSendButton = (Button)findViewById(R.id.refSendButton);

        patientName.setText(patient.getName());
        patientDob.setText(patient.getDOB());
        patientFor.setText(patient.getFor());
        patientImage.setImageResource(R.drawable.group);









        refSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable messageText = refMessage.getText();
                jsonBody(messageText);
                messageText.clear();
                sendReferral(referralJson);
                Log.e("ReferralSendingJson", referralJson.toString());

                Intent myIntent = new Intent(RecyclerReferralConfirmActivity.this, MainActivity.class);
                RecyclerReferralConfirmActivity.this.startActivity(myIntent);`

            }
        });

    }

    private void jsonBody(Editable messageText){

        refParticipantsArray = new JSONArray();
        recParticipantsArray = new JSONArray();


        for (int i = 0; i< currentStaff.size(); i++) {
            try {
                JSONObject refferralParticipants = new JSONObject();
                refferralParticipants.put("userId", currentStaff.get(i).getStaffId());
                refferralParticipants.put("specialityName", currentStaff.get(i).getStaffSpecialty());
                refferralParticipants.put("fullName", currentStaff.get(i).getStaffname());
                refferralParticipants.put("photo", "img/user/false/"+ currentStaff.get(i).getStaffId());
                refferralParticipants.put("practiceName", "");
                refferralParticipants.put("practiceId", 1);
                refParticipantsArray.put(refferralParticipants);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i< referringStaff.size(); i++) {
            try {
                JSONObject receivingParticipants = new JSONObject();


                receivingParticipants.put("practiceName", "");
                receivingParticipants.put("specialityName", referringStaff.get(i).getStaffSpecialty());
                receivingParticipants.put("npiNumber", "");
                receivingParticipants.put("fullName", referringStaff.get(i).getStaffname());
                receivingParticipants.put("photo", "img/user/false/"+ referringStaff.get(i).getStaffId());
                receivingParticipants.put("Id", referringStaff.get(i).getStaffId());
                receivingParticipants.put("userType", "");
                receivingParticipants.put("type", "");
                receivingParticipants.put("isNonVitalUser", "");
                receivingParticipants.put("userId", referringStaff.get(i).getStaffId());
                receivingParticipants.put("specialityId", referringStaff.get(i).getStaffId());
                recParticipantsArray.put(receivingParticipants);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONArray Empty1 = new JSONArray();
        JSONArray Empty2 = new JSONArray();
        JSONArray Empty3 = new JSONArray();
        JSONArray Empty4 = new JSONArray();
        JSONArray Empty5 = new JSONArray();

        referralJson = new JSONObject();
        try {
            referralJson.put("userId", userId);
            referralJson.put("patientName", patient.getName());
            referralJson.put("patientDob", patient.getDOB());
            referralJson.put("referralFor", patient.getFor());
            referralJson.put("patientPhoto", "");
            referralJson.put("message", messageText);
            referralJson.put("referralParticipants", refParticipantsArray);
            referralJson.put("receivingParticipants", recParticipantsArray);
            referralJson.put("otherParticipants", Empty1);
            referralJson.put("dicomImages", Empty2);
            referralJson.put("referralEvents", Empty3);
            referralJson.put("referralTodo", Empty4);
            referralJson.put("referralTags", Empty5);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    myTeamRecyclerList = (RecyclerView)findViewById(R.id.recyclerViewMyTeam);
                    //myTeamList.add();
                    //list = StaffObject.createContactsList(20);
                    // Create adapter passing in the sample user data
                    adapter1 = new RecyclerViewAdapter(RecyclerReferralConfirmActivity.this, currentStaff);
                    // Attach the adapter to the recyclerview to populate items
                    myTeamRecyclerList.setAdapter(adapter1);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecyclerReferralConfirmActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    myTeamRecyclerList.setLayoutManager(layoutManager);



        /*
        End
         */



        /*
        RecyclerView Referral Team
         */

                    ReferralTeamRecyclerlist = (RecyclerView)findViewById(R.id.recyclerViewReferralTeam);
                    //list.add(0,referralDoc);

                    // Create adapter passing in the sample user data
                    adapter = new RecyclerViewAdapter(RecyclerReferralConfirmActivity.this, referringStaff);
                    // Attach the adapter to the recyclerview to populate items
                    ReferralTeamRecyclerlist.setAdapter(adapter);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager1
                            = new LinearLayoutManager(RecyclerReferralConfirmActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    ReferralTeamRecyclerlist.setLayoutManager(layoutManager1);
                    // That's all!


        /*
        End
         */



                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };

    public void sendReferral(JSONObject referralJson){


        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();


        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();



        String url = "https://staging.vitalengine.com/portal-api/api/user/referral/save";

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

         MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, String.valueOf(referralJson));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", auth_token_type + " " + auth_token_string)
                .addHeader("user-tz", "-330")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.i(TAG, "call api error");
                Log.e("Volley", e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response)  {

                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    final String body = response.body().string();
                    Log.e("VolleyAuth", "Success!");

                    JSONObject Jobject = new JSONObject(body);


                    //JSONObject sub = Jobject.getJSONObject("response");

                    Log.e("USERAPI", String.valueOf(Jobject));




                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e){
                    Log.e("VolleyAuth", e.toString());
                }

            }


        });
    }


    public void getDocApi (int doc, final boolean flag){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();

        String credentials = "ezhu:Ccare@123";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Log.e("Test", auth);


        //SharedPreferences preferences = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);


        //String url = "https://randomuser.me/api/";
        String url  = "https://staging.vitalengine.com/portal-api/api/user/quickinfo?userId=" + doc;

        //String url = "https://staging.vitalengine.com/portal-api/api/login/getUserDetails?userName=ezhu";

        //String url = "http://192.168.1.49:8888/portal-api/api/login/getUserDetails?userName=ezhu";

        //String url = "http://httpbin.org/basic-auth/user/passwd";
        //String url = "http://10.0.2.2:3000/response";

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
            public void onResponse(Call call, okhttp3.Response response)  {

                try {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    final String body = response.body().string();

                    //final String jsonData = response.body().string();
                    JSONObject Jobject = new JSONObject(body);


                    //JSONArray Jarray = Jobject.getJSONArray("inboxMsgList");


                    StaffObject msg3 = new StaffObject(String.valueOf(Jobject.get("displayName")));
                    msg3.setStaffId((Integer) Jobject.get("userId"));
                    msg3.setStaffSpecialty((String) Jobject.get("specialityName"));
                    //msg3.setStaffSpecialty((String) Jobject.get("specialityName"));

                       if (flag == true) {
                           referringStaff.add(0,msg3);
                       }
                        if (flag == false) {
                            currentStaff.add(0,msg3);
                            handler.sendEmptyMessage(1);
                        }



                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e){
                    Log.e("Volley2", e.toString());
                }

            }



        });
    }

}