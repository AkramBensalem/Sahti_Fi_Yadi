package com.gadg.sahtifiyadi.items;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;


public class Doctors implements Parcelable {
    //Les constants
    public static final String FIREBASE_ID = "firebaseId";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String ADRESS = "ADRESS";
    public static final String SPECIALISTE = "SPECIALISTÉ";
    public static final String SERVICES = "SERVICES";
    public static final String TIME = "TIME";
    public static final String PHONE = "PHONE";
    
	
	public static final String DOCTOR = "doctor";




    private String Doctor_ID_Firebase;
    private final String NameDoctor;
    private final String PlaceDoctor;
    private  String Wilaya;
    private  String Commune;
    private final String phone;
    private final String spec;
    private final String type;
    private final String service;
    private final String time;
    private String ImageUrl;



    public  Doctors(String id,String name, String place, String wilaya , String commune ,String phone, String spec, String type,String service,String time,String image) {
        this.Doctor_ID_Firebase=id;
        this.NameDoctor = name;
        this.PlaceDoctor = place;
        this.Wilaya = wilaya;
        this.Commune = commune;
        this.phone = phone;
        this.spec = spec;
        this.ImageUrl = image;
        this.type = type;
        this.service= service;
        this.time = time;
    }


    protected Doctors(Parcel in) {
        Doctor_ID_Firebase = in.readString();
        NameDoctor = in.readString();
        PlaceDoctor = in.readString();
        Wilaya = in.readString();
        Commune = in.readString();
        phone = in.readString();
        spec = in.readString();
        type = in.readString();
        service = in.readString();
        time = in.readString();
        ImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Doctor_ID_Firebase);
        dest.writeString(NameDoctor);
        dest.writeString(PlaceDoctor);
        dest.writeString(Wilaya);
        dest.writeString(Commune);
        dest.writeString(phone);
        dest.writeString(spec);
        dest.writeString(type);
        dest.writeString(service);
        dest.writeString(time);
        dest.writeString(ImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Doctors> CREATOR = new Creator<Doctors>() {
        @Override
        public Doctors createFromParcel(Parcel in) {
            return new Doctors(in);
        }

        @Override
        public Doctors[] newArray(int size) {
            return new Doctors[size];
        }
    };

    public String getNameDoctor() {
        return NameDoctor;
    }

    public String getPlaceDoctor() {
        return PlaceDoctor;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpec() {
        return spec;
    }


    public String getDoctor_ID_Firebase() {
        return Doctor_ID_Firebase;
    }

    public void setDoctor_ID_Firebase(String doctor_ID_Firebase) {
        Doctor_ID_Firebase = doctor_ID_Firebase;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    static Intent starter(Context context,String firebaseID,String name,String imageID,String adress,String service,String specialist,String phone,String time) {
      /*  Intent chatIntent = new Intent(context, Details_Doctor.class);
        chatIntent.putExtra(FIREBASE_ID, firebaseID);
        chatIntent.putExtra(IMAGE,imageID );
        chatIntent.putExtra(NAME, name);
        chatIntent.putExtra(ADRESS, adress);
        chatIntent.putExtra(SERVICES, service);
        chatIntent.putExtra(SPECIALISTE, specialist);
        chatIntent.putExtra(TIME, time);
        chatIntent.putExtra(PHONE, phone);

        return chatIntent;
		*/
      return null;
    }
	static Intent starter(Context context, Doctors doctor) {
      /*  Intent chatIntent = new Intent(context, Details_Doctor.class);
        chatIntent.putExtra(DOCTOR, doctor);
        return chatIntent;
		*/
      return null;
    }


    public String getType() {
        return type;
    }

    public String getService() {
        return service;
    }

    public String getTime() {
        return time;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }

    public String getCommune() {
        return Commune;
    }

    public void setCommune(String commune) {
        Commune = commune;
    }
}
