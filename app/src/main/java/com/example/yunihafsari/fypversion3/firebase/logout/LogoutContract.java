package com.example.yunihafsari.fypversion3.firebase.logout;

/**
 * Created by yunihafsari on 05/06/2017.
 */

public interface LogoutContract {

    interface View{
        void onLogoutSuccess(String message);
        void onLogoutFailure(String message);
    }

    interface Presenter{
        void logout();
    }

    interface Interactor{
        void performFirebaseLogout();
    }

    interface OnLogoutListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
