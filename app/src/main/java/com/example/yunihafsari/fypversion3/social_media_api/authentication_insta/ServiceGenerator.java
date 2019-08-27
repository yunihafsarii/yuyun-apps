package com.example.yunihafsari.fypversion3.social_media_api.authentication_insta;

import com.example.yunihafsari.fypversion3.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yunihafsari on 11/04/2017.
 */

public class ServiceGenerator {

    public static GetTokenServices createTokenService(){
        return getRetrofit().create(GetTokenServices.class);
    }

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_INSTAGRAM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
