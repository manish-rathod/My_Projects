package com.example.networking;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class CustomArrayAdapter extends BaseAdapter {
    Activity activity;
    JSONArray js;
    LayoutInflater layoutInflater;
    HashMap<String, Bitmap> images = new HashMap<String, Bitmap>();
    public  CustomArrayAdapter(Activity activity, JSONArray js){
        this.activity = activity;
        this.js = js;
        layoutInflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return js.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class Viewholder{
        TextView name;
        TextView fullName;
        ImageView owner;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;

        View v = convertView;
        if(v == null){
            viewholder = new Viewholder();
            v = layoutInflater.inflate(R.layout.listview, parent,false);
            viewholder.name =(TextView) v.findViewById(R.id.name);
            viewholder.fullName = (TextView) v.findViewById(R.id.fullName);
            viewholder.owner = (ImageView) v.findViewById(R.id.owner);
            v.setTag(viewholder);

        }else{
            viewholder = (Viewholder) v.getTag();
        }
        final Viewholder imageholder = viewholder;

        try {
            JSONObject object = (JSONObject) js.get(position);
            viewholder.name.setText((String)object.get("name"));
            viewholder.fullName.setText((String)object.get("full_name"));
            JSONObject owner = (JSONObject) object.get("owner");

            String imageUrl = (String) owner.get("avatar_url");

//            NetworkImage imgData = new NetworkImage() {
//
//                @Override
//                void imageProcess(Bitmap result) {
//
//                    if(imageholder.owner.getDrawable() == null){
//                        Log.d("image", "imageProcess: "+result);
//                        imageholder.owner.setImageBitmap(result);
//                        this.setImg(imageholder.owner);
//                    }
//
//                }
//            };


            if(!images.containsKey(imageUrl)) {
                FetchImage fetchImage = new FetchImage(viewholder.owner, CustomArrayAdapter.this);
                fetchImage.execute(imageUrl);
            }else{
                viewholder.owner.setImageBitmap(images.get(imageUrl));
            }

//
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

    
}
