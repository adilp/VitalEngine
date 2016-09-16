package com.adilpatel.vitalengine.FragmentPackage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.homeScreenRecycler.allRecyclerViewAdapter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class allFragment extends Fragment {

    private String currentUserId;
    private ArrayAdapter namesArrayAdapter;
    //private ArrayList<String> names;
    //private ListView usersListView;
    private RecyclerView usersListView;
    String names[] = {"Anant Kharod, MD", "Mustafa Ahmed, MD"};
    String msg[] = {"What time do you want to get started adding more stuff go over the line", "Presentation is tomorrow"};
    boolean readUnread [] = {false,false};
    public static int [] images={R.drawable.msgone,R.drawable.msgtwo};

    ArrayList<MessageData> arrMessageData; //= new ArrayList<MessageData>();
    //CustomAdapterAll adapter;

    allRecyclerViewAdapter adapter;


    public allFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        //usersListView = (ListView) rootView.findViewById(R.id.allListView);
        usersListView = (RecyclerView) rootView.findViewById(R.id.allListView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, names);





        ArrayList<MessageData> arrMessageData = new ArrayList<MessageData>();

        MessageData msg1 = new MessageData();
        msg1.setName("ALL");
        msg1.setMessage("Mustafa Ahmed: What time do you want to get started adding more stuff go over the line");
        msg1.setImage(R.drawable.msgone);
        msg1.setRead(true);
        msg1.setSubject("Test Subject");
        msg1.setType("referral");


        MessageData msg2 = new MessageData();
        msg2.setName("Group 1");
        msg2.setMessage("Adil Patel: Presentation is tomorrow adding");
        msg2.setImage(R.drawable.group);
        msg2.setType("conversation");
      // msg2.setSubject("Subject 2");

       MessageData msg3 = new MessageData();
        msg3.setName("Adil Patel");
        msg3.setMessage("Presentation is tomorrow");
        msg3.setImage(R.drawable.msgthree);
        msg3.setType("message");
        //msg2.setSubject("Subject 2");


//        arrMessageData.add(msg1);
//        arrMessageData.add(msg2);
//        arrMessageData.add(msg3);

//        CustomAdapterAll adapter = new CustomAdapterAll(getActivity().getBaseContext(), arrMessageData);
//        usersListView.setAdapter(adapter);


        callApi();

        return rootView;

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new allRecyclerViewAdapter(getActivity().getBaseContext(), arrMessageData);
                    usersListView.setAdapter(adapter);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    usersListView.setLayoutManager(layoutManager);
                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };

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


        //SharedPreferences preferences = this.getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);


        //String url = "https://randomuser.me/api/";
        String url  = "https://staging.vitalengine.com/portal-api/api/user/inbox/list?userId=" +
                userId +
                "&folderId=-1&tagId=0&page=1&itemPerPage=10&showMsgInFolder=false";

        //String url = "https://staging.vitalengine.com/portal-api/api/login/getUserDetails?userName=ezhu";

        //String url = "http://192.168.1.49:8888/portal-api/api/login/getUserDetails?userName=ezhu";

        //String url = "http://httpbin.org/basic-auth/user/passwd";
        //String url = "http://10.0.2.2:3000/response";

        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", auth_token_type + " " + auth_token_string)
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

                    Log.e("Array", String.valueOf(Jarray.length()));


//                    if (Jarray == null){
//                        Toast.makeText(getActivity(),
//                                "Null",
//                                Toast.LENGTH_LONG).show();
//                    }


                    arrMessageData = new ArrayList<>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

                        MessageData msg3 = new MessageData();
                        msg3.setName((String) object.get("fromUser"));
                        msg3.setMessage((String) object.get("message"));
                        msg3.setImage(R.drawable.msgone);
                        msg3.setRead(true);

                        msg3.setType((String) object.get("conversationDate"));

                        if (object.get("messageType").equals("CONVERSATION")) {

                            msg3.setType("conversation");
                            msg3.setSubject((String) object.get("subject"));


                        } else if (object.get("messageType").equals("MESSAGE")) {
                            msg3.setType("message");
                            msg3.setSubject((String) object.get("subject"));
                        } else {
                            msg3.setType("referral");
                            msg3.setPatient((String) object.get("patient"));
                        }

                        arrMessageData.add(msg3);

                        //Log.e("DetailObject", object.getString("fromUser"));
                        //Log.e("DetailObject", msg3.getName());

                        handler.sendEmptyMessage(1);

                    }


                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e) {
                    Log.e("Volley2", e.toString());
                }

            }


        });
    }

}
