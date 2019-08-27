package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.ui.fragments.main_fragment.ChatFragment;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MenuItem settingMenu_lock, settingMenu_unlock;

    // this is fo another activity or fragment
    public static void startActivity(Context context,
                                     String receiver,
                                     String receiverUid,
                                     String firebaseToken){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getExtras().getString(Constants.ARG_RECEIVER));

        setupFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        settingMenu_unlock = menu.findItem(R.id.unlock_message);
        new SharedPrefUtil(this).saveBoolean(Constants.MESSAGE_ENCRYPTION, false);
        settingMenu_lock = menu.findItem(R.id.lock_message);
        settingMenu_lock.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.unlock_message){
            // change the picture to lock
            settingMenu_lock.setVisible(true);
            settingMenu_unlock.setVisible(false);
            Toast.makeText(this,"message will be encrypted",Toast.LENGTH_LONG).show();
            new SharedPrefUtil(this).saveBoolean(Constants.MESSAGE_ENCRYPTION, true);
        }else if(id==R.id.lock_message){
            settingMenu_unlock.setVisible(true);
            settingMenu_lock.setVisible(false);
            Toast.makeText(this,"message will not be encrypted",Toast.LENGTH_LONG).show();
            new SharedPrefUtil(this).saveBoolean(Constants.MESSAGE_ENCRYPTION, false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupFragment() {
        FragmentTransaction frafmentTransaction = getSupportFragmentManager().beginTransaction();
        frafmentTransaction.replace(R.id.frame_layout_content_chat,
                ChatFragment.newInstance(getIntent().getExtras().getString(Constants.ARG_RECEIVER),
                        getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID),
                        getIntent().getExtras().getString(Constants.ARG_FIREBASE_TOKEN)),
                ChatFragment.class.getSimpleName());
        frafmentTransaction.commit();
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseChatMainAPP.setChatApplicationOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseChatMainAPP.setChatApplicationOpen(false);
    }
     */
}
