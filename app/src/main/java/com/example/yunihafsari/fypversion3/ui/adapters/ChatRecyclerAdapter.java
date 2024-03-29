package com.example.yunihafsari.fypversion3.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.model.apps_model.Chat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by yunihafsari on 14/03/2017.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<Chat> mChats;

    public ChatRecyclerAdapter(List<Chat> mChats) {
        this.mChats = mChats;
    }

    public void add(Chat chat){
        mChats.add(chat);
        notifyItemInserted(mChats.size()-1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(TextUtils.equals(mChats.get(position).senderUid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
            configireMyChatViewHolder((MyChatViewHolder) holder, position);
        }else{
            configureOtherChatViewHolder((OtherChatHolder) holder, position);
        }
    }

    private void configireMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position){
        Chat chat = mChats.get(position);

        String alphabet = chat.sender.substring(0,1);

        myChatViewHolder.txtChatMessage.setText(chat.messagee);
        myChatViewHolder.txtUserAlphabet.setText(alphabet);
    }

    private void configureOtherChatViewHolder(OtherChatHolder otherChatHolder, int position){
        Chat chat = mChats.get(position);

        String alphabet = chat.sender.substring(0,1);

        otherChatHolder.txtChatMessage.setText(chat.messagee);
        otherChatHolder.txtUserAlphabet.setText(alphabet);
    }

    @Override
    public int getItemCount() {
        if(mChats != null){
            return mChats.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(mChats.get(position).senderUid, FirebaseAuth.getInstance().getCurrentUser().getUid())){
           return VIEW_TYPE_ME;
        }else{
            return VIEW_TYPE_OTHER;
        }

    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder{

        private TextView txtChatMessage,txtUserAlphabet;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }

    private static class OtherChatHolder extends RecyclerView.ViewHolder{

        private TextView txtChatMessage, txtUserAlphabet;

        public OtherChatHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }
}
