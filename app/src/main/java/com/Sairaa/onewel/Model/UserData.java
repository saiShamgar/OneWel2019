package com.Sairaa.onewel.Model;

import com.Sairaa.onewel.Utils.Contants;

public class UserData {

    String user_name= Contants.USERNAME;
    String user_email= Contants.USER_EMAIL;
    String user_image= Contants.USER_IMAGE;
    String user_created_on= Contants.CREATED_ON;

    public UserData(String user_name, String user_email, String user_image, String user_created_on) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_image = user_image;
        this.user_created_on = user_created_on;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_created_on() {
        return user_created_on;
    }

    public void setUser_created_on(String user_created_on) {
        this.user_created_on = user_created_on;
    }
}
