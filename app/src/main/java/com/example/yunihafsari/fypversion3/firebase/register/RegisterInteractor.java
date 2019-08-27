package com.example.yunihafsari.fypversion3.firebase.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by yunihafsari on 24/03/2017.
 */

public class RegisterInteractor implements RegisterContract.Interactor {

    private static final String TAG = RegisterInteractor.class.getSimpleName();

    private RegisterContract.OnRegistrationListener registrationListener;

    public RegisterInteractor(RegisterContract.OnRegistrationListener registrationListener) {
        this.registrationListener = registrationListener;
    }

    @Override
    public void performFirebaseRegistration(Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "registration successful "+task.isSuccessful());
                        if(task.isSuccessful()){
                            registrationListener.onSuccess(task.getResult().getUser());
                        }else{
                            registrationListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }
}
