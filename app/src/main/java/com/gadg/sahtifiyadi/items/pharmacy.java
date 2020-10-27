package com.gadg.sahtifiyadi.items;

import android.content.Context;
import android.content.Intent;

import com.gadg.sahtifiyadi.message.chatRoom.chatRoom;

public class pharmacy {
    String PHARMA_ID_Firebase;
    String thename;
    String theadress;
    private int wilaya;
    private int commune;
    String phone;
    String time;
    String ImageUrl;
    String description;
    public static final String RECIVER = "Reciver";
    public static final String SENDER = "sender";

    public pharmacy(String id_firebase, String thenamem, String theadressm,int wilaya ,int commune ,String phone, String time, String image,String description) {

        this.PHARMA_ID_Firebase=id_firebase;
        this.time=time;
        this.theadress=theadressm;
        this.wilaya = wilaya;
        this.commune = commune;
        this.thename=thenamem;
        this.phone=phone;
        this.ImageUrl = image;
        this.description=description;

    }

    public pharmacy(String id_firebase, String thenamem, String theadressm, String phone, String time, String image,String description){
        this.PHARMA_ID_Firebase=id_firebase;
        this.time=time;
        this.theadress=theadressm;
        this.thename=thenamem;
        this.phone=phone;
        this.ImageUrl = image;
        this.description=description;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getThename(){return thename;}
    public String getTheadress(){return theadress;}
    public void setThename(String n){thename=n;}
    public void setTheadress(String a){theadress=a;}
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone){this.phone=phone;}



    public String getPHARMA_ID_Firebase() {
        return PHARMA_ID_Firebase;
    }

    public void setPHARMA_ID_Firebase(String PHARMA_ID_Firebase) {
        this.PHARMA_ID_Firebase = PHARMA_ID_Firebase;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public static Intent starter(Context context, String reciver, String sender) {
        Intent chatIntent = new Intent(context, chatRoom.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;

    }

    public int getWilaya() {
        return wilaya;
    }

    public void setWilaya(int wilaya) {
        this.wilaya = wilaya;
    }

    public int getCommune() {
        return commune;
    }

    public void setCommune(int commune) {
        this.commune = commune;
    }
}