package com.jenle.interviewapp.login;

import android.content.Context;

/**
 * Defines the contract between the presenter and the view
 * Created by Jenle Samuel on 12/12/16.
 */

public interface LoginContract {

    interface View {
        public void showProgress();
        public void closeProgress();
    }

    interface Presenter {
        public void login(Context context, String email, String password);
    }
}
