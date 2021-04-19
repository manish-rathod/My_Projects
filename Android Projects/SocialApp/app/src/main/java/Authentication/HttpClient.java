package Authentication;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.socialapp.BuildConfig;
import com.example.socialapp.Contsants;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import Login_SignUp.TokenDetails;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpClient {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    private static AsyncHttpClient client = new AsyncHttpClient();
    static Gson gson = new Gson();
    static SharedPreferences preferences;
    static TokenDetails tokenDetails;

    HttpClient(Context context){
        preferences = context.getSharedPreferences(Contsants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String tokenDetailsJson = preferences.getString(Contsants.PREFERENCE_TOKENDETAILS, null);
        tokenDetails = gson.fromJson(tokenDetailsJson, TokenDetails .class);
    }






    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("appId", "socnet");
        client.addHeader("Authorization", "Bearer "+tokenDetails.token);
        client.addHeader("Authorization", "Bearer "+tokenDetails.token);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity params,String contentType, AsyncHttpResponseHandler responseHandler){
//        client.post(getAbsoluteUrl(url), params, responseHandler);
        client.addHeader("appId", "socnet");
        client.post(context, getAbsoluteUrl(url),params, "application/json", responseHandler);
    }

    public static void postFormData(Context context, String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("appId", "socnet");
        client.addHeader("Authorization", "Bearer "+tokenDetails.token);
        client.post(context, getAbsoluteUrl(url),params,responseHandler);
    }

    public static void patchProfile(Context context, String url,RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("appId", "socnet");
        client.addHeader("Authorization", "Bearer "+tokenDetails.token);
        client.patch(context, getAbsoluteUrl(url),params,responseHandler);
    }

    private static String getAbsoluteUrl(String url){
        Log.d("TAG", "getAbsoluteUrl: "+BASE_URL+ url);
        return BASE_URL + url;
    }
}
