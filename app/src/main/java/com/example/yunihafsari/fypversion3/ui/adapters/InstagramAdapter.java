package com.example.yunihafsari.fypversion3.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.instagram_model.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yunihafsari on 16/04/2017.
 */

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.Holder>{

    private ArrayList<Media> medias;

    public InstagramAdapter(ArrayList<Media> medias) {
        this.medias = medias;

        //Log.i("testing","did you get this "+medias.get(0).getCaption().getText());
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.instagram_model, parent, false);
        return new Holder(row);


    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Media media = medias.get(position);


        String user_name = media.getUser().getUsername();

        /*
        String get_caption = "";
        get_caption = media.getCaption().getText();
        String put_caption="";
        if(get_caption!=null){
            put_caption = get_caption;
        }else{
            put_caption="No caption";
        }
         */
        String put_caption="";
        try{
            String get_caption = media.getCaption().getText();

            if(get_caption!=null){
                put_caption = get_caption;
            }else{
                put_caption="No caption";
            }
        }catch (NullPointerException e){

        }

        // tips please use null pointer exception in try catch format

        Picasso.with(holder.pic_profile.getContext()).load(media.getUser().getProfile_picture_url()).into(holder.pic_profile);
        Picasso.with(holder.pic_media_update.getContext()).load(media.getImages().getThumbnail().getUrl()).into(holder.pic_media_update);
        holder.text_username.setText(media.getUser().getUsername());
        holder.text_likes.setText(media.getLikes().getCount()+" likes");
        holder.text_caption.setText(user_name+" || "+put_caption+"");
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView pic_profile;
        private ImageView pic_media_update;
        private TextView text_username;
        private TextView text_likes;
        private TextView text_caption;

        public Holder(View itemView) {
            super(itemView);

            pic_profile = (ImageView) itemView.findViewById(R.id.pic_profile);
            pic_media_update = (ImageView) itemView.findViewById(R.id.pic_media_update);
            text_username = (TextView) itemView.findViewById(R.id.text_username);
            text_likes = (TextView) itemView.findViewById(R.id.text_likes);
            text_caption = (TextView) itemView.findViewById(R.id.text_caption);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
