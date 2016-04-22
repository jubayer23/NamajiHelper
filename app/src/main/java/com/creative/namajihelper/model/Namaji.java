package com.creative.namajihelper.model;

/**
 * Created by comsol on 20-Apr-16.
 */
public class Namaji {
    int id;
    String userName;
    String mobileNo;

    public Namaji(int id, String userName, String mobileNo) {
        this.id = id;
        this.userName = userName;
        this.mobileNo = mobileNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
