package com.example.Deliverable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminMain extends AppCompatActivity implements View.OnClickListener{

    private Button classes;
    private Button account;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        classes = (Button) findViewById(R.id.classes);
        classes.setOnClickListener(this);

        account = (Button) findViewById(R.id.accounts);
        account.setOnClickListener(this);

        back = (Button) findViewById(R.id.signOut);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classes:
                startActivity(new Intent(this, Class.class));
                break;

            case R.id.accounts:
                startActivity(new Intent(this, Admin.class));
                break;

            case R.id.signOut:
                startActivity(new Intent(this, Main.class));
                break;
        }
        }

    }
