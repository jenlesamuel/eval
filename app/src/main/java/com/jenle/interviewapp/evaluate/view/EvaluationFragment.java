package com.jenle.interviewapp.evaluate.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
    private TextView communicationView;
    private Button submit;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "EVALUATIONFRAGMENT";
    private Context context;


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
        context = this.getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        contentView = (LinearLayout) view.findViewById(R.id.container);
        candidateView = (EditText) view.findViewById(R.id.candidate_name);
        jobView = (EditText) view.findViewById(R.id.job);
        interviewerView = (EditText) view.findViewById(R.id.interviewer_name);
        dateView = (EditText) view.findViewById(R.id.date);
        commentsView = (EditText)view.findViewById(R.id.comments);

        // Set onClick listeners for all radio buttons
        RadioButtonClickedListener radioListener = new RadioButtonClickedListener();
        (view.findViewById(R.id.option1)).setOnClickListener(radioListener);
        (view.findViewById(R.id.option2)).setOnClickListener(radioListener);
        (view.findViewById(R.id.option3)).setOnClickListener(radioListener);
        (view.findViewById(R.id.option4)).setOnClickListener(radioListener);
        (view.findViewById(R.id.option5)).setOnClickListener(radioListener);

        communicationView = (TextView)view.findViewById(R.id.communication);

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
    public void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return;
    }


    /**
     * Handler evaluation submission
     */
    public void saveRecord(){

        boolean valid = true;
        Utils utils = new Utils();

        String candidateName = candidateView.getText().toString();
        String candidateLabel = utils.getStringFromResource(context, R.string.candidate_name);
        if (!utils.isValidName(candidateView, candidateLabel, candidateName)) valid = false;

        String jobTitle = jobView.getText().toString();
        String jobLabel =  utils.getStringFromResource(context, R.string.job_title);
        if (!utils.isValidNonEmpty(jobView, jobLabel, jobTitle)) valid = false;

        String interviewerName = interviewerView.getText().toString();
        String interviewerNameLabel = utils.getStringFromResource(context, R.string.interviewer_name);
        if (!utils.isValidName(interviewerView, interviewerNameLabel, interviewerName)) valid = false;

        String interviewDate = dateView.getText().toString();
        if (!utils.isValidDate(dateView, interviewDate)) valid = false;

        String comments = commentsView.getText().toString();
        String commentsLabel =  utils.getStringFromResource(context, R.string.comments);
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

            String validationError = utils.getStringFromResource(context, R.string.validation_error);
            showMessage(validationError);

            return;

        }else{

            Evaluation evaluation = new EvaluationServiceImpl().create(candidateName, jobTitle,
                    interviewerName, interviewDate, ratings, comments);

            EvaluateContract.Presenter evaluationPresenter = new EvaluationPresenter(this, context);
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
}
