package com.example.yunihafsari.fypversion3.model.apps_model;

/**
 * Created by yunihafsari on 24/03/2017.
 */

public class User {
    public String uid;
    public String email;
    public String firebaseToken;

    public User(){

    }

    public User(String uid, String email, String firebaseToken) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }
}
