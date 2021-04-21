package Authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.example.socialapp.R;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import POJO.PostsResponse;
import POJO.Response;
import cz.msebera.android.httpclient.Header;

public class PostService {

    ProgressDialog progressDialog;
    Gson gson = new Gson();
    FetchData fetchData;
    Response returnResponse = new Response();

    public void createPost(final Activity activity, RequestParams params){
        HttpClient httpClient = new HttpClient(activity);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("uploading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        httpClient.postFormData(activity, "/post", params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("create", "onSuccess: "+statusCode);
                Log.d("response", "onSuccess: "+response);
                progressDialog.dismiss();
                activity.finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("createPost", "onFailure: "+statusCode);
                for(int i =0; i< headers.length; i++){
                    Log.d("Header", "onFailure: "+headers[i]);
                };
                progressDialog.dismiss();
            }

        });
    }

    public void getPosts(Activity activity, RequestParams params, final FetchData fetchData, String path){
        this.fetchData = fetchData;
        HttpClient httpClient = new HttpClient(activity);

        httpClient.get(path, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("create", "onSuccess: "+statusCode);
                try {
                    for (int i=0; i<response.length(); i++)
                    Log.d("response", "onSuccess: "+response.get(i));
//                    Double currency = (Double) response.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                returnResponse.postsResponse = gson.fromJson(response.toString(), PostsResponse[].class);
                fetchData.ProcessData(returnResponse);

            }


            @Override
            public void onFailure(int statusCode, Header[] headers,String string ,Throwable throwable) {

                Log.d("getPost", "onFailure: "+statusCode);
                for(int i =0; i< headers.length; i++){
                    Log.d("Header", "onFailure: "+headers[i]);
                };
            }
        });




    }
}
