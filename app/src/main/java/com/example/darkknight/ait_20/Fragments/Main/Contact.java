package com.example.darkknight.ait_20.Fragments.Main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darkknight.ait_20.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contact extends Fragment {


    public Contact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_contact, container, false);
        return v;
    }

}
