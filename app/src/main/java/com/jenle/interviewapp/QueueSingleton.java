package com.jenle.interviewapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * A singleton to ensure that an instance of the request queue persists throughout the life of the application
 * Created by Jenle Samuel on 12/8/16.
 */

public class QueueSingleton {

    private static QueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context context;

    private QueueSingleton(Context context) {
        this.context = context;
        //mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if (mRequestQueue == null) {
            mRequestQueue =  Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public static synchronized QueueSingleton getInstance(Context context){
        if (mInstance == null){
            mInstance = new QueueSingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
