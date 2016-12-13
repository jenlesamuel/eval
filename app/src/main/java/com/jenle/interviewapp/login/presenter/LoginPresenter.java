package com.jenle.interviewapp.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.login.LoginContract;
import com.jenle.interviewapp.login.model.LoginNetworkService;
import com.jenle.interviewapp.sync.QueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * Handles login action
 * Created by Jenle Samuel on 12/12/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private Context appContext;
    private LoginContract.View loginView;

    public LoginPresenter(Context appContext, LoginContract.View loginView){
        this.appContext = appContext;
        this.loginView = loginView;
    }

    @Override
    public void login(Context context, String email, String password) {

        HashMap<String, String> credentials = new HashMap<String, String>();
        credentials.put(Config.KEY_EMAIL, email);
        credentials.put(Config.KEY_PASSWORD, password);

        JSONObject param = new JSONObject(credentials);
        makeRemoteCall(param);

    }

    public void makeRemoteCall(JSONObject param){

        // Create request
        String url = Config.REMOTE_BASE_URL+Config.EVALUATION_PATH;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, param, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){

            }

        });

        // Make remote call
        QueueSingleton mQueueSingleton = QueueSingleton.getInstance(appContext);
        mQueueSingleton.addToRequestQueue(jsonObjectRequest);
    }
}
