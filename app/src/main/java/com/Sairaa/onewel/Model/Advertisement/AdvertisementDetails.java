package com.Sairaa.onewel.Model.Advertisement;

public class AdvertisementDetails {

    String shop_name;
    String phone_num;
    String ref_num;
    String shop_type;
    String address;
    String latitude;
    String longitude;
    String landmark;
    String opens_from;
    String  closes_to;
    String shop_desc;
    String image_url;

    public AdvertisementDetails(String shop_name, String phone_num, String ref_num, String shop_type, String address, String latitude, String longitude, String landmark, String opens_from, String closes_to, String shop_desc, String image_url) {
        this.shop_name = shop_name;
        this.phone_num = phone_num;
        this.ref_num = ref_num;
        this.shop_type = shop_type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.landmark = landmark;
        this.opens_from = opens_from;
        this.closes_to = closes_to;
        this.shop_desc = shop_desc;
        this.image_url = image_url;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getRef_num() {
        return ref_num;
    }

    public void setRef_num(String ref_num) {
        this.ref_num = ref_num;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOpens_from() {
        return opens_from;
    }

    public void setOpens_from(String opens_from) {
        this.opens_from = opens_from;
    }

    public String getCloses_to() {
        return closes_to;
    }

    public void setCloses_to(String closes_to) {
        this.closes_to = closes_to;
    }

    public String getShop_desc() {
        return shop_desc;
    }

    public void setShop_desc(String shop_desc) {
        this.shop_desc = shop_desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
