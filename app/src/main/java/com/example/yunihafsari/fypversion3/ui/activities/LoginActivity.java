package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.ui.fragments.main_fragment.LoginFragment;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "vAcHgOw5re66apoKP0bmHOGMP";
    private static final String TWITTER_SECRET = "LRJW3Bh4ICHrfdYINvuO4u1YlIIxL8choBAxda4U1lw5iDdQeP";


    // start intent will be used by other activity
    public static void startIntent (Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startIntent(Context context, int flags){
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        setFragment();
    }

    private void setFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_login,
                LoginFragment.newInstance(),
                LoginFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
