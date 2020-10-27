package com.gadg.sahtifiyadi.items;

import android.content.Context;
import android.content.Intent;


import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;

import java.util.Date;

public class Message implements Comparable<Message>{
    public static final String RECIVER_IMAGE = "ReciverImageUrl";




    private String Message_ID_Firebase;
    private final String Sender;
    private final String message;
    private final String is_Readed;
    private final String imageResource;

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    private Date Date;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";



    public Message(String id,String senderName, String message, String hopitalImage,String readStatus, Date date) {
        this.Message_ID_Firebase=id;
        this.Sender = senderName;
        this.message = message;
        this.imageResource = hopitalImage;
        is_Readed = readStatus;
        this.Date =date;
    }


    public String getSender() {
        return Sender;
    }

    public String getMessage() {
        return message;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getMessage_ID_Firebase() {
        return Message_ID_Firebase;
    }
    public boolean Is_Readed(){
        return this.is_Readed.equals("true");
    }
    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }
    static Intent starter(Context context, String reciver, String sender,String image) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        chatIntent.putExtra(RECIVER_IMAGE,image);

        return chatIntent;
    }

    @Override
    public int compareTo(Message o) {
        return o.getDate().compareTo(this.getDate());
    }
}
