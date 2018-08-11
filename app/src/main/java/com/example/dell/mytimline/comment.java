package com.example.dell.mytimline;

import java.io.Serializable;

/**
 * Created by dell on 7/25/2018.
 */

public class comment implements Serializable {

    String imageurl;
    String email;
    String comt;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComt() {
        return comt;
    }

    public void setComt(String comt) {
        this.comt = comt;
    }

    public comment(String imageurl, String email, String comt) {

        this.imageurl = imageurl;
        this.email = email;
        this.comt = comt;
    }

    public comment(){

    }
}
