package com.jenle.interviewapp;

/**
 * Created by Jenle Samuel on 12/9/16.
 */

public final class Config {
    public static final String REMOTE_BASE_URL = "https://eval-webapp.herokuapp.com/";
    public static final String EVALUATION_PATH = "api/evaluations/";
    public static final String LOGIN_PATH = "api/auth/login/";
    public static final String SOURCE = "source";
    public static final String BULK_SYNC = "bulk_sync";
    public static final String UNI_SYNC = "uni_sync";
    public static final String PARAM = "param";
    public static final String ID = "id";
    public static final String KEY_REMOTE_ID = "remote_id";
    public static final String KEY_CANDIDATE_NAME = "candidate_name";
    public static final String KEY_JOB_TITLE = "job_title";
    public static final String KEY_INTERVIEWER_NAME = "interviewer_name";
    public static final String KEY_INTERVIEW_DATE = "interview_date";
    public static final String KEY_COMMUNICATION = "communication";
    public static final String KEY_PROBLEM_SOLVING = "problem_solving";
    public static final String KEY_CONFLICT_RESOLUTION = "conflict_resolution";
    public static final String KEY_TEAMWORK = "teamwork";
    public static final String KEY_WORK_EXPERIENCE = "work_experience";
    public static final String KEY_EDUCATIONAL_BACKGROUND = "educational_background";
    public static final String KEY_LEADERSHIP = "leadership";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final int HTTP_200_OK = 200;
    public static final int HTTP_201_CREATED = 201;
    public static final int HTTP_400_BAD_REQUEST = 400; // status code returned by remote API if login credentials is invalid
    public static final String RESTORE_STATE = "restore";
    public static final String ALREADY_IN_SYNC_ACTION = "com.jenle.interviewapp.IN_SYNC_ACTION"; // Intent action when local db is in sync with remote db
    public static final String SYNC_COMPLETE = "com.jenle.interviewapp.SYNC_ACTION"; // Intent action when sync is complete

}