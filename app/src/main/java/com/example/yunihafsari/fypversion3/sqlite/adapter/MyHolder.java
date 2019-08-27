package com.example.yunihafsari.fypversion3.sqlite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;

/**
 * Created by yunihafsari on 06/04/2017.
 */

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView name;
    TextView phone_number;
    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        // ASSIGN FROM MODEL XML
        imageView = (ImageView) itemView.findViewById(R.id.model_image);
        name = (TextView) itemView.findViewById(R.id.model_name);
        phone_number = (TextView) itemView.findViewById(R.id.model_number);

        //itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
