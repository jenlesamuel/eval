package com.jenle.interviewapp.db;

import android.provider.BaseColumns;

/**
 * Created by samm on 12/3/16.
 */

public final  class InterviewAppContract {
    private InterviewAppContract(){}

    public static class EvaluationContract implements BaseColumns {
        public static final String TABLE_NAME = "evaluation";
        public static final String CANDIDATE_NAME= "candidate_name";
        public static final String JOB_TITLE = "job_title";
        public static final String INTERVIEWER_NAME = "interviewer_name";
        public static final String INTERVIEW_DATE = "date";
        public static final String COMMUNICATION = "communication";
        public static final String PROBLEM_SOLVING = "problem_solving";
        public static final String CONFLICT_RESOLUTION = "conflict_resolution";
        public static final String TEAMWORK = "teamwork";
        public static final String WORK_EXPERIENCE = "work_experience";
        public static final String EDUCATIONAL_BACKGROUND = "educational_background";
        public static final String LEADERSHIP = "leadership";
        public static final String COMMENTS = "comments";
        public static final String STATUS = "status";
    }
}
