package com.jenle.interviewapp.evaluate.model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.InterviewAppException;
import com.jenle.interviewapp.QueueSingleton;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.model.Evaluation;
import com.jenle.interviewapp.evaluate.model.EvaluationDAO;
import com.jenle.interviewapp.evaluate.model.EvaluationServiceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jenle Samuel on 12/9/16.
 */

public class EvaluationNetworkService extends IntentService {
    private static final String TAG = "EvaluationNwkService";
    private String source;
    private Utils utils;
    private Context context;
    private EvaluationDAO evaluationDAO;
    private JSONArray param;
    private int statusCode ;

    public EvaluationNetworkService(){
        super("EvaluationNetworkService");
    }

    @Override
    public void onHandleIntent(Intent intent){

        try {

            source = intent.getStringExtra(Config.SOURCE);
            utils = new Utils();
            context = getApplicationContext();
            evaluationDAO = utils.getEvaluationDAO(context);

            if (source.equals(Config.BULK_SYNC)) { // Sync was initiated by user

                // Retrieve network call param from db if user initiated sync
                ArrayList<HashMap<String, Object>> records = evaluationDAO.retrieveUnsynced(true);

                //Check if db is already in sync
                if (records.isEmpty()) {
                    sendInSyncBroadcast();
                    return;
                }

                param = utils.toJSONArray(records);
                Log.d(TAG, param.toString());

            } else if (source.equals(Config.UNI_SYNC)){ // implicit sync after a record is inserted into the db

                // Retrieve network call param from intent
                Long id = intent.getLongExtra(Config.ID, 0);
                if (id == 0) return;
                Evaluation evaluation = (Evaluation) intent.getSerializableExtra(Config.PARAM);
                param = new EvaluationServiceImpl().evaluationToJSONArray(evaluation, id);
                Log.d(TAG, param.toString());

            }

            // Get network call request
            String url = Config.REMOTE_BASE_URL+Config.EVALUATION_PATH;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, param, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    try {
                        //Retrieve synced record ids from response
                        // CHECK FOR EXPIRED TOKEN RESPONSE
                        if (statusCode == Config.HTTP_201_CREATED){
                            int l = response.length();
                            int[] syncedIds = new int[l];
                            for (int i = 0; i < l; i++) {
                                syncedIds[i] = response.getInt(i);
                            }

                            Log.d(TAG, response.toString());

                            // Update sync status of synced records

                            evaluationDAO.updateSyncedStatus(syncedIds);

                            if (source.equals(Config.BULK_SYNC)) {

                                // Only show sync status if sync was initiated by user
                                String message = getResources().getString(R.string.sync_success);
                                showSyncMessage(context, message);
                            }
                        } else {

                            if (source.equals(Config.BULK_SYNC)){
                                showSyncMessage(context, getResources().getString(R.string.sync_failed));
                                Log.d(TAG, statusCode+" "+response.toString());
                            }
                        }


                    } catch (JSONException je) {

                        if (source.equals(Config.BULK_SYNC))
                            showSyncMessage(context, getResources().getString(R.string.sync_failed));

                        Log.d(TAG, "ERROR OCCURRED WHILE PARSING JSON ARRAY" + je.getMessage());
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if (source.equals(Config.BULK_SYNC))
                        showSyncMessage(context, getResources().getString(R.string.sync_failed));

                    Log.d(TAG, "ERROR OCCURED WHILE MAKING NETWORK CALL "+statusCode + error.getMessage()+error.toString());
                }
            }){


                @Override
                protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                    statusCode = response.statusCode;
                    return super.parseNetworkResponse(response);

                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = utils.getStringFromPreference(context, context.getString(R.string.token));
                    String tokenHeader = "Token "+token;

                    Log.d(TAG, token);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");
                    params.put("Authorization",tokenHeader);


                    return params;
                }
            };

            // Make network call
            QueueSingleton mQueueSingleton = QueueSingleton.getInstance(context);
            mQueueSingleton.addToRequestQueue(jsonArrayRequest);

        }catch(InterviewAppException iae){

            if (source.equals(Config.BULK_SYNC))
                showSyncMessage(context, getResources().getString(R.string.sync_failed));

            Log.d(TAG, "DATABASE RELATED ERROR OCCURRED" + iae.getMessage());
        }

    }


    public void showSyncMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return;
    }

    /**
     * Broadcast sent to notify activity component that local db is already ion sync with remote db
     *
     */
    public void sendInSyncBroadcast(){
        Intent localIntent = new Intent(Config.BROADCAST_IN_SYNC_ACTION).putExtra(Config.SYNC_STATUS, Config.IN_SYNC);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
