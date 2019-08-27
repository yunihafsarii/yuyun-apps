package com.example.yunihafsari.fypversion3.social_media_api.authentication_insta;

import com.example.yunihafsari.fypversion3.model.instagram_model.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yunihafsari on 11/04/2017.
 */

public interface GetTokenServices {

    @FormUrlEncoded
    @POST("/oauth/access_token")
    Call<TokenResponse> getAccessToken(@Field("client_id") String client_id,
                                       @Field("client_secret") String client_secret,
                                       @Field("redirect_uri") String redirect_uri,
                                       @Field("grant_type") String grant_type,
                                       @Field("code") String code,
                                       @Field("scope") String scope);
}
