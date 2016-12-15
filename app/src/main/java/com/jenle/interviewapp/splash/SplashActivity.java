package com.jenle.interviewapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.view.EvaluationActivity;
import com.jenle.interviewapp.login.view.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Check if user has a valid session and redirect as necessary
        String token = new Utils().getStringFromPreference(this, getResources().getString(R.string.token));
        if (token != null) {
            startActivity(new Intent(this, EvaluationActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
