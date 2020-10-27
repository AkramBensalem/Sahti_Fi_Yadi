package com.gadg.sahtifiyadi.items;


public class Etablissement {
    public static final String ID_FIREBASE = "ID_FIREBASE";

    private String hospital_ID_Firebase;
    private String hopitalName;
    private String hopitaldescription;
    private String hopitalPlace;
    private String Wilaya;
    private String Commune;
    private int    hopitalType;
    private String hopitalservice;
    private String hopitalContact;
    private String imageResource;




    public Etablissement(String hospital_ID_Firebase, String name, String description, String place, String wilaya, String commune,
                         int type, String contact, String service, String hopitalImage) {
        this.hopitalName = name;
        this.hopitaldescription = description;
        this.hopitalPlace = place;
        this.Wilaya = wilaya;
        this.Commune = commune;
        this.hopitalType = type;
        this.hopitalContact=contact;
        this.hopitalservice = service;
        this.imageResource = hopitalImage;
        this.setHospital_ID_Firebase(hospital_ID_Firebase);
    }




    public String getHopitalName() {
        return hopitalName;
    }
    public String getHopitalservice() {
        return hopitalservice;
    }
    public String getHopitalPlace() {
        return hopitalPlace;
    }
    public String getHopitalContact(){
        return hopitalContact;
    }

    public  String getImageResource() {
        return imageResource;
    }

    public String getHospital_ID_Firebase() {
        return hospital_ID_Firebase;
    }

    public void setHospital_ID_Firebase(String hospital_ID_Firebase) {
        this.hospital_ID_Firebase = hospital_ID_Firebase;
    }

    public String getHopitaldescription() {
        return hopitaldescription;
    }

    public void setHopitaldescription(String hopitaldescription) {
        this.hopitaldescription = hopitaldescription;
    }

    public int getHopitalType() {
        return hopitalType;
    }

    public void setHopitalType(int hopitalType) {
        this.hopitalType = hopitalType;
    }
}