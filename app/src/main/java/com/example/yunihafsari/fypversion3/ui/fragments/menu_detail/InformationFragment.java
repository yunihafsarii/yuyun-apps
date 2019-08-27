package com.example.yunihafsari.fypversion3.ui.fragments.menu_detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {

    private TextView email, address, phone_number, relation, twitter, instagram;

    public InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information, container, false);

        email = (TextView) v.findViewById(R.id.info_email);
        address = (TextView) v.findViewById(R.id.info_address);
        phone_number = (TextView) v.findViewById(R.id.info_phone_number);
        relation = (TextView) v.findViewById(R.id.info_relation);
        twitter = (TextView) v.findViewById(R.id.info_twitter);
        instagram = (TextView) v.findViewById(R.id.info_instagram);

        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getContext());
        email.setText(sharedPrefUtil.getString(Constants.CONTACT_EMAIL));
        address.setText(sharedPrefUtil.getString(Constants.CONTACT_ADDRESS));
        phone_number.setText(sharedPrefUtil.getString(Constants.CONTACT_PHONE_NUMBER));
        relation.setText(sharedPrefUtil.getString(Constants.CONTACT_RELATION));
        twitter.setText(sharedPrefUtil.getString(Constants.CONTACT_TWITTER_USERNAME));
        instagram.setText(sharedPrefUtil.getString(Constants.CONTACT_INSTAGRAM_USERNAME));

        Log.i("CHECK","address "+sharedPrefUtil.getString(Constants.CONTACT_ADDRESS));
        Log.i("CHECK", "phone number "+sharedPrefUtil.getString(Constants.CONTACT_PHONE_NUMBER));

        return v;
    }

}
