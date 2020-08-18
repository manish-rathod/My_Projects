package com.example.gallery;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    List<String> images;
    LayoutInflater layoutInflater;
    public ImageAdapter(Activity activity){
        this.activity = activity;
        images = data();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public String getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView pictures;
        if(convertView == null){
            pictures = new ImageView(activity);
            pictures.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pictures.setLayoutParams(new GridView.LayoutParams(270, 270));
        }else {
            pictures = (ImageView) convertView;
        }

        Glide.with(activity).load(images.get(position)).centerCrop().into(pictures);

//        v = layoutInflater.from(activity).inflate(R.layout.gallery, parent, false);
//
//        TextView name = (TextView) v.findViewById(R.id.name);
//        ImageView thumbnail = v.findViewById(R.id.thumbnail);
//        name.setText(images.get(position));

        return pictures;
    }

    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                activity,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{permission},
                    requestCode);
        }
        else {
            Toast.makeText(activity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public List<String> data(){
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,101);

        List<String> paths = new ArrayList<String>();

        String[] projections = new String[]{

                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = activity.getContentResolver().query(images, projections,null,null,null);

        assert cur != null;
//        Log.d("Listing Images", "count: "+cur.getCount());

        if(cur.moveToFirst()){
            String bucket;
            String data;
//            Log.d("HEllo", "onCreate: ");
            int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
            int folderColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            do{
                data = cur.getString(dataColumn);
                bucket = cur.getString(folderColumn);

//                Log.d("Images"," paths: "+data);
                paths.add(data);
            }while(cur.moveToNext());
        }
        cur.close();
        return paths;
    }
}
