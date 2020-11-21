package com.example.duan1_nhom6.Model;

public class PhoneDetail {
    private String guagrantee;
    private String id;
    private String introduce;



    public PhoneDetail(String guagrantee, String id, String introduce) {
        this.guagrantee = guagrantee;
        this.id = id;
        this.introduce = introduce;
    }

    public PhoneDetail() {
    }

    public String getGuagrantee() {
        return guagrantee;
    }

    public void setGuagrantee(String guagrantee) {
        this.guagrantee = guagrantee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
