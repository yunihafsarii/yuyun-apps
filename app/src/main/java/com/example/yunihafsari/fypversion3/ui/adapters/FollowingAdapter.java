package com.example.yunihafsari.fypversion3.ui.adapters;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.instagram_model.Following;
import com.example.yunihafsari.fypversion3.sqlite.adapter.ItemClickListener;
import com.example.yunihafsari.fypversion3.utils.ContactListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yunihafsari on 17/04/2017.
 */

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.Holder>{

    private ArrayList<Following> followings;
    private String sender;
    private ContactListener contactListener;
    private Dialog dialog;
    private TextView textView;

    public FollowingAdapter(ArrayList<Following> followings, String sender, ContactListener contactListener, Dialog dialog, TextView textView) {
        this.followings = followings;
        this.sender = sender;
        this.contactListener = contactListener;
        this.dialog = dialog;
        this.textView = textView;
    }

    public FollowingAdapter(ArrayList<Following> followings, String sender) {
        this.followings = followings;
        this.sender = sender;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row;
        if(sender.equals("dialog")){
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.instagram_following2, parent, false);
        }else {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.instagram_following, parent, false);
        }
        return new FollowingAdapter.Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Following single_following = followings.get(position);

        final int position2 = holder.getAdapterPosition();

        Picasso.with(holder.following_image.getContext()).load(single_following.getProfile_picture_url()).into(holder.following_image);
        holder.following_username.setText("@"+single_following.getUsername());
        holder.following_full_name.setText(single_following.getFull_name());


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClickListener(View v, int pos) {
                    contactListener.onInstagramUsernameReceived(followings.get(position2).getUsername());
                    dialog.dismiss();
                    textView.setText(followings.get(position2).getUsername());
                }
            });


    }

    @Override
    public int getItemCount() {
        return followings.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView following_image;
        private TextView following_username;
        private TextView following_full_name;
        private ItemClickListener itemClickListener;

        public Holder(View itemView) {
            super(itemView);

            following_image = (ImageView) itemView.findViewById(R.id.following_image);
            following_username = (TextView) itemView.findViewById(R.id.following_username);
            following_full_name = (TextView) itemView.findViewById(R.id.following_full_name);

            if(sender.equals("dialog")){
                itemView.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
    }


}
