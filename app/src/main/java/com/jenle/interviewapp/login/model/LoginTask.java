package com.jenle.interviewapp.login.model;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.login.LoginContract;
import com.jenle.interviewapp.sync.QueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jenle Samuel on 12/12/16.
 */

public class LoginTask extends AsyncTask<HashMap<String, String>, Void, Void> {

    private LoginContract.View loginView;
    private Context context;

    public LoginTask(Context context, LoginContract.View loginView){
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        loginView.showProgress();
    }

    @Override
    protected Void doInBackground(HashMap<String, String>... params){
        return null;
    }

    @Override
    protected void onPostExecute(Void result){

    }
}
