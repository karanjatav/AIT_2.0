package com.example.darkknight.ait_20.Fragments.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.darkknight.ait_20.Activities.HomeScreen;
import com.example.darkknight.ait_20.Adapters.NewsFeed_Adapter;
import com.example.darkknight.ait_20.Pojo.Newsfeed_Object;
import com.example.darkknight.ait_20.R;
import com.example.darkknight.ait_20.Utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.darkknight.ait_20.Utils.Utils.LOCAL_URL;
import static com.example.darkknight.ait_20.Utils.Utils.MIS_MATCH;
import static com.example.darkknight.ait_20.Utils.Utils.SERVER_URL;
import static com.example.darkknight.ait_20.Utils.Utils.showProgress;

public class NewsFeed extends Fragment {

    ViewFlipper viewFlipper;

    private View mProgressView;


    Animation slidein_r2l, slideout_r2l,
            slidein_r2l_a, slideout_r2l_a,
            slidein_l2r, slideout_l2r;

    Button next_btn, prev_btn, result_btn, sched_btn, login_btn;


    GestureDetector mGestureDetector;

    ProgressDialog PD;

    static Bitmap image = null;
    static Bitmap conv_bm;

    RecyclerView newsfeed_rv;


    static String name, rollno, course, sem, email, TAG = "karan_homescreem", ADDRESS = SERVER_URL + "homepage_newsfeed/newsfeed_get_jsondata.php";
    ArrayList<Newsfeed_Object> newsfeed_objects;


    public NewsFeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_feed, container, false);

        mProgressView = v.findViewById(R.id.newsfeed_progress);
        newsfeed_rv = (RecyclerView) v.findViewById(R.id.newsfeed_rv);

        setupViewFlipper(v);
        getInfo();

        return v;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //SETTING UP RECYCLER VIEW

    public void getInfo() {
        showProgress(true, getActivity(), mProgressView);
        StringRequest sr = new StringRequest(Request.Method.POST, ADDRESS, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                // setUpArray(response);
                showProgress(false, getActivity(), mProgressView);
                if (!Objects.equals(response, MIS_MATCH)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result_only = jsonObject.getString("data");
                        JSONArray jsonArray = new JSONArray(result_only);
                        newsfeed_objects = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            newsfeed_objects.add(new Newsfeed_Object(jsonObject2.getString("subject"),
                                    jsonObject2.getString("description"),
                                    jsonObject2.getString("type"),
                                    jsonObject2.getString("link")));

                        }

                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        newsfeed_rv.setLayoutManager(llm);
                        newsfeed_rv.setAdapter(new NewsFeed_Adapter(getActivity(),newsfeed_objects));

                    } catch (JSONException e) {
                        Log.d(TAG, "Exception==" + e.toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
                }
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
              /*  params.put(USER_NAME, user_name);
                params.put(PASSWORD, password);*/
                return params;
            }


        };

        AppController.getInstance().addToRequestQueue(sr);
    }


    //---------------------------------------------------------------------------------------------------------
    //SETTING UP THE VIEW FLIPPER

    public void setImgVw(ImageView i) {
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);

        viewFlipper.addView(i);
    }

    public void setImgVw2(ImageView i) {
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setBackgroundColor(Color.parseColor("#000000"));
        viewFlipper.addView(i);
    }

    public void flipping() {
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(slidein_r2l_a);
        viewFlipper.setOutAnimation(slideout_r2l_a);
        viewFlipper.setFlipInterval(8000);
    }

    public void setupViewFlipper(View v) {
        viewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);

        slidein_r2l = AnimationUtils.loadAnimation(getActivity(), R.anim.xslidein_r2l);
        slideout_r2l = AnimationUtils.loadAnimation(getActivity(), R.anim.xslideout_r2l);
        slidein_r2l_a = AnimationUtils.loadAnimation(getActivity(), R.anim.xslidein_r2l_a);
        slideout_r2l_a = AnimationUtils.loadAnimation(getActivity(), R.anim.xslideout_r2l_a);
        slidein_l2r = AnimationUtils.loadAnimation(getActivity(), R.anim.xslidein_l2r);
        slideout_l2r = AnimationUtils.loadAnimation(getActivity(), R.anim.xslideout_l2r);

        prev_btn = (Button) v.findViewById(R.id.back);
        next_btn = (Button) v.findViewById(R.id.forward);

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Previous_Activated();

            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_Activated();

            }
        });

        ImageView imgvw_1 = new ImageView(getActivity());
        imgvw_1.setImageResource(R.drawable.campus);
        setImgVw(imgvw_1);

        ImageView imgvw_2 = new ImageView(getActivity());
        imgvw_2.setImageResource(R.drawable.k_5);
        setImgVw2(imgvw_2);

        ImageView imgvw_3 = new ImageView(getActivity());
        imgvw_3.setImageResource(R.drawable.k_9);
        setImgVw2(imgvw_3);

        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }

        });

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(getActivity(), customGestureDetector);

        flipping();
    }

    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe Right to Left (next)
            if (e1.getX() > e2.getX()) {
                Next_Activated();
            }

            // Swipe Left to Right (previous)
            if (e1.getX() < e2.getX()) {
                Previous_Activated();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


    public void Next_Activated() {
        viewFlipper.setInAnimation(slidein_r2l);
        viewFlipper.setOutAnimation(slideout_r2l);
        viewFlipper.showNext();
        flipping();
    }

    public void Previous_Activated() {
        viewFlipper.setInAnimation(slidein_l2r);
        viewFlipper.setOutAnimation(slideout_l2r);
        viewFlipper.showPrevious();
        viewFlipper.setInAnimation(slidein_r2l_a);
        viewFlipper.setOutAnimation(slideout_r2l_a);
        flipping();
    }

    //-----------------------------------------------------------------------------------------------------------------------


}
