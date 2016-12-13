package com.jenle.interviewapp.login.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jenle.interviewapp.Config;
import com.jenle.interviewapp.R;
import com.jenle.interviewapp.Utils;
import com.jenle.interviewapp.login.LoginContract;
import com.jenle.interviewapp.sync.QueueSingleton;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View{

    private EditText emailView;
    private EditText passwordView;
    private Button submit;
    private Context context;
    private ProgressBar progressBar;

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
        context = getActivity();

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

                if (error) { // Validation failed, return
                    String invalidCredentialsError = context.getResources().getString(R.string.login_credentials_error);
                    showMessage(context, invalidCredentialsError);
                    return;
                }

                // Validation passed, initiate remote authentication


            }
        });

        return view;
    }




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

}
