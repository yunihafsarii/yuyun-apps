package com.example.yunihafsari.fypversion3.firebase.users.add;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public class AddUserPresenter implements AddUserContract.Presenter, AddUserContract.onUserDatabaseListener {

    private AddUserContract.View view;
    private AddUserInteractor addUserInteractor;

    public AddUserPresenter(AddUserContract.View view) {
        this.view = view;
        addUserInteractor = new AddUserInteractor(this);
    }

    @Override
    public void addUser(Context context, FirebaseUser firebaseUser) {
        addUserInteractor.addUserToDatabase(context, firebaseUser);
    }

    @Override
    public void onSuccess(String message) {
        view.addUserSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        view.addUserFailure(message);
    }
}
