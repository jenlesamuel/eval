package com.jenle.interviewapp.login.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.login.LoginContract;
import com.jenle.interviewapp.QueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Handles user login action
 * Created by Jenle Samuel on 12/12/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private Context appContext;
    private LoginContract.View loginView;
    private int statusCode;
    private static final String TAG = "LOGINCONTRACT";

    public LoginPresenter(Context appContext, LoginContract.View loginView){
        this.appContext = appContext;
        this.loginView = loginView;
    }

    @Override
    public void login(Context context, String email, String password) {

        HashMap<String, String> credentials = new HashMap<String, String>();
        credentials.put(Config.KEY_USERNAME, email);
        credentials.put(Config.KEY_PASSWORD, password);

        JSONObject param = new JSONObject(credentials);
        Log.d(TAG, param.toString());
        makeRemoteCall(param);

    }

    public void makeRemoteCall(JSONObject param){

        loginView.showProgress();

        // Create request
        String url = Config.REMOTE_BASE_URL+Config.LOGIN_PATH;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, param, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){

                loginView.closeProgress();

                if (statusCode == Config.HTTP_201_CREATED) { // 201 is returned by th api upon successful authentication

                    //Get auth token
                    String token = response.optString("token");

                    // Save token in preference
                    String tokenKey = appContext.getString(R.string.token);
                    Log.d(TAG, token);
                    new Utils().saveStringInPreference(appContext, tokenKey, token);

                    // redirect to evaluation activity
                    loginView.redirectToEvaluation();

                    return;

                }else if (statusCode == Config.HTTP_400_BAD_REQUEST){ // Invalid login credentials

                    String invalidCredentialsError = appContext.getString(R.string.login_credentials_error);
                    loginView.showMessage(appContext, invalidCredentialsError);
                    Log.d(TAG, statusCode+ response.toString());

                    return;
                } else{ // Handle other Http status codes

                    String loginError =  appContext.getString(R.string.login_error);
                    loginView.showMessage(appContext, loginError);
                    Log.d(TAG, "OTHER ERROR"+statusCode+ response.toString());

                    return;

                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

                loginView.closeProgress();
                //loginView.redirectToEvaluation();
                String loginError =  appContext.getString(R.string.login_error);
                loginView.showMessage(appContext, loginError);

                Log.d(TAG, "VOLLEY ERROR"+statusCode+ error.getMessage());

                return;
            }

        }) {

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }
        };

        // Make remote call
        QueueSingleton mQueueSingleton = QueueSingleton.getInstance(appContext);
        mQueueSingleton.addToRequestQueue(jsonObjectRequest);
    }
}
