package com.example.dell.mytimline;

import android.net.Uri;

import java.net.URI;

/**
 * Created by NITANT SOOD on 22-04-2018.
 */

public class UserData {
    String email,password,name,adhar_no, phone;
    String imageurl;
    String title;


public UserData()
{

}

    public String getAdhar_no() {
        return adhar_no;
    }

    public void setAdhar_no(String adhar_no) {
        this.adhar_no = adhar_no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserData(String email, String password, String name, String adhar_no , String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.adhar_no = adhar_no;
        this.phone = phone;

    }

    public UserData(String email, String password, String name, String adhar_no , String phone, String imageurl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.adhar_no = adhar_no;
        this.phone = phone;
        this.imageurl = imageurl;

    }

    public UserData(String imageurl ) {

        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
