package com.example.deliverable1fixed;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
/**
 * A class representing the 'deleting' classes
 *  @author Michias Shiferaw, Simon Brunet, Joseph Champeau, Charlie Haldane
 *  @version 2.0
 *  @since 2021-11-17
 */
public class InstructorDeleteClasses extends AppCompatActivity implements View.OnClickListener{

    private String userID;
    private User user;

    private DatabaseReference referenceClasses;

    private Spinner classesSpinner;
    private String selectedClass;
    private ArrayList<String> classesList;

    private Hashtable<String, String> classesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_instructor_delete_classes);

        userID = getIntent().getExtras().getString("arg"); // passed from previous page
        referenceClasses = FirebaseDatabase.getInstance().getReference("Classes");

        DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        referenceUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InstructorDeleteClasses.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });

        classesSpinner = (Spinner) findViewById(R.id.deleteClassToTeachSpinner);
        classesList = new ArrayList<>();
        classesList.add(0, "Select a class to delete");

        classesMap = new Hashtable<String, String>();

        pullClassesData();
        initializeDeleteSpinnerDropdown();
    }

    private void pullClassesData() {
        referenceClasses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Class classObject = snapshot.getValue(Class.class);
                    if(classObject != null) {
                        User instructor = classObject.instructor;
                        if (instructor.getUsername().equals(user.getUsername())) {
                            String uID = snapshot.getKey();
                            String classDescription = classObject.name + "-" + classObject.day + "'s : " + classObject.timeInterval;
                            if (!(classesList.contains(classDescription))) {
                                classesList.add(classDescription);
                                classesMap.put(classDescription, uID);
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InstructorDeleteClasses.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeDeleteSpinnerDropdown() {
        ArrayAdapter<String> classesAdapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classesList);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classesSpinner.setAdapter(classesAdapter);
        classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select a class to delete"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedClass = item;
                } else {
                    selectedClass = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(InstructorDeleteClasses.this, "Select a class to delete", Toast.LENGTH_LONG).show();
                selectedClass = "";
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteClassToTeachBtn:
                deleteClass();
                break;
            case R.id.homeBtn:
                Intent intentView = new Intent(InstructorDeleteClasses.this, InstructorMain.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
        }
    }

    private void deleteClass() {
        if (selectedClass.equals("")) {
            Toast.makeText(InstructorDeleteClasses.this, "Select a class to delete", Toast.LENGTH_SHORT).show();
        } else {
            String key = classesMap.get(selectedClass);
            if (key != null) {
                referenceClasses.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        referenceClasses.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(InstructorDeleteClasses.this, "Class deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        classesList.clear();
                        classesList.add(0, "Select a class to delete");
                        classesMap.clear();
                        pullClassesData();
                        initializeDeleteSpinnerDropdown();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(InstructorDeleteClasses.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}

