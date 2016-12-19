package com.jenle.interviewapp.evaluate.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.login.view.LoginActivity;
import com.jenle.interviewapp.evaluate.model.EvaluationNetworkService;

public class EvaluationActivity extends AppCompatActivity{
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SyncReceiver mSyncReceiver = new SyncReceiver();
        LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        // Register receiver for broadcasts when local db is already in sync with remote db
        IntentFilter inSyncFilter = new IntentFilter(Config.ALREADY_IN_SYNC_ACTION);
        mLocalBroadcastManager.registerReceiver(mSyncReceiver, inSyncFilter);

        // Register receiver for broadcasts when sync is complete
        IntentFilter syncCompleteFilter = new IntentFilter(Config.SYNC_COMPLETE);
        mLocalBroadcastManager.registerReceiver(mSyncReceiver, syncCompleteFilter);


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

            case R.id.logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sync(){

        Intent intent = new Intent(this, EvaluationNetworkService.class);
        intent.putExtra(Config.SOURCE, Config.BULK_SYNC);
        startService(intent);
        showSyncProgress(true);
    }

    public void logout() {

        new Utils().removeStringFromPreference(getApplicationContext(), getResources().getString(R.string.token));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Shows or hides a progress bar
     * @param bool
     */
    public void showSyncProgress(boolean bool) {
        if(bool){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            if (progressBar != null){
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void showToast(int messageId){
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    // Broadcast receiver for receiving update if local db is already in sync
    // with remote db, when a sync action is initiated by the user
    private class SyncReceiver extends BroadcastReceiver {

        private SyncReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent){

            String action = intent.getAction();

            if (action.equals(Config.ALREADY_IN_SYNC_ACTION)){

                showSyncProgress(false);
                showToast(R.string.in_sync);
            }else if (action.equals(Config.SYNC_COMPLETE)){

                showSyncProgress(false);
            }

        }
    }

}
