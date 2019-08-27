package com.example.yunihafsari.fypversion3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.ui.activities.AboutActivity;
import com.example.yunihafsari.fypversion3.ui.activities.AddContactActivity;
import com.example.yunihafsari.fypversion3.ui.activities.DuplicationActivity;
import com.example.yunihafsari.fypversion3.ui.activities.LoginActivity;
import com.example.yunihafsari.fypversion3.ui.activities.SocialMediaLoginActivity;
import com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom.ChatListFragment;
import com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom.ContactListFragment;
import com.example.yunihafsari.fypversion3.ui.fragments.menu_bottom.SocialListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity {

    BottomBar bottomBar;
    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar toolbar;



    public static void startActivity(Context context, int flags){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setToolbar();

        setBottomBar(savedInstanceState);

        setNavigationBar(savedInstanceState);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_contact) {
            startActivity(new Intent(MainActivity.this, AddContactActivity.class));
        }
        if( id == R.id.add_setting){
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        //TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Contact");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }



    private void setBottomBar(Bundle savedInstanceState) {
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottom_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId==R.id.bottom_contact_phone){
                    ContactListFragment contactListFragment = new ContactListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment, contactListFragment).commit();
                }else if(menuItemId==R.id.bottom_chat){
                    ChatListFragment chatListFragment = ChatListFragment.newInstance(ChatListFragment.TYPE_ALL);
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment, chatListFragment).commit();
                }else if(menuItemId==R.id.bottom_social){
                    SocialListFragment socialListFragment = new SocialListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_for_fragment ,socialListFragment).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
    }





    private void setNavigationBar(Bundle savedInstanceState) {
        // NAVIGATION HEADER
        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.footbal)
                .addProfiles(
                        new ProfileDrawerItem().withName(FirebaseAuth.getInstance().getCurrentUser().getEmail()).withIcon(getResources().getDrawable(R.drawable.profile_dec))
                ).withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                }).build();

        // NAVIGATION DRAWER
        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        // note, as for duplication and myprofile can be put in fragment instead of activity
                        // it can just replace FrameLayout from MainActivity

                        if(id==0){
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        }else if(id==1){
                            startActivity(new Intent(MainActivity.this, DuplicationActivity.class));
                        }else if(id==2){
                            Log.i("DEBUGGING", "this is social media connection");
                            startActivity(new Intent(MainActivity.this, SocialMediaLoginActivity.class));
                        }else if(id==3){
                            // this is logout
                            logout();
                            Log.i("YUNI", "this is the logout");
                            //Toast.makeText(MainActivity.this, "LOGOUT", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MainActivity.this, TestingActivity.class));
                        }else{
                            //Toast.makeText(MainActivity.this, "ABOUT", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Contact").withIcon(getResources().getDrawable(R.drawable.location)).withIdentifier(0));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Duplication").withIcon(getResources().getDrawable(R.drawable.ic_action_search)).withIdentifier(1));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Social Media").withIcon(getResources().getDrawable(R.drawable.share)).withIdentifier(2));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("About Formate").withIcon(getResources().getDrawable(R.drawable.about)).withIdentifier(3));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Logout").withIcon(getResources().getDrawable(R.drawable.logut)).withIdentifier(3));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("My Profile").withIcon(getResources().getDrawable(R.drawable.ic_action_myprofile)).withIdentifier(2));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Share this App").withIcon(getResources().getDrawable(R.drawable.share)));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Rate this App").withIcon(getResources().getDrawable(R.drawable.rate)));
        navigationDrawerLeft.addItem(new DividerDrawerItem());
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Help and Support").withIcon(getResources().getDrawable(R.drawable.help)).withIdentifier(4));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("About Formate").withIcon(getResources().getDrawable(R.drawable.about)).withIdentifier(3));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Logout").withIcon(getResources().getDrawable(R.drawable.logut)).withIdentifier(4));
        //navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Logout").withIcon(getResources().getDrawable(R.drawable.logut)).withIdentifier(2));
    }



    private void logout(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(FirebaseAuth.getInstance().getCurrentUser() != null){
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(MainActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
                            LoginActivity.startIntent(MainActivity.this, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        }else{
                            Toast.makeText(MainActivity.this, "No user logged in yet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}


