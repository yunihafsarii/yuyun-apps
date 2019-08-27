package com.example.yunihafsari.fypversion3.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestingActivity extends AppCompatActivity {

     /*
      how should i do identify key people
        1. in firebase (chat room table), get id
        2. check in which id got most of the child
        3. i must shorten those,
        4. myid_friendid - get friend id
        5. put that in the list
    */

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



        /*
        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all email that have registered to my firebase
                // create chat room like in firebase format , myuid_frienduid
                // using if else (myuid_frienduid), count children in that chat room
                // yang paling banyak munculkan, di list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         */


        databaseReference.child(Constants.ARG_USERS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                while(dataSnapshotIterator.hasNext()){
                    DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if(!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        users.add(user);
                        Log.i("hakiiiiim",""+user.uid+""+user.email);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(User user : users){
                    if(dataSnapshot.hasChild( FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid) ){
                        //dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid).getChildrenCount();
                        Log.i("DEBUGGING", user.email+" get this much chat "+dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"_"+user.uid).getChildrenCount());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
