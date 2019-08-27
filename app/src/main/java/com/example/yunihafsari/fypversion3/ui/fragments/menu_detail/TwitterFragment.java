package com.example.yunihafsari.fypversion3.ui.fragments.menu_detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.github.clans.fab.FloatingActionMenu;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterFragment extends ListFragment {

    private FloatingActionMenu materialDesignFAM;
    private com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;


    public TwitterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_twitter, container, false);

        materialDesignFAM = (FloatingActionMenu) v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_instagram);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_twitter);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                exchangeToInstagramFragment();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked


            }
        });

        return v;

    }

    private void exchangeToInstagramFragment(){
        InstagramFragment instagramFragment = new InstagramFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.insta_twitter_layout, instagramFragment).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(new SharedPrefUtil(getContext()).getString(Constants.CONTACT_TWITTER_USERNAME))
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);
    }
}
