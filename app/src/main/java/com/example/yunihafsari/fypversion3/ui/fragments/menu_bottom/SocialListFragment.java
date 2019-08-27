package com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend_collection;
import com.example.yunihafsari.fypversion3.social_media_api.function.GetFriendList;
import com.example.yunihafsari.fypversion3.social_media_api.twitter.ServiceTwitterFollowing;
import com.example.yunihafsari.fypversion3.ui.adapters.Twitter_following_adapter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.github.clans.fab.FloatingActionMenu;
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
 * A simple {@link Fragment} subclass.
 */
public class SocialListFragment extends Fragment{

    private Twitter_friend_collection twitter_friends;
    private RecyclerView recyclerView;
    private Twitter_following_adapter twitter_following_adapter;
    private FloatingActionMenu materialDesignFAM;
    private com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;
    private GetFriendList getFriendList;
    private ArrayList<Twitter_friend> twitter_list = new ArrayList<Twitter_friend>();

    public SocialListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_yuni_twitter);
        // set properties of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        materialDesignFAM = (FloatingActionMenu) v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_instagram);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_twitter);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                exchangeToInstagramList();

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked


            }
        });

        return v;
    }

    /*
    private void exchangeFragment(){
        TestingFragment testingFragment = new TestingFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment, testingFragment).commit();
    }
     */

    private void exchangeToInstagramList(){
        InstagramListFragment fragmentListFragment = new InstagramListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment, fragmentListFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getTwitterFollowingRequestAPI(new SharedPrefUtil(getContext()).getString(Constants.TWITTER_LOGIN_USERNAME));


    }


    private void getTwitterFollowingRequestAPI(String screen_name){
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


                    Log.i("HALLO2", "Alhamdulillah we can call my following "+stringBuilder.toString());

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

                        Log.i("TESTING", " "+twitter_list.get(0).getScreen_name());

                    }

                    twitter_following_adapter = new Twitter_following_adapter(twitter_list,"fragment");
                    recyclerView.setAdapter(twitter_following_adapter);


                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });

    }



}


/*
twitter_following_adapter = new Twitter_following_adapter(twitter_list);
recyclerView.setAdapter(twitter_following_adapter);
 */