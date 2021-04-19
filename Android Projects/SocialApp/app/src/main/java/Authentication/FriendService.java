package Authentication;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FriendService {

    public void FriendRequest(Activity activity, RequestParams params, String path){
        HttpClient httpClient = new HttpClient(activity);
        httpClient.get(path, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Send friend Request", "onSuccess: "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Send friend Request", "onFailure: "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Send friend Request", "onFailure: "+statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Send friend Request", "onFailure: "+statusCode);
                for(int i=0; i<headers.length; i++){
                    Log.d("freind request error", "onFailure: "+headers[i]);
                }
            }


        });
    }
}
