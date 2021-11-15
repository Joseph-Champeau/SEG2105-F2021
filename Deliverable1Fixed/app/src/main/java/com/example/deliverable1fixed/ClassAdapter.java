package com.example.deliverable1fixed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<Class> {

    public ClassAdapter(Context context, int resource, List<Class> classList) {
        super(context,resource,classList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Class class1 = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell, parent, false);
        }
        //Add later
        TextView tv = (TextView) convertView.findViewById(R.id.className);
        //ImageView iv = (ImageView) convertView.findViewById(R.id.classImage);
        tv.setText(class1.getDifficultyLevel()+"-"+class1.getName()+ "-" + class1.getDay()+ "'s at "+ class1.getTimeInterval() +" taught by " + class1.getInstructor().getFullName() +" ("+ class1.getCapacity() +" spots left)");
        //iv.setImageResource(class1.getCapacity());

        return convertView;
    }
}