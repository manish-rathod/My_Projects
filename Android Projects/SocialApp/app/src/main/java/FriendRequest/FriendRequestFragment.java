package FriendRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Authentication.FetchData;
import Authentication.ProfileService;
import Login_SignUp.User;
import POJO.Response;

public class FriendRequestFragment extends Fragment {

    public static final String Title = "Friends";

    RecyclerView.LayoutManager layoutManager;
    ProfileService profileService = new ProfileService();
    FetchData fetchData;
    EditText searchString;
    User searchedUsers[];
    FriendRequestAdapter adapter;



    public static FriendRequestFragment newInstance(){
        return  new FriendRequestFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.friends_fragment, null);


        ImageButton searchProfile = view.findViewById(R.id.searchProfile);
        searchString = view.findViewById(R.id.searchString);
        searchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProfile();
            }
        });

        adapter = new FriendRequestAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.Friends);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        fetchData = new FetchData() {
            @Override
            public void ProcessData(Response response) {
                searchedUsers = response.userResponse.searchedUsers;
                Log.d("Searched profile", "ProcessData: "+response.userResponse.searchedUsers[0].name);
                adapter.setData(searchedUsers);
                adapter.notifyDataSetChanged();
            }
        };
        String path = "/profile/search?"+"q="+searchString.getText().toString();
        profileService.getProfile(getActivity(), null, fetchData, path );

        return view;
    }

    public void searchProfile(){
        if(!searchString.getText().toString().equals(null)){

            fetchData = new FetchData() {
                @Override
                public void ProcessData(Response response) {
                    searchedUsers = response.userResponse.searchedUsers;
                    Log.d("Searched profile", "ProcessData: "+response.userResponse.searchedUsers[0].name);
                    adapter.setData(searchedUsers);
                    adapter.notifyDataSetChanged();
                }
            };
            String path = "/profile/search?"+"q="+searchString.getText().toString();
            profileService.getProfile(getActivity(), null, fetchData, path );



        }

    }
}
