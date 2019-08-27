package com.example.yunihafsari.fypversion3.model.instagram_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class Media {

    @SerializedName("id")
    private String id;

    @SerializedName("images")
    private images images;

    @SerializedName("caption")
    private Caption caption;

    @SerializedName("likes")
    private likes likes;

    @SerializedName("user")
    private Following user;

    public Media(String id, images images, Caption caption, likes likes, Following user) {
        this.id = id;
        this.images = images;
        this.caption = caption;
        this.likes = likes;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public images getImages() {
        return images;
    }

    public void setImages(images images) {
        this.images = images;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public likes getLikes() {
        return likes;
    }

    public void setLikes(likes likes) {
        this.likes = likes;
    }

    public Following getUser() {
        return user;
    }

    public void setUser(Following user) {
        this.user = user;
    }
}
