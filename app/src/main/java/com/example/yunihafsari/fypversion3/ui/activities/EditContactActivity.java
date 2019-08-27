package com.example.yunihafsari.fypversion3.ui.activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.instagram_model.Following;
import com.example.yunihafsari.fypversion3.model.instagram_model.FollowingCollection;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend_collection;
import com.example.yunihafsari.fypversion3.social_media_api.instagram.ServiceFollowing;
import com.example.yunihafsari.fypversion3.social_media_api.twitter.ServiceTwitterFollowing;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.FollowingAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.Twitter_following_adapter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.ContactListener;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.example.yunihafsari.fypversion3.utils.Validation;
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

public class EditContactActivity extends AppCompatActivity implements ContactListener{

    private Toolbar toolbar;
    private EditText email, address, phone_number, relation;
    private TextView twitter, instagram;
    private Button edit;
    private ImageButton button_twitter, button_instagram;

    private Twitter_friend_collection twitter_friends;
    private FollowingCollection followings;
    private ArrayList<Following> my_following=new ArrayList<Following>();
    private ArrayList<Twitter_friend> twitter_list = new ArrayList<Twitter_friend>();

    private RecyclerView recyclerView_twitter, recyclerView_instagram;

    private SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.toolbar_edit_contact);
        toolbar.setTitle("Edit Contact");
        setSupportActionBar(toolbar);

        sharedPrefUtil = new SharedPrefUtil(this);

        email = (EditText) findViewById(R.id.info_email);
        address = (EditText) findViewById(R.id.info_address);
        phone_number = (EditText) findViewById(R.id.info_phone_number);
        relation = (EditText) findViewById(R.id.info_relation);
        twitter = (TextView) findViewById(R.id.info_twitter);
        instagram = (TextView) findViewById(R.id.info_instagram);
        edit = (Button) findViewById(R.id.edit_friend);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_contact(sharedPrefUtil.getInteger(Constants.CONTACT_ID));
            }
        });

        button_twitter = (ImageButton) findViewById(R.id.edit_twitter);
        button_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwitterDialog();
            }
        });

        button_instagram = (ImageButton) findViewById(R.id.edit_instagram);
        button_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstagramDialog();
            }
        });


        email.setText(sharedPrefUtil.getString(Constants.CONTACT_EMAIL));
        address.setText(sharedPrefUtil.getString(Constants.CONTACT_ADDRESS));
        phone_number.setText(sharedPrefUtil.getString(Constants.CONTACT_PHONE_NUMBER));
        relation.setText(sharedPrefUtil.getString(Constants.CONTACT_RELATION));
        twitter.setText(sharedPrefUtil.getString(Constants.CONTACT_TWITTER_USERNAME));
        instagram.setText(sharedPrefUtil.getString(Constants.CONTACT_INSTAGRAM_USERNAME));

    }

    private void edit_contact(int id){
        String contact_email = email.getText().toString();
        String contact_address = address.getText().toString();
        String contact_phone_number = phone_number.getText().toString();
        String contact_relation = relation.getText().toString();
        String contact_twitter = twitter.getText().toString();
        String contact_instagram = instagram.getText().toString();


        /***********************************************************************************************************
         * this is validation
         * 1. must fill username and phone number
         * 2. validation for phone number - must follow phone number (XXX-XXXXXXX) format
         * **********************************************************************************************************/

        Validation validation = new Validation();
        boolean phone_number_validation = validation.phone_number_validation(contact_phone_number);

        // private EditText email, address, phone_number, relation;
        if(phone_number.getText().toString().length()==0){
            phone_number.setError("phone number not entered");
            phone_number.requestFocus();
        }else if(!phone_number_validation){
            phone_number.setError("format must be XXX-XXXXXXX");
            phone_number.requestFocus();
        }else{
            DBAdapter db = new DBAdapter(this);
            db.openDB();

            long result = db.UPDATE(id,new SharedPrefUtil(this).getString(Constants.CONTACT_NAME), contact_email, contact_phone_number,
                    contact_relation, contact_address, contact_twitter, contact_instagram);

            if(result>0){
                Toast.makeText(this,"Edit Successfully",Toast.LENGTH_LONG).show();
                // email, address, phone_number, relation;
                email.setText("");
                address.setText("");
                phone_number.setText("");
                relation.setText("");
            }else{
                Toast.makeText(this,"Edit failed", Toast.LENGTH_LONG).show();
            }

            db.closeDB();
        }
    }



    private void showTwitterDialog(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.twitter_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();


        recyclerView_twitter = (RecyclerView) dialog.findViewById(R.id.myRecycler_twitter);
        recyclerView_twitter.setHasFixedSize(true);
        recyclerView_twitter.setLayoutManager(new LinearLayoutManager(this));
        ///recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, R.drawable.));
        getTwitterFollowingRequestAPI(new SharedPrefUtil(this).getString(Constants.TWITTER_LOGIN_USERNAME));
        Twitter_following_adapter twitter_following_adapter = new Twitter_following_adapter(twitter_list, "dialog", this, dialog, twitter);
        recyclerView_twitter.setAdapter(twitter_following_adapter);
    }


    private void showInstagramDialog(){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.instagram_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();

        recyclerView_instagram = (RecyclerView) dialog.findViewById(R.id.myRecycler_insta);
        recyclerView_instagram.setHasFixedSize(true);
        recyclerView_instagram.setLayoutManager(new LinearLayoutManager(this));
        getInstagramData();
        FollowingAdapter followingAdapter = new FollowingAdapter(my_following, "dialog", this, dialog, instagram);
        recyclerView_instagram.setAdapter(followingAdapter);
    }


    private void getInstagramData(){
        Call<ResponseBody> responseBodyCall= ServiceFollowing.createServie().getFollowing(new SharedPrefUtil(this).getString(Constants.INSTAGRAM_TOKEN));
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

                           // Log.i("HALLO4", "Alhamdulillah we can get perdata "+followings.get(1).getId());

                            my_following = new ArrayList<Following>();
                            // (String id, String username, String full_name, String profile_picture_url)
                            for(Following following1 : followings){
                                Following following_for_adapter = new Following(following1.getId(), following1.getUsername(), following1.getFull_name(), following1.getProfile_picture_url());
                                my_following.add(following_for_adapter);
                            }


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



                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }


    @Override
    public void onTwitterUsernameReceived(String username_twitter) {

    }

    @Override
    public void onInstagramUsernameReceived(String username_instagram) {

    }
}
