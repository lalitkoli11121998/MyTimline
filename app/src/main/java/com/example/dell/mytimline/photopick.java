package com.example.dell.mytimline;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dell on 7/10/2018.
 */

public class photopick implements Serializable {

    String Name;
    String imageuri;
    int like;
    String id;
    ArrayList<comment>arrayList = new ArrayList<>();
    String picimageurl;
    String  email;


    public photopick( String imageuri, int like, String id, ArrayList<comment> arrayList,  String picimageurl,String  email) {
        this.imageuri = imageuri;
        this.like = like;
        this.id = id;
        this.arrayList = arrayList;
        this.picimageurl =picimageurl;
        this.email =email;
    }

    public ArrayList<comment> getArrayList() {

        return arrayList;
    }

    public void setArrayList(ArrayList<comment> arrayList) {
        this.arrayList = arrayList;
    }

    public photopick(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicimageurl() {
        return picimageurl;
    }

    public void setPicimageurl(String picimageurl) {
        this.picimageurl = picimageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public photopick(String imageuri, int like, String id) {
        this.imageuri = imageuri;
        this.like = like;
        this.id =id;

    }

    public photopick(String imageuri, int like, String id, String picimageurl , String  email) {
        this.imageuri = imageuri;
        this.like = like;
        this.id =id;
        this.picimageurl = picimageurl;
        this.email = email;


    }

    public int getLike() {
        return like;

    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
