package com.Sairaa.onewel.Model.Customer;

public class CustomerDetails {

    String name;
    String phone;
    String ref_num;

    public CustomerDetails(String name, String phone, String ref_num) {
        this.name = name;
        this.phone = phone;
        this.ref_num = ref_num;
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

    public String getRef_num() {
        return ref_num;
    }

    public void setRef_num(String ref_num) {
        this.ref_num = ref_num;
    }
}
