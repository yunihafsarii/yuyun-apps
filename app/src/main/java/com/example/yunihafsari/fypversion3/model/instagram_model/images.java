package com.example.yunihafsari.fypversion3.model.instagram_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class images {

    @SerializedName("thumbnail")
    private thumbnail thumbnail;

    public thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
