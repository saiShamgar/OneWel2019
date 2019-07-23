package com.Sairaa.onewel.Model.Customer;

public class CUSTOMER_PAYING_DETAILS {
    String name;
    String totalPrice;
    String discountPrice;
    String Description;
    String date,time;

    public CUSTOMER_PAYING_DETAILS(String name, String totalPrice, String discountPrice, String description, String date, String time) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        Description = description;
        this.date = date;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
