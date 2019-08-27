package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.firebase.users.get.GetUserContract;
import com.example.yunihafsari.fypversion3.firebase.users.get.GetUsersPresenter;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.ui.adapters.MyFragmentPagerAdapter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactDetailActivity extends AppCompatActivity implements GetUserContract.View {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private GetUsersPresenter getUsersPresenter;
    private User user;
    private ArrayList<User> users_result = new ArrayList<>();

    private MenuItem settingMenu_lock, settingMenu_unlock;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        getUsersPresenter = new GetUsersPresenter(this);
        getUsersPresenter.getAllUsers();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(new SharedPrefUtil(this).getString(Constants.CONTACT_NAME));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Log.i("CRISYEEE", users_result.get(0).email);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        settingMenu_unlock = menu.findItem(R.id.unlock_message);
        new SharedPrefUtil(this).saveBoolean(Constants.MESSAGE_ENCRYPTION, false);
        settingMenu_lock = menu.findItem(R.id.lock_message);
        settingMenu_lock.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_contact) {
            startActivity(new Intent(ContactDetailActivity.this, EditContactActivity.class));
        }else if(id==R.id.unlock_message){
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
        }else if(id==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
        int count=0;
        for(int position=0; position<users.size(); position++){
            Log.i("BRUNOOOMARSSS",users.get(position).email);
            Log.i("DEBUGGING",""+sharedPrefUtil.getString(Constants.CONTACT_EMAIL));
            if(users.get(position).email.equals(sharedPrefUtil.getString(Constants.CONTACT_EMAIL))){
                //(String uid, String email, String firebaseToken)
                user = new User(users.get(position).uid, users.get(position).email, users.get(position).firebaseToken);
                Log.i("TIMBERLAKE",users.get(position).email);
                count++;
                //SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
                sharedPrefUtil.saveString(Constants.CHAT_RECEIVER,users.get(position).email);
                sharedPrefUtil.saveString(Constants.CHAT_UID,users.get(position).uid);
                sharedPrefUtil.saveString(Constants.CHAT_FIREBASETOKEN,users.get(position).firebaseToken);

            }

        }

        if(count==0){
                Log.i("CRISTIANO","this guy havent installed your app yet");
                sharedPrefUtil.saveString(Constants.CHAT_RECEIVER,"yuni_zero");
                sharedPrefUtil.saveString(Constants.CHAT_UID,"yuni_zero");
                sharedPrefUtil.saveString(Constants.CHAT_FIREBASETOKEN,"yuni_zero");


        }


        viewPager.setAdapter(new MyFragmentPagerAdapter(this, getSupportFragmentManager(), getResources().getStringArray(R.array.titles_tab)));

        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onGetAllUsersFailre(String message) {

    }

}
