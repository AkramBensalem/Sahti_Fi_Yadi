package com.gadg.sahtifiyadi.items;


import org.apache.commons.lang3.text.WordUtils;

public class Wilaya {
    private int id;
    private String name;
    private String[] communes;

    public Wilaya(int id, String wilayaName, String[] communes) {
        setId(id);
        setWilayaName(wilayaName);
        setCommunes(communes);
    }

    public Wilaya(String id, String wilayaName, String[] communes) {
       setId(id);
       setWilayaName(wilayaName);
       setCommunes(communes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getWilayaName() {
       return WordUtils.capitalizeFully(name);
    }

    public void setWilayaName(String WilayaName) {
        this.name = WordUtils.capitalizeFully(WilayaName);
    }

    public String[] getCommunes() {
        String[] communesList = new String[this.communes.length];
        int i =0;
        for (String s:this.communes) {
            communesList[i] = WordUtils.capitalizeFully(s);
            i++;
        }
        return communesList;
    }

    public void setCommunes(String[] communes) {
        String[] communesList = new String[communes.length];
        int i =0;
        for (String s:communes) {
            communesList[i] = WordUtils.capitalizeFully(s);
            i++;
        }
        this.communes = communesList;
    }


}
