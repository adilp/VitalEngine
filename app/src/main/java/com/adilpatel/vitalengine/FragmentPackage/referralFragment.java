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
import com.adilpatel.vitalengine.ActivitiesPackage.MainActivity;
import com.adilpatel.vitalengine.Models.MessageData;
import com.adilpatel.vitalengine.R;
import com.adilpatel.vitalengine.homeScreenRecycler.referralsRecyclerViewAdapter;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class referralFragment extends Fragment {

    //private static String TAG = MainActivity.class.getSimpleName();
    private static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView usersListView;
    ArrayList<MessageData> arrMessageData; //= new ArrayList<>();
    Bitmap image;





    referralsRecyclerViewAdapter adapter;


    public referralFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



       // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new StethoInterceptor()).build();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_referral, container, false);
        //usersListView = (ListView) rootView.findViewById(R.id.referralListView);
        usersListView = (RecyclerView) rootView.findViewById(R.id.referralListView);


        callApi();



//        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                MessageData obj = arrMessageData.get(position);
//
//                int test = obj.getId();
//
//
//
//                Toast.makeText(getActivity(), "CLICKED " + obj.getId(), Toast.LENGTH_SHORT).show();
//
//                Intent myIntent = new Intent(getActivity(), ChatlistActivity.class);
//                //myIntent.putParcelableArrayListExtra("NAME", (ArrayList<? extends Parcelable>) selectedStaff);
//                myIntent.putExtra("refId", obj.getId());
//                //myIntent.putExtra("messagePerson", currentPerson);
//                startActivity(myIntent);
//            }
//        });



//        adapter = new CustomAdapterReferral(getActivity().getBaseContext(), arrMessageData);
//        usersListView.setAdapter(adapter);




        return rootView;

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter = new referralsRecyclerViewAdapter(getActivity().getBaseContext(), arrMessageData);
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
                Log.i(TAG, "call api error");
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



                    arrMessageData = new ArrayList<>();

                   for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);

                        if (object.get("messageType").equals("REFERRALS")) {

                            //String time = object.get("conversationDate");

                            MessageData msg3 = new MessageData();
                            msg3.setName((String) object.get("fromUser"));
                            msg3.setMessage((String) object.get("message"));
                            msg3.setRead(true);
                            msg3.setSubject((String) object.get("patient"));
                            msg3.setType((String) object.get("conversationDate"));
                            msg3.setId((Integer) object.get("referralId"));

                            //Log.e("RefId", object.getString("referralId"));
                            //getImage((String)object.get("photo"));
                            //msg3.setImage(image);

                            //msg3.setImage(R.drawable.msgone);

                            arrMessageData.add(msg3);
                        } else{
                           // Log.e("DetailObject", object.getString("fromUser"));
                            //Log.e("RefId", object.getString("referralId"));
                        }


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
        URLConnection conn = imgurl.openConnection();
        conn.addRequestProperty("Authorization", auth_token_type + " "+ auth_token_string);
        conn.connect();

        InputStream in = conn.getInputStream();

        Bitmap bmp = BitmapFactory.decodeStream(in);
        image = bmp;


    }

}
