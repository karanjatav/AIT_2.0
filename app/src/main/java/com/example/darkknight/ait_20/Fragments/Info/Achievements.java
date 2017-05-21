package com.example.darkknight.ait_20.Fragments.Info;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darkknight.ait_20.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Achievements extends Fragment {


    public Achievements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_achievements, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
