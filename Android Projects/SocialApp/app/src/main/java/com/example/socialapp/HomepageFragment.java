package com.example.socialapp;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomepageFragment extends Fragment {

    public static final String Title = "Homepage";

    public static HomepageFragment newInstance(){
        return  new HomepageFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, null);
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        try {
            obj1.put("profileImage","null");
            obj1.put("profileName","Manish Rathod");
            obj1.put("postTitle","First post");
            obj1.put("postImage","null");
            obj1.put("postLikes",2);
            obj1.put("postDislikes",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj2.put("profileImage","null");
            obj2.put("profileName","Ravindra Rathod");
            obj2.put("postTitle","First post of Ravindra");
            obj2.put("postImage","null");
            obj2.put("postLikes",3);
            obj2.put("postDislikes",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray posts = new JSONArray();
        posts.put(obj1);
        posts.put(obj2);


        ListView listView = view.findViewById(R.id.home);
        HomepageAdapter adapter = new HomepageAdapter(getActivity(), posts);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
