package com.example.yunihafsari.fypversion3.firebase.users.get;

import com.example.yunihafsari.fypversion3.model.apps_model.User;

import java.util.List;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public interface GetUserContract {
    interface View{
        void onGetAllUsersSuccess(List<User> users);
        void onGetAllUsersFailre(String message);
    }

    interface Presenter{
        void getAllUsers();
    }

    interface Interactor{
        void getAllUserFromFirebase();
    }

    interface onGetAllUsersListener{
        void onGetAllUsersSuccess(List<User> users);
        void onGetAllUsersFailure(String message);
    }
}
