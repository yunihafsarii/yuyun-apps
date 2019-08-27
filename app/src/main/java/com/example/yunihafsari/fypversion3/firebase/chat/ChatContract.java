package com.example.yunihafsari.fypversion3.firebase.chat;

import android.content.Context;

import com.example.yunihafsari.fypversion3.model.apps_model.Chat;

/**
 * Created by yunihafsari on 14/03/2017.
 */

public interface ChatContract {
    interface View{
        void onSendMessageSuccess();
        void onSendMessageFailure(String message);
        void onGetMessageSuccess(Chat chat);
        void onGetMessageFailure(String message);
    }

    interface Presenter{
        void sendMessage(Context context, Chat chat, String receiverFirebaseToken);
        void getMessage(String senderUid, String receiverUid);
    }

    interface Interactor{
        void sendMessageToFirebase(Context context, Chat chat, String receiverFirebaseToken);
        void getMessageFromFirebase(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener{
        void onSendMessageSuccess();
        void onSendMessageFailure(String message);
    }

    interface OnGetMessageListener{
        void onGetMessageSuccess(Chat chat);
        void onGetMessageFailure(String message);
    }
}
