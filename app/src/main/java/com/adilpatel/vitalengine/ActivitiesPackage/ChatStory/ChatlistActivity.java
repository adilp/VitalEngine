package com.adilpatel.vitalengine.ActivitiesPackage.ChatStory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.R;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class ChatlistActivity extends AppCompatActivity {

    private EditText messageBodyField;
    private ChatStoryAdapter adapter;
    private ListView messagesList;
    private HashMap<Integer, chatMessageModel> messages;
    private Integer ref;
   // private ArrayList<String> otherMessage;

    Integer refId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);


        Intent b = getIntent();
        refId = b.getParcelableExtra("refId");
        ref = b.getExtras().getInt("refId");



        Toast.makeText(this, "Here " + ref, Toast.LENGTH_SHORT).show();

        messagesList = (ListView) findViewById(R.id.listMessages);

        messageBodyField = (EditText) findViewById(R.id.messageBodyField);

        messages = new HashMap<>();

//        chatMessageModel first = new chatMessageModel();
//        first.setMessage("Hello");
//        first.setDirection(0);
//
//        chatMessageModel second = new chatMessageModel();
//        second.setMessage("Adil");
//        second.setDirection(1);

//        messages.put(0, first);
//        messages.put(1, second);




        adapter = new ChatStoryAdapter(this, messages);
        messagesList.setAdapter(adapter);


        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the message!
            }
        });


        callApi();
    }

    private void callApi() {
        String username, passowrd;
        username = "user";
        passowrd = "passwd";
        //OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();

        String credentials = "ezhu:Ccare@123";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Log.e("Test", auth);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");


        String url = "https://staging.vitalengine.com/portal-api/api/user/referral/details?referralId=" + refId;

        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", auth_token_type + " " + auth_token_string)
                .addHeader("user-tz", "-330")
                        //.addHeader("Content-Type", "application/json")
                .build();




        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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

                    //Log.e("JSON", Jobject.getString("currTime"));
                    JSONArray Jarray = Jobject.getJSONArray("messages");

                    Log.e("Array", String.valueOf(Jarray.length()));


//                    if (Jarray == null){
//                        Toast.makeText(getActivity(),
//                                "Null",
//                                Toast.LENGTH_LONG).show();
//                    }

                    //messages = new HashMap<>();

                    //arrMessageData = new ArrayList<>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

                        //if (object.get("messageType").equals("REFERRALS")) {

                            //String time = object.get("conversationDate");

//                            MessageData msg3 = new MessageData();
//                            msg3.setName((String) object.get("fromUser"));
//                            msg3.setMessage((String) object.get("message"));
//                            msg3.setImage(R.drawable.msgone);
//                            msg3.setRead(true);
//                            msg3.setSubject((String) object.get("patient"));
//                            msg3.setType((String) object.get("conversationDate"));
//                            msg3.setId((Integer) object.get("referralId"));
//                            Log.e("RefId", object.getString("referralId"));


                            chatMessageModel first = new chatMessageModel();
                            first.setMessage((String) object.get("message"));
                            first.setTime((String)object.get("createdDate"));


                            Integer from = (Integer) object.get("createdByUserId");

                            if (from == 1002){

                                first.setDirection(0);

                                Log.e("Fromuser", String.valueOf(from));
                            } else {
                                first.setDirection(1);
                                first.setSender((String)object.get("createdBy"));
                            }

                        messages.put(i, first);


                           // arrMessageData.add(msg3);

                            //messages.put(i,msg3);
                        //} else{
                            //Log.e("DetailObject", object.getString("fromUser"));
                            //Log.e("RefId", object.getString("referralId"));
                       // }


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

