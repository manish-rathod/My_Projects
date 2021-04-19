package com.example.socialapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button signUp;
    Button loginButton;
    LinearLayout divider;
    login_page_fragment login_page_fragment = new login_page_fragment();
    signup_page_fragment signup_page_fragment = new signup_page_fragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUp = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);
        divider = findViewById(R.id.divider);

        fragmentManager= getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null).add(R.id.login, login_page_fragment);
        fragmentTransaction.commit();



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp.getTag().equals("below")){
                    changeButtonPosition(loginButton, signUp, divider);
                    signup_page_fragment = new signup_page_fragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login, signup_page_fragment);
                    fragmentTransaction.commit();
                }else{
                    String email = signup_page_fragment.getUserEmail();
                    String password = signup_page_fragment.getUserPassword();
                    String username = signup_page_fragment.getUserName();
                    openActivity();
                }

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginButton.getTag() == "below"){
                    changeButtonPosition(signUp, loginButton,divider);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login, login_page_fragment);
                    fragmentTransaction.commit();
                }else{
                    String email = login_page_fragment.getUserEmail();
                    String password = login_page_fragment.getUserPassword();
                    openActivity();

                }
            }
        });
    }

    private void changeButtonPosition(Button topButton, Button bottomButton, LinearLayout divider){

        RelativeLayout.LayoutParams layoutParams;
        layoutParams= (RelativeLayout.LayoutParams) topButton.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.divider);
        topButton.setLayoutParams(layoutParams);
        topButton.setTag("below");


        layoutParams = (RelativeLayout.LayoutParams) bottomButton.getLayoutParams();
        layoutParams.removeRule(RelativeLayout.BELOW);
        bottomButton.setLayoutParams(layoutParams);
        bottomButton.setTag("");

        layoutParams = (RelativeLayout.LayoutParams) divider.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, bottomButton.getId());
        divider.setLayoutParams(layoutParams);


    }

    private void openActivity(){
        Intent intent = new Intent(this, homepage.class);
        startActivity(intent);
    }
}
