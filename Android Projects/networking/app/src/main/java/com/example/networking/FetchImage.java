package com.example.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class FetchImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    CustomArrayAdapter customArrayAdapter;
    String url;
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);


    }

    public FetchImage(ImageView imageView, CustomArrayAdapter customArrayAdapter){
        this.imageView = imageView;
        this.customArrayAdapter = customArrayAdapter;
    }

    @Override
    protected Bitmap doInBackground(String[]  urls) {
        try {
            this.url = urls[0];
            if(customArrayAdapter.images.containsKey(url)) {
                return customArrayAdapter.images.get(url);
            }
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
//            Log.d("Create image", "doInBackground: "+bmp);
                 customArrayAdapter.images.put(this.url,bmp);
                return bmp;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
