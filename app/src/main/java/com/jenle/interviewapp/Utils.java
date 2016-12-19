package com.jenle.interviewapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.jenle.interviewapp.db.InterviewAppDbHelper;
import com.jenle.interviewapp.evaluate.model.EvaluationDAO;
import com.jenle.interviewapp.evaluate.model.EvaluationDAOImpl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An API of utilities
 * Created by samm on 12/2/16.
 */

public class Utils {

    private static final String TAG = "UTILS";

    /**
     * A factory method for an EvaluationDAO instance
     * @param context
     * @return EvaluationDAO
     * @throws InterviewAppException Throws exception if error occurs
     */
    public EvaluationDAO getEvaluationDAO(Context context) throws InterviewAppException{
        try{

            SQLiteDatabase db = new InterviewAppDbHelper(context).getWritableDatabase();
            EvaluationDAO evaluationDAO = new EvaluationDAOImpl(db);
            return evaluationDAO;

        }catch(Exception e){
            Log.d(TAG, e.getMessage()); // for debugging

            String evaluationError = context.getString(R.string.evaluation_record_creation_error);
            throw new InterviewAppException(evaluationError);
        }
    }

    /**
     * Returns the string to be dipslayed if an evalution record was not successfully inserted into the database
     * @param context
     * @return String
     */
    public String getEvaluationCreationError(Context context){
        return context.getString(R.string.evaluation_record_creation_error);
    }

    /**
     * Returns the string to be dipslayed if an evalution record was successfully inserted into the database
     * @param context
     * @return String
     */
    public String getEvaluationCreationSuccess(Context context){
        return context.getString(R.string.evaluation_record_creation_success);
    }

    /**
     * Validates a user submitted username
     * @param view The view holding the name to validate
     * @param fieldName
     * @param input
     * @return boolean. True if validation passes, false otherwise
     */
    public boolean isValidName(EditText view, String fieldName, String input) {
        /*String regex = "^[a-zA-Z\\s]{3,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean outcome = matcher.matches(); */
        boolean outcome = (input.trim().length() >= 3);
        if (!outcome) view.setError("Enter a valid "+fieldName);

        return outcome;
    }

    public boolean isValidDate(EditText dateView, String input){
        /**String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        String regex="";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        boolean outcome = matcher.matches(); */
        boolean outcome = (input.trim().length() > 0);
        if (!outcome) dateView.setError("Select correct date");

        return outcome;
    }

    public boolean isValidNonEmpty(EditText view, String fieldName, String input) {
        /*String regex = "^[a-zA-Z]{1,}[a-zA-Z\\d\\s]{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean outcome = matcher.matches(); */
        boolean outcome = (input.trim().length() >= 3);
        if (!outcome) view.setError("Enter valid "+fieldName);
        return outcome;
    }

    public String getStringFromResource(Context context, int id){
        return context.getResources().getString(id);
    }

    public JSONArray toJSONArray(ArrayList records){
        return new JSONArray(records);
    }

    /**
     * Saves a string value in SharedPreference
     * @param context
     * @param key The key of the value to be saved
     * @param value The value to be saved
     */
    public void saveStringInPreference(Context context, String key, String value){
        String preferenceFileKey = context.getString(R.string.preference_file_key);
        SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public String getStringFromPreference(Context context, String key){
        String preferenceFileKey = context.getString(R.string.preference_file_key);
        SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);

        return sharedPrefs.getString(key, null);
    }

    public void removeStringFromPreference(Context context, String key){
        String preferenceFileKey = context.getString(R.string.preference_file_key);
        SharedPreferences sharedPrefs = context.getSharedPreferences(preferenceFileKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.commit();
    }

}
