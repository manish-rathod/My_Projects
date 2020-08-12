package com.example.recyleview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.ViewHolder> {

    JSONArray data;
    public RecylerAdapter(JSONArray data){
        this.data = data;
        Log.d("Inside adapter", "RecylerAdapter: "+this.data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView fullname;

        public ViewHolder(View linearLayout){
            super(linearLayout);
        }
    }

    public RecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ViewHolder v = new (ViewHolder) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.name = (TextView) v.findViewById(R.id.name);
        vh.fullname = (TextView) v.findViewById(R.id.fullname);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject object = (JSONObject) data.get(position);
            Log.d("viewholder", "onBindViewHolder: "+object);
            holder.name.setText((String)object.get("name"));
            holder.fullname.setText((String)object.get("full_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return data.length();
    }
}
