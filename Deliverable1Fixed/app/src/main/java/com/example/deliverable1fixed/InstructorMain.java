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
    private Button viewClasses;
    private Button teachClass;
    private Button manageClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_instructor_main);

        userID = getIntent().getExtras().getString("arg"); // passed from previous page
        reference = FirebaseDatabase.getInstance().getReference("Users");

        logout = (Button) findViewById(R.id.instructorSignOut);
        logout.setOnClickListener(this);

        viewClasses = (Button) findViewById(R.id.instructorViewClassesBtn);
        viewClasses.setOnClickListener(this);

        teachClass = (Button) findViewById(R.id.instructorTeachClassesBtn);
        teachClass.setOnClickListener(this);

        manageClasses = (Button) findViewById(R.id.instructorManageClassesBtn);
        manageClasses.setOnClickListener(this);

        final TextView UsernameWTextView = (TextView) findViewById(R.id.instructorUsername);
        final TextView TypeWTextView = (TextView) findViewById(R.id.instructorUserType);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String username = userProfile.username;
                    String type = userProfile.type;
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
            case R.id.instructorViewClassesBtn:
                Intent intentView = new Intent(InstructorMain.this, AdminClass.class);
                intentView.putExtra("arg", userID);
                startActivity(intentView);
                break;

            case R.id.instructorTeachClassesBtn:
                Intent intentTeach = new Intent(InstructorMain.this, InstructorTeachClass.class);
                intentTeach.putExtra("arg", userID);
                startActivity(intentTeach);
                break;

            case R.id.instructorManageClassesBtn:
                Intent intentManage = new Intent(InstructorMain.this, AdminAccounts.class);
                intentManage.putExtra("arg", userID);
                startActivity(intentManage);
                break;

            case R.id.instructorSignOut:
                startActivity(new Intent(InstructorMain.this, FrontScreen.class));
                break;
        }
    }
}
