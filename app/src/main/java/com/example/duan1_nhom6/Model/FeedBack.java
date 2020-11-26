package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FeedBack {
    private String reason;
    private String describe;
    private String sender;
    private String id;

    public FeedBack() {
    }

    public FeedBack(String reason, String describe, String sender) {
        this.reason = reason;
        this.describe = describe;
        this.sender = sender;
    }

    public FeedBack(String reason, String describe, String sender, String id) {
        this.reason = reason;
        this.describe = describe;
        this.sender = sender;
        this.id = id;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reason", reason);
        result.put("describe", describe);
        result.put("sender", sender);
        result.put("id", id);

        return result;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
