package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Item extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);

        getSupportActionBar().setTitle("Add Item");

        Button add_item = findViewById(R.id.add_item);


        myDb = new DatabaseHelper(this);

        task = (EditText) findViewById(R.id.task);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: can optimise
                myDb.addItem(task.getText().toString());
                openActivity();
            }
        });

    }
        public void openActivity(){
            finish();
        }


}