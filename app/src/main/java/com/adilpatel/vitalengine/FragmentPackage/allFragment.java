package com.adilpatel.vitalengine.FragmentPackage;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.adilpatel.vitalengine.API.BasicAuthInterceptor;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.homeScreenRecycler.allRecyclerViewAdapter;
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


public class allFragment extends Fragment {


    private RecyclerView usersListView;


    ArrayList<MessageData> arrMessageData; //= new ArrayList<MessageData>();


    allRecyclerViewAdapter adapter;

    Bitmap image;


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




        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String auth_token_string = settings.getString("token", ""/*default value*/);
        String auth_token_type = settings.getString("tokenType", "");
        String userId = settings.getString("userId", "");

        Log.i("prefs", auth_token_type);


        String url  = "https://staging.vitalengine.com/portal-api/api/user/inbox/list?userId=" +
                userId +
                "&folderId=-1&tagId=0&page=1&itemPerPage=100&showMsgInFolder=false";


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
                        msg3.setRead(true);

                        getImage((String) object.get("photo"));
                        msg3.setImage(image);

                        //msg3.setType((String) object.get("messageType"));

                        if (object.get("messageType").equals("CONVERSATION")) {

                            msg3.setType("conversation");
                            msg3.setSubject((String) object.get("subject"));


                        } else if (object.get("messageType").equals("MESSAGE")) {
                            msg3.setType("message");

                        } else {
                            msg3.setType("referral");
                            msg3.setPatient((String) object.get("patient"));
                        }

                        arrMessageData.add(msg3);



                        handler.sendEmptyMessage(1);

                    }


                    // Log.e("FullJsonReply", body.toString());
                } catch (Exception e) {
                    Log.e("Volley2", e.toString());
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
