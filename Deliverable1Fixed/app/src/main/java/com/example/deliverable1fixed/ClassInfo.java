package com.example.deliverable1fixed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClassInfo extends AppCompatActivity{
    Class selectedclass1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.cell);
        //getSelectedclass1();
        //setValues();

    }

    private void getSelectedclass1() {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedclass1 = getParsedclass1(parsedStringID);
    }

    private Class getParsedclass1(String parsedID) {
        for (Class class1 : InstructorSearchClasses.classesList)
        {
            if(class1.instructor.getFullName().equals(parsedID))
                return class1;
        }
        return null;
    }

    private void setValues(){
        //TextView tv = (TextView) findViewById(R.id.className);
        //ImageView iv = (ImageView) findViewById(R.id.gymSessImage);

        //tv.setText(selectedclass1.getClassType() + " - " + selectedclass1.getName());
        //iv.setImageResource(selectedclass1.getCapacity());
    }
}