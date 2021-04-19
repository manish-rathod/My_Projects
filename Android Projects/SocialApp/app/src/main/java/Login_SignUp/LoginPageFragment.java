package Login_SignUp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


public class LoginPageFragment extends Fragment {
    SharedPreferences preferences;
    Gson gson = new Gson();
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.login_page_fragment, null);
        preferences= getActivity().getSharedPreferences(Contsants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return view;
    }

    private String getUserEmail(){
        EditText email = getView().findViewById(R.id.email);
        return email.getText().toString();
    }
    private String getUserPassword(){
        EditText password = getView().findViewById(R.id.password);
        return password.getText().toString();
    }

    public void login(){

        final AuthenticationService service = new AuthenticationService();
        JSONObject object = new JSONObject();
        try {
            object.put("username", getUserEmail());
            object.put("password", getUserPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            FetchData fetchData = new FetchData() {
                @Override
                public void ProcessData(Response response) {
                    //Login_SignUp.User@a9f3a49
                    Log.d("USer", "ProcessData: "+gson.toJson(response.userResponse.user));
                    preferences.edit().putString(Contsants.PREFERENCE_USER, gson.toJson(response.userResponse.user)).apply();
                    preferences.edit().putString(Contsants.PREFERENCE_TOKENDETAILS,gson.toJson(response.userResponse.tokenDetails)).apply();


                    VerifyLogin(response);
//                    progressDialog.dismiss();
                }
            };
            service.login(getContext(), object, fetchData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void VerifyLogin(Response response){
        if(response.status == 200){
            openActivity();
        }else if(response.status == 403 ){
            Toast.makeText(getContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
        }
    }

    private void openActivity(){
        Intent intent = new Intent(getActivity(), Homepage.class);
        startActivity(intent);
    }

}
