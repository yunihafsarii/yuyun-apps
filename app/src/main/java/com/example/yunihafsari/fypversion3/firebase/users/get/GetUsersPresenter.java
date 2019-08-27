package com.example.yunihafsari.fypversion3.firebase.users.get;

import com.example.yunihafsari.fypversion3.model.apps_model.User;

import java.util.List;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public class GetUsersPresenter implements GetUserContract.Presenter, GetUserContract.onGetAllUsersListener {

    private GetUserContract.View view;
    private GetUserInteractor getUserInteractor;

    public GetUsersPresenter(GetUserContract.View view) {
        this.view = view;
        getUserInteractor = new GetUserInteractor(this);
    }

    @Override
    public void getAllUsers() {
        getUserInteractor.getAllUserFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        view.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        view.onGetAllUsersFailre(message);
    }
}
