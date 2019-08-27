package com.example.yunihafsari.fypversion3.model.instagram_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class Caption {

    @SerializedName("id")
    private String id;

    @SerializedName("text")
    private String text;

    @SerializedName("created_time")
    private String created_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
