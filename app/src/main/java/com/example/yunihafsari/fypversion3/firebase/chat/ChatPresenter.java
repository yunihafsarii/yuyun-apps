package com.example.yunihafsari.fypversion3.firebase.chat;

import android.content.Context;

import com.example.yunihafsari.fypversion3.model.apps_model.Chat;

/**
 * Created by yunihafsari on 14/03/2017.
 */

public class ChatPresenter implements ChatContract.Presenter, ChatContract.OnSendMessageListener, ChatContract.OnGetMessageListener {

    private ChatContract.View view;
    private ChatInteractor chatInteractor;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;
        chatInteractor = new ChatInteractor(this, this);
    }

    @Override
    public void onGetMessageSuccess(Chat chat) {
        view.onGetMessageSuccess(chat);
    }

    @Override
    public void onGetMessageFailure(String message) {
        view.onGetMessageFailure(message);
    }

    @Override
    public void onSendMessageSuccess() {
        view.onSendMessageSuccess();
    }

    @Override
    public void onSendMessageFailure(String message) {
        view.onSendMessageFailure(message);
    }

    @Override
    public void sendMessage(Context context, Chat chat, String receiverFirebaseToken) {
        chatInteractor.sendMessageToFirebase(context, chat, receiverFirebaseToken);
    }

    @Override
    public void getMessage(String senderUid, String receiverUid) {
        chatInteractor.getMessageFromFirebase(senderUid, receiverUid);
    }
}
