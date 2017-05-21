package com.example.darkknight.ait_20.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by DARKKNIGHT on 5/16/2017.
 */

public class Utils {


    public static String LOCAL_URL = "http://192.168.43.221/aitapp/",
            SERVER_URL = "https://aitapp.000webhostapp.com/";
    public static String
            USER_NAME = "user_name",
            PASSWORD = "password",
            IMAGE_URL = "image_url",
            STUDENT_NAME = "student_name",
            STUDENT_ROLLNO = "student_rollno",
            STUDENT_EMAIL = "student_email",
            STUDENT_MOBILE = "student_mobile",
            STUDENT_COURSE = "student_course",
            STUDENT_SEMESTER = "student_semester",
            USERNAME = "username",
            MIS_MATCH = "mis_match",
            EXAM_TYPE = "exam_type",
            IS_LOGGEDIN = "is_loggedin";

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, Context context, final View mProgressView) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



}
