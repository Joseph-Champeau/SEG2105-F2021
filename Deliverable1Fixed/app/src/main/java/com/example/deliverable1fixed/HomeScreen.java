package com.example.deliverable1fixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity {
    public String userID;
    private DatabaseReference reference;

    private Button logout;
    private Button admin;
    private Button viewClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_home_screen);

        admin = (Button) findViewById(R.id.adminButton); // viewed by admins only
        viewClasses = (Button) findViewById(R.id.viewClasses); // viewed by all atm
        logout = (Button) findViewById(R.id.signOut);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeScreen.this, FrontScreen.class));
            }
        });

        userID = getIntent().getStringExtra("arg"); // passed from previous page
        reference = FirebaseDatabase.getInstance().getReference("Users");

        final TextView UsernameWTextView = (TextView) findViewById(R.id.UsernameW);
        final TextView TypeWTextView = (TextView) findViewById(R.id.TypeW);
        final TextView EmailWTextView = (TextView) findViewById(R.id.emailW);
        final TextView NameWTextView = (TextView) findViewById(R.id.nameW);
        final TextView AgeWTextView = (TextView) findViewById(R.id.ageW);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String username = userProfile.username;
                    String type = userProfile.type;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    String name = userProfile.fullName;

                    UsernameWTextView.setText("Username: " + username);
                    TypeWTextView.setText("Type: " + type);
                    EmailWTextView.setText("Email: " + email);
                    NameWTextView.setText("Name: "+ name);
                    AgeWTextView.setText("Age: "+ age);
                    if (userProfile.type != null) {
                        if (userProfile.type.equals("Member") || userProfile.type.equals("Instructor")) { // if user is not Admin type
                            admin.setVisibility(View.GONE); // hide admin button from view
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeScreen.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);
                        String type = userProfile.type;

                        if(type.equals("Admin")) {
                            Intent intentClasses = new Intent(HomeScreen.this, AdminMain.class);
                            intentClasses.putExtra("arg", userID);
                            startActivity(intentClasses);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomeScreen.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        viewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);
                        String type = userProfile.type;

                        if(type.equals("Instructor")) {
                            Intent intentClasses = new Intent(HomeScreen.this, InstructorMain.class);
                            intentClasses.putExtra("arg", userID);
                            startActivity(intentClasses);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomeScreen.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
