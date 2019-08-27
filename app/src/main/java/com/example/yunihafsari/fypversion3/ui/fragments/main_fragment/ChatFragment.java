package com.example.yunihafsari.fypversion3.ui.fragments.main_fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.firebase.chat.ChatContract;
import com.example.yunihafsari.fypversion3.firebase.chat.ChatPresenter;
import com.example.yunihafsari.fypversion3.model.apps_model.Chat;
import com.example.yunihafsari.fypversion3.model.apps_model.PushNotificationEvent;
import com.example.yunihafsari.fypversion3.sqlite.database.DBAdapter;
import com.example.yunihafsari.fypversion3.ui.adapters.ChatRecyclerAdapter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.scottyab.aescrypt.AESCrypt;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ChatContract.View, TextView.OnEditorActionListener{

    private RecyclerView recyclerView;

    private EditText mTxtMessage;

    private ProgressDialog progressDialog;

    private ChatRecyclerAdapter chatRecyclerAdapter;

    private ChatPresenter chatPresenter;


    public static ChatFragment newInstance(String receiver, String receiverUid, String firebaseToken){
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECEIVER, receiver);
        args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
        args.putString(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(args);
        return chatFragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        // event bust here
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // event bus here
        EventBus.getDefault().unregister(this);
    }

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_chat);
        mTxtMessage = (EditText) fragmentView.findViewById(R.id.edit_text_message);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);

        mTxtMessage.setOnEditorActionListener(this);

        chatPresenter = new ChatPresenter(this);
        chatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(), getArguments().getString(Constants.ARG_RECEIVER_UID));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEND){
            if(mTxtMessage.getText().toString().length()!=0){
                sendMessage();
                return true;
            }
        }
        return false;
    }

    private void sendMessage() {
        boolean encryption =false;
        String ui_message = mTxtMessage.getText().toString();
        String message=null;
        if(new SharedPrefUtil(getContext()).getBoolean(Constants.MESSAGE_ENCRYPTION)==true){
            try {
                encryption = true;
                message = AESCrypt.encrypt(Constants.ENCRYPTION_CODE, ui_message);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }else{
            encryption = false;
            message = ui_message;
        }

        /*
        1. i need to check receiver, if he/she is not included in user server, then write text
         */

        String receiver = getArguments().getString(Constants.ARG_RECEIVER);
        String receiverUid = getArguments().getString(Constants.ARG_RECEIVER_UID);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverFirebaseToken = getArguments().getString(Constants.ARG_FIREBASE_TOKEN);
        Chat chat = new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                encryption,
                System.currentTimeMillis());
        chatPresenter.sendMessage(getActivity().getApplicationContext(), chat, receiverFirebaseToken);
    }

    @Override
    public void onSendMessageSuccess() {
        mTxtMessage.setText("");
        Toast.makeText(getActivity(), "message sent", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetMessageSuccess(Chat chat) {



        if(chatRecyclerAdapter==null){
            chatRecyclerAdapter = new ChatRecyclerAdapter(new ArrayList<Chat>());
            recyclerView.setAdapter(chatRecyclerAdapter);
        }

        // DO IT HERE
        if(chat.encryption==true){
            try {
                String decrypted_message = AESCrypt.decrypt(Constants.ENCRYPTION_CODE, chat.messagee);

                Chat decrypted_chat = new Chat(chat.sender, chat.receiver, chat.senderUid, chat.receiverUid, decrypted_message, chat.encryption, chat.timestamp);
                chatRecyclerAdapter.add(decrypted_chat);
                //recyclerView.smoothScrollToPosition(chatRecyclerAdapter.getItemCount()-1);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }else{
            chatRecyclerAdapter.add(chat);
            //recyclerView.smoothScrollToPosition(chatRecyclerAdapter.getItemCount()-1);
        }

        recyclerView.smoothScrollToPosition(chatRecyclerAdapter.getItemCount()-1);

    }

    private boolean checkFirebaseStatusOfEmail(){
        boolean result = false;

        // read database
        // if firebase is true , return true

        DBAdapter db = new DBAdapter(getActivity());

        // open
        db.openDB();

        // get friends from db
        Cursor c = db.getAllFriends();

        Log.i("DEBUGGING--------CHATTT",""+getArguments().getString(Constants.ARG_RECEIVER));

        // loop thru data then adding to arraylist
        while(c.moveToNext()){
            if(getArguments().getString(Constants.ARG_RECEIVER).equals(c.getString(2))){
                Log.i("DEBUGGING----column2",""+c.getString(2));
                if(c.getInt(8)==1){
                    Log.i("DEBUGGING----column8",""+c.getInt(8));
                    result = true;
                }
            }
        }


        db.closeDB();

        return result;
    }

    @Override
    public void onGetMessageFailure(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        if(message.equals("unable to get message") && !checkFirebaseStatusOfEmail()){
            mTxtMessage.setText("You're the only one here!");
            mTxtMessage.setEnabled(false);
        }

        // or receiver email column is false
        Log.i("DEGUGGING---------CHAT",""+checkFirebaseStatusOfEmail());
    }





    @Subscribe
    public void onPushnotificationEvent(PushNotificationEvent pushNotificationEvent){
        if(chatRecyclerAdapter == null || chatRecyclerAdapter.getItemCount()==0){
            chatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }



}
