package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FeedBack {
    private String reason;
    private String describe;
    private String id_user;

    public FeedBack() {
    }

    public FeedBack(String reason, String describe, String id_user) {
        this.reason = reason;
        this.describe = describe;
        this.id_user = id_user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reason", reason);
        result.put("describle", describe);
        result.put("id_user", id_user);

        return result;
    }
}
