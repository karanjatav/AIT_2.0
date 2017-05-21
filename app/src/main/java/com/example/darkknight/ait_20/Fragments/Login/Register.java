package com.example.darkknight.ait_20.Fragments.Login;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.darkknight.ait_20.R;
import com.example.darkknight.ait_20.Utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.darkknight.ait_20.Utils.Utils.IMAGE_URL;
import static com.example.darkknight.ait_20.Utils.Utils.LOCAL_URL;
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
public class Register extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    Button image_sel, upload_btn, register_btn;
    ImageView show_pass, show_conpass;

    EditText name_et, rollno_et, email_et, mobile_et, course_et, sem_et, username_et, password_et, conpassword_et;

    ImageView imgPicture;

    Bitmap image;

    LinearLayout ln;

    Spinner course_spn, sem_spn;

    static String course, sem;

    AlertDialog alertDialog;

    static String encoded_image2;
    static String encodedImage;

    private final String[] Courses = {"Select Your Course", "Digital Electronics", "Medical Electronics", "Computer Engineering", "Information Technology"};

    private final String[] Semesters = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th"};

    private String TAG = "karan_register", ADDRESS = SERVER_URL + "register.php";

    private View mProgressView;


    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mProgressView = v.findViewById(R.id.register_progress);


        ln = (LinearLayout) v.findViewById(R.id.ln);
        imgPicture = (ImageView) v.findViewById(R.id.imageView);

        name_et = (EditText) v.findViewById(R.id.xname_et);
        rollno_et = (EditText) v.findViewById(R.id.xrollno_et);
        email_et = (EditText) v.findViewById(R.id.xemail_et);
        mobile_et = (EditText) v.findViewById(R.id.xmobile_et);
        username_et = (EditText) v.findViewById(R.id.xusername_et);
        password_et = (EditText) v.findViewById(R.id.xpassword_et);
        conpassword_et = (EditText) v.findViewById(R.id.xcon_password_et);


        // upload_btn=(Button)v.findViewById(R.id.xupload_btn);
        image_sel = (Button) v.findViewById(R.id.ximage_btn);
        register_btn = (Button) v.findViewById(R.id.xregister_btn);
        show_pass = (ImageView) v.findViewById(R.id.xshow_pass);
        show_conpass = (ImageView) v.findViewById(R.id.xshow_conpass);

        encodedImage = null;


        course_spn = (Spinner) v.findViewById(R.id.xcourse_spn);
        ArrayAdapter<String> course_adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, Courses);
        course_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert course_spn != null;
        course_spn.setAdapter(course_adapter);


        sem_spn = (Spinner) v.findViewById(R.id.xsem_spn);
        ArrayAdapter<String> sem_adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, Semesters);
        sem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert sem_spn != null;
        sem_spn.setAdapter(sem_adapter);

        course_spn.setOnItemSelectedListener(this);
        sem_spn.setOnItemSelectedListener(this);

        show_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password_et.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        password_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        show_conpass.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        conpassword_et.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        conpassword_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return v;


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = null;
        parent.getItemAtPosition(position).toString();

        switch (parent.getId()) {
            case R.id.xcourse_spn:

                item = parent.getItemAtPosition(position).toString();
                course = item;
                break;

            case R.id.xsem_spn:

                item = parent.getItemAtPosition(position).toString();
                sem = item;
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void register() {
        final String name = name_et.getText().toString();
        final String rollno = rollno_et.getText().toString();
        final String email = email_et.getText().toString();
        final String mobile = mobile_et.getText().toString();
        final String username = username_et.getText().toString();
        final String password = password_et.getText().toString();
        final String con_password = conpassword_et.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(name)) {
            name_et.setError(getString(R.string.name_empty));
            focusView = name_et;
            cancel = true;
        }
        if (TextUtils.isEmpty(rollno)) {
            rollno_et.setError(getString(R.string.rollno_empty));
            focusView = rollno_et;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            email_et.setError(getString(R.string.email_empty));
            focusView = email_et;
            cancel = true;
        } else if (!isEmailValid(email)) {
            email_et.setError(getString(R.string.error_invalid_email));
            focusView = email_et;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobile)) {
            mobile_et.setError(getString(R.string.mobile_empty));
            focusView = mobile_et;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            username_et.setError(getString(R.string.username_empty));
            focusView = username_et;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            password_et.setError(getString(R.string.password_empty));
            focusView = password_et;
            cancel = true;
        }
        if (!Objects.equals(password, con_password)) {
            conpassword_et.setError(getString(R.string.con_password_error));
            focusView = conpassword_et;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true,getActivity(),mProgressView);

            StringRequest sr = new StringRequest(Request.Method.POST, ADDRESS, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showProgress(false,getActivity(),mProgressView);

                    Log.d(TAG, response);
                    switch (response) {
                        case "ok": {
                            Toast.makeText(getActivity(), "Registered", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                            break;

                        }
                        case "roll_!ok": {
                            rollno_et.setError(getString(R.string.rollno_registered));
                            rollno_et.requestFocus();
                            break;

                        }
                        case "user_!ok": {
                            username_et.setError(getString(R.string.username_registered));
                            rollno_et.requestFocus();
                            break;

                        }
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
                    params.put(IMAGE_URL, "no url yet");
                    params.put(STUDENT_NAME, name);
                    params.put(STUDENT_ROLLNO, rollno);
                    params.put(STUDENT_EMAIL, email);
                    params.put(STUDENT_MOBILE, mobile);
                    params.put(STUDENT_COURSE, course);
                    params.put(STUDENT_SEMESTER, sem);
                    params.put(USERNAME, username);
                    params.put(PASSWORD, password);

                    return params;
                }


            };

            AppController.getInstance().addToRequestQueue(sr);
        }
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