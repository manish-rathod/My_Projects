package com.example.recyleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private JSONArray js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyleview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Data data = new Data() {
            @Override
            public void dataProcess(String string) {
                try {
                    js = new JSONArray(string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RecylerAdapter recylerAdapter = new RecylerAdapter(js);
                recyclerView.setAdapter(recylerAdapter);
            }
        };

        FetchData fetchData = new FetchData(data);
        fetchData.execute("https://api.github.com/repositories");
    }
}