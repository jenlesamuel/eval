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
import android.widget.Toast;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.login.view.LoginActivity;
import com.jenle.interviewapp.evaluate.model.EvaluationNetworkService;

public class EvaluationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            // Don't restore state when Activity is recreated from fragment
            boolean restoreState = savedInstanceState.getBoolean(Config.RESTORE_STATE, true);
            if (!restoreState) savedInstanceState = null;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IntentFilter inSyncFilter = new IntentFilter(Config.BROADCAST_IN_SYNC_ACTION);
        InSyncReceiver mInsyncReceiver = new InSyncReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mInsyncReceiver, inSyncFilter
        );
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
    }

    public void logout() {

        new Utils().removeStringFromPreference(getApplicationContext(), getResources().getString(R.string.token));
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        // Save flag to not restore state if activity is recreated from fragment
        boolean restoreState = getIntent().getBooleanExtra(Config.RESTORE_STATE, true);
        savedInstanceState.putBoolean(Config.RESTORE_STATE, restoreState);

        super.onSaveInstanceState(savedInstanceState);

    }

    // Broadcast receiver for receiving update if local db is already in sync
    // with remote db, when a sync action is initiated by the user
    private class InSyncReceiver extends BroadcastReceiver {

        private InSyncReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent){
            // No need to handle intent
            // Just show message since the intent is only sent when db is already in sync

            Toast.makeText(context, R.string.in_sync, Toast.LENGTH_SHORT).show();
        }
    }

}
