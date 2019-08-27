package com.example.yunihafsari.fypversion3.ui.fragments.menu_detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {


    public SocialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TwitterFragment twitterFragment = new TwitterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.insta_twitter_layout, twitterFragment).commit();
    }
}
