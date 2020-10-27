package com.gadg.sahtifiyadi.ui.don_de_sang;

import android.content.Context;
import android.content.Intent;

import com.gadg.sahtifiyadi.MainActivity;

public class don_de_song {
    private String _ID_firebase;
    private String age;
    private String fullname;
    private String  grsanguin;
    private String imaged;
    private String contact;
    private String adressd;
    private String Wilaya;
    private String Commmune;
    public static final String RECIVER = "Reciver";
    public static final String RECIVER_IMAGE = "ReciverImageUrl";

    public static final String SENDER = "sender";

    public don_de_song(String _ID_firebase, String fullname, String adressd ,String wilaya, String commmune, String contact,String age,String grsanguin, String imaged) {
        this._ID_firebase = _ID_firebase;
        this.age=age;
        this.fullname = fullname;
        this.grsanguin = grsanguin;
        this.imaged = imaged;
        this.contact = contact;
        this.adressd = adressd;
        this.Wilaya = wilaya;
        this.Commmune = commmune;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getGrsanguin() {
        return grsanguin;
    }

    public void setGrsanguin(String grsanguin) {
        this.grsanguin = grsanguin;
    }

    public String getImaged() {
        return imaged;
    }

    public void setImaged(String imaged) {
        this.imaged = imaged;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdressd() {
        return adressd;
    }

    public void setAdressd(String adressd) {
        this.adressd = adressd;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String get_ID_firebase() {
        return _ID_firebase;
    }

    public void set_ID_firebase(String _ID_firebase) {
        this._ID_firebase = _ID_firebase;
    }
    static Intent starter(Context context, String reciver, String sender) {
       // Intent chatIntent = new Intent(context, chatRoom.class);
        Intent chatIntent = new Intent(context, MainActivity.class);
        chatIntent.putExtra(RECIVER, reciver);
        chatIntent.putExtra(SENDER, sender);
        return chatIntent;
    }

    public String getCommmune() {
        return Commmune;
    }

    public void setCommmune(String commmune) {
        Commmune = commmune;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }
}
