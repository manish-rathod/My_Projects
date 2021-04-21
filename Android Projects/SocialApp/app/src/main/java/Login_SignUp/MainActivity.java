package Login_SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.socialapp.Contsants;
import com.example.socialapp.R;

import Homepage.Homepage;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Button bottomButton;
    Button topButton;
    LinearLayout divider;
    LoginPageFragment LoginPageFragment = new LoginPageFragment();
    SignUpPageFragment SignUpPageFragment = new SignUpPageFragment();
    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(Contsants.PREFERENCE_NAME, MODE_PRIVATE);
        if(preferences.getString(Contsants.PREFERENCE_USER, null) != null){
            openActivity();
        }

        bottomButton = findViewById(R.id.bottomButton);
        topButton = findViewById(R.id.topButton);
        divider = findViewById(R.id.divider);

        fragmentManager= getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null).add(R.id.login, LoginPageFragment);
        fragmentTransaction.commit();



        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomButton.getText().toString().equals("Login")){
//                    changeButtonPosition(topButton, bottomButton, divider);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login, LoginPageFragment);
                    fragmentTransaction.commit();
                }else if(bottomButton.getText().toString().equals("Sign Up")){
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login, SignUpPageFragment);
                    fragmentTransaction.commit();

                }
                changeButton();


            }
        });

        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topButton.getText().toString().equals("Sign Up")){
                    Log.d("TAG", "onClick: signup");
                    SignUpPageFragment.signUp();
                }else{
                    LoginPageFragment.login();
                }

            }
        });
    }



    private void changeButton(){
        String temp = topButton.getText().toString();
        topButton.setText(bottomButton.getText().toString());
        bottomButton.setText(temp);
    }

    private void openActivity(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }


}
