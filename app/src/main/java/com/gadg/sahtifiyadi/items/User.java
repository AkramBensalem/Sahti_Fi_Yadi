package com.gadg.sahtifiyadi.items;

public class User {
   private String Name;
   private String FireBaseId;
   private String Type;


   public User(String name, String type, String fireBaseId){
       this.Name= name;
       this.Type = type;
       this.FireBaseId = fireBaseId;
   }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFireBaseId() {
        return FireBaseId;
    }

    public void setFireBaseId(String fireBaseId) {
        FireBaseId = fireBaseId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
