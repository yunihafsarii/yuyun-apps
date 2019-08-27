package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.instagram_model.TokenResponse;
import com.example.yunihafsari.fypversion3.social_media_api.authentication_insta.AuthenticationDialog;
import com.example.yunihafsari.fypversion3.social_media_api.authentication_insta.AuthenticationListener;
import com.example.yunihafsari.fypversion3.social_media_api.authentication_insta.ServiceGenerator;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;

public class SocialMediaLoginActivity extends AppCompatActivity implements AuthenticationListener{

    /*
    objectives
    1. must login and get token - DONE
    2. save that token somewhere and utilize them - DONE
    3. after login, you must change the something in UI, maybe button to textview (you are logged in as @username) - DONE
    4. the default UI display - DONE
     */
    private Toolbar toolbar;
    private Button insta_login;
    private TwitterLoginButton twitter_login;
    private TextView insta_after_login, twitter_after_login;
    private AuthenticationDialog dialog;
    private String mAuthToken;

    private SharedPrefUtil sharedPrefUtil;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media_login);

        sharedPrefUtil = new SharedPrefUtil(SocialMediaLoginActivity.this);

        //TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.social_media_toolbar);
        toolbar.setTitle("Social Media Connection");
        setSupportActionBar(toolbar);

        insta_login = (Button) findViewById(R.id.login_insta);
        twitter_login = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        insta_after_login = (TextView) findViewById(R.id.insta_after_login);
        twitter_after_login = (TextView) findViewById(R.id.twitter_after_login);

        String check_if_user_has_logined= new SharedPrefUtil(this).getString(Constants.INSTAGRAM_TOKEN);
        if(check_if_user_has_logined!=null){
            insta_login.setVisibility(View.INVISIBLE);
            insta_after_login.setVisibility(View.VISIBLE);
            insta_after_login.setText("you are logged in as "+new SharedPrefUtil(this).getString(Constants.INSTAGRAM_LOGIN_USERNAME));
        }else{
            insta_login.setVisibility(View.VISIBLE);
            insta_after_login.setVisibility(View.INVISIBLE);
        }

        String check_if_user_has_logined_to_Twitter = new SharedPrefUtil(this).getString(Constants.TWITTER_LOGIN_USERNAME);
        if(check_if_user_has_logined_to_Twitter!=null){
            twitter_login.setVisibility(View.INVISIBLE);
            twitter_after_login.setVisibility(View.VISIBLE);
            twitter_after_login.setText("you are logged in as "+new SharedPrefUtil(this).getString(Constants.TWITTER_LOGIN_USERNAME));
        }else{
            twitter_login.setVisibility(View.VISIBLE);
            insta_after_login.setVisibility(View.INVISIBLE);
        }

        dialog = new AuthenticationDialog(SocialMediaLoginActivity.this, this);

        insta_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });


        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        twitter_login.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                String username = result.data.getUserName();
                Log.i("DEBUGGING_LOGIN","username "+username);

                twitter_login.setVisibility(View.INVISIBLE);
                twitter_after_login.setText("you are logged in as "+username);
                twitter_after_login.setVisibility(View.VISIBLE);

                sharedPrefUtil.saveString(Constants.TWITTER_LOGIN_USERNAME,username);
                //Toast.makeText(getApplicationContext(), "welcome "+username, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
                Toast.makeText(getApplicationContext(), "failed to log in", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onCodeReceived(String code) {
        if(code!=null){
            dialog.dismiss();
        }

        // this is part of retrofit
        final Call<TokenResponse> accessToken = ServiceGenerator.createTokenService().getAccessToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URL, Constants.AUTHERIZATION_CODE, code, Constants.SCOPE);
        accessToken.enqueue(new retrofit2.Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    mAuthToken = response.body().getAccess_token();

                    //new SharedPrefUtil(getApplicationContext()).saveString(Constants.DEVELOPER_TOKEN, mAuthToken);

                    //Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    //intent.putExtra("AUTH_TOKEN", mAuthToken);
                    //startActivity(intent);

                    /*
                    i have my access_token
                    1. i need to request URL - http or retrofit
                    2. then, get data - json or gson
                     */
                    //Intent intent = new Intent(MainActivity.this, FollowingActivity.class);
                    //startActivity(intent);
                    insta_login.setVisibility(View.INVISIBLE);
                    insta_after_login.setText("you are logged in as "+response.body().getUser().getUsername());
                    insta_after_login.setVisibility(View.VISIBLE);

                    Log.i(TAG,"this is the result token "+mAuthToken);
                    Log.i(TAG, response.body().getUser().getFull_name());

                    // save my token to sharepreference

                    sharedPrefUtil.saveString(Constants.INSTAGRAM_TOKEN,mAuthToken);
                    sharedPrefUtil.saveString(Constants.INSTAGRAM_LOGIN_USERNAME,response.body().getUser().getUsername());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        twitter_login.onActivityResult(requestCode, resultCode, data);
    }
}
