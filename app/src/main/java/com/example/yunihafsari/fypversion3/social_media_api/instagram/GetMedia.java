package com.example.yunihafsari.fypversion3.social_media_api.instagram;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public interface GetMedia {

    //"https://api.instagram.com/v1/users/5331927321/media/recent/?access_token=
    @GET("/v1/users/{user-id}/media/recent")
    Call<ResponseBody> getMedia(@Path("user-id") String userId,
                                @Query("access_token") String accessToken);
}
