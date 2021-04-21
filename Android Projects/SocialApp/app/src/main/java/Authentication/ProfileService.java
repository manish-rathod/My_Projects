package Authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import Login_SignUp.User;
import POJO.UserResponse;
import POJO.Response;
import cz.msebera.android.httpclient.Header;

public class ProfileService {
    ProgressDialog progressDialog;
    User user;
    Gson gson = new Gson();
    Response returnResponse = new Response();


    public void updateProfileImage(final Activity activity, RequestParams params){
        HttpClient httpClient = new HttpClient(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("uploading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        httpClient.patchProfile(activity, "/profile/image", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("create", "onSuccess: "+statusCode);
                Log.d("response", "onSuccess: "+response);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable ,JSONObject response) {
                Log.d("createPost", "onFailure: "+statusCode);
                Log.d("TAG", "onFailure: "+response);
                for(int i =0; i< headers.length; i++){
                    Log.d("Header", "onFailure: "+headers[i]);
                };
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("createPost", "onFailure: "+statusCode);
                for(int i =0; i< headers.length; i++){
                    Log.d("Header", "onFailure: "+headers[i]);
                };
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("createPost", "onFailure: "+statusCode);
                for(int i =0; i< headers.length; i++){
                    Log.d("Header", "onFailure: "+headers[i]);
                };
                progressDialog.dismiss();            }
        });

    }

    public void getProfile(final Activity activity, RequestParams params, final FetchData fetchData, String path){
        HttpClient httpClient = new HttpClient(activity);
        httpClient.get(path, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = gson.fromJson(response.toString(), User.class);
                UserResponse userResponse = new UserResponse();
                userResponse.user = user;
                returnResponse.userResponse = userResponse;
                fetchData.ProcessData(returnResponse);
                Log.d("res", "onSuccess: "+response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                returnResponse.userResponse = new UserResponse();

                returnResponse.userResponse.searchedUsers = gson.fromJson(response.toString(), User[].class);
                fetchData.ProcessData(returnResponse);
                Log.d("Searched", "onSuccess: "+response);
                Log.d("searched", "onSuccess: "+returnResponse.userResponse.searchedUsers.length);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("json object", "onFailure: "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("json array", "onFailure: "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("response String", "onFailure: "+statusCode);
            }
        });
    }
}
