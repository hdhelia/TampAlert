package com.example.harshal.hackholyoke;

public class users {

    private String name;
    private String phone;
    private String email;
    private double Lat;
    private double Long;
    private boolean isfree;
    private boolean isRegistered;

    public boolean getIsRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public users(){
    }

    public users(String name, String phone, String email, double lat, double aLong, boolean isfree, boolean isRegistered) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        Lat = lat;
        Long = aLong;
        this.isfree = isfree;
        this.isRegistered = isRegistered;
    }

    public boolean getIsfree() {
        return isfree;
    }

    public void setIsfree(boolean isfree) {
        this.isfree = isfree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }


}
