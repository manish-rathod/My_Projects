package com.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomArrayAdapter extends BaseAdapter {
    List<Items> itemsList;
    LayoutInflater layoutInflater;
    Activity activity;
    public CustomArrayAdapter(Activity activity, List<Items> items) {
        itemsList = items;
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private static class Viewholder{
        TextView txt;
        CheckBox chk;
    }


    Viewholder viewholder;

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            viewholder = new Viewholder();
            v = layoutInflater.inflate(R.layout.list_item, parent, false);
            viewholder.txt = (TextView) v.findViewById(R.id.task);
            viewholder.chk = (CheckBox) v.findViewById(R.id.checkbox);
            v.setTag(viewholder);
        }else{
            viewholder = (Viewholder) v.getTag();
        }

        Log.d("custom", "getView: "+itemsList.get(position).task + itemsList.get(position).status);
        viewholder.txt.setText(itemsList.get(position).task);
        if(itemsList.get(position).status == 0){
            viewholder.chk.setChecked(false);
        }else{
            viewholder.chk.setChecked(true);
        }
        return v;
    }
}
