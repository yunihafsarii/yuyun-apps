package com.example.yunihafsari.fypversion3.firebase.users.get;

import android.text.TextUtils;
import android.util.Log;

import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public class GetUserInteractor implements GetUserContract.Interactor {

    private GetUserContract.onGetAllUsersListener onGetAllUsersListener;


    public GetUserInteractor(GetUserContract.onGetAllUsersListener onGetAllUsersListener) {
        this.onGetAllUsersListener = onGetAllUsersListener;
    }

    @Override
    public void getAllUserFromFirebase() {
        Log.i("hakiiimmm","HAI FROM GET USER");
        FirebaseDatabase.getInstance().getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("hakiiimmm","HAI FROM GET USER");
                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                List<User> users = new ArrayList<>();
                while(dataSnapshotIterator.hasNext()){
                    DataSnapshot dataSnapshotChild = dataSnapshotIterator.next();
                    User user = dataSnapshotChild.getValue(User.class);
                    if(!TextUtils.equals(user.uid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        users.add(user);
                        Log.i("hakiiiiim",""+user.uid);
                    }
                }
                onGetAllUsersListener.onGetAllUsersSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
            }
        });
    }
}
