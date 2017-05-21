package com.example.darkknight.ait_20.Fragments.Main;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.darkknight.ait_20.Adapters.Results_Adapter;
import com.example.darkknight.ait_20.Pojo.Result_Object;
import com.example.darkknight.ait_20.R;
import com.example.darkknight.ait_20.Utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.darkknight.ait_20.Utils.Utils.EXAM_TYPE;
import static com.example.darkknight.ait_20.Utils.Utils.LOCAL_URL;
import static com.example.darkknight.ait_20.Utils.Utils.SERVER_URL;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_ROLLNO;
import static com.example.darkknight.ait_20.Utils.Utils.STUDENT_SEMESTER;
import static com.example.darkknight.ait_20.Utils.Utils.showProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class Results extends Fragment implements AdapterView.OnItemSelectedListener {

    Button get_result_btn, capture;

    String sem, exam_type, TAG = "karan_result";
    Spinner sp, sp2;
    TextView stu_id_tv, sem_tv, exam_type_tv;
    LinearLayout stu_info_ll, result_ll;
    String ADDRESS = SERVER_URL + "results.php";

    private View mProgressView;


    private final String[] Semesters = {"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th"};
    private final String[] Examtype = {"Select Exam Type", "1st Sessional", "2nd Sessional", "External Exams"};


    LinearLayout layout;


    private ArrayList<Result_Object> result_objects;

    Results_Adapter results_adapter;

    private RecyclerView result_rv;

    TextView grand_total_tv, percentage_total_tv, max_marks_total_tv, obt_marks_total_tv;




    public Results() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results, container, false);

        SharedPreferences wwz_SP = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String student_rollno =wwz_SP.getString(STUDENT_ROLLNO, " ");

        layout = (LinearLayout) v.findViewById(R.id.result_xll);

        mProgressView = v.findViewById(R.id.result_progress);


        grand_total_tv = (TextView) v.findViewById(R.id.sub_code_total_xtv);
        percentage_total_tv = (TextView) v.findViewById(R.id.sub_name_total_xtv);
        max_marks_total_tv = (TextView) v.findViewById(R.id.max_marks_total_xtv);
        obt_marks_total_tv = (TextView) v.findViewById(R.id.obt_marks_total_xtv);


        result_objects = new ArrayList<>();


        result_ll = (LinearLayout) v.findViewById(R.id.xresult_ll);

        result_rv = (RecyclerView) v.findViewById(R.id.result_xrv);
        result_rv.setNestedScrollingEnabled(false);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        result_rv.setLayoutManager(llm);


        stu_id_tv = (TextView) v.findViewById(R.id.xstu_id_tv);
        sem_tv = (TextView) v.findViewById(R.id.xsem_tv);
        exam_type_tv = (TextView) v.findViewById(R.id.xexamtype_tv);

        stu_id_tv.setText(student_rollno);

        sp = (Spinner) v.findViewById(R.id.xsem_spinner);
        ArrayAdapter<String> sem_adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, Semesters);

        sem_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert sp != null;
        sp.setAdapter(sem_adapter);

        sp2 = (Spinner) v.findViewById(R.id.xexamtype_spinner);
        ArrayAdapter<String> examtype_adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, Examtype);
        examtype_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert sp2 != null;
        sp2.setAdapter(examtype_adapter);

        sp.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);

        get_result_btn = (Button) v.findViewById(R.id.xget_result_btn);
        get_result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResult(student_rollno);
            }
        });



        return v;
    }

    //------------------------------------------------------------------------------------------------------------
    //SETTING THE SPINNERS
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item;
        parent.getItemAtPosition(position).toString();

        switch (parent.getId()) {
            case R.id.xsem_spinner:

                item = parent.getItemAtPosition(position).toString();
                sem = item;
                sem_tv.setText(item);
                break;

            case R.id.xexamtype_spinner:

                item = parent.getItemAtPosition(position).toString();
                exam_type = item;
                exam_type_tv.setText(item);
                break;
        }

    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //----------------------------------------------------------------------------------------------------------
    //GETTING RESULT

    public void getResult(String student_rollno) {

        String semester = null, examtype=null;

        switch (sem) {
            case "Select Semester": {
                semester = "0";
                break;
            }
            case "1st": {
                semester = "1";
                break;
            }
            case "2nd": {
                semester = "2";
                break;
            }
            case "3rd": {
                semester = "3";
                break;
            }
            case "4th": {
                semester = "4";
                break;
            }
            case "5th": {
                semester = "5";
                break;
            }
            case "6th": {
                semester = "6";
                break;
            }
        }

        switch (exam_type){
            case "Select Exam Type": {
                examtype = "0";
                break;
            }
            case "1st Sessional": {
                examtype = "s1";
                break;
            }
            case "2nd Sessional": {
                examtype = "s2";
                break;
            }
            case "External Exams": {
                examtype = "ex";
                break;
            }
        }

        if (Objects.equals(semester, "0")) {
            Toast.makeText(getActivity(), "Please Select Your Semester", Toast.LENGTH_LONG).show();
        } else if (Objects.equals(examtype, "0")) {
            Toast.makeText(getActivity(), "Please Select Your ExamType", Toast.LENGTH_LONG).show();
        } else {
            getResultData(student_rollno,semester,examtype);
        }
    }

    public void getResultData(final String student_rollno, final String semester, final String exam_type) {
        showProgress(true, getActivity(), mProgressView);


        StringRequest sr = new StringRequest(Request.Method.POST, ADDRESS, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false, getActivity(), mProgressView);

                Log.d(TAG, response);
                setResult(response,exam_type);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, getActivity(), mProgressView);

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "" + error.getMessage() + "," + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(STUDENT_ROLLNO, student_rollno);
                params.put(STUDENT_SEMESTER, semester);
                params.put(EXAM_TYPE, exam_type);


                return params;
            }


        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    public void setResult(String jsonData,String exam_type){
        if (jsonData != null) {
            try {
                JSONObject jsonObject;
                JSONArray jsonArray;

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layout.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                result_objects.clear();
                jsonObject = new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("result");
                int count = 0;
                //int count = jsonArray.length();
                String subject;
                String code;

                int total_max, total_obt, total_per,marks,max,min,total = 0,total2=0;


                JSONObject JO = null, Jl = null;

                while (count < jsonArray.length() - 1) {
                    JO = jsonArray.getJSONObject(count);
                    subject = JO.getString("subject");
                    marks = JO.getInt("marks");
                    max = JO.getInt("max");
                    min = JO.getInt("min");
                    code = JO.getString("code");


                    // s2 = JO.getString("s2");
                    total += max;
                    total2 += marks;
                   /* Converter2 converter = new Converter2(subject,description);
                    customAdapter.add(converter);*/


                    Log.d("karan22", "Subject= " + subject);
                    Log.d("json_raw_sub", "Max= " + String.valueOf(max));
                    Log.d("json_raw_sub", "Marks=" + String.valueOf(marks));



                    if (((Objects.equals(exam_type, "s2") || Objects.equals(exam_type, "s1")) && marks < 8) || (Objects.equals(exam_type, "ex") && marks < min)) {
                        result_objects.add(new Result_Object(code, subject, String.valueOf(max), String.valueOf(marks),"fail"));
                    } else {
                        result_objects.add(new Result_Object(code, subject, String.valueOf(max), String.valueOf(marks),"pass"));

                    }


                    count++;




                }


                results_adapter = new Results_Adapter(result_objects);
                result_rv.setAdapter(results_adapter);




                // max_total.setText(total);

                // obt_total.setText(total2);
                count = jsonArray.length();
                Log.d("count", "count=" + count);
                Jl = jsonArray.getJSONObject(count - 1);
                total_max = Jl.getInt("total_max");
                total_obt = Jl.getInt("total_obt");
                total_per = Jl.getInt("total_per");

                Log.d("json_raw_sub_karan", "max_total:" + total_max);
                Log.d("json_raw_sub_karan", "marks_total:" + total_obt);


                // grand_total_tv.setText();
                percentage_total_tv.setText(String.valueOf(total_per) + "%");
                max_marks_total_tv.setText(String.valueOf(total_max));
                obt_marks_total_tv.setText(String.valueOf(total_obt));



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            Toast.makeText(getActivity(), "no data received", Toast.LENGTH_SHORT).show();
        }

    }


}
