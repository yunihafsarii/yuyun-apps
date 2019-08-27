package com.example.yunihafsari.fypversion3.firebase.users.add;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public interface AddUserContract {
    interface View{
        void addUserSuccess(String message);
        void addUserFailure(String message);
    }

    interface Presenter{
        void addUser(Context context, FirebaseUser firebaseUser);
    }

    interface Interactor{
        void addUserToDatabase(Context context, FirebaseUser firebaseUser);
    }

    interface onUserDatabaseListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
