package com.example.darkknight.ait_20.Pojo;

import java.util.ArrayList;

/**
 * Created by DARKKNIGHT on 5/21/2017.
 */

public class Sched_Group {

    private String Name;
    private ArrayList<Sched_Child> Items;

    public Sched_Group(String name, ArrayList<Sched_Child> items) {
        Name = name;
        Items = items;
    }

    public Sched_Group() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Sched_Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Sched_Child> items) {
        Items = items;
    }
}
