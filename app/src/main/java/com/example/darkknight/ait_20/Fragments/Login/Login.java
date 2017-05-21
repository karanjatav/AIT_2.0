package com.example.darkknight.ait_20.Fragments.Login;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.darkknight.ait_20.Activities.HomeScreen;
import com.example.darkknight.ait_20.R;
import com.example.darkknight.ait_20.Utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.darkknight.ait_20.Utils.Utils.IMAGE_URL;
import static com.example.darkknight.ait_20.Utils.Utils.IS_LOGGEDIN;
import static com.example.darkknight.ait_20.Utils.Utils.LOCAL_URL;
import static com.example.darkknight.ait_20.Utils.Utils.MIS_MATCH;
import static com.example.darkknight.ait_20.Utils.Utils.PASSWORD;
import static com.example.darkknight.ait_20.Utils.Utils.SERVER_URL;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_COURSE;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_EMAIL;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_MOBILE;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_NAME;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_ROLLNO;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_SEMESTER;
import static com.example.darkknight.ait_20.Utils.Utils.USERNAME;
import static com.example.darkknight.ait_20.Utils.Utils.USER_NAME;
import static com.example.darkknight.ait_20.Utils.Utils.showProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {


    private EditText mPasswordView,mEmailView;
    private View mProgressView;

    private String TAG = "karan_login",
            user_name, password;
    private String ADDRESS = SERVER_URL + "login.php";

    ImageView app_logo;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_login, container, false);


        app_logo = (ImageView)v.findViewById(R.id.app_logo);
        app_logo.requestFocus();
        mEmailView = (EditText) v.findViewById(R.id.username_tv);

        mPasswordView = (EditText) v.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) v.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "in Progress");
                attemptLogin();
            }
        });

Button register_btn = (Button)v.findViewById(R.id.email_register_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtnClicked();
            }
        });


        mProgressView = v.findViewById(R.id.login_progress);
        return v;
    }

    private void attemptLogin() {
        Log.d(TAG, "in Progress");


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        user_name = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
    /*    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user_name)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(user_name)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/
        if (cancel) {
            focusView.requestFocus();
        } else {

            showProgress(true,getActivity(),mProgressView);
            Log.d(TAG, "in Progress");


            StringRequest sr = new StringRequest(Request.Method.POST, ADDRESS, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response);
                    // setUpArray(response);
                    showProgress(false,getActivity(),mProgressView);
                    if(!Objects.equals(response, MIS_MATCH)) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result_only = jsonObject.getString("info");
                            JSONArray jsonArray = new JSONArray(result_only);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                SharedPreferences k_SP = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = k_SP.edit();
                                editor.putString(IMAGE_URL, jsonObject2.getString("image"));
                                Log.d(TAG, "name=="+jsonObject2.getString("name"));
                                editor.putString(STUDENT_NAME, jsonObject2.getString("name"));
                                editor.putString(STUDENT_ROLLNO, jsonObject2.getString("rollno"));
                                editor.putString(STUDENT_EMAIL, jsonObject2.getString("email"));
                               // editor.putString(STUDENT_MOBILE, jsonObject2.getString("mobile"));
                                editor.putString(STUDENT_COURSE, jsonObject2.getString("course"));
                                editor.putString(STUDENT_SEMESTER, jsonObject2.getString("sem"));
                                //editor.putString(USERNAME, jsonObject2.getString("username"));
                                editor.putBoolean(IS_LOGGEDIN, true);

                                editor.apply();
                            }

                            Intent homeScreenIntent =  new Intent(getActivity(), HomeScreen.class);
                            startActivity(homeScreenIntent);

                        } catch (JSONException e) {
                            Log.d(TAG, "Exception=="+e.toString());

                        }
                    }else {
                        Toast.makeText(getActivity(),"Username or Password Incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false,getActivity(),mProgressView);
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.d(TAG, "" + error.getMessage() + "," + error.toString());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(USER_NAME, user_name);
                    params.put(PASSWORD, password);
                    return params;
                }


            };

            AppController.getInstance().addToRequestQueue(sr);

        }
    }




    public void registerBtnClicked(){
        Register fragobj = new Register();
        getFragmentManager().beginTransaction().replace(R.id.login_xll, fragobj).addToBackStack(null).commit();

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}
