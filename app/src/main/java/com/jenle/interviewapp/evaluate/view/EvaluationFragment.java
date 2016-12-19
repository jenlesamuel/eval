package com.jenle.interviewapp.evaluate.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.InterviewAppException;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.db.InterviewAppContract;
import com.jenle.interviewapp.evaluate.EvaluateContract;
import com.jenle.interviewapp.evaluate.model.Evaluation;
import com.jenle.interviewapp.evaluate.model.EvaluationServiceImpl;
import com.jenle.interviewapp.evaluate.presenter.EvaluationPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class EvaluationFragment extends Fragment implements EvaluateContract.View{

    private LinearLayout contentView;
    private EditText candidateView, jobView, interviewerView, dateView, commentsView;
    private Button submit;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "EVALUATIONFRAGMENT";
    private Context appContext;
    private EvaluateContract.Presenter evaluationPresenter;
    private AppCompatActivity parentActivity;
    private Utils utils;
    private String candidateLabel;
    private String interviewerNameLabel;
    private String jobLabel;
    private String commentsLabel;
    private TextWatcher candidateViewWatcher;
    private TextWatcher jobViewWatcher;
    private TextWatcher interviewerViewWatcher;
    private TextWatcher commentsViewWatcher;


    public EvaluationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        utils = new Utils();
        parentActivity = (AppCompatActivity) getActivity();
        appContext = parentActivity.getApplicationContext();
        evaluationPresenter = new EvaluationPresenter(this, appContext);

        contentView = (LinearLayout) view.findViewById(R.id.container);

        candidateView = (EditText) view.findViewById(R.id.candidate_name);
        candidateLabel = utils.getStringFromResource(appContext, R.string.candidate_name);
        candidateViewWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s){
                utils.isValidName(candidateView, candidateLabel, s.toString());
            }
        };
        setFocusChangeListener(candidateView, candidateViewWatcher);




        jobView = (EditText) view.findViewById(R.id.job);
        jobLabel =  utils.getStringFromResource(appContext, R.string.job_title);
        jobViewWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s){
                utils.isValidNonEmpty(jobView, jobLabel, s.toString());
            }
        };

        setFocusChangeListener(jobView, jobViewWatcher);


        interviewerView = (EditText) view.findViewById(R.id.interviewer_name);
        interviewerNameLabel = utils.getStringFromResource(appContext, R.string.interviewer_name);
        interviewerViewWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s){
                utils.isValidName(interviewerView, interviewerNameLabel, s.toString());
            }
        };
        setFocusChangeListener(interviewerView, interviewerViewWatcher);


        commentsView = (EditText)view.findViewById(R.id.comments);
        commentsLabel =  utils.getStringFromResource(appContext, R.string.comments);
        commentsViewWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s){
                utils.isValidNonEmpty(commentsView, commentsLabel, s.toString());
            }
        };
        setFocusChangeListener(commentsView, commentsViewWatcher);



        dateView = (EditText)view.findViewById(R.id.date);
        dateView.setFocusable(false);
        dateView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showDatePicker();

            }
        });

        submit = (Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveRecord();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Displays a date picker
     */
    public void showDatePicker(){
        DialogFragment fragment = new DatePickerFragment();
        fragment.setTargetFragment(EvaluationFragment.this, 0);
        fragment.show(EvaluationFragment.this.getActivity().getSupportFragmentManager(), "datepicker");
    }

    public void setDate(String date){
        dateView.setText(date);
        dateView.setError(null);
    }

    /**
     * Returns a string representation of the current date
     * @return string
     */
    public String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd");
        return df.format(new Date());

    }


    @Override
    public void showProgressDialog(){
        Log.d(TAG, "SHOWING PROGRESS");
    }

    @Override
    public void closeProgressDialog(){
        Log.d(TAG, "CLOSING PROGRESS");
    }

    @Override
    public void showToast(String message){
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void showErrorDialog(){
        DialogFragment errorDialog = new ErrorDialog();
        errorDialog.show(parentActivity.getSupportFragmentManager(), "error_dialog");
        return;
    }

    public void showSuccessDialog(String title, String message){
        DialogFragment successDialog = SuccessDialog.newInstance(title, message);
        successDialog.setTargetFragment(EvaluationFragment.this, 0);
        successDialog.show(parentActivity.getSupportFragmentManager(), "success_dialog");
        return;
    }

    /**
     * Handler evaluation submission
     */
    public void saveRecord(){

        boolean valid = true;
        Utils utils = new Utils();

        String candidateName = candidateView.getText().toString();
        if (!utils.isValidName(candidateView, candidateLabel, candidateName)) valid = false;

        String jobTitle = jobView.getText().toString();
        if (!utils.isValidNonEmpty(jobView, jobLabel, jobTitle)) valid = false;

        String interviewerName = interviewerView.getText().toString();
        if (!utils.isValidName(interviewerView, interviewerNameLabel, interviewerName)) valid = false;

        String interviewDate = dateView.getText().toString();
        if (!utils.isValidDate(dateView, interviewDate)) valid = false;

        String comments = commentsView.getText().toString();
        if (!utils.isValidNonEmpty(commentsView, commentsLabel, comments)) valid = false;


        /** Get ratings **/
        int count = contentView.getChildCount(); // Get count of all Views in layout

        String[] competencies = getResources().getStringArray(R.array.competencies);
        int num = competencies.length;
        int[] ratings = new int[num];

        int index = 0; // Used to identify the position of a RadioGroup relative to other RadioGroups in the layout
        for (int i=0; i < count; i++) {

            View view = contentView.getChildAt(i);
            if (view instanceof RadioGroup) {

                RadioGroup radioGroup = (RadioGroup) view;
                RadioButtonClickedListener radioListener = new RadioButtonClickedListener();

                addListeners(radioGroup, radioListener); // Add click listener to each radio button during iteration.

                int checkedId = radioGroup.getCheckedRadioButtonId();

                switch(checkedId){
                    case R.id.option1:
                        ratings[index] = 1;
                        break;
                    case R.id.option2:
                        ratings[index] = 2;
                        break;
                    case R.id.option3:
                        ratings[index] = 3;
                        break;
                    case R.id.option4:
                        ratings[index] = 4;
                        break;
                    case R.id.option5:
                        ratings[index] = 5;
                        break;
                    default:
                        valid = false;
                        showRadioError(true, radioGroup);
                        break;
                }

                index++;
            }
        }

        if (!valid) { //validation failed

            String validationError = utils.getStringFromResource(appContext, R.string.validation_error);
            showToast(validationError);

            return;

        }else{

            Evaluation evaluation = new EvaluationServiceImpl().create(candidateName, jobTitle,
                    interviewerName, interviewDate, ratings, comments);

            evaluationPresenter.addEvaluationRecord(evaluation);

            return;
        }
    }

    /**
     * Sets error on a RadioGroup if no option is checked or remove error if checked.
     * @param option Possible values are true or false
     * @param radioGroup
     */
    public void showRadioError(boolean option, RadioGroup radioGroup) {
        RadioButton lastRadio = (RadioButton) radioGroup.findViewById(R.id.option5);
        if (option) {
            lastRadio.setError("Required");
        }else{
            lastRadio.setError(null);
        }
    }

    /**
     * Listener class for when a radio button is clicked
     */
    class RadioButtonClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            boolean checked = ((RadioButton)view).isChecked();
            Log.d(TAG, "clicked");
            if (checked) {
                // Remove any required validation error from radio group if it exists
                RadioGroup radioGroup = (RadioGroup)view.getParent();
                showRadioError(false, radioGroup);
            }

        }
    }

    public void addListeners(RadioGroup radioGroup, View.OnClickListener listener){
        int count =  radioGroup.getChildCount();

        for (int i=0; i<count; i++){
            radioGroup.getChildAt(i).setOnClickListener(listener);
        }

    }

    public void clearFields(){
        // Set all view to their default state
        // Remove text changed listeners to prevent views from showing error when after clearing fields.

        candidateView.removeTextChangedListener(candidateViewWatcher);
        candidateView.setText("");

        jobView.removeTextChangedListener(jobViewWatcher);
        jobView.setText("");

        interviewerView.removeTextChangedListener(interviewerViewWatcher);
        interviewerView.setText("");

        dateView.setText("");

        commentsView.removeTextChangedListener(commentsViewWatcher);
        commentsView.setText("");

        int count = contentView.getChildCount(); // Get count of all Views in layout

        for (int i=0; i < count; i++) {

            View view = contentView.getChildAt(i);

            if (view instanceof RadioGroup) {

                RadioGroup radioGroup = (RadioGroup) view;
                radioGroup.clearCheck();
            }
        }
    }

    public void setFocusChangeListener(final EditText mEditText, final TextWatcher watcher){

       mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if (hasFocus) mEditText.addTextChangedListener(watcher);
            }
        });
    }
}
