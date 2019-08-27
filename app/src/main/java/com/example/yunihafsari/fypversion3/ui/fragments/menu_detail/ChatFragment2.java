package com.example.yunihafsari.fypversion3.ui.fragments.menu_detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment2 extends Fragment {


    /*
how should i do identify key people
1. in firebase (chat room table), get id
2. check in which id got most of the child
3. i must shorten those,
4. myid_friendid - get friend id
5. put that in the list
 */


    public ChatFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat2, container, false);
    }

}



