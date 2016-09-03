package com.adilpatel.vitalengine.API;

/**
 * Created by Adil on 8/10/16.
 */

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        // try the request
        Response response = chain.proceed(request);

        int tryCount = 0;
        while (!response.isSuccessful() && tryCount < 3) {

            Log.d("intercept", "Request is not successful - " + tryCount);

            tryCount++;

            // retry the request
            response = chain.proceed(request);
        }

        // otherwise just pass the original response on
        return response;
    }


//    private String credentials;
//
//    String cred = "ezhu:Ccare@123";
//    //String auth = "Basic "
//           // + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//
//    public BasicAuthInterceptor(String user, String password) {
////        this.credentials = "Basic "
////                + Base64.encodeToString(cred.getBytes(), Base64.NO_WRAP);//Credentials.basic(user, password);
//
//        this.credentials = "Basic ZXpodTpDY2FyZUAxMjM=";
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Request authenticatedRequest = request.newBuilder()
//                .header("Authorization", this.credentials).build();
//        return chain.proceed(authenticatedRequest);
//    }

}
