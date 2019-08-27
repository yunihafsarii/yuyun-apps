package com.example.yunihafsari.fypversion3.model.instagram_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class Following {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("profile_picture")
    private String profile_picture_url;

    public Following(String id, String username, String full_name, String profile_picture_url) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.profile_picture_url = profile_picture_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture_url() {
        return profile_picture_url;
    }

    public void setProfile_picture_url(String profile_picture_url) {
        this.profile_picture_url = profile_picture_url;
    }
}
