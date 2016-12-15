package com.jenle.interviewapp.evaluate.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.db.InterviewAppContract.EvaluationContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Implementation for EvaluationDAO interface
 *
 * Created by Jenle Samuel on 12/3/16.
 */

public class EvaluationDAOImpl implements EvaluationDAO{

    private SQLiteDatabase db;

    public EvaluationDAOImpl(SQLiteDatabase db){
        this.db = db;
    }

    /**
     * Inserts an evaluation record into the database
     * @param evaluation
     * @return long The primary key value for the newly inserted row
     */
    public long insert(Evaluation evaluation) {
        String candidateName = evaluation.getCandidateName();
        String jobTitle = evaluation.getJobTitle();
        String interviewerName = evaluation.getInterviewerName();
        String interviewDate = evaluation.getInterviewDate();
        int communication = evaluation.getCommunication();
        int problemSolving = evaluation.getProblemSolving();
        int conflictResolution = evaluation.getConflictResolution();
        int teamWork = evaluation.getTeamWork();
        int workExperience = evaluation.getWorkExperience();
        int educationalBackground = evaluation.getEducationalBackground();
        int leadership = evaluation.getLeadership();
        String comments = evaluation.getComments();

        ContentValues values = new ContentValues();
        values.put(EvaluationContract.CANDIDATE_NAME, candidateName);
        values.put(EvaluationContract.JOB_TITLE, jobTitle);
        values.put(EvaluationContract.INTERVIEWER_NAME, interviewerName);
        values.put(EvaluationContract.INTERVIEW_DATE, interviewDate);
        values.put(EvaluationContract.COMMUNICATION, communication);
        values.put(EvaluationContract.PROBLEM_SOLVING, problemSolving);
        values.put(EvaluationContract.CONFLICT_RESOLUTION, conflictResolution);
        values.put(EvaluationContract.TEAMWORK, teamWork);
        values.put(EvaluationContract.WORK_EXPERIENCE, workExperience);
        values.put(EvaluationContract.EDUCATIONAL_BACKGROUND, educationalBackground);
        values.put(EvaluationContract.LEADERSHIP, leadership);
        values.put(EvaluationContract.COMMENTS, comments);
        values.put(EvaluationContract.STATUS, 0);

        Long pk =  db.insert(EvaluationContract.TABLE_NAME, null, values);
        if (db != null) db.close();

        return pk;

    }

    @Override
    /**
     * Returns all unsynced records in the local database.
     * @return ArrayList
     */
    public ArrayList<HashMap<String, Object>> retrieveUnsynced(){
        String selection = EvaluationContract.STATUS + " = ?";
        String[] selectionArgs = {String.valueOf(0)};
        String[] projection = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor c = db.query(
                EvaluationContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );

        ArrayList<HashMap<String, Object>> records = new ArrayList<HashMap<String, Object>>();

        while (c.moveToNext()) {
            Long id = c.getLong(c.getColumnIndex(EvaluationContract._ID));
            String candidateName = c.getString(c.getColumnIndex(EvaluationContract.CANDIDATE_NAME));
            String jobTitle = c.getString(c.getColumnIndex(EvaluationContract.JOB_TITLE));
            String interviewerName = c.getString(c.getColumnIndex(EvaluationContract.INTERVIEWER_NAME));
            String interviewDate = c.getString(c.getColumnIndex(EvaluationContract.INTERVIEW_DATE));
            int communication = c.getInt(c.getColumnIndex(EvaluationContract.COMMUNICATION));
            int problemSolving = c.getInt(c.getColumnIndex(EvaluationContract.PROBLEM_SOLVING));
            int conflictResolution = c.getInt(c.getColumnIndex(EvaluationContract.CONFLICT_RESOLUTION));
            int teamWork = c.getInt(c.getColumnIndex(EvaluationContract.TEAMWORK));
            int workExperience = c.getInt(c.getColumnIndex(EvaluationContract.WORK_EXPERIENCE));
            int educationalBackground = c.getInt(c.getColumnIndex(EvaluationContract.EDUCATIONAL_BACKGROUND));
            int leadership = c.getInt(c.getColumnIndex(EvaluationContract.LEADERSHIP));
            String comments = c.getString(c.getColumnIndex(EvaluationContract.COMMENTS));
            int total = communication + problemSolving + conflictResolution + teamWork + workExperience
                    + educationalBackground + leadership;

            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put(Config.KEY_REMOTE_ID, id);
            record.put(Config.KEY_CANDIDATE_NAME, candidateName);
            record.put(Config.KEY_JOB_TITLE, jobTitle);
            record.put(Config.KEY_INTERVIEWER_NAME, interviewerName);
            record.put(Config.KEY_INTERVIEW_DATE, interviewDate);
            record.put(Config.KEY_COMMUNICATION, Integer.valueOf(communication));
            record.put(Config.KEY_PROBLEM_SOLVING, Integer.valueOf(problemSolving));
            record.put(Config.KEY_CONFLICT_RESOLUTION, Integer.valueOf(conflictResolution));
            record.put(Config.KEY_TEAMWORK, Integer.valueOf(teamWork));
            record.put(Config.KEY_WORK_EXPERIENCE, Integer.valueOf(workExperience));
            record.put(Config.KEY_EDUCATIONAL_BACKGROUND, Integer.valueOf(educationalBackground));
            record.put(Config.KEY_LEADERSHIP, Integer.valueOf(leadership));
            record.put(Config.KEY_COMMENTS, comments);
            record.put(Config.KEY_TOTAL, total);

            records.add(record);
        }

        if(c != null) c.close();
        if(db != null) db.close();

        return records;
    }

    @Override
    public void updateSyncedStatus(int[] ids){
        ContentValues values = new ContentValues();

        for (int i=0; i < ids.length; i++){
            int id = ids[i];
            values.put(EvaluationContract.STATUS, id);

            String selection = EvaluationContract._ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            int count = db.update(
                    EvaluationContract.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );
        }
    }

}
