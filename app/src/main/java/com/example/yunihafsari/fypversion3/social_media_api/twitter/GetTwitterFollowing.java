package com.example.yunihafsari.fypversion3.social_media_api.twitter;

import com.example.yunihafsari.fypversion3.utils.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by yunihafsari on 17/04/2017.
 */

public interface GetTwitterFollowing {

    // GET https://api.twitter.com/1.1/friends/list.json?cursor=-1&screen_name=twitterapi&skip_status=true&include_user_entities=false
   // httpURLConnection2.setRequestProperty("Authorization","Bearer "+auth.access_token);
   //                 httpURLConnection2.setRequestProperty("Content-Type", "application/json");
    @Headers("Authorization: Bearer "+ Constants.ACCESS_TOKEN)
    @GET("/1.1/friends/list.json")
    Call<ResponseBody> getTwitterFollowing(@Query("cursor") int cursor,
                                           @Query("screen_name") String screen_name,
                                           @Query("skip_status") boolean skip_status,
                                           @Query("include_user_entities") boolean include_user_entities);

}
