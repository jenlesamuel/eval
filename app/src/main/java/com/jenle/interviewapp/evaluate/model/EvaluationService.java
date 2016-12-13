package com.jenle.interviewapp.evaluate.model;

import org.json.JSONArray;

/**
 * Defines service methods for Evaluation model
 * Created by Jenle Samuel on 12/5/16.
 */

interface EvaluationService {
    public Evaluation create(String candidateName, String jobTitle, String interviewerName, String date, int[] ratings, String comments);
    public JSONArray evaluationToJSONArray(Evaluation evaluation, Long rowId);
}
