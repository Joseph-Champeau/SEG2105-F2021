package com.example.deliverable1fixed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * A class representing the adapter of Classes
 *  @author Michias Shiferaw, Simon Brunet, Joseph Champeau, Charlie Haldane
 *  @version 2.0
 *  @since 2021-11-17
 */
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
        TextView output = (TextView) convertView.findViewById(R.id.className);
        //ImageView iv = (ImageView) convertView.findViewById(R.id.classImage);
        if (!class1.getName().equals("Onboarding")) {
            if (class1.getDay().equals("N/A")) {
                output.setText(class1.getName() + "\nTaught by " + class1.getInstructor().getFullName() + "\n(Cancelled)");
            } else {
                output.setText(class1.getDifficultyLevel() + "-" + class1.getName() + "\n" + class1.getDay() + "'s at " + class1.getTimeInterval() + "\nTaught by " + class1.getInstructor().getFullName() + "\n (" + class1.getCapacity() + " spots left)");
            }
            //iv.setImageResource(class1.getCapacity());
        }
        return convertView;
    }
}