package com.example.networking;

import android.graphics.Bitmap;
import android.widget.ImageView;

abstract class NetworkData {
    public NetworkData(){}

    abstract void dataProcess(String result);
}
abstract class NetworkImage {
    ImageView img;
    public NetworkImage(){

    }

    public void setImg(ImageView img){
        this.img = img;
    }

    abstract void imageProcess(Bitmap bitmap);
}
