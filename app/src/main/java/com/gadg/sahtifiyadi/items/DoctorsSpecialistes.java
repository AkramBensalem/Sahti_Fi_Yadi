package com.gadg.sahtifiyadi.items;

public class DoctorsSpecialistes {
    private final String SpecialisteName;
    private final int SpecialisteImage;
    private final String SpecialiteCount;




    public DoctorsSpecialistes(String name,String count ,int image) {
        this.SpecialisteName = name;
        this.SpecialiteCount = count;
        this.SpecialisteImage = image;
    }

    public String getSpecialiteCount() {
        return this.SpecialiteCount;
    }
       public String getSpecialiste() {
        return this.SpecialisteName;
    }

     public int getImageSpecialiste() {return this.SpecialisteImage;}

}
