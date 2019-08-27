package com.example.yunihafsari.fypversion3.firebase.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yunihafsari on 12/03/2017.
 */

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.OnLoginListener mOnLoginListener;

    public LoginInteractor(LoginContract.OnLoginListener mOnLoginListener) {
        this.mOnLoginListener = mOnLoginListener;
    }

    @Override
    public void performFirebaseLogin(final Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginInteractor", "firebase login complete");
                        if(task.isSuccessful()){
                            // update token in database
                            updateFirebaseToken(task.getResult().getUser().getUid(),
                                    new SharedPrefUtil(activity.getApplicationContext()).getString(Constants.ARG_FIREBASE_TOKEN, null));
                            mOnLoginListener.onSuccess(task.getResult().toString());

                        }else{
                            mOnLoginListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    private void updateFirebaseToken(String uid, String token){
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.ARG_USERS)
                .child(uid)
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token);
        Log.i("token updated",""+uid+" "+token);
    }
}
