package com.example.mybank.adapters;

import java.util.ArrayList;

import com.example.mybank.ExpListChild;
import com.example.mybank.ExpListGroups;
import com.example.mybank.R;
import com.example.mybank.R.id;
import com.example.mybank.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableDrawerAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExpListGroups> groups;

    public ExpandableDrawerAdapter(Context context, ArrayList<ExpListGroups> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ExpListChild> chList = groups.get(groupPosition)
                .getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {

    	ExpListChild child = (ExpListChild) getChild(groupPosition,
                childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_item, null);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.child_img);
        iv.setImageResource(child.getImage());
        TextView tv = (TextView) convertView.findViewById(R.id.child_name);
        tv.setText(child.getName().toString());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ExpListChild> chList = groups.get(groupPosition)
                .getItems();

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
    	
    	
    	   	
    	ExpListGroups group = (ExpListGroups) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.group_item, null);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.group_img);
        iv.setImageResource(group.getImage());
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