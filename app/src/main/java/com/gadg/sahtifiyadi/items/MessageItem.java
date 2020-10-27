package com.gadg.sahtifiyadi.items;

import android.content.Context;
import android.content.Intent;

import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageItem implements Comparable<MessageItem>{

    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";




    private String Message_ID_Firebase;
    private final String Sender;
    private final String recent_message;
    private final boolean is_Readed;
    private final String imageResource;
    private final String full_Message;
    private Date Date;





    public MessageItem(String id, String senderName, String message,  String full_message, String date,String readStatus, String msgImage) {

        this.Message_ID_Firebase=id;
        this.Sender = senderName;
        this.recent_message = message;
        this.imageResource = msgImage;
        this.is_Readed = readStatus.equals("false");
        this.full_Message = full_message;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            this.Date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String getSender() {
        return Sender;
    }

    public String getRecent_message() {
        return recent_message;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getMessage_ID_Firebase() {
        return Message_ID_Firebase;
    }

    public boolean Is_Readed(){

        return this.is_Readed;
    }
    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }

    public String getFull_Message() {
        return full_Message;
    }
    public java.util.Date getDate() {

        return this.Date;
    }
    public String getDate2String() {
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return date.format(this.Date);
    }


    public void setDate(java.util.Date date) {
        Date = date;
    }

    public void setDate(String date) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            this.Date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(MessageItem o) {
        return o.getDate().compareTo(this.getDate());
    }

    public static Intent starter(Context context, String reciver, String sender, String image) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        chatIntent.putExtra(RECIVER_IMAGE,image);
        return chatIntent;
    }

}
