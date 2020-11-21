package com.example.duan1_nhom6.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Carts {

    private int amount;
    private String id_phone;
    private String id_user;
    private boolean check;


    public Carts() {
    }

    public Carts(int amount, String id_phone, String id_user) {
        this.amount = amount;
        this.id_phone = id_phone;
        this.id_user = id_user;
        this.check = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("id_phone", id_phone);
        result.put("id_user", id_user);


        return result;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getId_phone() {
        return id_phone;
    }

    public void setId_phone(String id_phone) {
        this.id_phone = id_phone;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
