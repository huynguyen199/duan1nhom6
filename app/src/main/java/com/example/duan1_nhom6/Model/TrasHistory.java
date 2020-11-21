package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TrasHistory {
    private String date;
    private String id_user;
    private int total;

    public TrasHistory(String date, String id_user, int total) {
        this.date = date;
        this.id_user = id_user;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("id_user", id_user);
        result.put("total", total);

        return result;
    }

    public TrasHistory() {
    }


}
