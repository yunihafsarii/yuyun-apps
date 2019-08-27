package com.example.yunihafsari.fypversion3.ui.fragments.menu_detail;


import android.database.Cursor;
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
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Instagram;
import com.example.yunihafsari.fypversion3.model.instagram_model.Media;
import com.example.yunihafsari.fypversion3.model.instagram_model.MediaCollection;
import com.example.yunihafsari.fypversion3.social_media_api.instagram.ServiceMedia;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.InstagramAdapter;
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
public class InstagramFragment extends Fragment {

    private MediaCollection medias;
    private TextView no_insta;
    private InstagramAdapter instagramAdapter;
    private RecyclerView recyclerView;
    private FloatingActionMenu materialDesignFAM;
    private com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;

    private ArrayList<Instagram> instagrams = new ArrayList<>();


    public InstagramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instagram, container, false);

        // in case no insta
        no_insta = (TextView) v.findViewById(R.id.no_insta);

        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView_yuni);
        // set properties of recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
                exchangeToTwitterFragment();
            }
        });

        return v;
    }

    private void exchangeToTwitterFragment(){
        TwitterFragment twitterFragment = new TwitterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.insta_twitter_layout, twitterFragment).commit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get data from database
        retrieveFromDatabase();
        // match with instagram name for this specific contact detail
        Log.i("QATAR_YUN","number of instagrams "+instagrams.size());
        String id_to_search="";
        for(Instagram instagram : instagrams){
            //Log.i("POINTT_DEBAGGING",""+instagram.instagram_username);
            if(new SharedPrefUtil(getContext()).getString(Constants.CONTACT_INSTAGRAM_USERNAME).equals(instagram.instagram_fullname)){
                //Log.i("POINTT_DEBAGGING",""+instagram.instagram_id);
                id_to_search= instagram.instagram_id;
            }
        }
        Log.i("POINTT_DEBAGGING",""+new SharedPrefUtil(getContext()).getString(Constants.CONTACT_INSTAGRAM_USERNAME));
        Log.i("POINTT_DEBAGGING",""+id_to_search);

        getMediaInstagramTask(id_to_search); // 1698017712 (yuni)  // 5331927321 (abu)



    }

    // SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getContext());
    //    email.setText(sharedPrefUtil.getString(Constants.CONTACT_EMAIL));
    // Constants.DEVELOPER_TOKEN
    private void getMediaInstagramTask(String id){
        Call<ResponseBody> responseBodyCall = ServiceMedia.createService().getMedia(id, new SharedPrefUtil(getContext()).getString(Constants.INSTAGRAM_TOKEN));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("HALLO5", "Alhamdulillah we now get API for media update");
                    /*
                    i must handle here, if empty meaning that that particular user is in private mode
                     */

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

                    Log.i("HALLO6","Alhamdulillah the stream now is in form of string /n"+stringBuilder.toString() );

                    try {
                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                        JSONArray data = jsonObject.getJSONArray("data");

                        if(data.toString()!=null){
                            Gson gson = new Gson();
                            medias = gson.fromJson(data.toString(), MediaCollection.class);
                            //Log.i("HALLO07", "Alhamdulillah now i get access each data of media (caption) "+medias.get(0).getCaption().getText());
                            //Log.i("HALLO07", "Alhamdulillah now i get access each data of media (username) "+medias.get(0).getUser().getUsername());

                            /*
                            1. result = arraylist of media
                            2. send = to adapter
                             */

                            ArrayList<Media> my_medias = new ArrayList<Media>();

                            // public Media(String id, images images, Caption caption, likes likes, Following user) {
                            for(Media media1 : medias){
                                Media media_for_adapter = new Media(media1.getId(), media1.getImages(), media1.getCaption(),media1.getLikes(), media1.getUser());
                                my_medias.add(media_for_adapter);
                            }

                            instagramAdapter = new InstagramAdapter(my_medias);
                            recyclerView.setAdapter(instagramAdapter);

                        }else{
                            Log.i("HALLO07", "they dont have instagram babyyyyyyyyyyyy");
                            no_insta.setVisibility(View.VISIBLE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.i("HALLO90","NO INSTA, PLEASE" );
                    no_insta.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    private void retrieveFromDatabase(){
        DBAdapter db = new DBAdapter(getActivity());

        // open
        db.openDB();

        // get friends from db
        Cursor c = db.getAllInstagramFriends();

        // loop thru data then adding to arraylist
        while(c.moveToNext()){
            int id = c.getInt(0);
            String instagram_id = c.getString(1);
            String instagram_full_name = c.getString(2);
            String instagram_username = c.getString(3);

            // create single instagram
            Instagram instagram = new Instagram(instagram_id, instagram_full_name, instagram_username);

            // add to instagram list
            instagrams.add(instagram);


        }

        // testing purpose
        Log.i("FYPPP", "this is size "+instagrams.size());
        for(Instagram instagram1 : instagrams){
            Log.i("FYPPP", ""+instagram1.instagram_username);
        }


        db.closeDB();
    }

}
