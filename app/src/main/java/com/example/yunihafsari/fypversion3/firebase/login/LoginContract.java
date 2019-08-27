package com.example.yunihafsari.fypversion3.firebase.login;

import android.app.Activity;

/**
 * Created by yunihafsari on 12/03/2017.
 */

public interface LoginContract {
    interface View{
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
    }

    interface Presenter{
        void Login(Activity activity, String email, String password);
    }

    interface Interactor{
        void performFirebaseLogin(Activity activity, String email, String password);
    }

    interface OnLoginListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
