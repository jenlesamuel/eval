package com.jenle.interviewapp.login.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.evaluate.view.EvaluationActivity;
import com.jenle.interviewapp.login.LoginContract;
import com.jenle.interviewapp.login.presenter.LoginPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View{

    private EditText emailView;
    private EditText passwordView;
    private Button submit;
    private Context appContext;
    private ProgressBar progressBar;
    private LoginContract.Presenter loginPresenter;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailView = (EditText) view.findViewById(R.id.email);
        passwordView = (EditText) view.findViewById(R.id.password);
        submit = (Button)view.findViewById(R.id.login);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean error = false;

                String email = emailView.getText().toString();
                String password = passwordView.getText().toString();

                // Do validation
                Utils utils = new Utils();
                if (!utils.isValidNonEmpty(emailView, "Email", email)) error = true;
                if (!utils.isValidNonEmpty(passwordView, "Password", password)) error = true;

                if (error) return; // Validation failed, return

                // Validation passed, initiate remote authentication
                loginPresenter.login(appContext, email, password);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        appContext = this.getActivity().getApplicationContext();
        loginPresenter = new LoginPresenter(appContext, this);
    }

    @Override
    public void showMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        return;
    }

    @Override
    public void closeProgress(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void redirectToEvaluation(){
        AppCompatActivity parentActivity = (AppCompatActivity) this.getActivity();
        parentActivity.startActivity(new Intent(parentActivity, EvaluationActivity.class));
        parentActivity.finish();
    }

}
