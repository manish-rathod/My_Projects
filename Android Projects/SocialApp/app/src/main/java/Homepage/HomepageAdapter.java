package Homepage;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.socialapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import POJO.Post;
import POJO.PostsResponse;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {
    PostsResponse response[] = {};
    Gson gson = new Gson();
    Activity activity;
//    Post post;
    public HomepageAdapter(Activity activity){
        this.activity = activity;
    }

    public void setData(PostsResponse response[]){
        this.response = response;
        Log.d("response in adapter", "setData: "+response.length);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts,parent ,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.profileImage = (ImageView) view.findViewById(R.id.profileImage);
        viewHolder.profileName = (TextView) view.findViewById(R.id.profileName);
        viewHolder.postTitle = (TextView) view.findViewById(R.id.postTitle);
        viewHolder.postImage = (ImageView) view.findViewById(R.id.postImage);
        viewHolder.postLikes = (Button) view.findViewById(R.id.postLikes);
        viewHolder.postDislikes = (Button) view.findViewById(R.id.postDislikes);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("length", "onBindViewHolder: "+position);
            PostsResponse post = response[position];
            Log.d("text", "onBindViewHolder: "+post.text);
            Log.d("img", "onBindViewHolder: "+post.img);
            Log.d("id", "onBindViewHolder: "+post._id);
            Log.d("name", "onBindViewHolder: "+post.postedBy.name);
//            post = gson.fromJson(obj.toString(), Post.class);
            holder.profileName.setText(post.postedBy.name);
//            viewholder.profileImage.setText(obj.get("profileName").toString());
            holder.postTitle.setText(post.text);
            holder.postLikes.setText(" Like");
            holder.postDislikes.setText(" Dislike");
            if(post.img == null){
                holder.postImage.setVisibility(View.GONE);
                Log.d("postImage", "onBindViewHolder: "+post.img);
            }else{
                holder.postImage.setVisibility(View.VISIBLE);
                Glide.with(activity).load(post.img).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.postImage);
            }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return response.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView profileName, postTitle;
        ImageView profileImage, postImage;
        Button postLikes, postDislikes;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
    }


}
