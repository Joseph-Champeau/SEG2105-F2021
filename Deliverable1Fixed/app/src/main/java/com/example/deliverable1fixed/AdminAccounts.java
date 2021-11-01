package com.example.deliverable1fixed;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class AdminAccounts extends AppCompatActivity implements View.OnClickListener{

    private Button homeButton;
    private Button deleteInstructorsButton;
    private Button deleteMembersButton;
    private Hashtable<String, String> users;

    private Spinner instructorsDropdown;
    private Spinner memberDropdown;
    private String selectedMemberEmail;
    private String selectedInstructorEmail;

    private DatabaseReference mDatabaseReference;

    private ArrayList<String> instructorEmails;
    private ArrayList<String> memberEmails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_admin_manage_accounts);

        homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(this);

        deleteInstructorsButton = (Button) findViewById(R.id.deleteInstructorBtn);
        deleteInstructorsButton.setOnClickListener(this);

        deleteMembersButton = (Button) findViewById(R.id.deleteMemberBtn);
        deleteMembersButton.setOnClickListener(this);

        instructorsDropdown = (Spinner) findViewById(R.id.manageInstructorsSpinner);
        memberDropdown = (Spinner) findViewById(R.id.manageMembersSpinner);


        instructorEmails = new ArrayList<>();
        instructorEmails.add(0, "Select instructor");
        memberEmails = new ArrayList<>();
        memberEmails.add(0, "Select member");
        users = new Hashtable<String, String>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String type = snapshot.child("type").getValue(String.class);
                    String uID = snapshot.getKey();
                    String email = snapshot.child("email").getValue(String.class);

                    if (type != null && (type.equals("Instructor") || type.equals("Member")))  {
                        users.put(email, uID);
                    }

                    if (type != null && type.equals("Instructor")) {
                        instructorEmails.add(email);
                    }

                    if (type != null && type.equals("Member")) {
                        memberEmails.add(email);
                    }
                    initializeInstructorDropdown();
                    initializeMemberDropdown();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminAccounts.this, "Something bad", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                startActivity(new Intent(this, AdminMain.class));
                break;
            case R.id.deleteInstructorBtn:
                deleteInstructor();
                break;
            case R.id.deleteMemberBtn:
                deleteMember();
                break;
        }
    }

    public void initializeInstructorDropdown() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, instructorEmails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorsDropdown.setAdapter(adapter);

        instructorsDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select instructor"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedInstructorEmail = item;
                } else {
                    selectedInstructorEmail = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AdminAccounts.this, "Select instructor", Toast.LENGTH_LONG).show();
                selectedInstructorEmail = "";
            }
        });
    }

    public void initializeMemberDropdown() {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, memberEmails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberDropdown.setAdapter(adapter);

        memberDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select member"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedMemberEmail = item;
                } else {
                    selectedMemberEmail = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AdminAccounts.this, "Select member", Toast.LENGTH_LONG).show();
                selectedMemberEmail = "";
            }
        });
    }

    public void deleteInstructor() { // add warning & confirm functionality
        if (!(selectedInstructorEmail.equals(""))) {
            String key = users.get(selectedInstructorEmail);
            if (key != null) {
                FirebaseDatabase.getInstance().getReference("Users").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mDatabaseReference.child(key).removeValue(); // delete from realtime database
                        // need to delete user authentication credentials...this requires Admin SDK which breaks current project. Credentials are deleted manually for now
                        Toast.makeText(AdminAccounts.this, "Instructor deleted", Toast.LENGTH_SHORT).show();
                        finish(); // closes activity
                        startActivity(getIntent()); // reloads activity with updated data
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminAccounts.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
                selectedInstructorEmail=""; // reset selection
            }
        }
    }

    public void deleteMember() { // add warning & confirm functionality
        if (!(selectedMemberEmail.equals(""))) {
            String key = users.get(selectedMemberEmail);
            if (key != null) {
                FirebaseDatabase.getInstance().getReference("Users").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mDatabaseReference.child(key).removeValue(); // delete from realtime database
                        // need to delete user authentication credentials...
                        Toast.makeText(AdminAccounts.this, "Instructor deleted", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminAccounts.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
                selectedMemberEmail=""; // reset selection
            }
        }
    }
}