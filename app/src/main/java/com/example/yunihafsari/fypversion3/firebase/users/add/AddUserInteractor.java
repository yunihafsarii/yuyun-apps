package com.example.yunihafsari.fypversion3.firebase.users.add;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.User;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public class AddUserInteractor implements AddUserContract.Interactor {

    private AddUserContract.onUserDatabaseListener onUserDatabaseListener;

    public AddUserInteractor(AddUserContract.onUserDatabaseListener onUserDatabaseListener) {
        this.onUserDatabaseListener = onUserDatabaseListener;
    }

    @Override
    public void addUserToDatabase(final Context context, FirebaseUser firebaseUser) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(), new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN));
        databaseReference.child(Constants.ARG_USERS)
                .child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            onUserDatabaseListener.onSuccess(context.getString(R.string.user_successfully_added));
                        }else{
                            onUserDatabaseListener.onFailure(context.getString(R.string.user_unable_to_add));
                        }
                    }
                });
    }
}
