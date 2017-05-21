package com.example.darkknight.ait_20.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.darkknight.ait_20.Pojo.Sched_Child;
import com.example.darkknight.ait_20.Pojo.Sched_Group;
import com.example.darkknight.ait_20.R;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by DARKKNIGHT on 5/21/2017.
 */

public class ExpandListAdapter  extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Sched_Group> groups;


    public ExpandListAdapter(Context context, ArrayList<Sched_Group> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Sched_Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Sched_Child child = (Sched_Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }



        TextView date = (TextView) convertView.findViewById(R.id.xdate);
        TextView day = (TextView) convertView.findViewById(R.id.xday);
        TextView sub_code = (TextView) convertView.findViewById(R.id.xsub_code);
        TextView subject = (TextView) convertView.findViewById(R.id.xsubject);
        /*NetworkImageView iv = (NetworkImageView) convertView
                .findViewById(R.id.flag);*/

        date.setText(child.getDate());
        day.setText(child.getDay());
        sub_code.setText(child.getSubject_Code());
        subject.setText(child.getSubject());


        //tv.setText(child.getDate().toString()+"--"+child.getDay().toString());
        // iv.setImageUrl(child.getImage(), imageLoader);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Sched_Child> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Sched_Group group = (Sched_Group) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}