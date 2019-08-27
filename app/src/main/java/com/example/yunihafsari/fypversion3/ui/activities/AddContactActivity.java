package com.example.yunihafsari.fypversion3.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity implements ContactListener{

    private Toolbar toolbar;
    private RecyclerView recyclerView_twitter, recyclerView_instagram;
    private Twitter_friend_collection twitter_friends;
    private FollowingCollection followings;
    private ArrayList<Twitter_friend> twitter_list = new ArrayList<Twitter_friend>();
    private ArrayList<Following> my_following=new ArrayList<Following>();

    private ArrayList<User> users_1 = new ArrayList<User>();

    // image related
    ImageView new_contact_image;
    ImageButton get_image_from_gallery;
    final int REQUEST_CODE_GALERY = 999;

    // sqlite related
    EditText name, number, email, relation, address;
    Button saveContact;
    ImageButton button_twitter, button_instagram;
    TextView username_twitter, username_instagram;
    private String get_twitter_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.toolbar_add_contact);
        toolbar.setTitle("Add Contact");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // image related
        new_contact_image = (ImageView) findViewById(R.id.my_friend_image);
        get_image_from_gallery = (ImageButton) findViewById(R.id.get_image_from_galery);
        get_image_from_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddContactActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALERY);
            }
        });


        // sqlite related
        name = (EditText) findViewById(R.id.friend_name);
        number = (EditText) findViewById(R.id.friend_number);
        email = (EditText) findViewById(R.id.friend_email);
        relation = (EditText) findViewById(R.id.friend_relation);
        address = (EditText) findViewById(R.id.friend_address);
        saveContact = (Button) findViewById(R.id.add_friend);
        username_twitter = (TextView) findViewById(R.id.username_twitter);
        username_instagram = (TextView) findViewById(R.id.username_insta);
        button_twitter = (ImageButton) findViewById(R.id.button_twitter);
        button_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTwitterDialog();
            }
        });
        button_instagram = (ImageButton) findViewById(R.id.button_insta);
        button_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstagramDialog();
            }
        });
        saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContactToDatabase();
            }
        });

        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();

                while(dataSnapshotIterator.hasNext()){
                    DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if(!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        users_1.add(user);
                        Log.i("hakiiiiim",""+user.email);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
        FollowingAdapter followingAdapter = new FollowingAdapter(my_following, "dialog", this, dialog, username_instagram);
        recyclerView_instagram.setAdapter(followingAdapter);
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
        Twitter_following_adapter twitter_following_adapter = new Twitter_following_adapter(twitter_list, "dialog", this, dialog, username_twitter);
        recyclerView_twitter.setAdapter(twitter_following_adapter);
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

                            //Log.i("HALLO4", "Alhamdulillah we can get perdata "+followings.get(1).getId());

                            my_following = new ArrayList<Following>();
                            // (String id, String username, String full_name, String profile_picture_url)
                            for(Following following1 : followings){
                                Following following_for_adapter = new Following(following1.getId(), following1.getUsername(),
                                        following1.getFull_name(), following1.getProfile_picture_url());
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


    private void saveContactToDatabase() {
        String contact_name = name.getText().toString();
        String contact_number = number.getText().toString();
        String contact_email = email.getText().toString();
        String contact_relation = relation.getText().toString();
        String contact_address = address.getText().toString();
        String contact_username_twitter = username_twitter.getText().toString();
        String contact_username_instagram = username_instagram.getText().toString();


        Integer firebase = checkEmailInDatabase(contact_email);
        Log.i("CRAZUYUNIYUYUN-------", "lemme know what you have to say "+firebase);

        Log.i("CRAZUYUNIYUYUN", contact_username_twitter);
        byte [] contact_image = imageViewToByte(new_contact_image);


        /***********************************************************************************************************
         * this is validation
         * 1. must fill username and phone number
         * 2. validation for phone number - must follow phone number (XXX-XXXXXXX) format
         * 3. validation for name - no validation, user can enter anything
         * **********************************************************************************************************/

        Validation validation = new Validation();
        boolean phone_number_validation = validation.phone_number_validation(contact_number);

        // EditText name, number, email, relation, address;
        if(name.getText().toString().length()==0 && number.getText().toString().length() ==0){
            name.setError("name and phone number not entered");
            name.requestFocus();
            number.setError("phone number and name not entered");
            number.requestFocus();
        }else if(name.getText().toString().length()==0){
            name.setError("name not entered");
            name.requestFocus();
        }else if(number.getText().toString().length()==0){
            number.setError("phone number not entered");
            number.requestFocus();
        }else if(!phone_number_validation){
            number.setError("format must be XXX-XXXXXXX");
            number.requestFocus();
        }else{
            // start saving to database
            DBAdapter db = new DBAdapter(this);

            // open db
            db.openDB();

            // insert to database
            long result = db.add(contact_name, contact_email, contact_number, contact_relation, contact_address, contact_username_twitter, contact_username_instagram, firebase, contact_image);

            if(result>0){
                name.setText("");
                number.setText("");
                email.setText("");
                relation.setText("");
                address.setText("");
                username_twitter.setText("");
                username_instagram.setText("");
                Toast.makeText(AddContactActivity.this, contact_name+" successfully added", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddContactActivity.this, "failed to add", Toast.LENGTH_SHORT).show();
            }

            // close db
            db.closeDB();

        }

    }




    private Integer checkEmailInDatabase(String email){

        Integer result =0;

        for(User user : users_1){
            Log.i("test222222",""+user.email);
        }

        Log.i("LELE---------","WHY DOES NOT YOU SHOWED UP, OMEGE"+users_1.size());

        for(User user : users_1){
            if(email.equals(user.email)){
                Log.i("LELE---------",""+user.email);
                result = 1;
            }
        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         if(item.getItemId()==android.R.id.home){
            this.finish();
             return true;
         }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE_GALERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALERY);
            }else{
                Toast.makeText(getApplicationContext(), "You dont have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri uri = data.getData();

        if(requestCode==REQUEST_CODE_GALERY && resultCode==RESULT_OK && data!= null){
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                new_contact_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onTwitterUsernameReceived(String username_twitter) {
        Log.i("CHECK","Alhamdulillah get twitter name to activity "+username_twitter);

    }

    @Override
    public void onInstagramUsernameReceived(String username_instagram) {
        Log.i("CHECK","Alhamdulillah get instagram name to activity "+username_instagram);
    }


}
