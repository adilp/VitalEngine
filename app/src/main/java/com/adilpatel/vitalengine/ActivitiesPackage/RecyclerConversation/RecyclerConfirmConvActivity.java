package com.adilpatel.vitalengine.ActivitiesPackage.RecyclerConversation;

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
import com.adilpatel.vitalengine.Models.Patient;
import com.adilpatel.vitalengine.Models.StaffObject;
import com.adilpatel.vitalengine.R;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RecyclerConfirmConvActivity extends AppCompatActivity {


    List<StaffObject> currentStaff; //= new ArrayList<>();


    EditText messageMessage;
    Button messageSendButton;
    EditText messagesSubject;


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

    JSONArray refParticipantsArray ;
    JSONArray recParticipantsArray ;

    JSONObject referralJson;

    int image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_recycler_confirm_message);

        Stetho.initializeWithDefaults(this);

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




        messageMessage = (EditText)findViewById(R.id.messageMessageBodyField);
        messageSendButton = (Button)findViewById(R.id.messageSendButton);
        messagesSubject = (EditText)findViewById(R.id.messageSubjectBodyField);











        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Editable messageText = messageMessage.getText();
                Editable subjectText = messagesSubject.getText();
                jsonBody(messageText, subjectText);

                sendReferral(referralJson);
                Log.e("ConvoSent", referralJson.toString());

                Intent myIntent = new Intent(RecyclerConfirmConvActivity.this, MainActivity.class);
                RecyclerConfirmConvActivity.this.startActivity(myIntent);

            }
        });

    }

    private void jsonBody(Editable messageText, Editable subjectText){

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
            referralJson.put("referralParticipants", refParticipantsArray);
            referralJson.put("receivingParticipants", recParticipantsArray);
            referralJson.put("message", messageText);
            referralJson.put("subject", subjectText);
            referralJson.put("dicomImages", Empty2);
            referralJson.put("conversationTags", Empty3);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    myTeamRecyclerList = (RecyclerView)findViewById(R.id.recyclerToMessage);
                    //myTeamList.add();
                    //list = StaffObject.createContactsList(20);
                    // Create adapter passing in the sample user data
                    adapter1 = new RecyclerViewAdapter(RecyclerConfirmConvActivity.this, currentStaff);
                    // Attach the adapter to the recyclerview to populate items
                    myTeamRecyclerList.setAdapter(adapter1);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(RecyclerConfirmConvActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    myTeamRecyclerList.setLayoutManager(layoutManager);



        /*
        End
         */



        /*
        RecyclerView Referral Team
         */

                    ReferralTeamRecyclerlist = (RecyclerView)findViewById(R.id.recyclerFromMessage);
                    //list.add(0,referralDoc);

                    // Create adapter passing in the sample user data
                    adapter = new RecyclerViewAdapter(RecyclerConfirmConvActivity.this, referringStaff);
                    // Attach the adapter to the recyclerview to populate items
                    ReferralTeamRecyclerlist.setAdapter(adapter);
                    // Set layout manager to position the items
                    LinearLayoutManager layoutManager1
                            = new LinearLayoutManager(RecyclerConfirmConvActivity.this, LinearLayoutManager.HORIZONTAL, false);
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



        String url = "https://staging.vitalengine.com/portal-api/api/user/conversation/save";

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

    public void getImage(String id) throws IOException {

        String credentials = "ezhu:Ccare@123";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Log.e("Test", auth);


        //SharedPreferences preferences = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(RecyclerConfirmConvActivity.this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);


        URL imgurl = new URL("https://staging.vitalengine.com/portal-api/" + id);
        URLConnection conn = imgurl.openConnection();
        conn.addRequestProperty("Authorization", auth_token_type + " "+ auth_token_string);
        conn.connect();

        InputStream in = conn.getInputStream();

//        Bitmap bmp = BitmapFactory.decodeStream(in);
//        image = bmp;


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
                    //getImage((String) Jobject.get("userId"));
                    msg3.setStaffPic(image);

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