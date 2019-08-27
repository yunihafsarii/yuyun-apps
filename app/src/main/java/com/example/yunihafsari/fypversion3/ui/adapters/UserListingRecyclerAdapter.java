package com.example.yunihafsari.fypversion3.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.User;

import java.util.List;

/**
 * Created by yunihafsari on 13/03/2017.
 */

public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {

    private List<User> mUsers;
    private Context context;

    public UserListingRecyclerAdapter(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    public void add(User user){
        mUsers.add(user);
        notifyItemInserted(mUsers.size()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);

        String alphabet = user.email.substring(0,1);

        holder.txtUsername.setText(user.email);
        holder.txtUserAlphabet.setText(alphabet);
    }

    @Override
    public int getItemCount() {
        if(mUsers!=null){
            return mUsers.size();
        }
        return 0;
    }

    public User getUser(int position){
        return mUsers.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtUserAlphabet, txtUsername;


        public ViewHolder(View itemView) {

                super(itemView);
                txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
                txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);

        }

    }

}
