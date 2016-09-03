package com.adilpatel.vitalengine.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adil on 8/8/16.
 */
public class gitapi {

    public static final String BASE_URL = "https://staging.vitalengine.com/portal-api/api/user/inbox/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
