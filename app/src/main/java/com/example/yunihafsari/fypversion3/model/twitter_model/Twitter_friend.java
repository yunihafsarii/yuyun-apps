package com.example.yunihafsari.fypversion3.model.twitter_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 17/04/2017.
 */

public class Twitter_friend {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_image_url")
    private String profile_image_url;

    @SerializedName("screen_name")
    private String screen_name;

    public Twitter_friend(long id, String name, String profile_image_url, String screen_name) {
        this.id = id;
        this.name = name;
        this.profile_image_url = profile_image_url;
        this.screen_name = screen_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }
}
