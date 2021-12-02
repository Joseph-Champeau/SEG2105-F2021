package com.example.deliverable1fixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

public class MemberViewClass extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference referenceClasses;
    private String userID;
    private User user;

    private Spinner classesSpinner;
    private ArrayList<String> classesList;
    private String selectedClass;

    //private ListView listView;
    private TextView details;

    private Hashtable<String, Class> classesMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_member_view_class);

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
                Toast.makeText(MemberViewClass.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });

        classesSpinner = (Spinner) findViewById(R.id.mycmSpinner);
        classesList = new ArrayList<String>();
        classesList.add(0, "Select a class");
        classesMap = new Hashtable<String, Class>();
        details = (TextView) findViewById(R.id.classDetails);
        pullClassesData();
        initializeClassesSpinnerDropdown();
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.viewClassDetailBtn:
                viewClassDetails();
                break;
            case R.id.homeBtn:
                Intent intentView = new Intent(MemberViewClass.this, MemberMain.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;
            case R.id.myenrollmentbtn :
                enroll();
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
                    ArrayList<Class> myClasses = user.getMyClasses();
                    if(classObject != null && myClasses != null) {
                        String testIfCancelled = classObject.day;
                        if (!(testIfCancelled.equals("N/A"))) {
                            if (!(filterOutAlreadyEnrolledClasses(myClasses, classObject))) {
                                String uID = snapshot.getKey();
                                String classDescription = classObject.name + " - " + classObject.day + "'s : " + classObject.timeInterval;
                                if (!(classesList.contains(classDescription))) {
                                    classesList.add(classDescription);
                                    classesMap.put(classDescription, classObject);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MemberViewClass.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    /** Filters out classes that the user is already enrolled in. Prevents enrolling duplication
     * @param enrolledClasses List of classes the user is already enrolled in.
     * @param c Class selected from database to check.
     * @return Boolean true if user is already enrolled in selected Class c. False otherwise. */
    public boolean filterOutAlreadyEnrolledClasses(ArrayList<Class> enrolledClasses, Class c) {
        for (Class v : enrolledClasses) {
            if (v != null) {
                if (v.name.equals(c.name)) {
                    return true;
                }
            }
        }
        return false;
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
                    details.setVisibility(View.GONE);
                    selectedClass = item;
                } else {
                    selectedClass = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MemberViewClass.this, "Select a class to view its members", Toast.LENGTH_LONG).show();
                selectedClass = "";
            }
        });
    }

    /** Initializes the members list of the selected last for the ListView ArrayAdapter.
     * @param classSelected the class selected in the Spinner dropdown
     * @return ArrayList<String</> of member descriptions for the ListView. */
    private void pullClassData(String classSelected) {
        ArrayList<String> membersDesc = new ArrayList<String>();
        String output;
        Class  key = classesMap.get(classSelected);
        if (key != null) {
            if (key.getInstructor()==(null)){
                details.setText(key.getDifficultyLevel() + "-" + key.getName() + "\n" + key.getDay() + "'s at " + key.getTimeInterval() + "\n (" + key.getCapacity() + " spots left)");
            } else {
                details.setText(key.getDifficultyLevel() + "-" + key.getName() + "\n" + key.getDay() + "'s at " + key.getTimeInterval() + "\nTaught by " + key.getInstructor().getFullName() + "\n (" + key.getCapacity() + " spots left)");
                details.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(MemberViewClass.this, "This class currently has no published details", Toast.LENGTH_LONG).show();
            details.setVisibility(View.GONE);

        }
       // return membersDesc;
    }
    /** Displays ListView of members enrolled in the selected class. */
    private void viewClassDetails() {
        if (!(selectedClass.equals(""))) {
            pullClassData(selectedClass);
            //setUpList(pullMembersData(selectedClass));
        } else {
            Toast.makeText(MemberViewClass.this, "Please select a class to view its description", Toast.LENGTH_LONG).show();
            details.setVisibility(View.GONE);
        }
    }

    /** Displays ListView of members enrolled in the selected class. */
    private void enroll() {
        if (!(selectedClass.equals(""))) {
            enrolling(selectedClass);
            //setUpList(pullMembersData(selectedClass));
        } else {
            Toast.makeText(MemberViewClass.this, "Please select a class to view its description", Toast.LENGTH_LONG).show();
            details.setVisibility(View.GONE);
        }
    }

    private void enrolling(String selectedClass1) {
        if (!(selectedClass1.equals(""))) {



            // ADD CLASS ENROLLMENT VERIFICATION HERE (CAPACITY AND TIME INTERVAL CONDITIONS)



            Class key = classesMap.get(selectedClass1);
            user.addClass(key);
            Toast.makeText(MemberViewClass.this, "New Class Added", Toast.LENGTH_LONG).show();
            DatabaseReference referenceUsers = FirebaseDatabase.getInstance().getReference("Users").child(userID);
            referenceUsers.child("myClasses").child(String.valueOf(user.getMyClasses().size() - 1)).setValue(key);

            // resets key page elements and hides previously shown info
            classesList.clear();
            classesList.add(0, "Select a class");
            classesMap.clear();
            details.setVisibility(View.GONE);
            pullClassesData();
            initializeClassesSpinnerDropdown();
        } else {
            Toast.makeText(MemberViewClass.this, "The attempt to enroll in a class was denied", Toast.LENGTH_LONG).show();
            details.setVisibility(View.GONE);
        }
    }
}
