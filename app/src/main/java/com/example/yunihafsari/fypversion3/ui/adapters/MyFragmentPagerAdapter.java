package com.example.yunihafsari.fypversion3.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.ui.fragments.main_fragment.ChatFragment;
import com.example.yunihafsari.fypversion3.ui.fragments.menu_detail.InformationFragment;
import com.example.yunihafsari.fypversion3.ui.fragments.menu_detail.SocialFragment;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

/**
 * Created by yunihafsari on 22/03/2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;
    private User user;
    private Context context;

    public MyFragmentPagerAdapter(Context context, FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.context = context;
        this.mTabTitles = mTabTitles;
    }


    @Override
    public Fragment getItem(int position) {

        //Log.i("CREISYEE", new SharedPrefUtil(context.getApplicationContext()).getString("receiver_email"));
        switch (position){
            case 0:
                return new InformationFragment();
            case 1:
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context.getApplicationContext());
                String receiver = sharedPrefUtil.getString(Constants.CHAT_RECEIVER);
                String uid = sharedPrefUtil.getString(Constants.CHAT_UID);
                String firebaseToken = sharedPrefUtil.getString(Constants.CHAT_FIREBASETOKEN);
                Log.i("DEBAGGING_RAISA",""+receiver+" "+uid);
                //return new ChatFragment2();
                return ChatFragment.newInstance(receiver, uid, firebaseToken);
            case 2:
                return new SocialFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }



}
