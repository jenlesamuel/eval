package com.jenle.interviewapp.evaluate.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.EvaluateContract;

/**
 * Handles creation of evaluation records in the database
 * Created by Jenle Samuel on 12/3/16.
 */

 public class EvaluationTask extends AsyncTask<Evaluation, Void, Long> {

    private static final String TAG ="EVALUATION_TASK";
    private Context appContext;
    private EvaluateContract.View evaluateView;
    private Evaluation evaluation;

    public EvaluationTask(EvaluateContract.View evaluateView, Context appContext){

        this.evaluateView = evaluateView;
        this.appContext = appContext;
    }

    @Override
    protected void onPreExecute(){

        evaluateView.showProgressDialog();
    }

    @Override
    protected Long doInBackground(Evaluation... evaluations) {
        try {
            // Save evaluation in database
            EvaluationDAO evaluationDAO = new Utils().getEvaluationDAO(appContext);
            evaluation = evaluations[0];
            Long pk = evaluationDAO.insert(evaluation);

            return pk;
        }catch(Exception e) {

            Log.d(TAG, e.getMessage());
            return Long.valueOf("-1");
        }
    }

    @Override
    protected void onPostExecute(Long pk){

        if (pk == -1) { // Error occured while saving evaluation record
            String evaluationError = new Utils().getEvaluationCreationError(appContext);
            evaluateView.closeProgressDialog();
            evaluateView.showMessage(evaluationError);
            return;
        }

        // Successfully saved evaluation record
        String evaluationSuccess = new Utils().getEvaluationCreationSuccess(appContext);
        evaluateView.closeProgressDialog();
        evaluateView.showMessage(evaluationSuccess);
        evaluateView.recreate();
        //Save evaluation to remote server
       /* Intent intent = new Intent(context, EvaluationNetworkService.class);
        intent.putExtra(Config.ID, pk);
        intent.putExtra(Config.SOURCE, Config.UNI_SYNC);
        intent.putExtra(Config.PARAM, evaluation);
        context.startService(intent); */

       //((Fragment)evaluateView).getActivity().finish();
        return;
    }

}
