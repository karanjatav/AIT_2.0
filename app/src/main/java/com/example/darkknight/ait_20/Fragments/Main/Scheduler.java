package com.example.darkknight.ait_20.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.darkknight.ait_20.Adapters.ExpandListAdapter;
import com.example.darkknight.ait_20.Pojo.Sched_Child;
import com.example.darkknight.ait_20.Pojo.Sched_Group;
import com.example.darkknight.ait_20.R;
import com.example.darkknight.ait_20.Utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.darkknight.ait_20.Utils.Utils.LOCAL_URL;
import static com.example.darkknight.ait_20.Utils.Utils.SERVER_URL;
import static com.example.darkknight.ait_20.Utils.Utils.showProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class Scheduler extends Fragment {


    private ExpandListAdapter ExpAdapter;
    private ExpandableListView ExpandList;

    String ADDRESS = SERVER_URL + "scheduler.php",
            TAG = "karan_scheduler";

    private View mProgressView;

    public Scheduler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scheduler, container, false);
        ExpandList =  (ExpandableListView)v.findViewById(R.id.exp_list);

        mProgressView = v.findViewById(R.id.scheduler_progress);

        makejsonobjreq();
        return v;

    }

    private void makejsonobjreq() {
        showProgress(true, getActivity(), mProgressView);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ADDRESS,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showProgress(false, getActivity(), mProgressView);

                ArrayList<Sched_Group> list = new ArrayList<>();
                ArrayList<Sched_Child> ch_list;
                Log.d(TAG, response.toString());
                try {
                    Iterator<String> key = response.keys();
                    while (key.hasNext()) {
                        String k = key.next();

                        Sched_Group gru = new Sched_Group();
                        gru.setName(k);
                        ch_list = new ArrayList<>();

                        JSONArray ja = response.getJSONArray(k);

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jo = ja.getJSONObject(i);

                            Sched_Child ch = new Sched_Child();
                            ch.setDate(jo.getString("date"));
                            ch.setDay(jo.getString("day"));
                            ch.setSubject_Code(jo.getString("subject_code"));
                            ch.setSubject(jo.getString("subject"));
                            // ch.setName(jo.getString("table name"));
                            //ch.setImage(jo.getString("flag"));

                            ch_list.add(ch);
                        } // for loop end
                        gru.setItems(ch_list);
                        list.add(gru);
                    } // while loop end

                    ExpAdapter = new ExpandListAdapter(getActivity(), list);
                    ExpandList.setAdapter(ExpAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, getActivity(), mProgressView);

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }

}
