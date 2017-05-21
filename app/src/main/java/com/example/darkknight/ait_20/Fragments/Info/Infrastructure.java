package com.example.darkknight.ait_20.Fragments.Info;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.darkknight.ait_20.Adapters.Infrastructure_Adapter;
import com.example.darkknight.ait_20.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Infrastructure extends Fragment {


    ExpandableListView expandableListView;
    public Infrastructure() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_infrastructure, container, false);
        // Inflate the layout for this fragment
        expandableListView = (ExpandableListView)v.findViewById(R.id.exp_listview);
        List<String> Headings = new ArrayList<>();
        List<String> L1 = new ArrayList<>();
        List<String> L2 = new ArrayList<>();
        List<String> L3 = new ArrayList<>();
        List<String> L4 = new ArrayList<>();
        List<String> L5 = new ArrayList<>();
        List<String> L6 = new ArrayList<>();

        HashMap<String, List<String>> ChildList = new HashMap<>();
        String heading_items[] = getResources().getStringArray(R.array.header_titles);
        String l1 = getResources().getString(R.string.Computer);
        String l2 = getResources().getString(R.string.Digital);
        String l3 = getResources().getString(R.string.Medical);
        String l4 = getResources().getString(R.string.Instrumentation);
        String l5 = getResources().getString(R.string.Bca);
        String l6 = getResources().getString(R.string.Mobile);




        for (String title : heading_items) {
            Headings.add(title);
        }

        L1.add(l1);
        L2.add(l2);
        L3.add(l3);
        L4.add(l4);
        L5.add(l5);
        L6.add(l6);


        ChildList.put(Headings.get(0), L1);
        ChildList.put(Headings.get(1), L2);
        ChildList.put(Headings.get(2), L3);
        ChildList.put(Headings.get(3), L4);
        ChildList.put(Headings.get(4), L5);
        ChildList.put(Headings.get(5), L6);
        Infrastructure_Adapter myAdapter = new Infrastructure_Adapter(getActivity(), Headings, ChildList);
        expandableListView.setAdapter(myAdapter);
        return v;
    }

}
