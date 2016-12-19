package com.jenle.interviewapp.evaluate.model;

import com.jenle.interviewapp.evaluate.model.Evaluation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines interface methods for EvaluationContract CRUD operations
 * Created by Jenle Samuel on 12/3/16.
 */

public interface EvaluationDAO {
    public long insert(Evaluation evaluation);
    public ArrayList<HashMap<String, Object>> retrieveUnsynced(boolean persistConnection);
    public void updateSyncedStatus(int[] ids);
}