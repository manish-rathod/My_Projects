package Authentication;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import POJO.UserResponse;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import POJO.Response;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AuthenticationService {
    Response returnResponse = new Response();
    Gson gson = new Gson();
    ProgressDialog progressDialog;


    public void signUp(Context context, JSONObject json, final FetchData fetchData) throws UnsupportedEncodingException {
        StringEntity stringEntity = new StringEntity(json.toString());
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Signing Up");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        HttpClient.post(context,"/auth/register",stringEntity, "application/json", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("SignUp", "onSuccess: "+statusCode);
                UserResponse userResponse = gson.fromJson(response.toString(), UserResponse.class);
                Log.d("Login Response", "onSuccess: "+ userResponse);
                returnResponse.userResponse = userResponse;
                returnResponse.status = statusCode;
                fetchData.ProcessData(returnResponse);
                progressDialog.dismiss();
                for(int i=0; i< headers.length; i++){
                    Log.d("Login", "onFailure: "+headers[i]);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("SignUp", "onFailure: "+statusCode);
                progressDialog.dismiss();
                for(int i=0; i< headers.length; i++){
                    Log.d("Login", "onFailure: "+headers[i]);

                }
            }
        });
    }
    public void login(Context context, JSONObject json, final FetchData fetchData) throws UnsupportedEncodingException {
        StringEntity stringEntity = new StringEntity(json.toString());
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging in");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Log.d("Param", "login: "+json);
        HttpClient.post(context,"/auth/login",stringEntity,"application/json", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("SignUp", "onSuccess: "+response);
                UserResponse userResponse = gson.fromJson(response.toString(), UserResponse.class);
                Log.d("Login Response", "onSuccess: "+ userResponse);
                returnResponse.userResponse = userResponse;
                returnResponse.status = statusCode;
                fetchData.ProcessData(returnResponse);
                progressDialog.dismiss();;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Login", "onFailure: "+statusCode);
                returnResponse.status = statusCode;
                progressDialog.dismiss();
            }



        });

    }
}
