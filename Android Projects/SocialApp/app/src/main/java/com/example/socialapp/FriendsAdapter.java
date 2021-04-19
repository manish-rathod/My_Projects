package com.example.socialapp;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendsAdapter extends BaseAdapter {

    private Activity activity;
    private JSONArray friends;
    private LayoutInflater layoutInflater;

    public FriendsAdapter(Activity activity, JSONArray friends){
        this.activity = activity;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        ImageView friendImage;
        TextView friendName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = layoutInflater.inflate(R.layout.friends, parent, false);
            viewHolder.friendImage = v.findViewById(R.id.friendImage);
            viewHolder.friendName = v.findViewById(R.id.friendName);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        try {
            JSONObject obj = (JSONObject) friends.get(position);
            viewHolder.friendImage.setImageURI((Uri)obj.get("FriendImage"));
            viewHolder.friendName.setText(obj.get("FriendName").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
