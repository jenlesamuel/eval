package com.jenle.interviewapp.evaluate.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jenle.interviewapp.R;

/**
 * Created by Jenle Samuel on 12/18/16.
 */

public class SuccessDialog extends DialogFragment {
    private static final String TITLE_PARAM = "title";
    private static final String MESSAGE_PARAM = "message";

    public SuccessDialog(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = null;
        String message = null;

        //use the builder for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        if (getArguments() != null) {
            title = getArguments().getString(TITLE_PARAM);
            message = getArguments().getString(MESSAGE_PARAM);
        }

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                EvaluationFragment host = (EvaluationFragment)getTargetFragment();
                host.clearFields();

                dialog.dismiss();
            }
        });

        return builder.create();

    }

    /**
     * Factory method to create an instance of this fragment using the provided parameters
     * @param title Fragment title
     * @param message Fragment message
     * @return A new instance of InfoDialog
     */

    public static DialogFragment newInstance(String title, String message) {

        DialogFragment successDialog = new SuccessDialog();

        Bundle args = new Bundle();
        args.putString(TITLE_PARAM, title);
        args.putString(MESSAGE_PARAM, message);
        successDialog.setArguments(args);

        return successDialog;
    }



}
