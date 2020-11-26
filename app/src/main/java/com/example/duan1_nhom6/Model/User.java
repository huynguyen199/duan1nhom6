package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String fullname;
    private String id;
    private String imageURL;
    private String numberphone;
    private String status;
    private String role;


    public User() {
    }

    public User(String fullname, String numberphone, String role) {
        this.fullname = fullname;
        this.numberphone = numberphone;
        this.role = role;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullname", fullname);
        result.put("id", id);
        result.put("imageURL", imageURL);
        result.put("numberphone", numberphone);
        result.put("status", status);
        result.put("role", role);

        return result;
    }
    @Exclude
    public Map<String, Object> updateUser() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullname", fullname);
        result.put("numberphone", numberphone);
        result.put("role", role);

        return result;
    }

    public User(String fullname, String id, String imageURL, String numberphone, String status, String role) {
        this.fullname = fullname;
        this.id = id;
        this.imageURL = imageURL;
        this.numberphone = numberphone;
        this.status = status;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
