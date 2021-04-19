package com.example.socialapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomepageAdapter extends BaseAdapter {
    Activity activity;
    JSONArray posts;
    LayoutInflater layoutInflater;
    public HomepageAdapter(Activity activity, JSONArray posts){
        this.activity = activity;
        this.posts = posts;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return posts.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return posts.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder{
        TextView profileName, postTitle;
        ImageView profileImage, postImage;
        Button postLikes, postDislikes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        View view = convertView;
        if(view == null){
            viewholder = new ViewHolder();
            view =layoutInflater.inflate(R.layout.posts, parent, false);
            viewholder.profileImage = view.findViewById(R.id.profileImage);
            viewholder.profileName = view.findViewById(R.id.profileName);
            viewholder.postTitle = view.findViewById(R.id.postTitile);
            viewholder.postImage = view.findViewById(R.id.postImage);
            viewholder.postLikes = view.findViewById(R.id.postLikes);
            viewholder.postDislikes = view.findViewById(R.id.postDislikes);
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        try {
            JSONObject obj = (JSONObject) posts.get(position);
            viewholder.profileName.setText(obj.get("profileName").toString());
//            viewholder.profileImage.setText(obj.get("profileName").toString());
            viewholder.postTitle.setText(obj.get("postTitle").toString());
            viewholder.postLikes.setText(obj.get("postLikes").toString()+" Like");
            viewholder.postDislikes.setText(obj.get("postDislikes").toString()+" Dislike");
            if(obj.get("postImage").toString() == "null"){
                viewholder.postImage.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
