package com.jenle.interviewapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jenle.interviewapp.db.InterviewAppContract.EvaluationContract;

/**
 * A class that handles creation and upgrade of database
 * Created by samm on 12/3/16.
 */

public class InterviewAppDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InterviewApp.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = " NOT NULL";
    private static final String INTEGER_TYPE = " INTEGER";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EvaluationContract.TABLE_NAME + " (" +
                    EvaluationContract._ID + " INTEGER PRIMARY KEY," +
                    EvaluationContract.CANDIDATE_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    EvaluationContract.JOB_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    EvaluationContract.INTERVIEWER_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    EvaluationContract.INTERVIEW_DATE + TEXT_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.COMMUNICATION + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    EvaluationContract.PROBLEM_SOLVING + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    EvaluationContract.CONFLICT_RESOLUTION + INTEGER_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.TEAMWORK + INTEGER_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.WORK_EXPERIENCE + INTEGER_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.EDUCATIONAL_BACKGROUND+ INTEGER_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.LEADERSHIP+ INTEGER_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.COMMENTS+ TEXT_TYPE + NOT_NULL +COMMA_SEP +
                    EvaluationContract.STATUS + INTEGER_TYPE + NOT_NULL +" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EvaluationContract.TABLE_NAME;
    
    public InterviewAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }



}
