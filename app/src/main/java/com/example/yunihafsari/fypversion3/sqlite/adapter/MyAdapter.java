package com.example.yunihafsari.fypversion3.sqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Contact;
import com.example.yunihafsari.fypversion3.ui.activities.ContactDetailActivity;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;

import java.util.ArrayList;

/**
 * Created by yunihafsari on 06/04/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context context;
    ArrayList<Contact> contacts;

    public MyAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, null);

        MyHolder holder = new MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.phone_number.setText(contacts.get(position).getPhone_number());

        Log.i("ANOTHER TESTING","address "+contacts.get(position).getAddress());
        Log.i("ANOTHER TESTING","phone number "+contacts.get(position).getPhone_number());

        // image
        final byte[] friendImage = contacts.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(friendImage, 0, friendImage.length);
        holder.imageView.setImageBitmap(bitmap);

        // HERE IS WHERE I CAN HANDLE THE CLICK LISTENER
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                context.startActivity(new Intent(context, ContactDetailActivity.class));

                // put the chosen list in sharedpreference
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context);
                sharedPrefUtil.saveInt(Constants.CONTACT_ID, contacts.get(position).getId());
                sharedPrefUtil.saveString(Constants.CONTACT_NAME, contacts.get(position).getName());
                sharedPrefUtil.saveString(Constants.CONTACT_PHONE_NUMBER, contacts.get(position).getPhone_number());
                sharedPrefUtil.saveString(Constants.CONTACT_EMAIL, contacts.get(position).getEmail());
                sharedPrefUtil.saveString(Constants.CONTACT_RELATION, contacts.get(position).getRelation());
                sharedPrefUtil.saveString(Constants.CONTACT_ADDRESS, contacts.get(position).getAddress());
                sharedPrefUtil.saveString(Constants.CONTACT_TWITTER_USERNAME, contacts.get(position).getTwitter_username());
                sharedPrefUtil.saveString(Constants.CONTACT_INSTAGRAM_USERNAME, contacts.get(position).getInstagram_username());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
