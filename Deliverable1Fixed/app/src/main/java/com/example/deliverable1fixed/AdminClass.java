package com.example.deliverable1fixed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class AdminClass extends AppCompatActivity implements View.OnClickListener {

    private Button homeButton;

    private Button createButton;

    private Button deleteButton;
    private Spinner deleteSpinner;
    private String selectedClassForDeletion;

    private Button editButton;
    private Spinner editSpinner;
    private String selectedClassForEditing;

    private Hashtable<String, String> classesMap;

    private DatabaseReference mDatabaseReference;

    private ArrayList<String> classesForDeletion;
    private ArrayList<String> classesForEditing;

    private EditText editTextCreateName, editTextCreateDesc, editTextEditName, editTextEditDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_admin_manage_classes);

        homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(this);

        createButton = (Button) findViewById(R.id.createClassBtn);
        homeButton.setOnClickListener(this);

        deleteButton = (Button) findViewById(R.id.deleteClassBtn);
        homeButton.setOnClickListener(this);

        editButton = (Button) findViewById(R.id.editClassBtn);
        homeButton.setOnClickListener(this);

        editTextCreateName = (EditText) findViewById(R.id.text_create_class_name_field);
        editTextCreateDesc = (EditText) findViewById(R.id.text_create_class_description_field);
        editTextEditName = (EditText) findViewById(R.id.text_edit_class_name_field);
        editTextEditDesc = (EditText) findViewById(R.id.text_edit_class_description_field);

        deleteSpinner = (Spinner) findViewById(R.id.deleteClassSpinner); // need to add endless scroll functionality
        editSpinner = (Spinner) findViewById(R.id.editClassSpinner); // need to add endless scroll functionality

        classesMap = new Hashtable<String, String>();

        classesForDeletion = new ArrayList<>();
        classesForDeletion.add(0, "Select class");

        classesForEditing = new ArrayList<>();
        classesForEditing.add(0, "Select class");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Classes");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String uID = snapshot.getKey();
                    classesForDeletion.add(name);
                    classesForEditing.add(name);
                    classesMap.put(name, uID);
                    initializeDeleteClassDropdown();
                    initializeEditClassDropdown();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminClass.this, "Something bad", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initializeDeleteClassDropdown() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, classesForDeletion);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteSpinner.setAdapter(adapter);

        deleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select class"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedClassForDeletion = item;
                } else {
                    selectedClassForDeletion = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AdminClass.this, "Select class", Toast.LENGTH_LONG).show();
                selectedClassForDeletion = "";
            }
        });
    }

    public void initializeEditClassDropdown() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, classesForEditing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinner.setAdapter(adapter);

        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select class"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedClassForEditing = item;
                } else {
                    selectedClassForEditing = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AdminClass.this, "Select class", Toast.LENGTH_LONG).show();
                selectedClassForEditing = "";
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(this, AdminMain.class));
                break;
            case R.id.createClassBtn:
                createClass();
                break;
            case R.id.deleteClassBtn:
                deleteClass();
                break;
            case R.id.editClassBtn:
                editClass();
                break;
        }
    }

    public void createClass() {

        String name = editTextCreateName.getText().toString().trim();
        if(name.isEmpty()){
            String estring = "Enter a valid name";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            editTextCreateName.requestFocus();
            editTextCreateName.setError(ssbuilder);
            return;
        }

        String description = editTextCreateDesc.getText().toString().trim();
        if(description.isEmpty()) {
            String estring = "Enter a valid description";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            editTextCreateDesc.setError(ssbuilder);
            editTextCreateDesc.requestFocus();
            return;
        }
        ClassType newClass = new ClassType(name, description);
        FirebaseDatabase.getInstance().getReference().child("Classes").push().setValue(newClass);
        Toast.makeText(AdminClass.this, "Class created", Toast.LENGTH_SHORT).show();
        finish(); // closes activity
        startActivity(getIntent()); // reloads activity with updated data
    }

    public void editClass() {
        String name = editTextEditName.getText().toString().trim();
        if(name.isEmpty()){
            String estring = "Enter a valid name";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            editTextEditName.requestFocus();
            editTextEditName.setError(ssbuilder);
            return;
        }

        String description = editTextEditDesc.getText().toString().trim();
        if(description.isEmpty()) {
            String estring = "Enter a valid description";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            editTextEditDesc.setError(ssbuilder);
            editTextEditDesc.requestFocus();
            return;
        }

        if (!(selectedClassForEditing.equals(""))) {
            String key = classesMap.get(selectedClassForEditing);
            if (key != null) {
                FirebaseDatabase.getInstance().getReference("Classes").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mDatabaseReference.child(key).child("name").setValue(name); // set new name
                        mDatabaseReference.child(key).child("description").setValue(description); // set new description
                        Toast.makeText(AdminClass.this, "Class updated", Toast.LENGTH_SHORT).show();
                        finish(); // closes activity
                        startActivity(getIntent()); // reloads activity with updated data
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminClass.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });
                selectedClassForEditing = ""; // reset selection
            }
        }


    }

    public void deleteClass() {
        if (!(selectedClassForDeletion.equals(""))) {
            String key = classesMap.get(selectedClassForDeletion);
            if (key != null) {
                FirebaseDatabase.getInstance().getReference("Classes").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mDatabaseReference.child(key).removeValue(); // delete from realtime database
                        Toast.makeText(AdminClass.this, "Class deleted", Toast.LENGTH_SHORT).show();
                        finish(); // closes activity
                        startActivity(getIntent()); // reloads activity with updated data
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminClass.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });
                selectedClassForEditing = ""; // reset selection
            }
        }
    }
}