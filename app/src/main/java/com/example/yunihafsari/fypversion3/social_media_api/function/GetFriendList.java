package com.example.yunihafsari.fypversion3.social_media_api.function;

import android.util.Log;

import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend_collection;
import com.example.yunihafsari.fypversion3.social_media_api.twitter.ServiceTwitterFollowing;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yunihafsari on 18/04/2017.
 */

public class GetFriendList {

    private Twitter_friend_collection twitter_friends;
    private ArrayList<Twitter_friend> twitter_list;

    public GetFriendList(String screen_name) {
        twitter_list = new ArrayList<>();
        getTwitterFollowingRequestAPI(screen_name);
    }

    public ArrayList<Twitter_friend> getTwitter_list() {
        Log.i("CHECK", " "+twitter_list.get(0).getScreen_name());
        return this.twitter_list;
    }


    public void getTwitterFollowingRequestAPI (String screen_name){
        Call<ResponseBody> responseBodyCall = ServiceTwitterFollowing.createServie().getTwitterFollowing(Constants.FOLLOWING_CURSOR,
                screen_name, Constants.FOLLOWING_SKIP_STATUS, Constants.FOLLOWING_INCLUDE_USER_ENTITIES);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("HALLO1", "Alhamdulillah we successfully request twitter API");

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                    String line=null;
                    try {
                        while((line=bufferedReader.readLine())!=null){
                            stringBuilder.append(line);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONArray users = jsonObject.getJSONArray("users");

                        Log.i("HALLO3", "Alhamdulillah, we have catched jsonArray of API " + users.toString());

                        if(users.toString()!=null){
                            Gson gson = new Gson();
                            twitter_friends = gson.fromJson(users.toString(), Twitter_friend_collection.class);

                            Log.i("HALLO4","Alhamdulillah, now we can get single data (full_name) "+twitter_friends.get(0).getName());
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for(Twitter_friend single_list : twitter_friends){
                        // long id, String name, String profile_image_url, String screen_name
                        Twitter_friend list_for_adapter = new Twitter_friend(single_list.getId(),
                                single_list.getName(),
                                single_list.getProfile_image_url(),
                                single_list.getScreen_name());
                        Log.i("HALLO01", " "+list_for_adapter.getScreen_name());

                        twitter_list.add(list_for_adapter);
                    }
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });




    }
}
