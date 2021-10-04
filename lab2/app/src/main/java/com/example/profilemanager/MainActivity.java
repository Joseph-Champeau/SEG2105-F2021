package com.example.profilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button opengooglebtn;
    private TextView teamAddress;
    //TextView computationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opengooglebtn = (Button) findViewById(R.id.opengooglebtn);
    }
    public void OnOpenInGoogleMaps(View view){
            EditText teamAddress= (EditText) findViewById(R.id.teamAddressTextView);

            //Create a Uri from a string. Use the result to create an intent

            Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" +teamAddress.getText());

            //Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            //Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage ("com.google.android.apps.maps");

            //Attempt to start the acitivty that can handle the Intent
            startActivity(mapIntent);

    }
}