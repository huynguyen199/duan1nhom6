package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Phone {

    private int giatien;
    private String image;
    private String phonename;
    private String tenhang;
    private String id;


    public Phone() {
    }

    public Phone(int giatien, String image, String phonename, String tenhang, String id) {
        this.giatien = giatien;
        this.image = image;
        this.phonename = phonename;
        this.tenhang = tenhang;
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("giatien", giatien);
        result.put("image", image);
        result.put("phonename", phonename);
        result.put("tenhang", tenhang);
        result.put("id", id);


        return result;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhonename() {
        return phonename;
    }

    public void setPhonename(String phonename) {
        this.phonename = phonename;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
