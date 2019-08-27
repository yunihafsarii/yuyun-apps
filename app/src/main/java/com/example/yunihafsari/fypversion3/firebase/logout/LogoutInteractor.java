package com.example.yunihafsari.fypversion3.firebase.logout;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by yunihafsari on 05/06/2017.
 */

public class LogoutInteractor implements LogoutContract.Interactor{

    private LogoutContract.OnLogoutListener mOnLogoutListener;

    public LogoutInteractor(LogoutContract.OnLogoutListener onLogoutListener) {
        mOnLogoutListener = onLogoutListener;
    }

    @Override
    public void performFirebaseLogout() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
            mOnLogoutListener.onSuccess("Successfully logged out!");
        }else{
            mOnLogoutListener.onSuccess("No user logged in yet!");
        }
    }
}
