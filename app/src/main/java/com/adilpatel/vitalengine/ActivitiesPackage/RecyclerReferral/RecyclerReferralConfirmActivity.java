package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.adilpatel.vitalengine.Expand2.RecyclerViewAdapter;
import com.adilpatel.vitalengine.ListAdapters.ReferralConfirmAdapter;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Adil on 9/16/16.
 */
public class RecyclerReferralConfirmActivity extends AppCompatActivity {


    ArrayList<StaffObject> currentStaff;


    TextView patientName;
    TextView patientDob;
    TextView patientFor;
    ImageView patientImage;
    EditText refMessage;
    Button refSendButton;


    private ReferralConfirmAdapter listAdapter;
    private RecyclerView ReferralTeamRecyclerlist;
    private RecyclerView myTeamRecyclerList;

    ArrayList<StaffObject> myTeamList;

    int mydoc ;
    int myStaff ;
    int referringDoc ;
    int referringStaff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_confirm);



        /*
        Receiving data from past activity
         */
        Intent b = getIntent();


         mydoc = b.getExtras().getInt("docId");
         myStaff = b.getExtras().getInt("myStaff");
         referringDoc = b.getExtras().getInt("referringDoc");
         referringStaff = b.getExtras().getInt("referringDoc");


        //Referral Team doctor
//        StaffObject referralDoc = new StaffObject(referralTeamDoc.getDocname());
//        referralDoc.setStaffLocation(referralTeamDoc.getDocLocation());
//        referralDoc.setStaffPic(referralTeamDoc.getDocPic());
//        referralDoc.setStaffSpecialty(referralTeamDoc.getDocspecialty());
//
//        //My Team doctor
//        StaffObject myDoc = new StaffObject(myTeamDoc.getDocname());
//        myDoc.setStaffLocation(myTeamDoc.getDocLocation());
//        myDoc.setStaffPic(myTeamDoc.getDocPic());
//        myDoc.setStaffSpecialty(myTeamDoc.getDocspecialty());

        /*
        end
         */




        //lvTest = (TwoWayView) findViewById(R.id.MyTeamItems);
//
        //lvTest = (ListView)findViewById(R.id.MyTeamItems);
        //listAdapter = new ReferralConfirmAdapter(this, list);

        //lvTest.setAdapter(listAdapter);





        //ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, items);



        /*
        Patient stuff
         */

        patientName = (TextView)findViewById(R.id.referralConfirmPatientName);
        patientDob = (TextView)findViewById(R.id.referralConfirmDOB);
        patientFor = (TextView)findViewById(R.id.referralComfirmFor);
        patientImage = (ImageView)findViewById(R.id.referralConfirmPatientImage);
        refMessage = (EditText)findViewById(R.id.refMessageBodyField);
        refSendButton = (Button)findViewById(R.id.refSendButton);

//        patientName.setText(receivedPatient.getName());
//        patientDob.setText(receivedPatient.getDOB());
//        patientFor.setText(receivedPatient.getFor());
//        patientImage.setImageResource(R.drawable.group);


        /*
        RecyclerView My Team
         */

        myTeamRecyclerList = (RecyclerView)findViewById(R.id.recyclerViewMyTeam);
       // myTeamList.add(0,myDoc);
        //myTeamList.add();
        //list = StaffObject.createContactsList(20);
        // Create adapter passing in the sample user data
        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(this, myTeamList);
        // Attach the adapter to the recyclerview to populate items
        myTeamRecyclerList.setAdapter(adapter1);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        // Attach the adapter to the recyclerview to populate items
        //ReferralTeamRecyclerlist.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ReferralTeamRecyclerlist.setLayoutManager(layoutManager1);
        // That's all!


        /*
        End
         */


        refSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable messageText = refMessage.getText();
                //sendMessageApi(messageText);
            }
        });

    }


    public void getApi (int person){

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
        String url  = "https://staging.vitalengine.com/portal-api/api/user/inbox/list?userId=" +
                userId +
                "&folderId=-1&tagId=0&page=1&itemPerPage=1000&showMsgInFolder=false";

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
                    JSONObject sub = Jobject.getJSONObject("response");
                    JSONArray Jarray = sub.getJSONArray("inboxMsgList");
                    Log.e("JSON", String.valueOf(sub));
                    //Log.e("Array", String.valueOf(Jarray.length()));


//                    if (Jarray == null){
//                        Toast.makeText(getActivity(),
//                                "Null",
//                                Toast.LENGTH_LONG).show();
//                    }



                    //arrMessageData = new ArrayList<>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

                        if (object.get("messageType").equals("REFERRALS")) {

                            //String time = object.get("conversationDate");

                            MessageData msg3 = new MessageData();
                            msg3.setName((String) object.get("fromUser"));
                            msg3.setMessage((String) object.get("message"));
                            msg3.setImage(R.drawable.msgone);
                            msg3.setRead(true);
                            msg3.setSubject((String) object.get("patient"));
                            msg3.setType((String) object.get("conversationDate"));
                            msg3.setId((Integer) object.get("referralId"));
                            //Log.e("RefId", object.getString("referralId"));

                            //arrMessageData.add(msg3);
                        } else{
                            // Log.e("DetailObject", object.getString("fromUser"));
                            //Log.e("RefId", object.getString("referralId"));
                        }


                        //Log.e("DetailObject", object.getString("fromUser"));
                        //Log.e("DetailObject", msg3.getName());

                        //handler.sendEmptyMessage(1);

                    }


                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e){
                    Log.e("Volley2", e.toString());
                }

            }



        });
    }

}