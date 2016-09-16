package com.adilpatel.vitalengine.ActivitiesPackage.ChatStory;

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
import android.widget.EditText;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class ChatlistActivity extends AppCompatActivity {

    private EditText messageBodyField;
    //private ChatStoryAdapter adapter;
    private chatStoryRecyclerView adapter;
    //private ListView messagesList;
    private RecyclerView messagesList;
    private HashMap<Integer, chatMessageModel> messages;
    private Integer ref;
    private String type;

   // private ArrayList<String> otherMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);




        Intent b = getIntent();

        ref = b.getExtras().getInt("refId");
        type = b.getExtras().getString("typeOfActivity");



        Toast.makeText(this, "Here " + ref, Toast.LENGTH_SHORT).show();

       // messagesList = (ListView) findViewById(R.id.listMessages);
        messagesList = (RecyclerView) findViewById(R.id.listMessages);

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




//        adapter = new chatStoryRecyclerView(this, messages);
//        messagesList.setAdapter(adapter);


        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable messageText = messageBodyField.getText();
                sendMessageApi(messageText, type);
            }
        });


        callApi(type);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new chatStoryRecyclerView(ChatlistActivity.this, messages);
                    messagesList.setAdapter(adapter);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(ChatlistActivity.this, LinearLayoutManager.VERTICAL, false);
                    layoutManager.setReverseLayout(true);
                    messagesList.setLayoutManager(layoutManager);
                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };

    private void sendMessageApi(Editable messageText,  final String type){

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


        String url = "https://staging.vitalengine.com/portal-api/api/user/referral/post/save";

        RequestBody formBody = new FormBody.Builder()
                .add("referralId", String.valueOf(ref))
                .add("message", String.valueOf(messageText))
                .add("userId", userId)
                .build();

        Request request = new Request.Builder().url(url).post(formBody)
                .addHeader("Authorization", auth_token_type + " " + auth_token_string)
                .addHeader("user-tz", "-330")
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
                    Log.e("Posted", "Posted Message");
                    callApi(type);


                } catch (Exception e){
                    Log.e("Volley2", e.toString());
                }

            }

        });
    }





    private void callApi(String type) {

        String conversation = "conversation";
        String referral = "referral";
        String message = "message";

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();

        String credentials = "ezhu:Ccare@123";
        String auth = "Basic "
                + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        Log.e("Test", auth);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        final String userId = settings.getString("userId", "");

        String url = "";

        if (type.equals(conversation)) {

            url = "https://staging.vitalengine.com/portal-api/api/user/conversation/details?conversationId=" + ref + "&userId=" + userId;
        } else if (type.equals(referral)){
            url = "https://staging.vitalengine.com/portal-api/api/user/referral/details?referralId=" + ref +"&userId=" + userId;
        }  else if (type.equals(message)){
        url = "https://staging.vitalengine.com/portal-api/api/user/message/details?conversationId=" + ref +"&userId=" + userId;
    }


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


                    JSONObject Jobject = new JSONObject(body);

                    JSONObject sub = Jobject.getJSONObject("response");

                    JSONArray Jarray = sub.getJSONArray("messages");

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

                            int userIdInteger = Integer.parseInt(userId);

                            if (from == userIdInteger){
                                first.setDirection(0);
                                Log.e("Fromuser", String.valueOf(from));
                            } else {
                                first.setDirection(1);
                                first.setSender((String) object.get("createdBy"));
                               Log.e("Fromuser", String.valueOf(from));
                            }

                            Integer sysMessage = (Integer) object.get("isAnnotation");

                            if (sysMessage == 1){
                                first.setDirection(2);
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

