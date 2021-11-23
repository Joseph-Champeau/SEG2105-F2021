package com.example.deliverable1fixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class InstructorViewClassMembers extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference referenceClasses;
    private String userID;
    private User user;

    private Spinner classesSpinner;
    private ArrayList<String> classesList;
    private String selectedClass;

    private ListView listView;

    private Hashtable<String, ArrayList<User>> membersMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_instructor_view_class_members);

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
                Toast.makeText(InstructorViewClassMembers.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });

        classesSpinner = (Spinner) findViewById(R.id.cmSpinner);
        classesList = new ArrayList<String>();
        classesList.add(0, "Select a class");

        membersMap = new Hashtable<String, ArrayList<User>>();

        listView = (ListView) findViewById(R.id.classMembersListView);
        listView.setVisibility(View.GONE);

        pullClassesData();
        initializeClassesSpinnerDropdown();
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.viewClassMembersBtn:
                viewClassMembers();
                break;
            case R.id.homeBtn:
                Intent intentView = new Intent(InstructorViewClassMembers.this, InstructorMain.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
        }
    }


    /** Pulls Classes data from realtime database */
    private void pullClassesData() {
        referenceClasses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Class classObject = snapshot.getValue(Class.class);
                    if(classObject != null) {
                        String testIfCancelled = classObject.day;
                        User instructor = classObject.instructor;
                        if (testIfCancelled.equals("N/A")) {
                            if (instructor.getUsername().equals(user.getUsername())) {
                                String classDescription = classObject.name + " - " + "(cancelled)";
                                if (!(classesList.contains(classDescription)) && classObject.members != null) { // delete second condition once all classes have been created with Members as a parameter
                                    classesList.add(classDescription);
                                    membersMap.put(classDescription, classObject.members);
                                }
                            }
                        } else {
                            if (instructor.getUsername().equals(user.getUsername())) {
                                String classDescription = classObject.name + " - " + classObject.day + "'s : " + classObject.timeInterval;
                                if (!(classesList.contains(classDescription)) && classObject.members != null) { // delete second condition once all classes have been created with Members as a parameter
                                    classesList.add(classDescription);
                                    membersMap.put(classDescription, classObject.members);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InstructorViewClassMembers.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    /** Initializes classes spinner dropdown adapter. And instantiates OnClick item listener. */
    private void initializeClassesSpinnerDropdown() {
        ArrayAdapter<String> classesAdapter =
                new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classesList);
        classesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classesSpinner.setAdapter(classesAdapter);
        classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(parent.getItemAtPosition(position).equals("Select a class"))) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                    selectedClass = item;
                } else {
                    selectedClass = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(InstructorViewClassMembers.this, "Select a class to view its members", Toast.LENGTH_LONG).show();
                selectedClass = "";
            }
        });
    }

    /** Initializes the members list of the selected last for the ListView ArrayAdapter.
     * @param classSelected the class selected in the Spinner dropdown
     * @return ArrayList<String</> of member descriptions for the ListView. */
    private ArrayList<String> pullMembersData(String classSelected) {
        ArrayList<String> membersDesc = new ArrayList<String>();
        ArrayList<User> members = membersMap.get(classSelected);
        if (members != null) {
            for (User member : members) {
                if (member.getType().equals("Member"))
                membersDesc.add(member.getFullName() + " - " + member.getEmail());
            }
        }
        return membersDesc;
    }

    /** Instantiates the ListView holding the list of members of the selected class. */
    private void setUpList(ArrayList<String> classMembers) {
        if (!(classMembers.isEmpty())) {
            ArrayAdapter<String> listViewAdapter =
                    new ArrayAdapter<String>(InstructorViewClassMembers.this, android.R.layout.simple_list_item_1, classMembers);
            listView.setAdapter(listViewAdapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(InstructorViewClassMembers.this, "This class currently has no enrolled members", Toast.LENGTH_LONG).show();
            listView.setVisibility(View.GONE);
        }
    }

    /** Displays ListView of members enrolled in the selected class. */
    private void viewClassMembers() {
        if (!(selectedClass.equals(""))) {
            setUpList(pullMembersData(selectedClass));
        } else {
            Toast.makeText(InstructorViewClassMembers.this, "Please select a class to view its members", Toast.LENGTH_LONG).show();
            listView.setVisibility(View.GONE);
        }
    }

}
