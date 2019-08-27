package com.example.yunihafsari.fypversion3.social_media_api.instagram;

import com.example.yunihafsari.fypversion3.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class ServiceMedia {

    public static GetMedia createService(){
        return getRetrofit().create(GetMedia.class);
    }

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_INSTAGRAM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
