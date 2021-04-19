package Login_SignUp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialapp.Contsants;
import com.example.socialapp.R;
import com.google.gson.Gson;

import Authentication.AuthenticationService;
import Authentication.FetchData;
import POJO.Response;
import Homepage.Homepage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SignUpPageFragment extends Fragment {

    SharedPreferences preferences ;
    Gson gson = new Gson();
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_page_fragment, null);
        preferences = getContext().getSharedPreferences(Contsants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return view;

    }
    public String getUserEmail(){
        EditText email = getView().findViewById(R.id.email);
        return email.getText().toString();
    }
    public String getUserPassword(){
        EditText password = getView().findViewById(R.id.password);
        return password.getText().toString();
    }

    public String getUserName(){
        EditText username = getView().findViewById(R.id.username);
        return username.getText().toString();
    }

    public void signUp(){

        final AuthenticationService service = new AuthenticationService();
        JSONObject object = new JSONObject();
        try {
            object.put("email", getUserEmail());
            object.put("password", getUserPassword());
            object.put("name", getUserName());
            object.put("c_password", getUserPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            FetchData fetchData = new FetchData() {
                @Override
                public void ProcessData(Response response) {

                    preferences.edit().putString(Contsants.PREFERENCE_USER, gson.toJson(response.userResponse.user)).apply();
                    preferences.edit().putString(Contsants.PREFERENCE_TOKENDETAILS, gson.toJson(response.userResponse.tokenDetails)).apply();

                    VerifySignUp(response);
//                    progressDialog.dismiss();
                }
            };
            service.signUp(getContext(), object, fetchData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void openActivity(){
        Intent intent = new Intent(getActivity(), Homepage.class);
        startActivity(intent);
    }


    private void VerifySignUp(Response response){
        if(response.status == 200){
            openActivity();
        }else if(response.status == 204){
            Toast.makeText(getContext(), "User already exists", Toast.LENGTH_LONG).show();
        }
    }
}
