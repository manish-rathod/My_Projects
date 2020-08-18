package com.example.gallery;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class expanded_image extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView expandedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_expanded_image);


        String image = getIntent().getStringExtra("Image_Path");
        expandedImage = findViewById(R.id.expandedImage);
        Glide.with(this).load(image).into(expandedImage);
//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListner());


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }

//    private class ScaleListner extends ScaleGestureDetector.SimpleOnScaleGestureListener{
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//            expandedImage.setScaleX(mScaleFactor);
//            expandedImage.setScaleY(mScaleFactor);
//            return true;
//        }
//
//
//    }
}