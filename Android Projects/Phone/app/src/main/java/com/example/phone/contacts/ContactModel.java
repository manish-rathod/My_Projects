package com.example.phone.contacts;

import android.graphics.Bitmap;
import android.net.Uri;

public class ContactModel {
    public String id;
    public String name;
    public String mobileNumber;
    public Bitmap photo;
    public String photoURI;
    public Boolean menu = false;

    public Boolean show = false;

    public Boolean isShow(){
        return show;
    }

    public void setShow(Boolean val){
        show = val;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return mobileNumber;
    }

    public void setShowMenu(boolean b) {
        menu = b;
    }

    public boolean isShowMenu() {
        return menu;
    }


}
