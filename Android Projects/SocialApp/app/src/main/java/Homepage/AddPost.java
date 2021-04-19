package Homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.socialapp.Contsants;
import com.example.socialapp.R;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import Authentication.PostService;
import Login_SignUp.User;

public class AddPost extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Button addImage;
    SharedPreferences preferences;
    Gson gson = new Gson();
    ImageView newImage;
    EditText newPostTitle;
    String userJson, tokenDetailsJson;
    User user;
    String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        preferences = this.getSharedPreferences(Contsants.PREFERENCE_NAME, MODE_PRIVATE);
        userJson = preferences.getString(Contsants.PREFERENCE_USER, null);
        user = gson.fromJson(userJson, User.class);
        TextView userName = findViewById(R.id.userName);
        userName.setText(user.name);

        addImage = findViewById(R.id.addImage);
        newPostTitle = findViewById(R.id.newPostTile);
        newImage = findViewById(R.id.newPostImage);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addImage.setVisibility(View.GONE);
                Intent intent = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });


        Button addPost = findViewById(R.id.post);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EDIT", "onClick: ");
                try {
                    createPost();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] fileColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, fileColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(fileColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();
            newImage.setVisibility(View.VISIBLE);
//            newImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            Glide.with(this).load(imagePath).fitCenter().into(newImage);
            addImage.setText("Change Image");

        }
    }

    private void createPost() throws FileNotFoundException {
        PostService service = new PostService();
        RequestParams params = new RequestParams();
        if(newImage.getVisibility() == View.GONE){
            String postTitle = newPostTitle.getText().toString();
            Log.d("post title", "createPost: "+postTitle);
            params.put(Contsants.POST_MESSAGE, postTitle);
            service.createPost(this,params);
        }else if(newPostTitle.getText().toString().equals("")){
            Log.d("TAG", "createPost: "+imagePath);
            Log.d("TAG", "createPost: view not gone");
            File file = new File(imagePath);
            params.put(Contsants.POST_FILE, file);
            service.createPost(this, params);
        }else {
            String postTitle = newPostTitle.getText().toString();
            File file = new File(imagePath);
            params.put(Contsants.POST_FILE, file);
            params.put(Contsants.POST_MESSAGE, postTitle);
            service.createPost(this, params);
        }
    }
}
