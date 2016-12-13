package com.jenle.interviewapp.login.model;

import android.app.IntentService;
import android.content.Intent;

/**
 * Performs remote authentication of login credentials
 * Created by Jenle Samuel on 12/12/16.
 */

public class LoginNetworkService extends IntentService {

    public LoginNetworkService(){
        super("LoginNetworkService");
    }

    public void onHandleIntent(Intent intent) {

    }
}
