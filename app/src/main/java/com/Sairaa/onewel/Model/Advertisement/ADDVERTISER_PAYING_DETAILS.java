package com.Sairaa.onewel.Model.Advertisement;

public class ADDVERTISER_PAYING_DETAILS {

    String custer_number;
    String total_price;
    String discount_price;
    String description;
    String date,time;

    public ADDVERTISER_PAYING_DETAILS(String custer_number, String total_price, String discount_price, String description, String date, String time) {
        this.custer_number = custer_number;
        this.total_price = total_price;
        this.discount_price = discount_price;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getCuster_number() {
        return custer_number;
    }

    public void setCuster_number(String custer_number) {
        this.custer_number = custer_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
