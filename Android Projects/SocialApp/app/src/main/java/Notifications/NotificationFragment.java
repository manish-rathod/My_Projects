package Notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationFragment extends Fragment {

    public static final String Title = "Notifications";
    private RecyclerView.LayoutManager layoutManager;

    public static NotificationFragment newInstance(){
        return  new NotificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.notification_fragment, null);

        // TODO : make sure deleteable/mock data / mock code is put in a method  so that can be deleted with ease also makes it more readable
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        JSONObject obj3 = new JSONObject();
        try {
            obj1.put("notificationData", "friend");
            obj2.put("notificationData", "liked");
            obj3.put("notificationData", "disliked");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray array = new JSONArray();
        array.put(obj1);
        array.put(obj2);
        array.put(obj3);


        // TODO understand list view , but try to use recycler view.
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notifications);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        NotificationAdapter adapter = new NotificationAdapter(array);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
