package com.gadg.sahtifiyadi.items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageChatItem {




    private String Message_ID_Firebase;
    private final String msgName;
    private final String message;
    private final String imageResource;
    private String Date;





    public MessageChatItem(String id, String senderName, String message, String date, String msgImage) {

        this.Message_ID_Firebase=id;
        this.msgName = senderName;
        this.message = message;
        this.imageResource = msgImage;

        this.Date = date;
    }


    public String getMsgName() {
        return msgName;
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


    public void setMessage_ID_Firebase(String message_ID_Firebase) {
        Message_ID_Firebase = message_ID_Firebase;
    }

    public String getDate() {

        return this.Date;
    }



}
