package com.example.yunihafsari.fypversion3.social_media_api.twitter;

import android.content.Context;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by yunihafsari on 11/04/2017.
 */

public class TimelineSearch{

    Context context;

    public void search(String name){

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("fabric")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(context.getApplicationContext())
                .setTimeline(userTimeline)
                .build();

    }
}
