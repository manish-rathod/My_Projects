package com.example.networking;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<String, Void, String> {
    String data;
    JSONArray js;
    MainActivity activity;
    CustomArrayAdapter adapter;
    NetworkData networkData;

    public FetchData(NetworkData networkData){
        this.networkData = networkData;
    }

    @Override
    protected String doInBackground(String[] urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            if(data.startsWith("null")){
                data = data.substring(4,data.length());
            }
            Log.d("data received", "doInBackground: "+data);

//            Log.d("Data", "doInBackground: "+data);
//            js = new JSONArray(data);

            return data;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        networkData.dataProcess(result);
//        activity.data.setText(this.data);

    }


}
