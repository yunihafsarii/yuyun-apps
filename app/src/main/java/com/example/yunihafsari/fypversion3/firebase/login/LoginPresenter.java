package com.example.yunihafsari.fypversion3.firebase.login;

import android.app.Activity;

/**
 * Created by yunihafsari on 12/03/2017.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnLoginListener {

    private LoginContract.View loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractor(this);
    }

    @Override
    public void onSuccess(String message) {
        loginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        loginView.onLoginFailure(message);
    }

    @Override
    public void Login(Activity activity, String email, String password) {
        loginInteractor.performFirebaseLogin(activity, email, password);
    }
}
