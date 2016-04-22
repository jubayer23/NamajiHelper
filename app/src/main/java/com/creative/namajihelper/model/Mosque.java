package com.creative.namajihelper.model;

/**
 * Created by comsol on 20-Apr-16.
 */
public class Mosque {
    int id;
    String mosqueName;
    String mosqueType;
    String mobileNo;
    double lat;
    double lng;

    public Mosque(int id, String mosqueName, String mosqueType, String mobileNo, double lat, double lng) {
        this.id = id;
        this.mosqueName = mosqueName;
        this.mosqueType = mosqueType;
        this.mobileNo = mobileNo;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMosqueName() {
        return mosqueName;
    }

    public void setMosqueName(String mosqueName) {
        this.mosqueName = mosqueName;
    }

    public String getMosqueType() {
        return mosqueType;
    }

    public void setMosqueType(String mosqueType) {
        this.mosqueType = mosqueType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
