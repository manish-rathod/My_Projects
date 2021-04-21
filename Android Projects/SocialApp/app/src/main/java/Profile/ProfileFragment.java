package Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.socialapp.Contsants;
import com.example.socialapp.R;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import Authentication.FetchData;
import Authentication.PostService;
import Authentication.ProfileService;
import Homepage.HomepageAdapter;
import Login_SignUp.User;
import POJO.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    public static final String Title = "Profile";
    Gson gson = new Gson();
    private static int RESULT_LOAD_IMAGE = 1;
    String imagePath;
    ImageView userImage;
    ProfileService profileService = new ProfileService();
    SharedPreferences preferences;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HomepageAdapter adapter;
    PostService postService = new PostService();
    User user;

    public static ProfileFragment newInstance(){
        return  new ProfileFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.profile_fragment, null);
        preferences = getActivity().getSharedPreferences(Contsants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String userJson = preferences.getString(Contsants.PREFERENCE_USER, null);
        Log.d("UserJson", "onCreateView: "+userJson);
        user = new User();
        user = gson.fromJson(userJson, User.class);

        userImage = view.findViewById(R.id.userImage);

        if(preferences.getString(Contsants.USER_IMAGE, null) != null){
            Glide.with(getActivity()).load(preferences.getString(Contsants.USER_IMAGE, null)).circleCrop().into(userImage);
        }
        FetchData fetchData = new FetchData() {
            @Override
            public void ProcessData(Response response) {
                Glide.with(getActivity()).load(response.userResponse.user.profilePic).circleCrop().into(userImage);
                preferences.edit().putString(Contsants.USER_IMAGE, response.userResponse.user.profilePic);
            }
        };

        profileService.getProfile(getActivity(), null, fetchData, "/profile");

        ImageButton edit = view.findViewById(R.id.editProfile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        TextView textView = view.findViewById(R.id.username);
        textView.setText(user.name);

        recyclerView = view.findViewById(R.id.myPosts);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomepageAdapter(getActivity());
        refreshData();
        recyclerView.setAdapter(adapter);

        return  view;
    }

    public void refreshData(){
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FetchData fetchData = new FetchData() {
            @Override
            public void ProcessData(Response response) {
                adapter.setData(response.postsResponse);
                Log.d("TAG", "ProcessData: ");
                adapter.notifyDataSetChanged();
            }
        };

        postService.getPosts(getActivity(), null , fetchData, "/post/?postedBy="+user._id);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] fileColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImage, fileColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(fileColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();
            userImage.setVisibility(View.VISIBLE);
//            newImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            Glide.with(this).load(imagePath).diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().into(userImage);
            File updatedImage = new File(imagePath);
            RequestParams params = new RequestParams();
            try {
                params.put(Contsants.POST_FILE, updatedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            profileService.updateProfileImage(getActivity(),params);
            

        }
    }
}
