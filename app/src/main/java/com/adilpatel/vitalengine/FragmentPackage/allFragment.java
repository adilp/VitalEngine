package com.adilpatel.vitalengine.FragmentPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.homeScreenRecycler.allRecyclerViewAdapter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class allFragment extends Fragment {


    private RecyclerView usersListView;


    ArrayList<MessageData> arrMessageData; //= new ArrayList<MessageData>();


    allRecyclerViewAdapter adapter;

    SwipeRefreshLayout refreshLayout;


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

        usersListView = (RecyclerView) rootView.findViewById(R.id.allListView);




        new Load().execute(null, null, null);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                new Load().execute(null, null, null);
            }
        });

        return rootView;

    }

    class Load extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;

        private Context context;

        @Override
        protected Void doInBackground(Void... params) {


//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor()).addNetworkInterceptor(new StethoInterceptor()).build();

            String credentials = "ezhu:Ccare@123";
            String auth = "Basic "
                    + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            Log.e("Test", auth);




            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            String auth_token_string = settings.getString("token", ""/*default value*/);
            String auth_token_type = settings.getString("tokenType", "");
            String userId = settings.getString("userId", "");

            Log.i("prefs", auth_token_type);



            String url  = "https://staging.vitalengine.com/portal-api/api/user/inbox/list?userId=" +
                    userId +
                    "&folderId=-1&tagId=0&page=1&itemPerPage=1000&showMsgInFolder=false";


            Request request = new Request.Builder().url(url)
                    .addHeader("Authorization", auth_token_type + " " + auth_token_string)
                    .addHeader("user-tz", "-330")
                            //.addHeader("Content-Type", "application/json")
                    .build();




            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Log.i(TAG, "call api error");
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
                        JSONArray Jarray = sub.getJSONArray("inboxMsgList");

                        Log.e("Array", String.valueOf(Jarray.length()));




                        arrMessageData = new ArrayList<>();

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);

                            MessageData msg3 = new MessageData();
                            msg3.setName((String) object.get("fromUser"));
                            msg3.setMessage((String) object.get("message"));
                            msg3.setToUser((String) object.get("toUser"));
                            //msg3.setRead(true);
                            msg3.setRead((Integer) object.get("isRead"));
                            msg3.setPhotoURL((String) object.get("photo"));
                            String s = "14.015_AUDI";
                            String time = (String) object.get("conversationDate");
                            String[] parts = time.split("@"); //returns an array with the 2 parts
                            String firstPart = parts[1];
                            String secondPart = parts[0];
                            msg3.setLastDate(secondPart);


                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                            dateFormat.setTimeZone(TimeZone.getDefault());
                            Date date1 = dateFormat.parse(time);



                            msg3.setTime(dateFormat.format(date1));


                            //msg3.setImage(image);

                            //msg3.setType((String) object.get("messageType"));

                            if (object.get("messageType").equals("CONVERSATION")) {

                                msg3.setType("conversation");
                                msg3.setSubject((String) object.get("subject"));
                                msg3.setId((Integer) object.get("conversationId"));


                            } else if (object.get("messageType").equals("MESSAGE")) {
                                msg3.setType("message");
                                msg3.setId((Integer) object.get("conversationId"));

                            } else {
                                msg3.setType("referral");
                                msg3.setPatient((String) object.get("patient"));
                                msg3.setId((Integer) object.get("referralId"));
                            }

                            arrMessageData.add(msg3);



                            handler.sendEmptyMessage(1);

                        }


                    } catch (Exception e) {

                    }

                }


            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pd != null)
            {
                pd.dismiss();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Load().execute(null, null, null);
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

                    refreshLayout.setRefreshing(false);


                    break;
                default:
                    Log.d("TAG", msg.what + " ? ");
                    break;
            }
        }
    };



}
