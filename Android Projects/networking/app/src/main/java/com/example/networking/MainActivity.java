package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    public JSONArray js;
    public TextView data;
    CustomArrayAdapter adapter;
    SwipeRefreshLayout mySwipeRefreshLayout;
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        data = (TextView) findViewById(R.id.textView);


//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fetchData();
//            }
//        });
        mySwipeRefreshLayout = findViewById(R.id.refresh);
//        mySwipeRefreshLayout.setRefreshing(true);
        fetchData();
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchData();
                    }
                }
        );

    }



    private void fetchData(){
        NetworkData mainData = new NetworkData() {
            @Override
            void dataProcess(String result) {
                ListView listView = (ListView) findViewById(R.id.list);
                try {
                        js = new JSONArray(result);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                adapter = new CustomArrayAdapter(MainActivity.this,js);
                listView.setAdapter(adapter);
                mySwipeRefreshLayout.setRefreshing(false);

            }
        };
        FetchData fetchData = new FetchData(mainData);

        fetchData.execute("https://api.github.com/repositories");
    }


}