package com.example.deliverable1fixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Hashtable;
import java.util.Objects;

public class FrontScreen extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView register;
    private EditText editTextIdentifier, editTextPassword;
    private Button signIn;
    private ProgressBar progressBar;

    private Hashtable<String, String> identifierAuthMap;
    private Hashtable<String, String> identifierUsernameToEmailAuthMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_screen);
        FirebaseApp.initializeApp(this);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextIdentifier = (EditText) findViewById(R.id.identifier);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        identifierAuthMap = new Hashtable<String, String>();
        identifierUsernameToEmailAuthMap = new Hashtable<String, String>();

        pullUserData(false); // instantiate data pull (fixes login delay issue)
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;
        }
    }

    private boolean validateLoginFormFields(String identifier, String password) {
        if(identifier.isEmpty()){
            String estring = "Email/username is required";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(getResources().getColor(R.color.white));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            editTextIdentifier.setError(ssbuilder);
            editTextIdentifier.requestFocus();

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
            String estring = "Password must be longer than 6 characters";
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

    private boolean checkIdentifierType(String identifier) {
        return identifier.contains("@");
    }

    private void pullUserData(boolean identifierType) {
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (identifierType) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String email = snapshot.child("email").getValue(String.class);
                        String password = snapshot.child("password").getValue(String.class);
                        identifierAuthMap.put(email, password);
                    }
                    // Toast.makeText(FrontScreen.this, "Data Received", Toast.LENGTH_SHORT).show();
                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String password = snapshot.child("password").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        identifierAuthMap.put(email, password);
                        identifierUsernameToEmailAuthMap.put(username,email);
                    }
                    // Toast.makeText(FrontScreen.this, "Data Received", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FrontScreen.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateCredentials(String identifier, String password) {
        if (identifierAuthMap == null) {
            Toast.makeText(FrontScreen.this, "Login failed: service unavailable", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!(identifierAuthMap.containsKey(identifier))) {
            Toast.makeText(FrontScreen.this, "Login failed: invalid email/username", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!(Objects.equals(identifierAuthMap.get(identifier), password))) {
            Toast.makeText(FrontScreen.this, "Login failed: invalid password", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    private boolean matchUsernameToEmail(String username) {
        if (identifierUsernameToEmailAuthMap == null) {
            Toast.makeText(FrontScreen.this, "Login failed: service unavailable", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else if (!(identifierUsernameToEmailAuthMap.containsKey(username))) {
            Toast.makeText(FrontScreen.this, "Login failed: invalid email/username", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    private void initiateUserSession(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(FrontScreen.this, HomeScreen.class));
                } else {
                    Toast.makeText(FrontScreen.this, "Login failed: service unavailable", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void userLogin() {
        String identifier = editTextIdentifier.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(validateLoginFormFields(identifier, password)) {
            progressBar.setVisibility(View.VISIBLE);
            boolean type = checkIdentifierType(identifier);
            pullUserData(type);
            if(type) {
                if (validateCredentials(identifier, password)) {
                    initiateUserSession(identifier, password);
                }
            } else {
                if (matchUsernameToEmail(identifier)) {
                    String email = identifierUsernameToEmailAuthMap.get(identifier);
                    if (validateCredentials(email, password)) {
                        initiateUserSession(email, password);
                    }
                }
            }
        }
    }

}
