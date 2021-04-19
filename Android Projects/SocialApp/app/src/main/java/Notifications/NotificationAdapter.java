package Notifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialapp.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    JSONArray notifications;
    Context context;
    Gson gson = new Gson();

    public NotificationAdapter(JSONArray array){
        this.context = context;
        this.notifications = array;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView notificationData;

        public ViewHolder(View view){
            super(view);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.notificationData = (TextView) view.findViewById(R.id.notificationData);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            Log.d("Size", "onBindViewHolder: "+notifications.length());
            String data = notifications.get(position).toString();
            Log.d("Notifications", "onBindViewHolder: "+data);
            NotificationObject notificationObject = gson.fromJson(data, NotificationObject.class);
            holder.notificationData.setText(notificationObject.notificationData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.notifications.length();
    }
}