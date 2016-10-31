package com.adilpatel.vitalengine.FragmentPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.adilpatel.vitalengine.homeScreenRecycler.conversationRecyclerViewAdapter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class conversationsFragment extends Fragment {

    private RecyclerView usersListView;

    ArrayList<MessageData> arrMessageData; //= new ArrayList<MessageData>();

    conversationRecyclerViewAdapter adapter;
    Bitmap image;
    SwipeRefreshLayout refreshLayout;


    public conversationsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        usersListView = (RecyclerView) rootView.findViewById(R.id.conversationsListView);


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



                        arrMessageData = new ArrayList<>();

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);

                            if (object.get("messageType").equals("CONVERSATION")) {



                                Log.e("ConversationTest", object.getString("fromUser"));

                                MessageData msg3 = new MessageData();
                                msg3.setName((String) object.get("fromUser"));
                                msg3.setMessage((String) object.get("message"));
                                getImage((String) object.get("photo"));
                                msg3.setImage(image);
                                msg3.setRead(true);
                                msg3.setSubject((String) object.get("subject"));
                                msg3.setType((String) object.get("conversationDate"));


                                msg3.setId((Integer) object.get("conversationId"));

                                arrMessageData.add(msg3);
                            } else {

                            }




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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new conversationRecyclerViewAdapter(getActivity().getBaseContext(), arrMessageData);
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
                "&folderId=-1&tagId=0&page=1&itemPerPage=1000&showMsgInFolder=false";

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
               // Log.i(TAG, "call api error");
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

                    //Log.e("Array", String.valueOf(Jarray.length()));


//                    if (Jarray == null){
//                        Toast.makeText(getActivity(),
//                                "Null",
//                                Toast.LENGTH_LONG).show();
//                    }


                    arrMessageData = new ArrayList<>();

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

                        if (object.get("messageType").equals("CONVERSATION")) {

                            //String time = object.get("conversationDate");

                            Log.e("ConversationTest", object.getString("fromUser"));

                            MessageData msg3 = new MessageData();
                            msg3.setName((String) object.get("fromUser"));
                            msg3.setMessage((String) object.get("message"));
                            getImage((String) object.get("photo"));
                            msg3.setImage(image);
                            msg3.setRead(true);
                            msg3.setSubject((String) object.get("subject"));
                            msg3.setType((String) object.get("conversationDate"));
                            msg3.setId((Integer) object.get("conversationId"));

                            arrMessageData.add(msg3);
                        } else {
                            //Log.e("DetailObject", object.getString("fromUser"));
                        }


                        //Log.e("DetailObject", object.getString("fromUser"));
                        //Log.e("DetailObject", msg3.getName());

                        handler.sendEmptyMessage(1);

                    }


                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e) {
                    //Log.e("Volley2", e.toString());
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
                .getDefaultSharedPreferences(getActivity());
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);






         URL imgurl = new URL("https://staging.vitalengine.com/portal-api/" + id);
//        URLConnection conn = imgurl.openConnection();
//        conn.addRequestProperty("Authorization", auth_token_type + " "+ auth_token_string);
//        conn.connect();
//
//        InputStream in = conn.getInputStream();
//
//        Bitmap bmp = BitmapFactory.decodeStream(in);
//
//
//        image = bmp;

        Bitmap bmp = downloadImage(imgurl,auth_token_type, auth_token_string );

        image = bmp;

    }

    public static  Bitmap downloadImage(URL url, String auth_token_type, String auth_token_string) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 6;

        try {
            stream = getHttpConnection(url,auth_token_type, auth_token_string);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
        }
        catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("downloadImage"+ e1.toString());
        }
        return bitmap;
    }

    public static  InputStream getHttpConnection(URL urlString, String auth_token_type, String auth_token_string)  throws IOException {

        InputStream stream = null;
        URLConnection connection = urlString.openConnection();


        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.addRequestProperty("Authorization", auth_token_type + " " + auth_token_string);
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("downloadImage" + ex.toString());
        }
        return stream;
    }

}
