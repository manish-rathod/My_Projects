package FriendRequest;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialapp.R;
import com.google.gson.Gson;

import Authentication.FriendService;
import Login_SignUp.User;
import POJO.FriendRequest;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
//    private JSONArray friends;
    Gson gson = new Gson();
    FriendRequest friendRequest;
    User searchedResponse[] = {};
    FriendService friendService = new FriendService();
    Activity activity;
    User obj;

    FriendRequestAdapter(Activity activity){
        this.activity =activity;
    }

    public void setData(User searchedResponse[]){
        this.searchedResponse = searchedResponse;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.friendRequestImage = view.findViewById(R.id.friendImage);
        viewHolder.friendRequestName = view.findViewById(R.id.friendName);
        viewHolder.send = view.findViewById(R.id.friendRequestSend);
        viewHolder.reject = view.findViewById(R.id.friendRequestReject);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            obj = searchedResponse[position];
//            friendRequest = gson.fromJson(obj.toString(), FriendRequest.class);
            Log.d("friendRequestName", "onBindViewHolder: "+obj.name);
            holder.friendRequestName.setText(obj.name);
//            Log.d("FriendRequestImage", "onBindViewHolder: "+friendRequest.friendRequestImage);
            if(obj.profilePic != null){
                Log.d("Image exists", "onBindViewHolder: ");
                Glide.with(holder.friendRequestImage).load(obj.profilePic).circleCrop().into(holder.friendRequestImage);
            }

            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendService.FriendRequest(activity,null, "/freind/?freindId="+obj._id+"/accept");
                }
            });

            holder.reject.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendService.FriendRequest(activity,null, "/freind/?freindId="+obj._id+"/decline");

                }
            }));

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return searchedResponse.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView friendRequestImage;
        TextView friendRequestName;
        Button send, reject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
