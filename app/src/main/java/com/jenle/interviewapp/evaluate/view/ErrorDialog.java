package com.jenle.interviewapp.evaluate.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jenle.interviewapp.R;

/**
 * Created by Jenle Samuel on 12/18/16.
 */

public class ErrorDialog extends DialogFragment{

    public ErrorDialog(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //use the builder for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).setMessage(R.string.evaluation_record_creation_error);

        return builder.create();

    }


}
