package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    private String sender;
    private String receiver;
    private String massage;
    private String isseen;
    public Chat(String sender, String receiver, String massage,String isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.massage = massage;
        this.isseen = isseen;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sender", sender);
        result.put("receiver", receiver);
        result.put("massage", massage);
        result.put("isseen", isseen);

        return result;
    }
    public String isIsseen() {
        return isseen;
    }

    public void setIsseen(String isseen) {
        this.isseen = isseen;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
