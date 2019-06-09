package com.Sairaa.onewel.Model.promoter;

public class PromoterPersonalDetails {

    String name;
    String phone;
    String ref_number;
    String address;
    String upiNumber;
    String latitude;
    String Longitude;

    public PromoterPersonalDetails(String name, String phone, String ref_number, String address, String upiNumber, String latitude, String longitude) {
        this.name = name;
        this.phone = phone;
        this.ref_number = ref_number;
        this.address = address;
        this.upiNumber = upiNumber;
        this.latitude = latitude;
        Longitude = longitude;
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

    public String getRef_number() {
        return ref_number;
    }

    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpiNumber() {
        return upiNumber;
    }

    public void setUpiNumber(String upiNumber) {
        this.upiNumber = upiNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
