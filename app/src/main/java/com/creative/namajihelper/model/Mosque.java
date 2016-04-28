package com.creative.namajihelper.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by comsol on 20-Apr-16.
 */
public class Mosque implements Parcelable{
    int id;
    String mosqueName;
    String mosqueType;
    String mobileNo;
    double lat;
    double lng;
    String fajar;
    String juhar;
    String asar;
    String magrib;
    String esha;
    String eid;

    public Mosque(int id, String mosqueName, String mosqueType, String mobileNo, double lat, double lng, String fajar, String juhar, String asar, String magrib, String esha, String eid) {
        this.id = id;
        this.mosqueName = mosqueName;
        this.mosqueType = mosqueType;
        this.mobileNo = mobileNo;
        this.lat = lat;
        this.lng = lng;
        this.fajar = fajar;
        this.juhar = juhar;
        this.asar = asar;
        this.magrib = magrib;
        this.esha = esha;
        this.eid = eid;
    }

    protected Mosque(Parcel in) {
        id = in.readInt();
        mosqueName = in.readString();
        mosqueType = in.readString();
        mobileNo = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        fajar = in.readString();
        juhar = in.readString();
        asar = in.readString();
        magrib = in.readString();
        esha = in.readString();
        eid = in.readString();
    }

    public static final Creator<Mosque> CREATOR = new Creator<Mosque>() {
        @Override
        public Mosque createFromParcel(Parcel in) {
            return new Mosque(in);
        }

        @Override
        public Mosque[] newArray(int size) {
            return new Mosque[size];
        }
    };

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


    public String getFajar() {
        return fajar;
    }

    public void setFajar(String fajar) {
        this.fajar = fajar;
    }

    public String getJuhar() {
        return juhar;
    }

    public void setJuhar(String juhar) {
        this.juhar = juhar;
    }

    public String getAsar() {
        return asar;
    }

    public void setAsar(String asar) {
        this.asar = asar;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getEsha() {
        return esha;
    }

    public void setEsha(String esha) {
        this.esha = esha;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(mosqueName);
        parcel.writeString(mosqueType);
        parcel.writeString(mobileNo);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(fajar);
        parcel.writeString(juhar);
        parcel.writeString(asar);
        parcel.writeString(magrib);
        parcel.writeString(esha);
        parcel.writeString(eid);
    }
}
