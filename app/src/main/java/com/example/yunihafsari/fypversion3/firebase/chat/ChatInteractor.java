package com.example.yunihafsari.fypversion3.firebase.chat;

import android.content.Context;
import android.util.Log;

import com.example.yunihafsari.fypversion3.model.apps_model.Chat;
import com.example.yunihafsari.fypversion3.notification.FcmNotificationBuilder;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yunihafsari on 14/03/2017.
 */

public class ChatInteractor implements ChatContract.Interactor {

    private static final String TAG = "CHATTTTT";

    private ChatContract.OnSendMessageListener onSendMessageListener;
    private ChatContract.OnGetMessageListener onGetMessageListener;

    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener) {
        this.onSendMessageListener = onSendMessageListener;
    }

    public ChatInteractor(ChatContract.OnGetMessageListener onGetMessageListener) {
        this.onGetMessageListener = onGetMessageListener;
    }

    public ChatInteractor(ChatContract.OnSendMessageListener onSendMessageListener, ChatContract.OnGetMessageListener onGetMessageListener) {
        this.onSendMessageListener = onSendMessageListener;
        this.onGetMessageListener = onGetMessageListener;
    }

    @Override
    public void sendMessageToFirebase(final Context context, final Chat chatt, final String receiverFirebaseToken) {
        final String room_type_1 = chatt.senderUid+"_"+chatt.receiverUid;
        final String room_type_2 = chatt.receiverUid+"_"+chatt.senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room_type_1)){
                    Log.e(TAG,"send message to firebase "+room_type_1+"room 1 exist");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chatt.timestamp)).setValue(chatt);
                }else if(dataSnapshot.hasChild(room_type_2)){
                    Log.e(TAG,"send message to firebase "+room_type_2+"room 2 exist");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2).child(String.valueOf(chatt.timestamp)).setValue(chatt);
                }else{
                    Log.e(TAG,"send message to firebase "+room_type_1+"no room");
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1).child(String.valueOf(chatt.timestamp)).setValue(chatt);
                    getMessageFromFirebase(chatt.senderUid, chatt.receiverUid);
                }

                // send push notification to the receiver
                sendPushNotificationToReceiver(chatt.sender,
                        chatt.messagee,
                        chatt.senderUid,
                        new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                        receiverFirebaseToken);
                //mOnSendMessageListener.onSendMessageSuccess();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }


    @Override
    public void getMessageFromFirebase(String senderUid, String receiverUid) {
        final String room_type_1 = senderUid+"_"+receiverUid;
        final String room_type_2 = receiverUid+"_"+senderUid;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room_type_1)){
                    Log.e(TAG,"get message to firebase "+room_type_1+" room 1 exist");
                    FirebaseDatabase.getInstance().getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            onGetMessageListener.onGetMessageSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            onGetMessageListener.onGetMessageFailure("unable to get message "+databaseError.getMessage());
                        }
                    });
                }else if(dataSnapshot.hasChild(room_type_2)){
                    Log.e(TAG,"get message to firebase "+room_type_2+"room 2 exist");
                    FirebaseDatabase.getInstance().getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            onGetMessageListener.onGetMessageSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            onGetMessageListener.onGetMessageFailure("unable to get message "+databaseError.getMessage());
                        }
                    });
                }else{
                    Log.e(TAG,"get message to firebase , there is no such room");
                    onGetMessageListener.onGetMessageFailure("unable to get message");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetMessageListener.onGetMessageFailure("unable to get message "+databaseError.getMessage());
            }
        });

    }
}
