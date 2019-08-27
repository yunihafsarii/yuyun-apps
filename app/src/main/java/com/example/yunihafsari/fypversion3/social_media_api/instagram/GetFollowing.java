package com.example.yunihafsari.fypversion3.social_media_api.instagram;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public interface GetFollowing {


    // "https://api.instagram.com/v1/users/self/follows?access_token=
    @GET("/v1/users/self/follows")
    Call<ResponseBody> getFollowing(@Query("access_token") String accessToken);

}
