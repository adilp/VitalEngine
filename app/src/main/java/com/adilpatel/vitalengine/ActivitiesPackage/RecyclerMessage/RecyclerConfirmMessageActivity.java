package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerMessage;

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

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.ActivitiesPackage.MainActivity;
import com.adilpatel.vitalengine.Expand2.RecyclerViewAdapter;
import com.adilpatel.vitalengine.ListAdapters.ReferralConfirmAdapter;
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

public class RecyclerConfirmMessageActivity extends AppCompatActivity {

    List<StaffObject> fromList;
    List<StaffObject> toList;


    EditText messageMessage;
    Button messageSendButton;
    EditText messagesSubject;


    private ReferralConfirmAdapter listAdapter;
    private RecyclerView recyclerFromMessage;
    private RecyclerView recyclerToMessage;


    int mydoc;
    int referringDoc;
     //= new ArrayList<>();

    RecyclerViewAdapter adapter1;
    RecyclerViewAdapter adapter;
    String userId;

    JSONArray refParticipantsArray = new JSONArray();
    JSONArray recParticipantsArray = new JSONArray();

    JSONObject referralJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_confirm_message);

        Stetho.initializeWithDefaults(this);

        fromList = new ArrayList<>();
        toList = new ArrayList<>();


        Intent b = getIntent();


        mydoc = b.getExtras().getInt("myDoc");



        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        userId = settings.getString("userId", "");

        getDocApi(Integer.parseInt(userId), true);

        getDocApi(mydoc, false);


        messageMessage = (EditText)findViewById(R.id.messageMessageBodyField);
        messageSendButton = (Button)findViewById(R.id.messageSendButton);
        messagesSubject = (EditText)findViewById(R.id.messageSubjectBodyField);


        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable messageText = messageMessage.getText();
                Editable subjectText = messagesSubject.getText();
                jsonBody(messageText,subjectText );
                messageText.clear();
                sendMessage(referralJson);
                Log.e("ReferralSendingJson", referralJson.toString());

                Intent myIntent = new Intent(RecyclerConfirmMessageActivity.this, MainActivity.class);
                RecyclerConfirmMessageActivity.this.startActivity(myIntent);

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    recyclerFromMessage = (RecyclerView)findViewById(R.id.recyclerFromMessage);
                    //myTeamList.add();
                    //list = StaffObject.createContactsList(20);
                    // Create adapter passing in the sample user data
                    adapter1 = new RecyclerViewAdapter(RecyclerConfirmMessageActivity.this, fromList);
                    // Attach the adapter to the recyclerview to populate items
                    recyclerFromMessage.setAdapter(adapter1);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecyclerConfirmMessageActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerFromMessage.setLayoutManager(layoutManager);



        /*
        End
         */



        /*
        RecyclerView Referral Team
         */

                    recyclerToMessage = (RecyclerView)findViewById(R.id.recyclerToMessage);
                    //list.add(0,referralDoc);

                    // Create adapter passing in the sample user data
                    adapter = new RecyclerViewAdapter(RecyclerConfirmMessageActivity.this, toList);
                    // Attach the adapter to the recyclerview to populate items
                    recyclerToMessage.setAdapter(adapter);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager1
                            = new LinearLayoutManager(RecyclerConfirmMessageActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerToMessage.setLayoutManager(layoutManager1);
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



        String url  = "https://staging.vitalengine.com/portal-api/api/user/quickinfo?userId=" + doc;


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
                    //getImage((String) Jobject.get("userId"));
                    //msg3.setStaffPic(image);

                    //msg3.setStaffSpecialty((String) Jobject.get("specialityName"));

                    if (flag == true) {
                        fromList.add(msg3);
                    }
                    if (flag == false) {
                        toList.add(msg3);
                        handler.sendEmptyMessage(1);
                    }



                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e){
                    Log.e("Volley2", e.toString());
                }

            }



        });

    }

    private void jsonBody(Editable messageText, Editable messagesSubject){

        refParticipantsArray = new JSONArray();
        recParticipantsArray = new JSONArray();


        for (int i = 0; i< fromList.size(); i++) {
            try {
                JSONObject refferralParticipants = new JSONObject();
                refferralParticipants.put("userId", fromList.get(i).getStaffId());
                refParticipantsArray.put(refferralParticipants);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        for (int i = 0; i< toList.size(); i++) {
            JSONObject receivingParticipants = new JSONObject();
//            ArrayList<Integer> participants = new ArrayList();
//
//            participants.add(0, toList.get(0).getStaffId());

        JSONArray aJsonArray = new JSONArray();
        aJsonArray.put(toList.get(0).getStaffId());


            //receivingParticipants.put("userId", toList.get(i).getStaffId());

            //recParticipantsArray.put(receivingParticipants);

//        }

        JSONArray Empty2 = new JSONArray();
        JSONArray Empty3 = new JSONArray();


        referralJson = new JSONObject();
        try {
            referralJson.put("userId", userId);
            referralJson.put("participants", aJsonArray);
            referralJson.put("subject", messagesSubject);
            referralJson.put("message", messageText);
            referralJson.put("dicomImages", Empty2);
            referralJson.put("conversationTags", Empty3);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void sendMessage(JSONObject referralJson){


        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();


        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();



        String url = "https://staging.vitalengine.com/portal-api/api/user/message/save";

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
                .addHeader("Content-Type", "application/json")
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

}
