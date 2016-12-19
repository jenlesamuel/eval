package com.jenle.interviewapp.evaluate;

import android.content.Context;

import com.jenle.interviewapp.InterviewAppException;
import com.jenle.interviewapp.evaluate.model.Evaluation;

/**
 * Defines the contract between the view and the presenter
 * Created by samm on 12/3/16.
 */

public interface EvaluateContract {

    interface View {
        void showProgressDialog();
        void closeProgressDialog();
        void showToast(String message);
        void showErrorDialog();
        void showSuccessDialog(String title, String message);
    }

    interface Presenter {
        void addEvaluationRecord(Evaluation evaluation);
    }
}
