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
import com.example.yunihafsari.fypversion3.model.instagram_model.Following;
import com.example.yunihafsari.fypversion3.model.instagram_model.FollowingCollection;
import com.example.yunihafsari.fypversion3.social_media_api.instagram.ServiceFollowing;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.FollowingAdapter;
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
public class InstagramListFragment extends Fragment {

    private FollowingCollection followings;
    private RecyclerView recyclerView;
    private FollowingAdapter followingAdapter;

    private FloatingActionMenu materialDesignFAM;
    private com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;


    public InstagramListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_yuni_instagram);


        materialDesignFAM = (FloatingActionMenu) v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_instagram);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_twitter);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked


            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                exchangeToTwitter();

            }
        });

        return v;
    }

    private void exchangeToTwitter(){
        SocialListFragment socialListFragment = new SocialListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment, socialListFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // set properties of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getInstagramData();
    }

    // Constants.DEVELOPER_TOKEN
    private void getInstagramData(){
        Call<ResponseBody> responseBodyCall= ServiceFollowing.createServie().getFollowing(new SharedPrefUtil(getContext()).getString(Constants.INSTAGRAM_TOKEN));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("HALLO", "Alhamdulillah calling api is successful");

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

                    Log.i("HALLO2","Alhamdulillah the stream now is in form of string /n"+stringBuilder.toString() );
                    try {
                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONArray data = jsonObject.getJSONArray("data");

                        Log.i("HALLO3","Alhamdulillah, we have catched jsonArray of API "+data.toString());

                        if(data.toString()!=null){
                            Gson gson = new Gson();
                            followings = gson.fromJson(data.toString(),FollowingCollection.class);

                            //Log.i("HALLO4", "Alhamdulillah we can get perdata "+followings.get(1).getId());

                            ArrayList<Following> my_following = new ArrayList<Following>();
                            // (String id, String username, String full_name, String profile_picture_url)
                            for(Following following1 : followings){
                                Log.i("QATAR",""+following1.getId()+" "+following1.getFull_name());

                                /*
                                HERE IS WHERE I SAVE DATA IN DATABASE
                                 */
                                String instagram_id = following1.getId();
                                String instagram_full_name = following1.getFull_name();
                                String instagram_username = following1.getUsername();

                                store_instagramInfo_to_database(instagram_id, instagram_full_name, instagram_username);

                                Following following_for_adapter = new Following(following1.getId(), following1.getUsername(), following1.getFull_name(), following1.getProfile_picture_url());
                                my_following.add(following_for_adapter);
                            }

                            followingAdapter = new FollowingAdapter(my_following,"fragment");
                            recyclerView.setAdapter(followingAdapter);




                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    public void store_instagramInfo_to_database(String id, String full_name, String username){
        // start saving to database
        DBAdapter db = new DBAdapter(getContext());

        // open db
        db.openDB();

        // insert to database
        long result = db.add_to_instagram_table(id, full_name, username);

        if(result>0){
            Log.i("QATAR",""+full_name);
            Log.i("QATAR","successfully added data to instagram table");
        }else{
            Log.i("QATAR","failed in adding data to instagram table");
        }

        // close db
        db.closeDB();
    }
}
