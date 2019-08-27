package com.example.yunihafsari.fypversion3.ui.adapters;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.twitter_model.Twitter_friend;
import com.example.yunihafsari.fypversion3.sqlite.adapter.ItemClickListener;
import com.example.yunihafsari.fypversion3.utils.ContactListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yunihafsari on 17/04/2017.
 */

public class Twitter_following_adapter extends RecyclerView.Adapter<Twitter_following_adapter.Holder>{

    private ArrayList<Twitter_friend> twitter_friends;
    private TextView textView;
    private String sender;
    private Twitter_friend single_friend;
    private ContactListener contactListener;
    private Dialog dialog;


    public Twitter_following_adapter(ArrayList<Twitter_friend> twitter_friends, String sender, ContactListener contactListener,Dialog dialog, TextView textView) {
        this.twitter_friends = twitter_friends;
        this.sender = sender;
        this.contactListener = contactListener;
        this.dialog = dialog;
        this.textView = textView;
    }

    public Twitter_following_adapter(ArrayList<Twitter_friend> twitter_friends, String sender) {
        this.twitter_friends = twitter_friends;
        this.sender = sender;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row;


        if(sender.equals("dialog")){
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_following2, parent, false);
        }else {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_following, parent, false);
        }



        return new Twitter_following_adapter.Holder(row);

    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {



        final int position2 = holder.getAdapterPosition();

        single_friend = twitter_friends.get(position2);

        Picasso.with(holder.following_image.getContext()).load(single_friend.getProfile_image_url()).into(holder.following_image);
        holder.following_username.setText("@"+single_friend.getScreen_name());
        holder.following_full_name.setText(single_friend.getName());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                contactListener.onTwitterUsernameReceived(twitter_friends.get(position2).getScreen_name());
                Log.i("HALLO","this is response from click listener "+twitter_friends.get(position2).getScreen_name());
                Log.i("HALLO","this is adapter position "+twitter_friends.get(position2).getScreen_name());
                textView.setText("@"+twitter_friends.get(position2).getScreen_name());
                dialog.dismiss();
            }
        });





    }

    @Override
    public int getItemCount() {
        return twitter_friends.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView following_image;
        private TextView following_username;
        private TextView following_full_name;
        private ItemClickListener itemClickListener;

        public Holder(final View itemView) {
            super(itemView);

            following_image = (ImageView) itemView.findViewById(R.id.following_image);
            following_username = (TextView) itemView.findViewById(R.id.following_username);
            following_full_name = (TextView) itemView.findViewById(R.id.following_fullname);

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
