package com.example.yunihafsari.fypversion3.social_media_api.twitter;

import com.example.yunihafsari.fypversion3.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yunihafsari on 17/04/2017.
 */

public class ServiceTwitterFollowing {

    public static GetTwitterFollowing createServie(){
        return getRetrofit().create(GetTwitterFollowing.class);
    }

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
