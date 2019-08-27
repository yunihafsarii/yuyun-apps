package com.example.yunihafsari.fypversion3.model.instagram_model;

/**
 * Created by yunihafsari on 08/06/2017.
 */

public class TokenResponse {

    private InstagramUser user;
    private String access_token;

    public InstagramUser getUser() {
        return user;
    }

    public void setUser(InstagramUser user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "ClassPojo [user = "+user+", access_token = "+access_token+"]";
    }
}
