package com.jenle.interviewapp.evaluate.model;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Concrete implemenation of EvaluationNetworkService interface
 * Created by Jenle Samuel on 12/5/16.
 */

public class EvaluationServiceImpl implements EvaluationService{

    /**
     * Creates an Evaluation
     * @param candidateName
     * @param jobTitle
     * @param interviewerName
     * @param interviewDate
     * @param ratings
     * @param comments
     * @return Evaluation
     */
    public Evaluation create(String candidateName, String jobTitle,
                                       String interviewerName, String interviewDate, int[] ratings,
                                       String comments){


        int communication = ratings[0];
        int problemSolving = ratings[1];
        int conflictResolution = ratings[2];
        int teamWork = ratings[3];
        int workExperience = ratings[4];
        int educationalBackground = ratings[5];
        int leadership = ratings[6];

        Evaluation evaluation = new Evaluation(candidateName, jobTitle, interviewerName,
                                                interviewDate, communication, problemSolving, conflictResolution,
                                                teamWork, workExperience, educationalBackground, leadership,
                                                comments);
        return evaluation;
    }

    @Override
    /**
     * Converts a newly inserted evaluation record into JSONArray
     * This JSONArray serves as the param for a remote network call in an attempt
     * to remotely sync an evaluation after its saved in the local db
     * @param evaluation
     * @param rowId The id of the newly inserted evaluation
     */
    public JSONArray evaluationToJSONArray(Evaluation evaluation, Long rowId){
        HashMap<String, Object> record = new HashMap<String, Object>();
        record.put(Config.KEY_REMOTE_ID, rowId);
        record.put(Config.KEY_CANDIDATE_NAME, evaluation.getCandidateName());
        record.put(Config.KEY_JOB_TITLE, evaluation.getJobTitle());
        record.put(Config.KEY_INTERVIEWER_NAME, evaluation.getInterviewerName());
        record.put(Config.KEY_INTERVIEW_DATE, evaluation.getInterviewDate());
        record.put(Config.KEY_COMMUNICATION, evaluation.getCommunication());
        record.put(Config.KEY_PROBLEM_SOLVING, evaluation.getProblemSolving());
        record.put(Config.KEY_CONFLICT_RESOLUTION, evaluation.getConflictResolution());
        record.put(Config.KEY_TEAMWORK, evaluation.getTeamWork());
        record.put(Config.KEY_WORK_EXPERIENCE, evaluation.getWorkExperience());
        record.put(Config.KEY_EDUCATIONAL_BACKGROUND, evaluation.getEducationalBackground());
        record.put(Config.KEY_LEADERSHIP, evaluation.getLeadership());
        record.put(Config.KEY_COMMENTS, evaluation.getComments());

        ArrayList<HashMap<String, Object>> records = new ArrayList<HashMap<String, Object>>();
        records.add(record);

        return new Utils().toJSONArray(records);

    }

}
