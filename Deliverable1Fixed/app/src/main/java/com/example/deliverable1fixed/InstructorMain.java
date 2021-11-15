package com.example.deliverable1fixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.FirebaseApp;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InstructorMain extends AppCompatActivity implements View.OnClickListener {

    private String userID;
    private DatabaseReference reference;

    private Button logout;
    private Button searchClasses;
    private Button teachClass;
    private Button editClasses;
    private Button deleteClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_instructor_main);

        userID = getIntent().getExtras().getString("arg"); // passed from previous page
        reference = FirebaseDatabase.getInstance().getReference("Users");

        logout = (Button) findViewById(R.id.instructorSignOut);
        logout.setOnClickListener(this);

        searchClasses = (Button) findViewById(R.id.instructorSearchClassesBtn);
        searchClasses.setOnClickListener(this);

        teachClass = (Button) findViewById(R.id.instructorTeachClassesBtn);
        teachClass.setOnClickListener(this);

        editClasses = (Button) findViewById(R.id.instructorEditClassesBtn);
        editClasses.setOnClickListener(this);

        deleteClasses = (Button) findViewById(R.id.instructorDeleteClassesBtn);
        deleteClasses.setOnClickListener(this);

        final TextView UsernameWTextView = (TextView) findViewById(R.id.instructorUsername);
        final TextView TypeWTextView = (TextView) findViewById(R.id.instructorUserType);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String username = userProfile.getUsername();
                    String type = userProfile.getType();
                    UsernameWTextView.setText("Username: " + username);
                    TypeWTextView.setText("Type: " + type);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InstructorMain.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.instructorSearchClassesBtn:
                Intent intentSearch = new Intent(InstructorMain.this, InstructorSearchClasses.class);
                intentSearch.putExtra("arg", userID);
                startActivity(intentSearch);
                break;

            case R.id.instructorTeachClassesBtn:
                Intent intentTeach = new Intent(InstructorMain.this, InstructorTeachClass.class);
                intentTeach.putExtra("arg", userID);
                startActivity(intentTeach);
                break;

            case R.id.instructorEditClassesBtn:
                Intent intentEdit = new Intent(InstructorMain.this, InstructorEditClasses.class);
                intentEdit.putExtra("arg", userID);
                startActivity(intentEdit);
                break;
            case R.id.instructorDeleteClassesBtn:
                Intent intentDelete = new Intent(InstructorMain.this, InstructorDeleteClasses.class);
                intentDelete.putExtra("arg", userID);
                startActivity(intentDelete);
                break;

            case R.id.instructorSignOut:
                startActivity(new Intent(InstructorMain.this, FrontScreen.class));
                break;
        }
    }
}