package com.example.yunihafsari.fypversion3.model.instagram_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class likes {

    @SerializedName("count")
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
