package com.Sairaa.onewel.Model.Customer;

public class CUSTOMER_PAYING_DETAILS {
    String name;
    String totalPrice;
    String discountPrice;
    String Description;

    public CUSTOMER_PAYING_DETAILS(String name, String totalPrice, String discountPrice, String description) {
        this.name = name;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        Description = description;
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
}
