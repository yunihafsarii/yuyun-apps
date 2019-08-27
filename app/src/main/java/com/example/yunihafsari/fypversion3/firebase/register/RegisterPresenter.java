package com.example.yunihafsari.fypversion3.firebase.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by yunihafsari on 24/03/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegistrationListener{

    private RegisterContract.View view;
    private RegisterInteractor registerInteractor;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        registerInteractor = new RegisterInteractor(this);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        view.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String message) {
        view.onRegistrationFailure(message);
    }

    @Override
    public void register(Activity activity, String email, String password) {
        registerInteractor.performFirebaseRegistration(activity, email, password);
    }
}
