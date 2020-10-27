package com.gadg.sahtifiyadi.items;
import androidx.annotation.Nullable;

public class Speciality {
   private   long id;
   private String name;
   private String count;

    public Speciality(long id, String name, String count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Speciality){
            return this.name.equals(((Speciality) obj).name);
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.name);
    }
}
