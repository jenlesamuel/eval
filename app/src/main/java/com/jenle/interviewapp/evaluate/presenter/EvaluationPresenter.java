package com.jenle.interviewapp.evaluate.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jenle.interviewapp.InterviewAppException;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.EvaluateContract;
import com.jenle.interviewapp.evaluate.model.Evaluation;
import com.jenle.interviewapp.evaluate.model.EvaluationDAO;
import com.jenle.interviewapp.evaluate.model.EvaluationTask;

/**
 * Listen to and perform user actions initiated on the UI
 * Created by Jenle Samuel on 12/3/16.
 */

public class EvaluationPresenter implements EvaluateContract.Presenter{

    private EvaluateContract.View evaluateView;
    private Context appContext;

    public EvaluationPresenter(EvaluateContract.View evaluateView, Context appContext){
        this.evaluateView = evaluateView;
        this.appContext = appContext;
    }

    @Override
    /**
     * Delegates insertion of evaluation record to an AsyncTask
     * @param Evaluation The object to be inserted
     */
    public void addEvaluationRecord(Evaluation evaluation) {

        EvaluationTask evaluationTask = new EvaluationTask(evaluateView, appContext);
        evaluationTask.execute(evaluation);
    }
}
