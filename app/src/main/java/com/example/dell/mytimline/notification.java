package com.example.dell.mytimline;

/**
 * Created by dell on 8/13/2019.
 */

public class notification {
    String imageurl;
    String email;

    public notification(String imageurl, String email) {
        this.imageurl = imageurl;
        this.email = email;
    }

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
}
