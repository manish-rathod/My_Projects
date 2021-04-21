package com.example.gallery;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String image;
    GridView gridView;
    View childView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageAdapter adapter = new ImageAdapter(this);

        gridView  = (GridView) findViewById(R.id.galleryGridView);
        gridView.setAdapter(new ImageAdapter(this));

//        final ImageView expandedImage = (ImageView) findViewById(R.id.expandedImage);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image = (String) gridView.getItemAtPosition(position);
                Log.d("Expand Image", "onItemClick: "+image);
                childView = gridView.getChildAt(position - gridView.getFirstVisiblePosition());
                openActivity();
            }
        });

    }

    private void openActivity(){
        Intent intent = new Intent(this, expanded_image.class);
        intent.putExtra("Image_Path", image);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View)childView, "imageExpand");
        startActivity(intent, options.toBundle());
    }



}