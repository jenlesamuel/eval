package com.jenle.interviewapp.evaluate.view;

import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.InterviewAppException;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.model.EvaluationDAO;
import com.jenle.interviewapp.sync.EvaluationNetworkService;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_sync:
                sync();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sync(){

        Intent intent = new Intent(this, EvaluationNetworkService.class);
        intent.putExtra(Config.SOURCE, Config.BULK_SYNC);
        startService(intent);
    }

}
