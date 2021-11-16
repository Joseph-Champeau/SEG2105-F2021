package com.example.deliverable1fixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class representing Register User page
 *  @author Michias Shiferaw, Simon Brunet, Joseph Champeau, Charlie Haldane
 *  @version 2.0
 *  @since 2021-11-17
 */
public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword, editTextUsername;
    private ProgressBar progressBar;

    private ArrayList<String> emails;
    private ArrayList<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        FirebaseApp.initializeApp(this);
        TextView title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(this);

        TextView register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.FullName);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextEmail = (EditText) findViewById(R.id.identifier);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextUsername = (EditText) findViewById(R.id.UserName);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        radioGroup = findViewById(R.id.radioGroup);

        radioButton = findViewById(R.id.userTypeRadBtn);

        emails = new ArrayList<>();
        usernames = new ArrayList<>();
        pullUserData(); // initialize data pull
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.title:
                startActivity(new Intent(RegisterUser.this, FrontScreen.class));
                break;
            case R.id.register:
                registerUser();
                break;
        }
    }

    /** Handles radio button */
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    /** Pulls user data from realtime database */
    private void pullUserData() {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String username = snapshot.child("username").getValue(String.class);
                    usernames.add(username);
                    String email = snapshot.child("email").getValue(String.class);
                    emails.add(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterUser.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    /** Validates the user account registration form fields for Instructors and Members
     * @param age age of the user as a String
     * @param username username of the user as a String
     * @param fullName Full name of the user as a String
     * @param email email of the user as a String
     * @param password password of the user's account as a String
     * @return Boolean true if all registration fields are successfully validated. Boolean false otherwise.*/
    private boolean validateRegistrationFormFields(String email, String password, String fullName, String age, String username) {
        if(fullName.isEmpty()){
            String estring = "Enter a valid name";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextFullName.requestFocus();
            editTextFullName.setError(ssbuilder);

            return false;
        } else if(username.isEmpty()){
            String estring = "Enter a valid username";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextUsername.setError(ssbuilder);
            editTextUsername.requestFocus();

            return false;
        } else if(age.isEmpty()){
            String estring = "Enter a valid age";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextAge.setError(ssbuilder);
            editTextAge.requestFocus();

            return false;
        } else if(email.isEmpty()){
            String estring = "Enter a valid email";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextEmail.setError(ssbuilder);
            editTextEmail.requestFocus();

            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            String estring = "Enter a valid email";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextEmail.setError(ssbuilder);
            editTextEmail.requestFocus();

            return false;
        } else if(password.isEmpty()){
            String estring = "Enter a valid password";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextPassword.setError(ssbuilder);
            editTextPassword.requestFocus();

            return false;
        } else if(password.length() < 6){
            String estring = "Password must be longer than 6";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

            editTextPassword.setError(ssbuilder);
            editTextPassword.requestFocus();

            return false;
        } else {
            return true;
        }
    }

    /** Validates the user account email and username as unique or already existing for Instructors and Members.
     * @param username username of the user as a String
     * @param email email of the user as a String
     * @return Boolean true if email and username do not already exist. Boolean false if either already exists.*/
    private boolean validateRegistrationEmailAndUsername(String email, String username) {
        if (emails != null && usernames != null) { // ensures that there are users in the database
            if (emails.contains(email)) {
                Toast.makeText(RegisterUser.this, "Registration failed: Email -> " + email + " already in use", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return false;
            }
            if (usernames.contains(username)) {
                Toast.makeText(RegisterUser.this, "Registration failed: Username: -> " + username + " taken", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }
        return true;
    }

    /** Registers Instructors and Members. Push to realtime database and starts new activity if successful*/
    private void registerUser() {
        // fetch fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String type = radioButton.getText().toString();

        if(validateRegistrationFormFields(email, password, fullName, age, username)) {
            pullUserData();
            progressBar.setVisibility(View.VISIBLE);
            if(validateRegistrationEmailAndUsername(email, username)) {
                User newUser = new User(fullName, age, email, username, type, password);
                FirebaseDatabase.getInstance().getReference("Users").push().setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterUser.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(RegisterUser.this, FrontScreen.class ));
                    }
                });
            }
        }
    }
}
