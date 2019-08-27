package com.example.yunihafsari.fypversion3.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.ui.fragments.duplication_fragment.DuplicateNameFragment;
import com.example.yunihafsari.fypversion3.ui.fragments.duplication_fragment.DuplicateNumberFragment;

/**
 * Created by yunihafsari on 02/05/2017.
 */

public class DuplicationFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;
    private User user;
    private Context context;

    public DuplicationFragmentPagerAdapter(Context context, FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new DuplicateNameFragment();
            case 1:
                return new DuplicateNumberFragment();
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
