package com.example.phone;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.phone.contacts.ContactsFragment;
import com.example.phone.dialer.DialerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        GetContacts obj = new GetContacts(getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onNavigationItemReselected: "+item);
                switch (item.getItemId()){
                    case R.id.fav:
                        Log.d("Fav", "fav selected");
                        return true;

                    case R.id.dialer:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new DialerFragment()).commit();
                        Log.d("Dialer", "dialer selected");
                        return true;

                    case R.id.contacts:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new ContactsFragment()).commit();
                        Log.d("Contacts", "contacts selected");
                        return true;
                }

                return true;
            }
        });

//        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                Log.d("TAG", "onNavigationItemReselected: "+item.getItemId());
//                switch (item.getItemId()){
//                    case R.id.fav:
//                        Log.d("Fav", "fav selected");
//                        return;
//
//                    case R.id.dialer:
//                        Log.d("Dialer", "dialer selected");
//                        return;
//
//                    case R.id.contacts:
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.frameLayout, new ContactsFragment()).commit();
//                        Log.d("Contacts", "contacts selected");
//                        return;
//                }
//            }
//        });



    }


}
