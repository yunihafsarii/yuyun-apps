package com.example.yunihafsari.fypversion3.model.apps_model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yunihafsari on 14/03/2017.
 */
@IgnoreExtraProperties
public class Chat {
    public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String messagee;
    public boolean encryption;
    public long timestamp;

    public Chat(){

    }


    public Chat(String sender, String receiver, String senderUid, String receiverUid, String messagee,boolean encryption,long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.messagee = messagee;
        this.encryption = encryption;
        this.timestamp = timestamp;
    }
}
