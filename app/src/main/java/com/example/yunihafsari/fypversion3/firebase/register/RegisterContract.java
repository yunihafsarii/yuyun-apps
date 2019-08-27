package com.example.yunihafsari.fypversion3.firebase.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by yunihafsari on 24/03/2017.
 */

public interface RegisterContract {

    interface View{
        void onRegistrationSuccess(FirebaseUser firebaseUser);
        void onRegistrationFailure(String message);
    }

    interface Presenter{
        void register(Activity activity, String email, String password);
    }

    interface Interactor{
        void performFirebaseRegistration(Activity activity, String email, String password);
    }

    interface OnRegistrationListener{
        void onSuccess(FirebaseUser firebaseUser);
        void onFailure(String message);
    }
}